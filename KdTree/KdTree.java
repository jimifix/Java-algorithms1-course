public class KdTree {
    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = 0.0;
    private static final double YMAX = 1.0;
    
    private Node root;
    private int size;
    
    private static class Node {
        private Point2D p;      
        private RectHV rect;    
        private Node lb;        
        private Node rt;        
        
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
    
    // construct an empty Kd Tree
    public KdTree() {
        size = 0;
    }
    
    // is the Kd Tree empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // number of points in the Kd Tree
    public int size() {
        return size;
    }
    
    // add the point p to the Kd Tree (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)) root = insert(root, p, XMIN, YMIN, XMAX, YMAX, 0);
    }
    
    private Node insert(Node n, Point2D point, double xmin, double ymin, 
                        double xmax, double ymax, int lvl) {
        boolean cmp;
        if (n == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (lvl % 2 == 0) {
            cmp = point.x() < n.p.x();
            if (cmp) n.lb = insert(n.lb, point, xmin, ymin, n.p.x(), ymax, lvl + 1);
            else n.rt = insert(n.rt, point, n.p.x(), ymin, xmax, ymax, lvl + 1);
        }
        else {
            cmp = point.y() < n.p.y();
            if (cmp) n.lb = insert(n.lb, point, xmin, ymin, xmax, n.p.y(), lvl + 1);
            else n.rt = insert(n.rt, point, xmin, n.p.y(), xmax, ymax, lvl + 1);
        }
        return n;
    }
    
    // does the Kd Tree contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, 0) != null;
    }
    
    private Point2D contains(Node n, Point2D point, int lvl) {
        boolean cmp;
        if (n == null) return null;
        if (point.compareTo(n.p) == 0) return n.p; 
        if (lvl % 2 == 0) cmp = point.x() < n.p.x();
        else cmp = point.y() < n.p.y(); 
        if (cmp) return contains(n.lb, point, lvl + 1);
        else return contains(n.rt, point, lvl + 1); 
    }
    
    // draw all of the points to standard draw
    public void draw() {
            draw(root, 0);
    }
    
    private void draw(Node n, int lvl) {
        if (n != null) {
            draw(n.lb, lvl + 1);
            
            StdDraw.setPenRadius();
            if (lvl % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            
            n.p.draw();
            
            draw(n.rt, lvl + 1);
        }
    }
    
     // all points in the Kd Tree that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> s = new Stack<Point2D>();    
        searchRange(root, rect, s);
        return s;
    }
    
    private void searchRange(Node n, RectHV rect, Stack<Point2D> s) {
        if (n != null && n.rect.intersects(rect)) {
            if (rect.contains(n.p)) s.push(n.p);
            
            searchRange(n.lb, rect, s);
            searchRange(n.rt, rect, s);
        } 
    }
    
    // a nearest neighbor in the Kd Tree to p; null if Kd Tree is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        
        Point2D minP = root.p;
        
        minP = nearestSearch(root, p, minP);
        return minP;
    }
    
    private Point2D nearestSearch(Node n, Point2D point, Point2D minP) {
        
        if (n != null) {
            if (minP.distanceSquaredTo(point) >= n.rect.distanceSquaredTo(point)) {
                if (minP.distanceSquaredTo(point) > n.p.distanceSquaredTo(point)) {
                    minP = n.p;
                }
                
                if (n.lb != null && n.lb.rect.contains(point)) {
                    minP = nearestSearch(n.lb, point, minP);
                    minP = nearestSearch(n.rt, point, minP);
                }
                else {
                    minP = nearestSearch(n.rt, point, minP);
                    minP = nearestSearch(n.lb, point, minP);
                }
            }
        }
        return minP;
    }
}
