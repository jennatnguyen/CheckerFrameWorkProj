
public interface MemPoolInterface {
    // Insert a record and return its position handle.
    // space contains the record to be inserted, of length size.
    MemHandle insert(byte[] space, short size);

    // Free a block at the position specified by theHandle.
    // Merge adjacent free blocks.
    void remove(MemHandle theHandle);

    // Return the record with handle posHandle, up to size bytes, by
    // copying it into space.
    // Return the number of bytes actually copied into space.
    String get(MemHandle theHandle);

    // Dump a printout of the freeblock list
    void dump();
}
