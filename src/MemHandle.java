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
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        else if (obj.getClass() == this.getClass()) {
            MemHandle other = (MemHandle) obj;
            return this.getStart() == other.getStart();
        }
        else {
            return false;
        }
    }
}
