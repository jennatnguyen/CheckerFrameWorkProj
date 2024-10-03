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
    public static class Node<E>
    {
        private @Nullable Node<E> next;
        private @Nullable Node<E> previous;
        private @Nullable E data;

        /**
         * Creates a new node with the given data
         *
         * @param d the data to put inside the node
         */
        public Node(@Nullable E d)
        {
            data = d;
            next = null;
            previous = null;
        }

        /**
         * Sets the node after this node
         *
         * @param n the node after this one
         */
        public void setNext(@Nullable Node<E> n)
        {
            next = n;
        }

        /**
         * Sets the node before this one
         *
         * @param n the node before this one
         */
        public void setPrevious(@Nullable Node<E> n)
        {
            previous = n;
        }

        /**
         * Gets the next node
         *
         * @return the next node
         */
        public @Nullable Node<E> next()
        {
            return next;
        }

        /**
         * Gets the node before this one
         *
         * @return the node before this one
         */
        public @Nullable Node<E> previous()
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
     
     
   
    public DLList()
    {
        head = new DLList.Node<E>(null);
    	tail = new DLList.Node<E>(null);
    	head.setNext(tail);
    	tail.setPrevious(head);
    	size = 0;
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
        
        // make sure it returns non null
 	Node<E> previousNode = nodeAfter.previous();  // Store the previous node
    	if (previousNode != null) {
        	addition.setPrevious(previousNode);
        	previousNode.setNext(addition);  // Now dereference the local variable
    	}

        addition.setNext(nodeAfter);
        nodeAfter.setPrevious(addition);
        size++;

    }

    /**
     * gets the node at that index
     * 
     * @param index
     * @return node at index
     */
	 private Node<E> getNodeAtIndex(int index) {
	    if (index < 0 || size() <= index) {
		throw new IndexOutOfBoundsException("No element exists at " + index);
	    }

	    Node<E> current = head.next(); // as we have a sentinel node
	    for (int i = 0; i < index; i++) {
		if (current == null) {
		    throw new IllegalStateException("Unexpected null node encountered while traversing.");
		}
		current = current.next();
	    }

	    if (current == null) {
		throw new IllegalStateException("No node found at the given index.");
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
		if (current == null) {
		    throw new IllegalStateException("Unexpected null node encountered.");
		}

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
	public boolean remove(int index) {
	    Node<E> nodeToBeRemoved = getNodeAtIndex(index);

	    // If there's a previous node, set its next to the next node of the one being removed
	    Node<E> previousNode = nodeToBeRemoved.previous();
	    Node<E> nextNode = nodeToBeRemoved.next();

	    if (previousNode != null && nextNode != null) {
		previousNode.setNext(nextNode);  // Call only when both nodes are non-null
		nextNode.setPrevious(previousNode);
	    } else if (previousNode != null) {
		previousNode.setNext(null);  // Handle the case when nextNode is null
	    } else if (nextNode != null) {
		nextNode.setPrevious(null);  // Handle the case when previousNode is null
	    }

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
    Node<E> current = head.next();  // Start at the first actual node

    // Loop until we reach the tail
    while (current != null && !current.equals(getTail())) {
        E currentData = current.getData(); // Get the data in the current node

        // Check if currentData is non-null and matches the object to be removed
        if (currentData != null && currentData.equals(obj)) {
            Node<E> previousNode = current.previous();
            Node<E> nextNode = current.next();

            // Set the next of the previous node if it's not null
            if (previousNode != null) {
                previousNode.setNext(nextNode);
            }

            // Set the previous of the next node if it's not null
            if (nextNode != null) {
                nextNode.setPrevious(previousNode);
            }

            size--;
            return true;  // Element found and removed
        }

        // Move to the next node
        current = current.next();
    }
    return false;  // Element not found
}


    /**
     * Returns a string representation of the list If a list contains A, B, and C,
     * the following should be returned "{A, B, C}" (Without the quotations)
     *
     * @return a string representing the list
     */
	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    if (!isEmpty()) {
		Node<E> currNode = head.next(); // Start with the first actual node
		while (currNode != null && !currNode.equals(getTail())) { // Check if currNode is null
		    E element = currNode.getData(); // Safely access currNode's data
		    if (element != null) {
		        builder.append(element.toString());
		        // Store the next node in a variable
		        Node<E> nextNode = currNode.next(); 
		        
		        // Check if nextNode is not null and not equal to tail
		        if (nextNode != null && !nextNode.equals(getTail())) { 
		            builder.append(" -> ");
		        }
		    }
		    currNode = currNode.next(); // Move to the next node
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
   	public boolean hasNext() {
        // Ensure next is not null before checking next nodes
        	if (next == null) {
            	return false;
        	}
        	Node<E> nextNode = next.next(); // Safe to access next node now
        	return nextNode != null && nextNode.getData() != null;
    	}

        /**
         * Gets the next value in the list
         *
         * @return the next value
         * @throws NoSuchElementException if there are no nodes left in the list
         */
	@Override
	public E next() {
	    // Check if next is not null before proceeding
	    if (next == null) {
		throw new NoSuchElementException();
	    }

	    // Safely move to the next node
	    Node<E> tempNext = next.next(); // Store next node in a temporary variable
	    if (tempNext == null) {
		throw new NoSuchElementException(); // If it's null, throw exception
	    }
	    next = tempNext; // Assign tempNext to next

	    E nextData = next.getData(); // Get the data
	    if (nextData == null) {
		throw new NoSuchElementException(); // Check if data is null
	    }
	    
	    calledNext = true; // Mark next called
	    return nextData; // Return the data
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
	 public boolean hasNext() {
		// Check for null before dereferencing
		if (next == null) {
		    return false;
		}
		Node<E> prevNode = next.previous(); // Safe to access previous node now
		return prevNode != null && prevNode.getData() != null;
	 }

        /**
         * Gets the next value in the list
         *
         * @return the next value
         * @throws NoSuchElementException if there are no nodes left in the list
         */
	@Override
	public E next() {
	    // Check if next is null before proceeding
	    if (next == null) {
		throw new NoSuchElementException();
	    }

	    // Safely move to the previous node
	    Node<E> tempPrev = next.previous(); // Store previous node in a temporary variable
	    if (tempPrev == null) {
		throw new NoSuchElementException(); // If it's null, throw exception
	    }
	    next = tempPrev; // Assign tempPrev to next

	    E nextData = next.getData(); // Get the data
	    if (nextData == null) {
		throw new NoSuchElementException(); // Check if data is null
	    }

	    calledNext = true; // Mark next called
	    return nextData; // Return the data
	}
    }
}
