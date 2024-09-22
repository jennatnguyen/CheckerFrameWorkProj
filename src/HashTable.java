import org.checkerframework.checker.nullness.qual.*;
/**
 * This class implements the hash table data structure to store memory
 * handles for the artists and song.
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class HashTable {

    private Handle[] hashTable;
    private int currHashTableLen;
    private int totalItems;
    private MemPool memPool;

    /**
     * Constructor to initialize the hashTable, it updates the current hash
     * table lenght and initialized the memory handle array to store the
     * handles.
     */
    public HashTable(int initHashTableLen, MemPool memPool)
    {
        this.currHashTableLen = initHashTableLen;
        hashTable = new Handle[currHashTableLen];
        this.memPool = memPool;
    }

    /**
     * This method is used to insert a memory handle into the hashtable,
     * and it uses the string of what it is trying to store to find the
     * homeslot for the memory handle. If there are collisions, then it
     * will use quadratic probing to go find the next home slot.
     * 
     * 
     * @param str to be used for hashing
     * @param MH  to be inserted
     */
    public void insert(String str, MemHandle MH, int table)
    {
        if (totalItems >= (currHashTableLen / 2))
        {
            expandTable();
            if (table == 0)
            {
                System.out.println("Artist hash table size doubled.");
                // System.out.println("Artist hash table size doubled. [" +
                // hashTable.length + "]");
            }
            else
            {
                System.out.println("Song hash table size doubled.");
                // System.out.println("Song hash table size doubled.[" +
                // hashTable.length + "]");
            }
        }
        int homeSlot = sFold(str, currHashTableLen);
        int collisons = 0;
        int probedPosition = quadProbe(homeSlot, collisons);
        while (hashTable[probedPosition] != null
                && hashTable[probedPosition] != Handle.TOMBSTONE)
        {
            collisons++;
            probedPosition = quadProbe(homeSlot, collisons);
        }
        hashTable[probedPosition] = MH;
        ++totalItems;
//        if (table == 0) {
//            System.out.print("  ARTIST Hashtable: ");
//        }
//        else {
//            System.out.print("  SONG Hashtable: ");
//        }
//        printHashTable();
    }

    /**
     * Used for testing
     */
    private void printHashTable()
    {
        StringBuilder sb = new StringBuilder("[");
        for (Handle hand : hashTable)
        {
            if (hand != null)
            {
                sb.append(hand.toString() + ", ");
            }
            else
            {
                sb.append("null, ");
            }
        }
        sb.append("]\n");
        System.out.print(sb.toString());
    }

    /**
     * silently inserts the string str into the table, used when we
     * expanding the hash table
     * 
     * @param str   the string to be inserted
     * @param MH    the memory handle to be stored in the table
     * @param table the hash table to be inserted into
     */
    private void sInsert(String str, MemHandle MH, Handle[] table)
    {
        int homeSlot = sFold(str, currHashTableLen);
        int collisons = 0;
        int probedPosition = quadProbe(homeSlot, collisons);
        while (table[probedPosition] != null
                && table[probedPosition] != Handle.TOMBSTONE)
        {
            collisons++;
            probedPosition = quadProbe(homeSlot, collisons);
        }
        table[probedPosition] = MH;
    }

    /**
     * This removes a memory handle from the hashtable, to do this it finds
     * the home slot acording to the string given and keeps probing until
     * it either finds it or reaches a null slot
     * 
     * @param str       used to hash and find home slot
     * @param tableType used to print stuff
     */
    public void remove(String str, String tableType)
    {
        int homeSlot = sFold(str, currHashTableLen);
        int collisions = 0;
        while (true)
        {
            int probedPosition = quadProbe(homeSlot, collisions);
            Handle MHFound = hashTable[probedPosition];

            if (MHFound == null)
            {
                System.out.println("|" + str + "| does not exist in the "
                        + tableType + " database.");
                break;
            }
            else if (MHFound == Handle.TOMBSTONE)
            {
                ++collisions;
            }
            else
            {
                String memPoolString = memPool.get((MemHandle) MHFound);
                if (memPoolString.contains(str))
                {
                    memPool.remove((MemHandle) MHFound);
                    hashTable[probedPosition] = Handle.TOMBSTONE;
                    System.out.println("|" + str + "| is removed from the "
                            + tableType + " database.");
                    totalItems--;
                    break;
                }
                else
                {
                    ++collisions;
                }
            }
        }
    }

    /**
     * This method is used to check if a given string exists in the hash
     * table and memory pool by the memory handle.
     * 
     * @param str that you are looking to search
     * @return MemHandle returns null if not found, otherwise returns the
     *         memory handle
     */
    public @Nullable MemHandle search(String str)
    {
        int homeSlot = sFold(str, currHashTableLen);
        int collisions = 0;
        while (true)
        {
            int probedPosition = quadProbe(homeSlot, collisions);
            Handle MHFound = hashTable[probedPosition];

            if (MHFound == null)
            {
                return null;
            }
            else if (MHFound == Handle.TOMBSTONE)
            {
                ++collisions;
            }
            else
            {
                String memPoolString = memPool.get((MemHandle) MHFound);
                if (memPoolString.contains(str))
                {
                    return (MemHandle) MHFound;
                }
                else
                {
                    ++collisions;
                }
            }
        }
    }
    
    /**
     * 
     * 
     * @param str
     * @return int returns an integer where the current string would be placed in the 
     * array
     */
    public int findSpot(String str, int table)
    {
        if (totalItems >= (currHashTableLen / 2))
        {
            expandTable();
            if (table == 0)
            {
                System.out.println("Artist hash table size doubled.");
                //System.out.println("Artist hash table size doubled. [" + hashTable.length + "]");
            } else
            {
                System.out.println("Song hash table size doubled.");
                //System.out.println("Song hash table size doubled.[" + hashTable.length + "]");
            }
        }
        int homeSlot = sFold(str, currHashTableLen);
        int collisons = 0;
        int probedPosition = quadProbe(homeSlot, collisons);
        while (hashTable[probedPosition] != null
                && hashTable[probedPosition] != Handle.TOMBSTONE)
        {
            collisons++;
            probedPosition = quadProbe(homeSlot, collisons);
        }
        //hashTable[probedPosition] = MH;
        //++totalItems;
        return probedPosition;
    }
    
    /**
     * inserts a memory handle at the specified position
     * @param position
     */
    public void fastInsert(int position, MemHandle MH)
    {
        this.hashTable[position] = MH;
    }

    /**
     * This method is used to print the contents of the hash table
     * 
     * @param cat used for printing
     */
    public void printHashTable(String cat)
    {
        int count = 0;
        int total = -1;
        for (Handle memHandle : hashTable)
        {
            ++total;
            if (memHandle != Handle.TOMBSTONE && memHandle != null)
            {
                MemHandle thisHandle = (MemHandle) memHandle;
                System.out.println(
                        "|" + memPool.get(thisHandle) + "| " + total);
                // thisHandle.getStart()
                count++;
            }
        }
        System.out.println("total " + cat + ": " + count);
    }

    /**
     * This method is used to return the probed position based on the
     * homeslot and the ith time the probe is being called
     * 
     * @param indexPosition is the homeslot
     * @param iteration     the number of collisions so far
     * @return int representing the slot to try next
     */
    private int quadProbe(int indexPosition, int iteration)
    {
        return (indexPosition + (iteration * iteration))
                % currHashTableLen;
    }

    /**
     * This method is used to expand the size of the hash table by a factor
     * of 2 after expanding, this method also re-hashes all the memory
     * handles.
     */
    private void expandTable()
    {
        Handle[] newHashTable = new Handle[currHashTableLen * 2];
        currHashTableLen *= 2;
        for (Handle memHandle : hashTable)
        {
            if (memHandle != Handle.TOMBSTONE && memHandle != null)
            {
                MemHandle thisHandle = (MemHandle) memHandle;
                this.sInsert(memPool.get(thisHandle), thisHandle,
                        newHashTable);
            }
        }
        hashTable = newHashTable;
    }

    /**
     * Uses the string to find the index location for the object in the
     * hash table
     * 
     * @param s
     * @param tableLength
     * @return
     */
    private int sFold(String s, int tableLength)
    {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }
        return (int) (Math.abs(sum) % tableLength);
    }

}
