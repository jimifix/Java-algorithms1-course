import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private DoubleNode first, last;
    
    private class DoubleNode {
        private Item item;
        private DoubleNode next;
        private DoubleNode previous;
    }
   /**
   * Construct an empty deque
   */ 
    public Deque() {
        first = null;
        last = null;
        N = 0;   
    }
       
   /**
   * Is the deque empty?
   */     
    public boolean isEmpty() {
        return N == 0;
    }
       
   /**
   * Return the number of items on the deque
   */       
    public int size() {
        return N;
    }
       
   /**
   * Insert the item at the front
   */   
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Can't add null");
        DoubleNode oldfirst = first;
        first  = new DoubleNode();
        first.item = item;
        first.next = oldfirst;
        first.previous = null;
        if (oldfirst != null) oldfirst.previous = first;
        if (N == 0) last = first;
        N++;
   }
       
   /**
   * Insert the item at the end
   */   
   public void addLast(Item item) {
       if (item == null) throw new NullPointerException("Can't add null");
       DoubleNode oldlast = last;
       last = new DoubleNode();
       last.item = item;
       last.previous = oldlast;
       if (oldlast != null) oldlast.next = last;
       if (N == 0) first = last;
       N++;
   }
       
   /**
   * Delete and return the item at the front
   */   
   public Item removeFirst() {
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       Item item = first.item;
       if (first.next != null) first = first.next;
       first.previous = null;
       N--;
       return item;
   }
       
   /**
   * Delete and return the item at the end
   */   
   public Item removeLast() {
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       Item item = last.item;
       if (last.previous != null)last = last.previous;
       last.next = null;
       N--;
       return item;
   }
       
   /**
   * Return an iterator over items in order from front to end
   */   
   public Iterator<Item> iterator() {
       return new FrontToEndIterator();
   }
   
   private class FrontToEndIterator implements Iterator<Item> {
       private DoubleNode current = first;
       public boolean hasNext() { return current != null; }
       public void remove() { throw new UnsupportedOperationException(); }
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
       
   /**
   * Unit testing
   */   
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) deque.addFirst(item);
            else if (!deque.isEmpty()) StdOut.print(deque.removeFirst());
        }
        StdOut.println("(" + deque.size() + " left on stack)");
        
        StdOut.println("1st queue");
        for (String s : deque) {
            StdOut.println(s);
        }
    }
}
