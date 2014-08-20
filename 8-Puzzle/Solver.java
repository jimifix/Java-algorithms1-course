public class Solver { 
    private SearchNode result;

    // helper inner class
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int manhattan;
        private final int moves;
        private final int priority;
        private SearchNode previous;
        
        public SearchNode(Board b, SearchNode p) {
            board = b;
            previous = p;
            manhattan = board.manhattan();
            if (previous == null) moves = 0;
            else moves = previous.moves + 1;
            priority = moves + manhattan;    
        }

        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return 1;
            return 0;
        }       
    }
        
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial.isGoal()) 
            result = new SearchNode(initial, null);
        else 
            result = solve(initial, initial.twin());
    }
    
    private SearchNode solve(Board initial, Board twin) {
        SearchNode last;
        SearchNode lasttwin;
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> tpq = new MinPQ<SearchNode>();
        
        pq.insert(new SearchNode(initial, null));
        tpq.insert(new SearchNode(twin, null));
        
        while (true) {
            last = pq.delMin();
            for (Board neighbors:last.board.neighbors()) {
                if ((last.previous == null) 
                        || !neighbors.equals(last.previous.board))
                    pq.insert(new SearchNode(neighbors, last));
            }
            if (last.board.isGoal()) return last;
            
            lasttwin = tpq.delMin();
            for (Board neighbors:lasttwin.board.neighbors()) {
                if ((lasttwin.previous == null) 
                        || !neighbors.equals(lasttwin.previous.board))
                    tpq.insert(new SearchNode(neighbors, lasttwin));
            }
            if (lasttwin.board.isGoal()) return null;
        }  
    }       
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return result != null;
    }             
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (result != null)
            return result.moves;
        return -1;
    }                     
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {   
        if (result == null)
            return null;
        Stack<Board> s = new Stack<Board>();
        for (SearchNode node = result; node != null; node = node.previous)
            s.push(node.board);      
        return s;
    }
        
    // solve a slider puzzle (given below)    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
               blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
