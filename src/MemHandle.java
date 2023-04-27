/**
 * 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemHandle implements Handle{
    private int start;
    
    /**
     * Constructor
     */
    public MemHandle(int start)
    {
        this.start = start;
    }
    
    public int getStart() {
        return start;
    }
}
