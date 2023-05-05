/**
 * Memory pool interface as given in the assignment as a guide
 * 
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.05.04
 */
public interface MemPoolInterface {
    /**
     * Insert a record and return its position handle space contains the
     * record to be inserted, of length size.
     * 
     * @param space the byte array
     * @param size  of the data
     * @return a memory handle
     */
    public MemHandle insert(byte[] space, short size);

    /**
     * Free a block at the position specified by theHandle. Merge adjacent
     * free blocks.
     * 
     * @param theHandle that needs to be removed from the memory pool
     */
    void remove(MemHandle theHandle);

    /**
     * Return the record with handle posHandle, up to size bytes, by
     * copying it into space. Return the number of bytes actually copied
     * into space.
     * 
     * @param theHandle the memory handle to search
     * @return a string of characters which are stored at the position
     *         defined by the memory handle
     */
    String get(MemHandle theHandle);

    /**
     * Dump a printout of the freeblock list to the standard output
     */
    void dump();
}
