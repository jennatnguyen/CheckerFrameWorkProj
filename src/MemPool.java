import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemPool implements MemPoolInterface{
    
    DLList<FreeBlock> list = new DLList<FreeBlock>();
    byte[] pool;
    int initSize;
    /**
     * Constructor balls
     */
    public MemPool(int size)
    {
        initSize = size;
        pool = new byte[initSize];
        // TODO Auto-generated constructor stub
        list.add(new FreeBlock(0, initSize));
    }

    
    /**
     * 
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
            bb.position(0);
            bb.putShort(size);
            bb.put(space);
            list.remove(0);
            list.add(new FreeBlock(size, pool.length));
            return new MemHandle(0);
        }
        else
        {
            /*
             * Search through all the free blocks, check for the smallest which
             * can house the string
             * add the string in and adjust the free block which houses it.
             * check if the freeblock is empty, if so, free it and adjust the
             * list
             * get the memory handle with the starting position of the inserted
             * string
             */
            Iterator<FreeBlock> itr = list.iterator();
            int index = 0;
            int lowLen = Integer.MAX_VALUE; //store a reference to this cause using .get() on the index is slow ash
            FreeBlock fb;
            int fbLen = 0;
            for (int i = 0; i < list.size(); i++)
            {
                fb = list.get(i);
                fbLen = fb.getEnd() - fb.getStart();
                if (fbLen >= size && fbLen < lowLen)
                {
                    index = i;
                    lowLen = fbLen;
                }
            }
            if (lowLen == Integer.MAX_VALUE)
            {
                //this means none of the freeblocks can store this song
                //there is the rare possibility that this new string is bigger
                //than initSize in which case this code throws an exception but that's a
                //problem for later me to deal with
                byte[] newPool = new byte[pool.length + initSize];
                System.arraycopy(pool, 0, newPool, 0, pool.length);
                pool = newPool;
                bb = ByteBuffer.wrap(pool);
                list.getTail().getData().setStart(bb.position());
                list.getTail().getData().setEnd(pool.length);
                System.out.println("Memory pool expanded to be " + pool.length + " bytes.");
                this.insert(space, size);                
            }
            else 
            {
                bb.position(list.get(index).getStart());
                MH = bb.position();
                bb.putShort(size);
                bb.put(space);
                list.get(index).setStart(list.get(index).getStart() + size + 2);
            }
            if (list.get(index).getEnd() == list.get(index).getStart()) // Has to be index not list.size()
            {
                list.remove(list.size());
            }
            return new MemHandle(MH);
        }
    }

    
    /**
     * 
     * 
     * @param theHandle
     */
    @Override
    public void remove(MemHandle theHandle) {
        ByteBuffer bb = ByteBuffer.wrap(pool);
        bb.position(theHandle.getStart());
        short size = bb.getShort();
        FreeBlock newFBlock = new FreeBlock(theHandle.getStart(), theHandle.getStart() + 2 + size);        
        
        // Need to add to the beginning
        if (newFBlock.getStart() < list.get(0).getStart()) {
            list.add(0, newFBlock);
            merge(0);
        }
        // Need to add to the end
        else if (newFBlock.getStart() > list.get(list.size() -1).getEnd()) {
            list.add(newFBlock);
            merge(list.size() - 1);
        }
        // Need to add in the middle
        else {
            FreeBlock curr, prev;
            for (int i = 1; i < list.size(); i++)
            {
                curr = list.get(i);
                prev = list.get(i -1);
                if (newFBlock.getStart() >= prev.getEnd() && newFBlock.getStart() < curr.getStart()) {
                    list.add(i, newFBlock);
                    merge(i);
                    break;
                }
            }
        }
        
        
        // check for cases
        // case 0: Removing the first thing, followed by a MemHandle  m->M->F       
        // case 1: Removing the first thing, followed by a FreeBlock  m->F->M       
        // case 2: after FreeBlock, followed by MemHandle  F->m->M                  
        // case 3: stand alone, merge with next free block  F->m->F                 
        // case 4: Remove in the middle of other MemHandles  M->m->M                
        // case 5: Remove after MemHandle followed by FreeBlock  M->m->F            
        
    }
    
    /**
     * 
     */
    private void merge(int index) {
        if (index == 0) {
            if (list.get(0).getEnd() == list.get(1).getStart()) {
                list.get(0).setEnd(list.get(1).getEnd());
                list.remove(1);
            }
        }
        else if (list.get(index).getStart() == list.get(index - 1).getEnd()){
            list.get(index - 1).setEnd(list.get(index).getEnd());
            list.remove(index);
            if (index < list.size()) {
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
    public String get(MemHandle theHandle) {
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
        System.out.println(list.toString());
    }

}
