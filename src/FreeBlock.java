/**
 * 
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
    public FreeBlock(int nStart, int nEnd)
    {
        this.setStart(nStart);
        this.setEnd(nEnd);
    }
    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }
    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }
    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }
    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }
    
    /**
     * 
     */
    @Override
    public String toString() {
        return "(" + start + "," + end + ")";
    }

}
