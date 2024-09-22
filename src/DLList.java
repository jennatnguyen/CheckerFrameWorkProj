import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.qual.*;
/**
 * This provides implementation for the LList methods.
 *
 * @author Mark Wiggans (mmw125)
 * @version 3/29/15
 * @author Eric Williamson
 * @version 10/30/15
 * @author maellis1
 * @version 11/1/15
 * @param <E> The type of object the class will store
 */
 
 //supressed due to irrelevancy to null checking
@SuppressWarnings("initialization.fields.uninitialized")
public class DLList<E> implements Iterable<E>
{

    /**
     * This represents a node in a doubly linked list. This node stores data, a
     * pointer to the node before it in the list, and a pointer to the node after it
     * in the list
     *
     * @param <E> This is the type of object that this class will store
     * @author Mark Wiggans (mmw125)
     * @version 4/14/2015
     */
    protected static class Node<E>
    {
        private Node<E> next;
        private Node<E> previous;
        private @Nullable E data;

        /**
         * Creates a new node with the given data
         *
         * @param d the data to put inside the node
         */
        public Node(@Nullable E d)
        {
            data = d;
        }

        /**
         * Sets the node after this node
         *
         * @param n the node after this one
         */
        public void setNext(Node<E> n)
        {
            next = n;
        }

        /**
         * Sets the node before this one
         *
         * @param n the node before this one
         */
        public void setPrevious(Node<E> n)
        {
            previous = n;
        }

        /**
         * Gets the next node
         *
         * @return the next node
         */
        public Node<E> next()
        {
            return next;
        }

        /**
         * Gets the node before this one
         *
         * @return the node before this one
         */
        public Node<E> previous()
        {
            return previous;
        }

        /**
         * Gets the data in the node
         *
         * @return the data in the node
         */
        public @Nullable E getData()
        {
            return data;
        }
    }

    /**
     * How many nodes are in the list
     */
    private int size;

    /**
     * The first node in the list. THIS IS A SENTINEL NODE AND AS SUCH DOES NOT HOLD
     * ANY DATA. REFER TO init()
     */
    private Node<E> head;

    /**
     * The last node in the list. THIS IS A SENTINEL NODE AND AS SUCH DOES NOT HOLD
     * ANY DATA. REFER TO init()
     */
    private Node<E> tail;

    /**
     * Create a new DLList object.
     */
      //supressed due to irrelevancy to null checking
  @SuppressWarnings("method.invocation")
    public DLList()
    {
        init();
    }

    /**
     * Initializes the object to have the head and tail nodes
     */
    private void init()
    {
        head = new DLList.Node<E>(null);
        tail = new DLList.Node<E>(null);
        head.setNext(getTail());
        getTail().setPrevious(head);
        size = 0;
    }

    /**
     * Checks if the array is empty
     *
     * @return true if the array is empty
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements
     */
    public int size()
    {
        return size;
    }

    /**
     * Removes all of the elements from the list
     */
    public void clear()
    {
        init();
    }

    /**
     * Checks if the list contains the given object
     *
     * @param obj the object to check for
     * @return true if it contains the object
     */
    public boolean contains(E obj)
    {
        return lastIndexOf(obj) != -1;
    }

    /**
     * Gets the object at the given position
     *
     * @param index where the object is located
     * @return The object at the given position
     * @throws IndexOutOfBoundsException if there no node at the given index
     */
    public @Nullable E get(int index)
    {
        return getNodeAtIndex(index).getData();
    }

    /**
     * Adds a element to the end of the list
     *
     * @param newEntry the element to add to the end
     */
    public void add(E newEntry)
    {
        add(size(), newEntry);
    }

    /**
     * Adds the object to the position in the list
     *
     * @param index where to add the object
     * @param obj   the object to add
     * @throws IndexOutOfBoundsException if index is less than zero or greater than
     *                                   size
     * @throws IllegalArgumentException  if obj is null
     */
    public void add(int index, E obj)
    {
        if (index < 0 || size < index)
        {
            throw new IndexOutOfBoundsException();
        }
        if (obj == null)
        {
            throw new IllegalArgumentException(
                    "Cannot add null " + "objects to a list");
        }

        Node<E> nodeAfter;
        if (index == size)
        {
            nodeAfter = getTail();
        } else
        {
            nodeAfter = getNodeAtIndex(index);
        }

        Node<E> addition = new Node<E>(obj);
        addition.setPrevious(nodeAfter.previous());
        addition.setNext(nodeAfter);
        nodeAfter.previous().setNext(addition);
        nodeAfter.setPrevious(addition);
        size++;

    }

