public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String item;
        
         int k = Integer.parseInt(args[0]);
         
         while(!StdIn.isEmpty()) {
             item = StdIn.readString();
             rq.enqueue(item);
         }
         for (int i = 0; i < k; i++) {
             StdOut.println(rq.dequeue());
         }
    }
}
