import java.util.Comparator;
public class Point implements Comparable<Point> {
    
   // compare points by slope to this point 
   public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();        
   
   private final int x;
   private final int y;
   // construct the point (x, y)
   public Point(int x, int y) {
       this.x = x;
       this.y = y;
   }
   
   private class SlopeOrder implements Comparator<Point> {
       public int compare(Point p, Point q) {
           if (slopeTo(p) < slopeTo(q)) return -1;
           if (slopeTo(p) > slopeTo(q)) return 1;
           return 0;
       }
   }
   
   // draw this point
   public   void draw() {
       StdDraw.point(x, y);
   }
   
   // draw the line segment from this point to that point
   public   void drawTo(Point that) {
       StdDraw.line(this.x, this.y, that.x, that.y);
   }
   
   // string representation
   public String toString() {
       return "(" + x + ", " + y + ")";
   }
   
   // is this point lexicographically smaller than that point?
   public int compareTo(Point that) {
       if (this.y < that.y) return -1;
       if (this.y > that.y) return 1;
       if (this.x < that.x) return -1;
       if (this.x > that.x) return 1;
       return 0;
   }
   
   // the slope between this point and that point
   public double slopeTo(Point that) {
       if ((that.x == this.x) && (that.y != this.y)) return Double.POSITIVE_INFINITY;
       if ((that.y == this.y) && (that.x != this.x)) return 0.0;
       if ((that.x == this.x) && (that.y == this.y)) return Double.NEGATIVE_INFINITY;
       return (double)(that.y - this.y)/(that.x - this.x);
   }
}
