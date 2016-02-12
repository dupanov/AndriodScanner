import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private QueweNode first, last, next;

    public Deque()                           // construct an empty deque
    {
        first  = new QueweNode(null);
        first.next = null;
        first.previous = null;
        last = first;
        last.next = null;
        last.previous = null;
        size = 0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        QueweNode oldFirst = first;
        first = new QueweNode(item);
        first.previous = null;
        oldFirst.previous = first;
        first.next = oldFirst;
        size++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        QueweNode oldLast = last;
        first = new QueweNode(item);
        oldLast.previous = first;
        first.previous = null;
        first.next = oldLast;
        size++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        Item firsItem = (Item) first.item;
        first = first.next;
        size--;
        return firsItem;

    }
    public Item removeLast()                 // remove and return the item from the end
    {
        Item lastItem = (Item) last.item;
        last = last.previous;
        size--;
        return lastItem;

    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        QueweNode current = first;
        Iterator iter = new Iterator() {

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            public boolean hasPrevious() {
                return current.previous != null;
            }

            @Override
            public Object next() {
                return current.next.item;
            }
            public Object previous() {
                return current.previous.item;
            }
        };
        return iter;
    }

    private class QueweNode<Item> {
        public QueweNode previous;
        Item item;
        public QueweNode next;

        private QueweNode(Item nodeitem) {
            item = nodeitem;
            next = null;
            previous = null;
        }
    }

    public static void main(String[] args)   // unit testing
    {
        Deque<Integer> deq = new Deque<>();
        deq.addFirst(5);
        System.out.println(deq.removeFirst());
        System.out.println(deq.size());
        for (int i = 0; i <10 ; i++) {
            deq.addFirst(i);
            deq.addLast(i);
        }
        Iterator iter = deq.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " " + deq.size());
        }

    }


}
