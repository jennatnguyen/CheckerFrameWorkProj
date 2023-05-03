/**
 * 
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
     * Constructor
     */
    public HashTable(int initHashTableLen, MemPool memPool)
    {
        this.currHashTableLen = initHashTableLen;
        hashTable = new Handle[currHashTableLen];
        this.memPool = memPool;
    }
    
    /**
     * 
     * 
     * @param str
     * @param MH
     */
    public void insert(String str, MemHandle MH, int table) {
        if (totalItems >= (currHashTableLen / 2)) {
            expandTable();
            if (table == 0) {
                System.out.println("Artist hash table size doubled.");
            }
            else {
                System.out.println("Song hash table size doubled.");
            }
        }
        int homeSlot = sFold(str, currHashTableLen);
        int collisons = 0;
        int probedPosition = quadProbe(homeSlot, collisons);
        while (hashTable[probedPosition] != null || hashTable[homeSlot] == Handle.TOMBSTONE) {
            collisons++;
            probedPosition = quadProbe(homeSlot, collisons);
        }
        hashTable[probedPosition] = MH;
        ++totalItems;
    }
    
    /**
     * silently inserts the string str into the table
     * @param str the string to be inserted
     * @param MH the memory handle to be stored in the table
     * @param table the hash table to be inserted into
     */
    private void sInsert(String str, MemHandle MH, Handle[] table)
    {
        int homeSlot = sFold(str, currHashTableLen);
        int collisons = 0;
        int probedPosition = quadProbe(homeSlot, collisons);
        while (table[probedPosition] != null || table[homeSlot] == Handle.TOMBSTONE) {
            collisons++;
            probedPosition = quadProbe(homeSlot, collisons);
        }
        table[probedPosition] = MH;
    }
    
    /**
     * 
     * 
     * @param str
     * @return MemHandel
     */
    public void remove(String str, String tableType) {
        int homeSlot = sFold(str, currHashTableLen);
        int collisions = 0;
        while (true) {
            int probedPosition = quadProbe(homeSlot, collisions);
            Handle MHFound = hashTable[probedPosition];
            
            if (MHFound == null) {
                System.out.println("|" + str + "| does not exist in the "+tableType+" database.");
                break;
            }
            else if (MHFound == Handle.TOMBSTONE) {
                ++collisions;
            }
            else {
                String memPoolString = memPool.get((MemHandle) MHFound);
                if (memPoolString.contains(str)) {
                    memPool.remove((MemHandle) MHFound);
                    hashTable[probedPosition] = Handle.TOMBSTONE;
                    System.out.println("|" + str + "| is removed from the "+tableType+" database.");
                    totalItems--;
                    break;
                }
                else {
                    ++collisions;
                }
            }
        }
    }
    
    
    /**
     * 
     * 
     * @param str
     * @return MemHandle returns null if not found, otherwise returns the memory handle
     */
    public MemHandle search(String str) {
        int homeSlot = sFold(str, currHashTableLen);
        int collisions = 0;
        while (true) {
            int probedPosition = quadProbe(homeSlot, collisions);
            Handle MHFound = hashTable[probedPosition];
            
            if (MHFound == null) {
                return null;
            }
            else if (MHFound == Handle.TOMBSTONE) {
                ++collisions;
            }
            else {
                String memPoolString = memPool.get((MemHandle) MHFound);
                if (memPoolString.contains(str)) {
                    return (MemHandle) MHFound;
                }
                else {
                    ++collisions;
                }
            }
        }
    }
    
    public void printHashTable(String cat) {
        int count = 0;
        int total = -1;
        for (Handle memHandle : hashTable) {
            ++total;
            if (memHandle != Handle.TOMBSTONE && memHandle != null) {
                MemHandle thisHandle = (MemHandle) memHandle;
                System.out.println("|" + memPool.get(thisHandle) + "| " + total);
                // thisHandle.getStart()
                count++;
            }
        }
        System.out.println("total " + cat + ": " + count);
    }
    // Helper-Methods-----------------------------------------------------------
    /**
     * 
     * 
     * @param indexPosition
     * @param iteration
     * @return
     */
    private int quadProbe(int indexPosition, int iteration) {
        return (indexPosition + (iteration * iteration)) % currHashTableLen;
    }
    
    /**
     * 
     */
    private void expandTable() {
        Handle[] newHashTable = new Handle[currHashTableLen * 2];
        currHashTableLen *= 2;
        for (Handle memHandle : hashTable)
        {
            if (memHandle != Handle.TOMBSTONE && memHandle != null) 
            {
                MemHandle thisHandle = (MemHandle) memHandle;
                this.sInsert(memPool.get(thisHandle), thisHandle, newHashTable);
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
