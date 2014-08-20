import java.util.Arrays;
public class Fast {
    
    public static void main(String[] args) {
     // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] basepoints = new Point[N];
        Point[] points = new Point[N];
        Point[] segment = new Point[4];       
        double[] slope = new double[N];
        //double pq, pr, ps;
        Point p0;
      
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();     
            basepoints[i] = p; 
            points[i] = p;
        }

        for (int i = 0; i < N; i++) {
            p0 = basepoints[i];
            for (int j = 0; j < N; j++) {
                if (p0.compareTo(points[j]) != 0) slope[j]=p0.slopeTo(points[j]); 
            }
            Arrays.sort(points, p0.SLOPE_ORDER);
            int k = 1;
            while (k < N - 2) {
                //pq=points[0].slopeTo(points[k]); 
                //pr=points[k].slopeTo(points[k + 1]);
                //ps=points[k + 1].slopeTo(points[k + 2]); 
                if ((points[0].slopeTo(points[k]) == points[k].slopeTo(points[k + 1])) && (points[0].slopeTo(points[k]) == points[k + 1].slopeTo(points[k + 2]))) {
                    segment[0] = p0;
                    segment[1] = points[k];
                    segment[2] = points[k + 1];
                    segment[3] = points[k + 2];
                    Arrays.sort(segment);
                    if ((p0.compareTo(points[k]) < 0) && (p0.compareTo(points[k + 1]) < 0) && (p0.compareTo(points[k + 2]) < 0)){
                    segment[0].drawTo(segment[3]);
                    
                    //System.out.printf("%f -> %f -> %f\n",segment[0].slopeTo(segment[1]), segment[1].slopeTo(segment[2]),segment[2].slopeTo(segment[3]));
                    System.out.printf("%s -> %s -> %s -> %s\n",segment[0].toString() , segment[1].toString(), segment[2].toString(), segment[3].toString());
                    break;
                    }
                } 
                k++;
            }
           // }
        }
       

        // display to screen all at once
        StdDraw.show(0);
        
        
    }
}