    /**
     * gets the node at that index
     * 
     * @param index
     * @return node at index
     */
    private Node<E> getNodeAtIndex(int index)
    {
        if (index < 0 || size() <= index)
        {
            throw new IndexOutOfBoundsException(
                    "No element exists at " + index);
        }
        Node<E> current = head.next(); // as we have a sentinel node
        for (int i = 0; i < index; i++)
        {
            current = current.next();
        }
        return current;
    }

    /**
     * Gets the last time the given object is in the list
     *
     * @param obj the object to look for
     * @return the last position of it. -1 If it is not in the list
     */
	public int lastIndexOf(@Nullable E obj) {
	    Node<E> current = getTail().previous();
	    for (int i = size() - 1; i >= 0; i--) {
		E currentData = current.getData(); // Store in a variable
		if (currentData != null && currentData.equals(obj)) {
		    return i;
		}
		current = current.previous();
	    }
	    return -1;
	}

    /**
     * Removes the element at the specified index from the list
     *
     * @param index where the object is located
     * @throws IndexOutOfBoundsException if there is not an element at the index
     * @return true if successful
     */
    public boolean remove(int index)
    {
        Node<E> nodeToBeRemoved = getNodeAtIndex(index);
        nodeToBeRemoved.previous().setNext(nodeToBeRemoved.next());
        nodeToBeRemoved.next().setPrevious(nodeToBeRemoved.previous());
        size--;
        return true;
    }

    /**
     * Removes the first object in the list that .equals(obj)
     *
     * @param obj the object to remove
     * @return true if the object was found and removed
     */

 public boolean remove(@Nullable E obj) {
    Node<E> current = head.next();
    while (!current.equals(getTail())) {
        E currentData = current.getData(); // Store the data in a variable
        if (currentData != null && currentData.equals(obj)) {
            current.previous().setNext(current.next());
            current.next().setPrevious(current.previous());
            size--;
            return true;
        }
        current = current.next();
    }
    return false;
}


    /**
     * Returns a string representation of the list If a list contains A, B, and C,
     * the following should be returned "{A, B, C}" (Without the quotations)
     *
     * @return a string representing the list
     */
	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder("");
	    if (!isEmpty()) {
		Node<E> currNode = head.next();
		while (currNode != getTail()) {
		    E element = currNode.getData();
		    if (element != null) {
		        builder.append(element.toString());
		        if (currNode.next() != getTail()) {
		            builder.append(" -> ");
		        }
		    }
		    currNode = currNode.next();
		}
	    }
	    return builder.toString();
	}

    /**
     * Description to make iterator
     */
    @Override
    public Iterator<E> iterator()
    {
        return new DLListIterator<E>();
    }

    /**
     * Some comment
     * 
     * @return reverse iterator
     */
    public Iterator<E> reverseIterator()
    {
        return new RDLListIterator<E>();
    }

    /**
     * @return the tail
     */
    public Node<E> getTail()
    {
        return tail;
    }

    /**
     * @param tail the tail to set
     */
    public void setTail(Node<E> tail)
    {
        this.tail = tail;
    }

    private class DLListIterator<A> implements Iterator<E>
    {

        private Node<E> next;
        private boolean calledNext;

        /**
         * Creates a new DLListIterator
         */
        public DLListIterator()
        {
            next = head;
            calledNext = false;
        }

        /**
         * Checks if there are more elements in the list
         *
         * @return true if there are more elements in the list
         */
        @Override
        public boolean hasNext()
        {

            return next.next().getData() != null;

        }

        /**
         * Gets the next value in the list
         *
         * @return the next value
         * @throws NoSuchElementException if there are no nodes left in the list
         */
	@Override
	public E next() {
	    next = next.next();
	    E nextData = next.getData(); // Store the data in a variable
	    if (nextData == null) {
		throw new NoSuchElementException();
	    }
	    calledNext = true;
	    return nextData; // Return the stored data
	}

    }

    private class RDLListIterator<A> implements Iterator<E>
    {

        private Node<E> next;
        private boolean calledNext;

        /**
         * Creates a new DLListIterator
         */
        public RDLListIterator()
        {
            next = getTail();
            calledNext = false;
        }

        /**
         * Checks if there are more elements in the list
         *
         * @return true if there are more elements in the list
         */
        @Override
        public boolean hasNext()
        {

            return next.previous().getData() != null;

        }

        /**
         * Gets the next value in the list
         *
         * @return the next value
         * @throws NoSuchElementException if there are no nodes left in the list
         */
	@Override
	public E next() {
	    next = next.previous();
	    E nextData = next.getData(); // Store the data in a variable
	    if (nextData == null) {
		throw new NoSuchElementException();
	    }
	    calledNext = true;
	    return nextData; // Return the stored data
	}
    }
}
