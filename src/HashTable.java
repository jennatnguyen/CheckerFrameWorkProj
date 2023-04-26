/**
 * 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class HashTable {

    /**
     * Constructor
     */
    public HashTable()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Uses the string to find the index location for the object
     * in the hash table
     * 
     * @param s
     * @param tableLength
     * @return
     */
    public int sFold(String s, int tableLength) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }
        return (int)(Math.abs(sum) % tableLength);
    }

}
