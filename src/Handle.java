/**
 * 
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
    
    public String toString();
}
