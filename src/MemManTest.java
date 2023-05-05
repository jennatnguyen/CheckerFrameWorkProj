import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemManTest extends student.TestCase {

    /**
     * Setup method which runs before every test
     */
    public void setUp()
    {

    }

    /**
     * Test case for test the sample inputs we were provides
     * 
     * @throws IOException
     */
    public void testMainWithParameters() throws IOException
    {
        Exception thrown = null;
        try
        {
            MemMan.main(new String[]
            { "10", "32", "sampleInput.txt" });
        }
        catch (Exception e)
        {
            thrown = e;
            System.out.println(e);
        }
        assertTrue(thrown == null);
        String output = systemOut().getHistory();
        // below is for testing purposes
        File myFile = new File("output.txt");
        FileWriter fileW = new FileWriter("output.txt");
        fileW.write(output);
        fileW.close();
        // above is for testing purposes
        assertEquals("|When Summer's Through| does not exist in the song database.\r\n"
            + "(0,32)\r\n"
            + "total songs: 0\r\n"
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
            + "|Charley Patton| 4\r\n"
            + "|Bukka White| 5\r\n"
            + "|Ma Rainey| 7\r\n"
            + "total artists: 5\r\n"
            + "|Fixin' To Die Blues| 1\r\n"
            + "|Mississippi Boweavil Blues| 2\r\n"
            + "|Long Lonesome Blues| 5\r\n"
            + "|Ma Rainey's Black Bottom| 6\r\n"
            + "|Street Car Blues| 9\r\n"
            + "total songs: 5\r\n"
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
            + "|Charley Patton| 14\r\n"
            + "|Little Eva| 18\r\n"
            + "total artists: 6\r\n"
            + "|Fixin' To Die Blues| 1\r\n"
            + "|Street Car Blues| 5\r\n"
            + "|Got The Blues| 8\r\n"
            + "|Long Lonesome Blues| 15\r\n"
            + "|Ma Rainey's Black Bottom| 16\r\n"
            + "|The Things That I Used To Do| 17\r\n"
            + "|The Loco-Motion| 18\r\n"
            + "total songs: 7\r\n"
            + "|Jim Reeves| is added to the artist database.\r\n"
            + "|Jingle Bells| is added to the song database.\r\n"
            + "Memory pool expanded to be 320 bytes.\r\n"
            + "|Mongo Santamaria| is added to the artist database.\r\n"
            + "|Watermelon Man| is added to the song database.\r\n"
            + "(44,11) -> (121,4) -> (319,1)\r\n", output);
    }

    public void testEdge2() throws IOException
    {
        Exception thrown = null;
        try 
        {
            MemMan.main(new String[]
                { "10", "32", "edge.txt" });
        }
        catch (Exception e)
        {
            thrown = e;
            System.out.println(e);
        }
        assertTrue(thrown == null);
        String output = systemOut().getHistory();
        // below is for testing purposes
        File myFile = new File("output.txt");
        FileWriter fileW = new FileWriter("output.txt");
        fileW.write(output);
        fileW.close();
        // above is for testing purposes
    }
    
    public void testEdge() throws IOException
    {
        Exception thrown = null;
        try
        {
            MemMan.main(new String[]
            { "10", "32", "edge2.txt" });
        }
        catch (Exception e)
        {
            thrown = e;
            System.out.println(e);
        }
        assertTrue(thrown == null);
        String output = systemOut().getHistory();
        // below is for testing purposes
        File myFile = new File("output.txt");
        FileWriter fileW = new FileWriter("output.txt");
        fileW.write(output);
        fileW.close();
        // above is for testing purposes
        assertEquals("|Bruce Rowland| is added to the artist database.\r\n"
            + "|Mountain Theme| is added to the song database.\r\n"
            + "Memory pool expanded to be 64 bytes.\r\n"
            + "|Thomas Dolby| is added to the artist database.\r\n"
            + "Memory pool expanded to be 96 bytes.\r\n"
            + "|She Blinded Me With Science| is added to the song database.\r\n"
            + "|Cheo Feliciano| is added to the artist database.\r\n"
            + "Memory pool expanded to be 128 bytes.\r\n"
            + "|Juguete| is added to the song database.\r\n"
            + "|Johnny Winter| is added to the artist database.\r\n"
            + "Memory pool expanded to be 160 bytes.\r\n"
            + "|Be Careful With A Fool| is added to the song database.\r\n"
            + "|Kings Of Leon| is added to the artist database.\r\n"
            + "Memory pool expanded to be 192 bytes.\r\n"
            + "|Wicker Chair| is added to the song database.\r\n"
            + "Artist hash table size doubled.\r\n"
            + "|Happy Mondays| is added to the artist database.\r\n"
            + "Song hash table size doubled.\r\n"
            + "Memory pool expanded to be 224 bytes.\r\n"
            + "|Wrote For Luck (12 - Remastered version)| is added to the song database.\r\n"
            + "Memory pool expanded to be 256 bytes.\r\n"
            + "|Rita Chiarelli| is added to the artist database.\r\n"
            + "|Doggin' Around| is added to the song database.\r\n"
            + "Memory pool expanded to be 288 bytes.\r\n"
            + "|Tim Wilson| is added to the artist database.\r\n"
            + "Memory pool expanded to be 320 bytes.\r\n"
            + "Memory pool expanded to be 352 bytes.\r\n"
            + "|I Think My Wife Is Running Around On Me (Taco Hell)| is added to the song database.\r\n"
            + "|Ennio Morricone| is added to the artist database.\r\n"
            + "Memory pool expanded to be 384 bytes.\r\n"
            + "|Sporco Ma Distinto| is added to the song database.\r\n"
            + "|Tiny Bradshaw| is added to the artist database.\r\n"
            + "Memory pool expanded to be 416 bytes.\r\n"
            + "|Well Oh Well| is added to the song database.\r\n"
            + "|Happy Mondays| 2\r\n"
            + "|Bruce Rowland| 6\r\n"
            + "|Cheo Feliciano| 7\r\n"
            + "|Thomas Dolby| 8\r\n"
            + "|Ennio Morricone| 9\r\n"
            + "|Tim Wilson| 10\r\n"
            + "|Rita Chiarelli| 11\r\n"
            + "|Johnny Winter| 15\r\n"
            + "|Kings Of Leon| 16\r\n"
            + "|Tiny Bradshaw| 19\r\n"
            + "total artists: 10\r\n"
            + "|Bruce Rowland| is removed from the artist database.\r\n"
            + "(0,15) -> (387,29)\r\n"
            + "|Thomas Dolby| is removed from the artist database.\r\n"
            + "(0,15) -> (31,14) -> (387,29)\r\n"
            + "|Mountain Theme| is removed from the song database.\r\n"
            + "(0,45) -> (387,29)\r\n"
            + "|3 Doors Down| is added to the artist database.\r\n"
            + "|Sarah Yellin'| is added to the song database.\r\n"
            + "|Wrote For Luck (12 - Remastered version)| 0\r\n"
            + "|I Think My Wife Is Running Around On Me (Taco Hell)| 1\r\n"
            + "|Sarah Yellin'| 2\r\n"
            + "|She Blinded Me With Science| 4\r\n"
            + "|Doggin' Around| 8\r\n"
            + "|Well Oh Well| 10\r\n"
            + "|Wicker Chair| 12\r\n"
            + "|Be Careful With A Fool| 13\r\n"
            + "|Sporco Ma Distinto| 15\r\n"
            + "|Juguete| 19\r\n"
            + "total songs: 10\r\n"
            + "(0,45)\r\n"
            + "", output);

    }

    /**
     * This tests a case where you insert a set of artists then remove all
     * of them
     */
    public void testInsertFullRemoveFull()
    {
        Exception thrown = null;
        try
        {
            MemMan.main(new String[]
            { "10", "64", "RandomTest1.txt" });
        }
        catch (Exception e)
        {
            thrown = e;
            System.out.println(e);
        }
        assertTrue(thrown == null);
        String output = systemOut().getHistory();
        assertEquals(
                "|When Summer's Through| does not exist in the song database.\r\n"
                        + "(0,64)\r\n" + "total songs: 0\r\n"
                        + "total artists: 0\r\n"
                        + "|Playboi Carti| is added to the artist database.\r\n"
                        + "|Bane| is added to the song database.\r\n"
                        + "|Drake| is added to the artist database.\r\n"
                        + "|Going bad| is added to the song database.\r\n"
                        + "|West| is added to the artist database.\r\n"
                        + "|Euro$tep| is added to the song database.\r\n"
                        + "(55,9)\r\n"
                        + "|Drake| is removed from the artist database.\r\n"
                        + "(21,7) -> (55,9)\r\n"
                        + "|Euro$tep| is removed from the song database.\r\n"
                        + "(21,7) -> (45,10) -> (55,9)\r\n"
                        + "|Going bad| is removed from the song database.\r\n"
                        + "(21,18) -> (45,10) -> (55,9)\r\n"
                        + "|West| is removed from the artist database.\r\n"
                        + "(21,43)\r\n"
                        + "|Playboi Carti| is removed from the artist database.\r\n"
                        + "(0,15) -> (21,43)\r\n"
                        + "|Bane| is removed from the song database.\r\n"
                        + "(0,64)\r\n" + "total songs: 0\r\n"
                        + "total artists: 0\r\n",
                output);
    }
}
