import org.checkerframework.checker.nullness.qual.*;

/**
 * MemHandle is a wrapper class which stores an position to represent where in
 * the memory pool some data starts
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemHandle implements Handle {
    private int start;

    /**
     * Constructor
     */
    public MemHandle(int start)
    {
        this.start = start;
    }

    /**
     * Getter method to get the start position
     * @return start
     */
    public int getStart()
    {
        return start;
    }

    /**
     * toString method used for testing purposes
     */
    public String toString()
    {
        return "(" + start + ")";
    }

    /**
     * equals method to check if two MemHandles equal each other
     */
    public boolean equals(@Nullable Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (obj.getClass() == this.getClass())
        {
            MemHandle other = (MemHandle) obj;
            return this.getStart() == other.getStart();
        }
        else
        {
            return false;
        }
    }
}
