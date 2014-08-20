import java.util.Arrays;
public class Brute {
    
    public static void main(String[] args) {
     // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        double pq, pr, ps;

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
             
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();     
            points[i] = p; 
        }

        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {
                        pq=points[i].slopeTo(points[j]); 
                        pr=points[i].slopeTo(points[k]);
                        ps=points[i].slopeTo(points[l]); 
                        if ((pq == pr) && (pq == ps)) {
                            points[i].drawTo(points[l]);
                            System.out.printf("%f -> %f -> %f\n",pq, pr,ps);
                            System.out.printf("%s -> %s -> %s -> %s\n",points[i].toString() , points[j].toString(), points[k].toString(), points[l].toString());
                        }
                    }
                }
            }
        }
       
        // display to screen all at once
        StdDraw.show(0);        
    }
}
