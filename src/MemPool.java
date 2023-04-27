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
                byte[] newPool = new byte[pool.length + initSize];
                System.arraycopy(pool, 0, newPool, 0, pool.length);
                pool = newPool;
                bb = ByteBuffer.wrap(pool);
                bb.position(newPool.length - initSize);
                MH = bb.position();
                bb.putShort(size);
                bb.put(space);
                list.get(list.size()).setStart(bb.position());
                list.get(list.size()).setEnd(pool.length);
                index = list.size(); 
            }
            else 
            {
                bb.position(list.get(index).getStart());
                MH = bb.position();
                bb.putShort(size);
                bb.put(space);
                list.get(index).setStart(list.get(index).getStart() + size);
            }
            if (list.get(list.size()).getEnd() == list.get(list.size()).getStart())
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
    public void remove(MemHandle theHandle)
    {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        return "";
    }

    
    /**
     * 
     */
    @Override
    public void dump()
    {
        // TODO Auto-generated method stub
        
    }

}
