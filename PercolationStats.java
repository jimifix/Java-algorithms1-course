public class PercolationStats {
    private double [] xt;

   /**
   * Perform T independent computational experiments on an N-by-N grid
   */
    public PercolationStats(int N, int T) {
        int i, j;
        double t;
        
        if (N <= 0) throw new IllegalArgumentException(Integer.toString(N));
        if (T <= 0) throw new IllegalArgumentException(Integer.toString(T));
        
        xt = new double[T+1];
        
        for (int k = 1; k <= T; k++) {
            Percolation percolation = new Percolation(N);
            t = 0;
            while (!percolation.percolates()) {
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    percolation.open(i, j);
                    t = t + 1;
                }
            }
            xt[k] = t / (N * N);          
        }
        
    }
    
   /**
   * Sample mean of percolation threshold
   */
    public double mean() {
        return StdStats.mean(xt);
    }
    
   /**
   * Sample standard deviation of percolation threshold
   */
    public double stddev() {
        return  StdStats.var(xt);
    }
    
   /**
   * Returns lower bound of the 95% confidence interval
   */
    public double confidenceLo() {
       return mean() - (1.96 * stddev()/Math.sqrt(xt.length));
    }
        
   /**
   * Returns upper bound of the 95% confidence interval
   */
    public double confidenceHi() {
       return mean() + (1.96 * stddev()/Math.sqrt(xt.length));
    }
    
   /**
   * Test client, described below
   */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        //Stopwatch stopwatch = new Stopwatch();
        
        PercolationStats percStats = new PercolationStats(N, T);
        
        StdOut.printf("mean = %f\n", percStats.mean());
        StdOut.printf("stddev = %f\n", percStats.stddev());
        StdOut.print("95% confidence interval = ");
        StdOut.print(percStats.confidenceLo());
        StdOut.print(", ");
        StdOut.println(percStats.confidenceHi());
        
        //double time = stopwatch.elapsedTime();
        //StdOut.print("StopWatch = ");
        //StdOut.println(time);
    }
}
