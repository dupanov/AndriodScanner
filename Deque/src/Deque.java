/******************************************************************************
 *  Compilation:  javac LinkedStack.java
 *  Execution:    java LinkedStack < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A generic deque, implemented using a linked list. Each deque
 *  element is of type Item.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *  The <tt>Deque</tt> class represents a first-in-first-out  (FIFO) double ends queue of
 *  generic items.
 *  It supports the usual <em>addFirst</em>, <em>addLast</em> and <em>removeFirst</em> and
 *  <em>removeLast</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a non-static nested class for
 *  linked-list nodes.
 *  The <em>push</em>, <em>pop</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Vadim Dupanov
 */

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;
    private Node next;

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return first == null;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new NullPointerException("Trying to add null element");
        Node oldFirst = first;
        first = new Node();
        first.previous = null;
        first.next = oldFirst;
        first.item = item;
        size++;
        if (size == 1) last = first;
        if (size > 1)
            first.next.previous = first;
    }

    public void addLast(Item item)           // add the item to the end
    {
       if (item == null) throw new NullPointerException("Trying to add null element");
       Node oldLast = last;
       last = new Node();
       last.next = null;
       last.previous = oldLast;
       last.item = item;
       size++;
       if (size > 1)
          last.previous.next = last;
       if (size == 1) first = last;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item firsItem;
        firsItem = (Item) first.item;
        size--;
        if (size > 0) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }
        return firsItem;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item lastItem;
        lastItem = (Item) last.item;
        size--;
        if (size > 0) {
            last = last.previous;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        return lastItem;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIteratort();
    }

    private class Node {
        private Node previous;
        private Item item;
        private Node next;
    }

    public static void main(String[] args) // unit testing
    {

    }

    private class DequeIteratort implements Iterator<Item> {
        private Node current = first;

        /**
         * Returns {@code true} if the iteration forwards has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration forwards has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns {@code true} if the iteration backwards has more elements.
         * (In other words, returns {@code true} if {@link #previous} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration backwards has more elements
         */
        public boolean hasPrevious() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration forwards has no more elements
         */
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements to return");
            Item item = (Item) current.item;
            current = current.next;
            return item;
        }

        /**
         * Returns the previous element in the iteration.
         *
         * @return the previous element in the iteration
         * @throws NoSuchElementException if the iteration backwards has no more elements
         */
        public Item previous() {
            if(!hasPrevious()) throw new NoSuchElementException ("No more elements to return");
            Item item = (Item) current.item;
            current = current.previous;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException ("Unsupported operation");
        }
    }
}




