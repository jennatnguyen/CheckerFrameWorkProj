/**
 * FreeBlock is used to keep track of open positions in the memory pool. It
 * stores a start and a end position
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class FreeBlock {

    private int start, end;

    /**
     * Constructor
     */
    public FreeBlock(int nStart, int nEnd) {
    // Directly assign the values to the fields
        this.start = nStart;
        this.end = nEnd;
    } 
    /**
     * Getter method for start
     * 
     * @return the start
     */
    public int getStart()
    {
        return start;
    }

    /**
     * setter method for start
     * 
     * @param start the start to set
     */
    public void setStart(int start)
    {
        this.start = start;
    }

    /**
     * Getter method for the end position
     * 
     * @return the end
     */
    public int getEnd()
    {
        return end;
    }

    /**
     * setter position for the end position
     * 
     * @param end the end to set
     */
    public void setEnd(int end)
    {
        this.end = end;
    }

    /**
     * To string method used to print out the free block in the memory pool
     */
    @Override
    public String toString()
    {
        return "(" + start + "," + end + ")";
    }

}
