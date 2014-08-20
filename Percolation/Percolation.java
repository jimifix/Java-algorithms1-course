public class Percolation {
    private boolean[] op;
    private WeightedQuickUnionUF wqu;
    private int grid;
   /**
   * Create N-by-N grid, with all sites blocked
   */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException(Integer.toString(N));
        wqu = new WeightedQuickUnionUF(N*N+2);
        op = new boolean[N*N+2];
        grid = N;
        op[0] = true;
    }
    private int xyTo1D(int i, int j) {
        return ((i - 1) * grid) + j;
    }
   
   /**
   * Open site (row i, column j) if it is not already
   */
    public void open(int i, int j) {
        if (i < 1 || i > grid) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j < 1 || j > grid) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }
        int p = xyTo1D(i, j);
        int pup = xyTo1D(i-1, j);
        int pdw = xyTo1D(i+1, j);
        int plf = xyTo1D(i, j-1);
        int prg = xyTo1D(i, j+1);
        
        if (!op[p]) {
            op[p] = true;
            
            if (i == 1) wqu.union(0, p);
            if (i == grid) wqu.union(grid * grid + 1, p);
            
            if (i > 1) {
                if (isOpen(i-1, j)) wqu.union(pup, p);
            }
       
            if (i < grid) {
                if (isOpen(i+1, j)) wqu.union(pdw, p);
            }
          
            if (j > 1) {
                if (isOpen(i, j-1)) wqu.union(plf, p);
            }
         
            if (j < grid) {
                if (isOpen(i, j+1)) wqu.union(prg, p);
            } 
        }  
    }
    
   /**
   * Is site (row i, column j) open?
   */
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > grid) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j < 1 || j > grid) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }
        int p = xyTo1D(i, j);
        boolean flag;

        if (op[p]) flag = true;
        else flag = false;
        return flag;
    }
   /**
   * Is site (row i, column j) full?
   */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > grid) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j < 1 || j > grid) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }
        int p = xyTo1D(i, j);
        return  wqu.connected(p, 0);
    }
    
   /**
   * Does the system percolate?
   */
    public boolean percolates() {
        boolean flag = false;
       
        if (wqu.connected(grid * grid + 1, 0)) flag = true;
        return flag;
    }   
}
