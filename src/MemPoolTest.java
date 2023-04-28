import student.TestCase;

/**
 * 
 */

/**
 * @author sarth
 *
 */
public class MemPoolTest extends TestCase {
    MemPool mp;
    public void setUp()
    {
        mp = new MemPool(256);
    }

    /**
     * tests simple inserts, which don't exceed the total byte limit
     */
    public void testInsertSimple()
    {
        mp.insert("Nonstop".getBytes(), ((short)"Nonstop".getBytes().length));
        assertEquals(mp.get(new MemHandle(0)), "Nonstop");
        
        mp.insert("No Role Modelz".getBytes(), ((short)"No Role Modelz".getBytes().length));
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + 2)), "No Role Modelz");
        
        mp.insert("Love yourz".getBytes(), ((short)"Love yourz".getBytes().length));
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + "No Role Modelz".getBytes().length + 4)), "Love yourz");
        
        
        mp.remove(new MemHandle("Nonstop".getBytes().length + 2));
        //should remove No Role Modelz
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + "No Role Modelz".getBytes().length + 4)), "Love yourz");
        //love yourz should still be where it was
    }
    
    /**
     * tests to make sure insert expands correctly 
     */
    public void testInsertExpand()
    {
        mp = new MemPool(15); 
        
        mp.insert("Nonstop".getBytes(), ((short)"Nonstop".getBytes().length));
        assertEquals(mp.get(new MemHandle(0)), "Nonstop");
        mp.printOut();
        mp.dump();
        mp.insert("No Role Modelz".getBytes(), ((short)"No Role Modelz".getBytes().length)); 
        mp.printOut();
        mp.dump();
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + 2)), "No Role Modelz");
        
        //will have to expand to fit Love yourz 
        mp.insert("Love yourz".getBytes(), ((short)"Love yourz".getBytes().length));
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + "No Role Modelz".getBytes().length + 4)), "Love yourz");
        
        mp.remove(new MemHandle("Nonstop".getBytes().length + 2));
        //should remove No Role Modelz
        assertEquals(mp.get(new MemHandle("Nonstop".getBytes().length + "No Role Modelz".getBytes().length + 4)), "Love yourz");
        //love yourz should still be where it was
    }
    
    /**
     * tests to make sure insert inserts into the smallest freeblock that can fit
     */
    public void testRightFB()
    {
        
        mp.insert("Non".getBytes(), ((short)"Non".getBytes().length));
        assertEquals(mp.get(new MemHandle(0)), "Non");
        
        mp.insert("Modelz".getBytes(), ((short)"Modelz".getBytes().length)); 
        assertEquals(mp.get(new MemHandle("Non".getBytes().length + 2)), "Modelz");
        
        //will have to expand to fit Love yourz 
        mp.insert("Love".getBytes(), ((short)"Love".getBytes().length));
        assertEquals(mp.get(new MemHandle("Non".getBytes().length + "Modelz".getBytes().length + 4)), "Love");
        
        mp.insert("Up".getBytes(), ((short)"Up".getBytes().length));
        assertEquals(mp.get(new MemHandle("Non".getBytes().length + "Modelz".getBytes().length + "Love".getBytes().length + 6)), "Up");
        
        mp.insert("A".getBytes(), ((short)"A".getBytes().length));
        mp.insert("Tears".getBytes(), ((short)"Tears".getBytes().length));
        mp.insert("God".getBytes(), ((short)"God".getBytes().length));
        //after all these insertions the memory pool should have
        //memory handles at
        //5,13,19,23,26,33,38 bytes in respectively
        //which will all be of the following sizes
        //5,8,6,4,3,7,5
        //now we will remove the 8, 4, 7
        mp.remove(new MemHandle("Non".getBytes().length + 2));
        mp.remove(new MemHandle("Non".getBytes().length + "Modelz".getBytes().length + "Love".getBytes().length + 6));
        mp.remove(new MemHandle("Non".getBytes().length + "Modelz".getBytes().length + "Love".getBytes().length + "A".getBytes().length + "Up".getBytes().length + 10));
        mp.printOut();
        mp.dump();
        //I would recommend double checking the above actually did what it was supposed to
        //now if we try to insert a 4 character song, then it should go into the space where "Tears" was previously
        mp.insert("Risk".getBytes(), ((short)"Risk".getBytes().length));
        mp.printOut();
        mp.dump();
        assertEquals(mp.get(new MemHandle("Non".getBytes().length + "Modelz".getBytes().length + "Love".getBytes().length + "A".getBytes().length + "Up".getBytes().length + 10)), "Risk");
    }    
    
    /**
     * tests removal when only one thing is present, and
     * when it isn't
     */
    public void testRemove()
    {
        mp.insert("Non".getBytes(), ((short)"Non".getBytes().length));
        mp.remove(new MemHandle(0));
        assertEquals(mp.get(new MemHandle(0)), "Non");
        
        mp.insert("Non".getBytes(), ((short)"Non".getBytes().length));
        mp.insert("Stop".getBytes(), ((short)"Stop".getBytes().length));
        //mp.remove(new MemHandle(9999));
        //the above shouldn't throw an error
        mp.remove(new MemHandle("Non".getBytes().length + 2));
        assertEquals(mp.get(new MemHandle("Non".getBytes().length + 2)), "Stop");
    }
}
