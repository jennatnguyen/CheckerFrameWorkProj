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
    public void insert(String str, MemHandle MH) {
        if (totalItems > (currHashTableLen / 2)) {
            expandTable();
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
     * 
     * 
     * @param str
     * @return MemHandel
     */
    public void remove(String str) {
        int homeSlot = sFold(str, currHashTableLen);
        int collisions = 0;
        while (true) {
            int probedPosition = quadProbe(homeSlot, collisions);
            Handle MHFound = hashTable[probedPosition];
            
            if (MHFound == null) {
                System.out.println("Hahah can't remove |" + str + "| get fucked");
            }
            else if (MHFound == Handle.TOMBSTONE) {
                ++collisions;
            }
            else {
                String memPoolString = memPool.get((MemHandle) MHFound);
                if (memPoolString.contains(str)) {
                    memPool.remove((MemHandle) MHFound);
                    hashTable[probedPosition] = Handle.TOMBSTONE;
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
     * @return MemHandel
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
        System.arraycopy(hashTable, 0, newHashTable, 0, currHashTableLen);
        hashTable = newHashTable;
        currHashTableLen *= 2;
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
