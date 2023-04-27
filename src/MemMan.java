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
public class MemMan {
    private HashTable songs;
    private HashTable artists;
    private MemPool memPool;
    
    public MemMan(int hashSize, int blockSize) {
        songs = new HashTable(hashSize);
        artists = new HashTable(hashSize);
        memPool = new MemPool();
    }
    
    /**
     * 
     * 
     * @param str
     */
    public void remove(String category, String str) {
        if (category.equals("song")) {
            removeHelper(songs, str);
        }
        else {
            removeHelper(artists, str);
        }
    }
    
    private void removeHelper(HashTable hashTable, String str) {
        MemHandle MHFound = hashTable.search(str);
        if (MHFound != null) {
            
        }
    }
    
    /**
     * @param args the main input files
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        MemMan manager = new MemMan(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        
        
        File myFile = new File(args[2]);
        Scanner in = new Scanner(myFile);
        while (in.hasNextLine()) {
            
        }
        in.close();
    }
}
