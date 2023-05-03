/**
 * 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemManTest extends student.TestCase
{

    /**
     * 
     */
    public MemManTest()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public void setUp()
    {

    }

    /**
     * Test case for test the sample inputs we were provides
     */
    public void testMainWithParameters()
    {
        Exception thrown = null;
        try
        {
            MemMan.main(new String[]
            { "10", "32", "sampleInput.txt" });
        } catch (Exception e)
        {
            thrown = e;
            System.out.println(e);
        }
        assertTrue(thrown == null);
        String output = systemOut().getHistory();
        assertEquals(
                "|When Summer's Through| does not exist in the song database.\r\n"
                        + "(0,32)\r\n" + "total songs: 0\r\n"
                        + "total artists: 0\r\n"
                        + "|Blind Lemon Jefferson| is added to the artist database.\r\n"
                        + "Memory pool expanded to be 64 bytes.\r\n"
                        + "|Long Lonesome Blues| is added to the song database.\r\n"
                        + "|Ma Rainey| is added to the artist database.\r\n"
                        + "Memory pool expanded to be 96 bytes.\r\n"
                        + "|Ma Rainey's Black Bottom| is added to the song database.\r\n"
                        + "Memory pool expanded to be 128 bytes.\r\n"
                        + "|Charley Patton| is added to the artist database.\r\n"
                        + "|Mississippi Boweavil Blues| is added to the song database.\r\n"
                        + "Memory pool expanded to be 160 bytes.\r\n"
                        + "|Sleepy John Estes| is added to the artist database.\r\n"
                        + "Memory pool expanded to be 192 bytes.\r\n"
                        + "|Street Car Blues| is added to the song database.\r\n"
                        + "|Bukka White| is added to the artist database.\r\n"
                        + "Memory pool expanded to be 224 bytes.\r\n"
                        + "|Fixin' To Die Blues| is added to the song database.\r\n"
                        + "|Blind Lemon Jefferson| 0\r\n"
                        + "|Sleepy John Estes| 1\r\n"
                        + "|Charley Patton| 4\r\n" + "|Bukka White| 5\r\n"
                        + "|Ma Rainey| 7\r\n" + "total artists: 5\r\n"
                        + "|Fixin' To Die Blues| 1\r\n"
                        + "|Mississippi Boweavil Blues| 2\r\n"
                        + "|Long Lonesome Blues| 5\r\n"
                        + "|Ma Rainey's Black Bottom| 6\r\n"
                        + "|Street Car Blues| 9\r\n" + "total songs: 5\r\n"
                        + "Artist hash table size doubled.\r\n"
                        + "|Guitar Slim| is added to the artist database.\r\n"
                        + "Song hash table size doubled.\r\n"
                        + "Memory pool expanded to be 256 bytes.\r\n"
                        + "|The Things That I Used To Do| is added to the song database.\r\n"
                        + "|Style Council| does not exist in the artist database.\r\n"
                        + "|Ma Rainey| is removed from the artist database.\r\n"
                        + "|Mississippi Boweavil Blues| is removed from the song database.\r\n"
                        + "|(The Best Part Of) Breakin' Up| does not exist in the song database.\r\n"
                        + "(44,11) -> (97,28) -> (239,17)\r\n"
                        + "|Blind Lemon Jefferson| duplicates a record already in the artist database.\r\n"
                        + "|Got The Blues| is added to the song database.\r\n"
                        + "|Little Eva| is added to the artist database.\r\n"
                        + "Memory pool expanded to be 288 bytes.\r\n"
                        + "|The Loco-Motion| is added to the song database.\r\n"
                        + "|Blind Lemon Jefferson| 0\r\n"
                        + "|Bukka White| 4\r\n"
                        + "|Sleepy John Estes| 10\r\n"
                        + "|Guitar Slim| 12\r\n"
                        + "|Charley Patton| 14\r\n" + "|Little Eva| 18\r\n"
                        + "total artists: 6\r\n"
                        + "|Fixin' To Die Blues| 1\r\n"
                        + "|Street Car Blues| 5\r\n"
                        + "|Got The Blues| 8\r\n"
                        + "|Long Lonesome Blues| 15\r\n"
                        + "|Ma Rainey's Black Bottom| 16\r\n"
                        + "|The Things That I Used To Do| 17\r\n"
                        + "|The Loco-Motion| 18\r\n" + "total songs: 7\r\n"
                        + "|Jim Reeves| is added to the artist database.\r\n"
                        + "|Jingle Bells| is added to the song database.\r\n"
                        + "Memory pool expanded to be 320 bytes.\r\n"
                        + "|Mongo Santamaria| is added to the artist database.\r\n"
                        + "|Watermelon Man| is added to the song database.\r\n"
                        + "(44,11) -> (121,4) -> (319,1)\r\n",
                output);
    }

//    public void testEdge()
//    {
//        Exception thrown = null;
//        try {
//            MemMan.main(new String[] { "10", "32", "edge.txt" });
//        } 
//        catch (Exception e) {
//            thrown = e;
//            System.out.println(e);
//        }
//        assertTrue(thrown == null);
//        String output = systemOut().getHistory();
//        assertEquals("|Blind Lemon Jefferson| is added to the artist database.\r\n"
//                + "Memory pool expanded to be 64 bytes.\r\n"
//                + "|Long Lonesome Blues| is added to the song database.\r\n"
//                + "|Ma Rainey| is added to the artist database.\r\n"
//                + "Memory pool expanded to be 96 bytes.\r\n"
//                + "|Ma Rainey's Black Bottom| is added to the song database.\r\n"
//                + "Memory pool expanded to be 128 bytes.\r\n"
//                + "|Charley Patton| is added to the artist database.\r\n"
//                + "|Mississippi Boweavil Blues| is added to the song database.", output);
//    }
}
