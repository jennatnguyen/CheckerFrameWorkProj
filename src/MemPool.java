import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * This is the memory pool, it holds a array of bytes which acts as the
 * memory pool and can be used to store any bytes of data. It also holds a
 * list of free blocks which are the blocks in the memory which are open to
 * store data in
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemPool implements MemPoolInterface {

    private DLList<FreeBlock> list = new DLList<FreeBlock>();
    private byte[] pool;
    private int initSize;

    /**
     * Constructor to initialize all the list of free blocks, initialize
     * the byte array and the initital size of the memory pool
     */
    public MemPool(int size)
    {
        initSize = size;
        pool = new byte[initSize];
        // TODO Auto-generated constructor stub
        list.add(new FreeBlock(0, initSize - 1));
    }

    /**
     * Getter method for the list of free block
     * 
     * @return list that contains the free block
     */
    public byte[] getPool()
    {
        return pool;
    }

    /**
     * This method inserts an array of bytes into the memory pool based on
     * the best fit block of memory available. After it has inserted the
     * bytes of data into the memory pool, this block updates the free
     * blocks list and then creates a memory handle which can be returned
     * so that it can be inserted into a hashtable
     * 
     * @param space
     * @param size
     * @return MemHandle
     */
    @Override
    public MemHandle insert(byte[] space, short size)
    {
        ByteBuffer bb = ByteBuffer.wrap(pool);
        int MH = 0;
        if (list.isEmpty())
        {
            // we need to expand the pool and start writing at the end of
            // it
            bb = expandSize();
            bb.position(pool.length - initSize); // takes us to the end of
                                                 // the pool
            // bb.position(0);
            bb.putShort(size);
            bb.put(space);
            list.add(new FreeBlock((pool.length - initSize) + size + 2,
                    pool.length - 1));
            // list.add(new FreeBlock(size + 2, pool.length));
            // return new MemHandle(0);
            return new MemHandle(pool.length - initSize);
        }
        else
        {
            /*
             * Search through all the free blocks, check for the smallest
             * which can house the string add the string in and adjust the
             * free block which houses it. check if the freeblock is empty,
             * if so, free it and adjust the list get the memory handle
             * with the starting position of the inserted string
             */
            Iterator<FreeBlock> itr = list.iterator();
            int index = 0;
            int lowLen = Integer.MAX_VALUE; // store a reference to this
                                            // cause using .get() on the
                                            // index is slow ash
            FreeBlock fb;
            int fbLen = 0;
            for (int i = 0; i < list.size(); i++)
            {
                fb = list.get(i);
                fbLen = fb.getEnd() - fb.getStart() + 1;// starts at the
                                                        // first usable,
                                                        // and ends at last
                                                        // usable spot, so
                                                        // size is
                                                        // difference + 1
                if (fbLen >= (size + 2) && fbLen < lowLen)
                {
                    index = i;
                    lowLen = fbLen;
                }
            }
            if (lowLen == Integer.MAX_VALUE)
            {
                // this means none of the freeblocks can store this song
                // there is the rare possibility that this new string is
                // bigger
                // than initSize in which case this code throws an
                // exception but that's a
                // problem for later me to deal with
                if (list.getTail().previous().getData()
                        .getEnd() < pool.length - 1)
                {
                    list.add(new FreeBlock(pool.length,
                            pool.length + initSize - 1));
                }
                else
                {
                    list.getTail().previous().getData()
                            .setEnd(pool.length + initSize - 1);
                }
                bb = expandSize();
                // use above to expand the size of the pool
                // System.out.println("Memory pool expanded to be " +
                // pool.length + " bytes.");
                return (this.insert(space, size));
            }
            else
            {
                bb.position(list.get(index).getStart());
                MH = bb.position();
                bb.putShort(size);
                bb.put(space);
                list.get(index)
                        .setStart(list.get(index).getStart() + size + 2);
            }
            if (list.get(index).getEnd() == list.get(index).getStart() - 1) // if
                                                                            // they're
                                                                            // equal,
                                                                            // that
                                                                            // one
                                                                            // spot
                                                                            // can
                                                                            // be
                                                                            // used
            {
                list.remove(index);
            }

            return new MemHandle(MH);
        }
    }

    /**
     * This is a private method used to expand the size of the memory pool
     * when needed
     * 
     * @return bytebuffer around the new pool
     */
    private ByteBuffer expandSize()
    {
        ByteBuffer bb;
        byte[] newPool = new byte[pool.length + initSize];
        System.arraycopy(pool, 0, newPool, 0, pool.length);
        pool = newPool;
        bb = ByteBuffer.wrap(pool);
        return bb;
    }

    /**
     * This method takes a memory handle, creates a free block list and
     * performs a merge on that free block. It doesn't delete the data from
     * the memory pool but it does free that part of the pool.
     * 
     * @param theHandle
     */
    @Override
    public void remove(MemHandle theHandle)
    {
        // delete below variable later
        String currPool = new String(pool);
        ByteBuffer bb = ByteBuffer.wrap(pool);
        bb.position(theHandle.getStart());
        short size = bb.getShort();
        FreeBlock newFBlock = new FreeBlock(theHandle.getStart(),
                theHandle.getStart() + 1 + size); // this has to be one
        // it might just be the 1 difference
        // may be re-do later

        // if list is empty
        if (list.isEmpty())
        {
            list.add(newFBlock);
        }
        // Need to add to the beginning
        else if (newFBlock.getEnd() <= list.get(0).getStart())
        {
            list.add(0, newFBlock);
            merge(0);
        }
        // Need to add to the end
        else if (newFBlock.getStart() >= list.getTail().previous()
                .getData().getEnd())
        {
            list.add(newFBlock);
            merge(list.size() - 1);
        }
        // Need to add in the middle
        else
        {
            FreeBlock curr, prev;
            for (int i = 1; i < list.size(); i++)
            {
                curr = list.get(i);
                prev = list.get(i - 1);
                if (newFBlock.getStart() >= prev.getEnd()
                        && newFBlock.getEnd() <= curr.getStart())
                {
                    list.add(i, newFBlock);
                    merge(i);
                    break;
                }
            }
        }

        // check for cases
        // case 0: Removing the first thing, followed by a MemHandle
        // m->M->F
        // case 1: Removing the first thing, followed by a FreeBlock
        // m->F->M
        // case 2: after FreeBlock, followed by MemHandle F->m->M
        // case 3: stand alone, merge with next free block F->m->F
        // case 4: Remove in the middle of other MemHandles M->m->M
        // case 5: Remove after MemHandle followed by FreeBlock M->m->F

    }

    /**
     * This block first attenpts to merge with the block in the list before
     * it then calls itself on the block after it to merge with the block
     */
    private void merge(int index)
    {
        if (index == 0)
        {
            if (list.get(0).getEnd() >= list.get(1).getStart() - 1)
            {
                list.get(0).setEnd(list.get(1).getEnd());
                list.remove(1);
            }
        }
        else if (list.get(index).getStart() <= list.get(index - 1).getEnd()
                + 1)
        {
            list.get(index - 1).setEnd(list.get(index).getEnd());
            list.remove(index);
            if (index < list.size())
            {
                merge(index);
            }
        }
    }

    /**
     * 
     * 
     * @param theHandle
     * @return string
     */
    @Override
    public String get(MemHandle theHandle)
    {
        String testString = new String(pool);
        ByteBuffer bb = ByteBuffer.wrap(pool);
        bb.position(theHandle.getStart());
        short size = bb.getShort();
        byte[] arr = new byte[size];
        bb.get(arr);
        String str = new String(arr, 0, size);
        return str;
    }

    /**
     * 
     */
    @Override
    public void dump()
    {
        if (list.isEmpty())
        {
            System.out.print("(" + pool.length + ",0)");
        }
        for (int i = 0; i < list.size(); i++)
        {
            FreeBlock fb = list.get(i);
            System.out.print("(" + fb.getStart() + ","
                    + (fb.getEnd() - fb.getStart() + 1) + ")");
            if (i != list.size() - 1)
            {
                // System.out.print("(" + fb.getStart() + "," +
                // (fb.getEnd() - fb.getStart() +
                // 1) + ")");
                System.out.print(" -> ");
            }
//            else 
//            {
//                System.out.print("(" + fb.getStart() + "," + (fb.getEnd() + 1 - fb.getStart()) + ")");
//            }
        }
        System.out.println("");
    }

    /**
     * used for testing, to see what the pool looks like
     */
    protected void printOut()
    {
        System.out.println(new String(pool));
    }

}
