import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
/**
 * This is the main driver class to run the entire program.
 *
 * @author Shrey Patel (shreyp2305)
 * @author Sarthak Shrivastava (sarthaks)
 * @version 2023.04.25
 */
public class MemMan
{
    private MemPool memPool;
    private HashTable[] tables;

    public MemMan(int hashSize, int blockSize)
    {
        memPool = new MemPool(blockSize);
        tables = new HashTable[2];
        // tables[0]: artists
        tables[0] = new HashTable(hashSize, memPool);
        // tables[1]: song
        tables[1] = new HashTable(hashSize, memPool);
    }

    /**
     * 
     * 
     * @param str
     */
    public void remove(String category, String str)
    {
        if (category.equals("artist"))
        {
            tables[0].remove(str, "artist");
        } else
        {
            tables[1].remove(str, "song");
        }
    }

    /**
     * inserts the String into the corresponding hash table and the memory pool
     * 
     * @param str   is the String to be inserted
     * @param table table 0 means insert into table 0 (artists), 1 means insert into
     *              table 1(songs).
     */
    private void insert(String str, int table)
    {
        /*
         * start with hash table ->check if there are duplicates (a whole thing)(After
         * milestone 1) ->insert the String into the memory pool ->Search through all
         * the free blocks, check for the smallest which can house the string ->add the
         * string in and adjust the free block which houses it. ->check if the freeblock
         * is empty, if so, free it and adjust the list ->get the memory handle with the
         * starting position of the inserted string -> have hash table hash the artist
         * and the song string, find a position, deal with collisions, expansions and
         * insert the MH into that position
         */
        if (tables[table].search(str) == null)
        {
            int size = memPool.pool.length;
            MemHandle MH = memPool.insert(str.getBytes(),
                    (short) (str.getBytes().length));
            tables[table].insert(str, MH, table);
            if (MH.getStart() + 2 + (str.getBytes().length) > size)
            {
                System.out.println("Memory pool expanded to be "
                        + memPool.pool.length + " bytes.");
            }
            System.out.println("|" + str + "| is added to the "
                    + ((table == 0) ? "artist" : "song") + " database.");
        } else
        {
            System.out.println("|" + str
                    + "| duplicates a record already in the "
                    + ((table == 0) ? "artist" : "song") + " database.");
        }
    }

    public void print(String category)
    {
        if (category.equals("blocks"))
        {
            memPool.dump();
        } else if (category.equals("artists"))
        {
            tables[0].printHashTable(category);
        } else
        {
            tables[1].printHashTable(category);
        }
    }

    /**
     * @param args the main input files
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        MemMan manager = new MemMan(Integer.valueOf(args[0]),
                Integer.valueOf(args[1]));

        File myFile = new File(args[2]);
        Scanner in = new Scanner(myFile);
        while (in.hasNextLine())
        {
            String line = in.nextLine().trim();
            if (line.length() != 0)
            {
                Scanner lineItem = new Scanner(line);
                if (lineItem.hasNext())
                {
                    String command = lineItem.next();
                    if (command.equals("insert"))
                    {
                        // insert
                        String content = lineItem.nextLine();
                        Scanner contentIn = new Scanner(content);
                        contentIn.useDelimiter("<SEP>");
                        manager.insert(contentIn.next().trim(), 0);
                        manager.insert(contentIn.next().trim(), 1);
                        contentIn.close();
                    } else if (command.equals("remove"))
                    {
                        String category = lineItem.next().trim();
                        manager.remove(category,
                                lineItem.nextLine().trim());
                    } else
                    {
                        String category = lineItem.next().trim();
                        manager.print(category);
                    }
                }
                lineItem.close();
            }
        }
        in.close();
    }
}
