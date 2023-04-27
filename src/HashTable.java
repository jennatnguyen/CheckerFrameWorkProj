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

    /**
     * Constructor
     */
    public HashTable(int initHashTableLen)
    {
        this.currHashTableLen = initHashTableLen;
        hashTable = new Handle[currHashTableLen];
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
     * @return int
     */
    public MemHandle search(String str) {
        int homeSlot = sFold(str, currHashTableLen);
        //hashTable[]
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
