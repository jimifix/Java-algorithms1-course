import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;
    
    /**
    * Construct an empty randomized queue
    */   
    public RandomizedQueue() {
        a = (Item[]) new Object[2]; 
        N = 0;
    }
        
   /**
   * Is the queue empty?
   */   
    public boolean isEmpty() {
        return (N == 0);
    }
        
   /**
   * Return the number of items on the queue
   */   
    public int size() {
        return N;
    }
    
    private void resize(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tmp[i] = a[i];
        }
        a = tmp;   
    }
        
   /**
   * Add the item
   */   
    public void enqueue(Item item)  {
        if (item == null) throw new NullPointerException("Can't add null");
        if (a.length == N) resize(2 * a.length);
        a[N++] = item;
    }
        
   /**
   * Delete and return a random item
   */   
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int rnd = StdRandom.uniform(N);
        Item item = a[rnd];
        a[rnd] = a[--N]; 
        a[N] = null;
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }
        
   /**
   * Return (but do not delete) a random item
   */   
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return a[StdRandom.uniform(N)];        
    }
        
   /**
   * Return an independent iterator over items in random order
   */   
    public Iterator<Item> iterator() {
        return new RandomiseQueueIterator();
    }
    
    private class RandomiseQueueIterator implements Iterator<Item> {
        private int i;
        
        public RandomiseQueueIterator() {
            if (N == 0) throw new NoSuchElementException("Queue is empty");
            i = N;
            StdRandom.shuffle(a, 0, N - 1);
        }
        
        public boolean hasNext() { return i > 0; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() { 
            if (isEmpty()) throw new NoSuchElementException("Queue is empty");
            return a[--i]; 
        }
    }
        
   /**
   * Unit testing
   */   
    public static void main(String[] args) {
        RandomizedQueue<String> rndque = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) rndque.enqueue(item);
            else if (!rndque.isEmpty()) StdOut.print(rndque.dequeue());
        }

        StdOut.println("(" + rndque.size() + " left on stack)");
        StdOut.println(rndque.sample());
        
        StdOut.println("1st queue");
        for (String s : rndque) {
            StdOut.println(s);
        }
        
        StdOut.println("2st queue");
        for (String s : rndque) {
            StdOut.println(s);
        }
    }
}


