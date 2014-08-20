public class Board {
    private final int N;
    private final int [][] b;
    
    public Board(int[][] blocks) {
        this.b = copy(blocks);  
        N = b.length;
    }
    
    // board dimension N
    public int dimension()  {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((this.b[i][j] != (i * N) + j + 1) && this.b[i][j] != 0) count++;
            }          
        }
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        int j1 = 0, i1 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.b[i][j] != 0) {
                    i1 = (this.b[i][j] - 1) / N;
                    j1 = (this.b[i][j] - 1) % N;
                    count = count + Math.abs(i - i1) + Math.abs(j - j1);
                }
            }          
        }
        return count;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return (this.manhattan() == 0);
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int swap;
        int [][] tmp;
        tmp = copy(this.b);
        
        outerloop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if ((tmp[i][j] != 0) && (tmp[i][j + 1] != 0)) {
                    swap = tmp[i][j];
                    tmp[i][j] = tmp[i][j + 1];
                    tmp[i][j + 1] = swap;
                    break outerloop;
                }
            }          
        }
        return new Board(tmp);
    }
    
  // does this board equal y?
    public boolean equals(Object y) {   
        if (this == y) return true;
        if (y == null) return false;
        
        // Comparison object must be of the same class.
        if (this.getClass() != y.getClass()) return false;
        
        final Board that = (Board) y;
        if (this.b.length != that.b.length) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.b[i][j] != that.b[i][j]) return false;
            }            
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<Board>();
        int [][] t;
        
        t = copy(this.b);   
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.b[i][j] == 0) {
                    if (i == 0) {
                        swap(t, i, j, i + 1, j);
                        s.push(new Board(t));
                        swap(t, i + 1, j, i, j);
                    }
                    else if (i == N - 1) {
                        swap(t, i, j, i - 1, j);
                        s.push(new Board(t));
                        swap(t, i - 1, j, i, j);
                    }
                    else {
                        swap(t, i, j, i - 1, j);
                        s.push(new Board(t));
                        swap(t, i - 1, j, i, j);
                        swap(t, i, j, i + 1, j);
                        s.push(new Board(t));
                        swap(t, i + 1, j, i, j);
                    }          
                    if (j == 0) {
                        swap(t, i, j, i, j + 1);
                        s.push(new Board(t));
                        swap(t, i, j + 1, i, j);
                    }
                    else if (j == N - 1) {
                        swap(t, i, j, i, j - 1);
                        s.push(new Board(t));
                        swap(t, i, j - 1, i, j);
                    }
                    else {
                        swap(t, i, j, i, j - 1);
                        s.push(new Board(t));
                        swap(t, i, j - 1, i, j);
                        swap(t, i, j, i, j + 1);
                        s.push(new Board(t));
                        swap(t, i, j + 1, i, j);
                    }
                }
            }          
        }
        return s;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str.append(String.format("%2d ", b[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }
    
    private static void swap(int [][] t, int i1, int j1, int i2, int j2) {
        int swap;
        swap = t[i1][j1];
        t[i1][j1] = t[i2][j2];
        t[i2][j2] = swap;
    }
    
    private int [][] copy(int[][] source) {
        int len = source.length;
        int [][] tmp = new int [len][len];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source.length; j++) {
                tmp[i][j] = source[i][j];
            }
        }
        return tmp;
    }
}
