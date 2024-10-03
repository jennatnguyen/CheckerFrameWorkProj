import java.util.Iterator;
import java.util.NoSuchElementException;
import student.TestCase;
import org.checkerframework.checker.nullness.qual.*;
/**
 * @author Eric
 * @author maellis1
 * @author Shrey Patel (shreyp2305)
 * @version 2023.04.26
 *
 */

public class DLListTest extends TestCase
{
    /**
     * the list we will use
     */
     
    private DLList<String> list;
	public DLListTest() {
	    list = new DLList<>();
	}
    /**
     * run before every test case
     */
    @Override
    public void setUp()
    {
        list = new DLList<String>();
    }

    /**
     * Tests that an IndexOutOfBounds exception is thrown when the index is greater
     * than or equal to size and less than zero
     */
    public void testRemoveException()
    {
        list.add("A");
        Exception e = null;
        try
        {
            list.remove(2);
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try
        {
            list.remove(-1);
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }

    /**
     * Tests that objects can be removed at the beginning and end and that the size
     * is changed
     */
    public void testRemoveIndex()
    {
        list.add("A");
        list.add("B");
        assertTrue(list.remove(1));
        assertEquals(1, list.size());
        list.add("B");
        assertTrue(list.remove(0));
        assertEquals(1, list.size());
    }

    /**
     * Tests the add method. Ensures that it adds the object is added at the end and
     * the size is increased
     */
     //Because get() is nullable in dllist, we must make logic to make sure its not null in testadd
    public void testAdd()
    {
        assertEquals(0, list.size());
        list.add("A");
        assertEquals(1, list.size());
        list.add("B");
        assertEquals(2, list.size());
	String value = list.get(1);
	if (value == null) {
	    fail("Expected non-null value at index 1, but got null.");
	} else {
	    assertEquals("B", value);  // Safe to compare now
	}


    }



    /**
     * Tests that objects can be added at the beginning and end and that they are
     * placed correctly
     */
	public void testAddIndex()
	{
	    list.add("B");
	    list.add(0, "A");

	    // For "A"
	    String value0 = list.get(0);
	    if (value0 == null) {
		fail("Expected non-null value at index 0, but got null.");
	    } else {
		assertEquals("A", value0);
	    }

	    assertEquals(2, list.size());

	    list.add(2, "D");

	    // For "D"
	    String value2 = list.get(2);
	    if (value2 == null) {
		fail("Expected non-null value at index 2, but got null.");
	    } else {
		assertEquals("D", value2);
	    }

	    list.add(2, "C");

	    // For "C"
	    String valueC = list.get(2);
	    if (valueC == null) {
		fail("Expected non-null value at index 2, but got null.");
	    } else {
		assertEquals("C", valueC);
	    }
	}


    /**
     * This tests that the add method throws a null pointer exception when adding
     * null data to the list
     */
     @SuppressWarnings("nullness")  // Suppress the nullness warning for this test
    public void testAddNullException()
    {
        Exception e = null;
        try
        {
            list.add(null);
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }

    /**
     * This tests that the add method throws a Invalid argument when adding null
     * data to the list
     */
     @SuppressWarnings("nullness")  // Suppress the nullness warning for this test
    public void testAddIndexNullException()
    {
        Exception e = null;
        try
        {
            list.add(0, null);
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }

    /**
     * This tests when the add method is called and the index is greater than size
     * or less than zero
     */
    public void testAddException()
    {
        list.add("A");
        Exception e = null;
        try
        {
            list.add(2, "B");
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try
        {
            list.add(-1, "B");
        } catch (Exception exception)
        {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }

    /**
     * Tests removing a object changes the size appropiately and that you can remove
     * the first and last elements
     */
public void testRemoveObj()
{
    assertFalse(list.remove(null));  // Check removing null doesn't work
    list.add("A");
    list.add("B");
    assertTrue(list.remove("A"));

    // First occurrence of assertEquals("B", list.get(0));
    String value0 = list.get(0);
    if (value0 == null) {
        fail("Expected non-null value at index 0, but got null.");
    } else {
        assertEquals("B", value0);
    }

    assertEquals(1, list.size());

    list.add("C");
    assertTrue(list.remove("C"));

    // Second occurrence of assertEquals("B", list.get(0));
    String value1 = list.get(0);
    if (value1 == null) {
        fail("Expected non-null value at index 0, but got null.");
    } else {
        assertEquals("B", value1);
    }
}


    /**
     * Tests get when the index is greater than or equal to size and when the index
     * is less than zero
     */
    public void testGetException()
    {
        Exception exception = null;
        try
        {
            list.get(-1);
        } catch (Exception e)
        {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
        exception = null;
        list.add("A");
        try
        {
            list.get(1);
        } catch (IndexOutOfBoundsException e)
        {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }

    /**
     * Test contains when it does and does not contain the object
     */
    public void testContains()
    {
        assertFalse(list.contains("A"));
        list.add("A");
        assertTrue(list.contains("A"));
        assertFalse(list.contains("B"));
        list.add("B");
        assertTrue(list.contains("B"));
    }

    /**
     * Test lastIndexOf when the list is empty, when the object is not in the list,
     * and when it is at the beginning or end
     */
    public void testLastIndexOf()
    {
        assertEquals(-1, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(0, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(1, list.lastIndexOf("A"));
        list.add("B");
        assertEquals(1, list.lastIndexOf("A"));
        assertEquals(2, list.lastIndexOf("B"));
        list.add("A");
        assertEquals(3, list.lastIndexOf("A"));
    }

    /**
     * Tests isEmpty when empty and full
     */
    public void testIsEmpty()
    {
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }

    /**
     * Ensures that all of the objects are cleared and the size is changed
     */
    public void testClear()
    {
        list.add("A");
        list.clear();
        assertEquals(0, list.size());
        assertFalse(list.contains("A"));
    }

    /**
     * Tests the toString when there are 0, 1, and 2 objects in the list
     */
    public void testToString()
    {
        assertEquals("", list.toString());
        list.add("A");
        assertEquals("A", list.toString());
        list.add("B");
        assertEquals("A -> B", list.toString());
    }

    /**
     * Tests removing from an empty list
     */
    public void testRemoveFromEmpty()
    {
        list.add("dance");
        list.add(0, "safety");
        list.clear();
        assertFalse(list.remove("safety"));
        Exception exception;
        exception = null;
        try
        {
            list.remove(0);
        } catch (IndexOutOfBoundsException e)
        {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        DLList<String> emptyList = new DLList<String>();
        exception = null;
        try
        {
            emptyList.remove(0);
        } catch (IndexOutOfBoundsException e)
        {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }

    /**
     * Test case for all the methods in iterator
     */
    public void testIterator()
    {
        DLList<String> newList = new DLList<String>();
        for (int i = 0; i < 5; i++)
        {
            newList.add("name " + i);
        }
        Iterator<String> iter = newList.iterator();

        for (int i = 0; i < 5; i++)
        {
            assertTrue(iter.hasNext());
            assertEquals("name " + i, iter.next());
        }
        assertFalse(iter.hasNext());

        Exception thrown = null;
        try
        {
            iter.next();
        } catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);

    }

    /**
     * Test case for all the methods in iterator
     */
    public void testReverseIterator()
    {
        DLList<String> newList = new DLList<String>();
        for (int i = 0; i < 5; i++)
        {
            newList.add("name " + i);
        }
        Iterator<String> iter = newList.reverseIterator();

        for (int i = 4; i >= 0; i--)
        {
            assertTrue(iter.hasNext());
            assertEquals("name " + i, iter.next());
        }
        assertFalse(iter.hasNext());

        Exception thrown = null;
        try
        {
            iter.next();
        } catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);

    }

}
