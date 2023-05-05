/**
 * Interface with a Tombstone handle which is like a flynode used to store 
 * a placeholder in the hash table 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public interface Handle
{
    /**
     * Creates one flyNode which all nodes can set their pointers to
     */
    public static final Tombstone TOMBSTONE = new Tombstone();
    /**
     * To String method used for testing
     * @return a string representation of the handle
     */
    public String toString();
}
