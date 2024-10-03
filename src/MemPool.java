import java.nio.ByteBuffer;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.*;

/**
 * This is the memory pool, it holds an array of bytes which acts as the
 * memory pool and can be used to store any bytes of data. It also holds a
 * list of free blocks which are the blocks in the memory which are open to
 * store data in.
 * 
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemPool implements MemPoolInterface {

    private DLList<FreeBlock> list = new DLList<>();
    private byte[] pool;
    private int initSize;

    /**
     * Constructor to initialize all the list of free blocks, initialize
     * the byte array and the initial size of the memory pool.
     */
    public MemPool(int size) {
        initSize = size;
        pool = new byte[initSize];
        list.add(new FreeBlock(0, initSize - 1));
    }

    /**
     * Find a spot in the memory pool for the given size.
     */
    public MemHandle findSpot(short size) {
    int MH = 0;
    if (list.isEmpty()) {
        return new MemHandle(pool.length);
    } else {
        int index = 0;
        int lowLen = Integer.MAX_VALUE;
        FreeBlock fb;
        int fbLen = 0;

        for (int i = 0; i < list.size(); i++) {
            fb = list.get(i);
            if (fb != null) { // Check if fb is not null
                fbLen = fb.getEnd() - fb.getStart() + 1;
                if (fbLen >= (size + 2) && fbLen < lowLen) {
                    index = i;
                    lowLen = fbLen;
                }
            }
        }

        DLList.Node<FreeBlock> tail = list.getTail();

	DLList.Node<FreeBlock> previousNode = (tail != null) ? tail.previous() : null;
	FreeBlock lastBlock = (previousNode != null) ? previousNode.getData() : null;

        if (lowLen == Integer.MAX_VALUE) {
            if (lastBlock != null && lastBlock.getEnd() < pool.length - 1) {
                return new MemHandle(pool.length);
            } else if (lastBlock != null) {
                return new MemHandle(lastBlock.getStart());
            }
            return new MemHandle(pool.length);
        } else {
            FreeBlock currentBlock = list.get(index);
            if (currentBlock != null) {
                MH = currentBlock.getStart();
            } else {
                MH = pool.length; // fallback value
            }
        }
        return new MemHandle(MH);
    }
}


    /**
     * Insert bytes into the memory pool.
     */
	@Override
	public MemHandle insert(byte[] space, short size) {
	    ByteBuffer bb = ByteBuffer.wrap(pool);
	    int MH = 0;

	    if (list.isEmpty()) {
		bb = expandSize();
		bb.position(pool.length - initSize);
		bb.putShort(size);
		bb.put(space);
		list.add(new FreeBlock((pool.length - initSize) + size + 2, pool.length - 1));
		return new MemHandle(pool.length - initSize);
	    } else {
		Iterator<FreeBlock> itr = list.iterator();
		int index = 0;
		int lowLen = Integer.MAX_VALUE;
		FreeBlock fb;
		int fbLen = 0;

		for (int i = 0; i < list.size(); i++) {
		    fb = list.get(i);
		    if (fb != null) {
		        fbLen = fb.getEnd() - fb.getStart() + 1;
		        if (fbLen >= (size + 2) && fbLen < lowLen) {
		            index = i;
		            lowLen = fbLen;
		        }
		    }
		}

		DLList.Node<FreeBlock> tail = list.getTail();
	DLList.Node<FreeBlock> previousNode = (tail != null) ? tail.previous() : null;
	FreeBlock lastBlock = (previousNode != null) ? previousNode.getData() : null;


		if (lowLen == Integer.MAX_VALUE) {
		    if (lastBlock != null && lastBlock.getEnd() < pool.length - 1) {
		        list.add(new FreeBlock(pool.length, pool.length + initSize - 1));
		    } else if (lastBlock != null) {
		        lastBlock.setEnd(pool.length + initSize - 1);
		    }
		    bb = expandSize();
		    return this.insert(space, size);
		} else {
		    FreeBlock currentBlock = list.get(index);
		    if (currentBlock != null) {
		        bb.position(currentBlock.getStart());
		        MH = bb.position();
		        bb.putShort(size);
		        bb.put(space);
		        currentBlock.setStart(currentBlock.getStart() + size + 2);
		    }
		}

		// Check if the current block is empty and remove if so
		FreeBlock currentBlock = list.get(index);
		if (currentBlock != null && currentBlock.getEnd() == currentBlock.getStart() - 1) {
		    list.remove(index);
		}

		return new MemHandle(MH);
	    }
	}


    /**
     * Expand the size of the memory pool when needed.
     */
    private ByteBuffer expandSize() {
        byte[] newPool = new byte[pool.length + initSize];
        System.arraycopy(pool, 0, newPool, 0, pool.length);
        pool = newPool;
        return ByteBuffer.wrap(pool);
    }

    /**
     * Remove a memory handle.
     */
	@Override
	public void remove(MemHandle theHandle) {
	    ByteBuffer bb = ByteBuffer.wrap(pool);
	    bb.position(theHandle.getStart());
	    short size = bb.getShort();
	    FreeBlock newFBlock = new FreeBlock(theHandle.getStart(), theHandle.getStart() + 1 + size);

	    if (list.isEmpty()) {
		list.add(newFBlock);
	    } else {
		FreeBlock firstBlock = list.get(0);
		if (firstBlock != null && newFBlock.getEnd() <= firstBlock.getStart()) {
		    list.add(0, newFBlock);
		    merge(0);
		} else {
		    DLList.Node<FreeBlock> tail = list.getTail();
		    DLList.Node<FreeBlock> previousNode = (tail != null) ? tail.previous() : null;
		    FreeBlock previousFreeBlock = (previousNode != null) ? previousNode.getData() : null;

		    if (previousFreeBlock != null && newFBlock.getStart() >= previousFreeBlock.getEnd()) {
		        list.add(newFBlock);
		        merge(list.size() - 1);
		        return;
		    }

		    for (int i = 1; i < list.size(); i++) {
		        FreeBlock curr = list.get(i);
		        FreeBlock prev = list.get(i - 1);
		        if (curr != null && prev != null && newFBlock.getStart() >= prev.getEnd() && newFBlock.getEnd() <= curr.getStart()) {
		            list.add(i, newFBlock);
		            merge(i);
		            break;
		        }
		    }
		}
	    }
	}


    /**
     * Merge adjacent free blocks.
     */
	private void merge(int index) {
	    if (index == 0 && list.size() > 1) {
		FreeBlock firstBlock = list.get(0);
		FreeBlock secondBlock = list.get(1);
		if (firstBlock != null && secondBlock != null && 
		    firstBlock.getEnd() >= secondBlock.getStart() - 1) {
		    firstBlock.setEnd(secondBlock.getEnd());
		    list.remove(1);
		}
	    } else if (index > 0 && index < list.size()) {
		FreeBlock currentBlock = list.get(index);
		FreeBlock previousBlock = list.get(index - 1);
		if (currentBlock != null && previousBlock != null && 
		    currentBlock.getStart() <= previousBlock.getEnd() + 1) {
		    previousBlock.setEnd(currentBlock.getEnd());
		    list.remove(index);
		    merge(index - 1); // check previous block again
		}
	    }
	}


    /**
     * Retrieve a string from a memory handle.
     */
    @Override
    public String get(MemHandle theHandle) {
        ByteBuffer bb = ByteBuffer.wrap(pool);
        bb.position(theHandle.getStart());
        short size = bb.getShort();
        byte[] arr = new byte[size];
        bb.get(arr);
        return new String(arr, 0, size);
    }

    /**
     * Dump the current state of the memory pool.
     */
@Override
public void dump() {
    if (list.isEmpty()) {
        System.out.print("(" + pool.length + ",0)");
    } else {
        for (int i = 0; i < list.size(); i++) {
            FreeBlock fb = list.get(i);
            if (fb != null) { // Check if fb is not null
                System.out.print("(" + fb.getStart() + "," + (fb.getEnd() - fb.getStart() + 1) + ")");
                if (i != list.size() - 1) {
                    System.out.print(" -> ");
                }
            } else {
                // Handle the case where fb is null, if necessary
                System.out.print("(null)");
            }
        }
    }
    System.out.println();
}


    /**
     * Used for testing, to see what the pool looks like.
     */
    protected void printOut() {
        System.out.println(new String(pool));
    }
}

