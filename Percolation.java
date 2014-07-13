public class Percolation {
    private boolean[][] grid;
    private int N;
    private int[] id; // id[i] = parent of i
    private int[] sz; // sz[i] = number of objects in subtree rooted at i

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grid = new boolean[N][N];
        id = new int[N * N + 2];
        sz = new int[N * N + 2];
        for (int i = 0; i < N * N + 2; i++) {
            id[i] = i;
            sz[i] = 1;
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = false;
    }

    // Homework API
    public void open(int row, int col) {
        int i = row -1;
        int j = col -1;
        // check if node is within bounds
        if (this.isWithinBounds(i, j)) {      
            // open site (row i, column j) if it is not already
            if (!isOpen(row, col)) { // check if position if open
                
                grid[i][j] = true; // if not open it

                // check if element is on the top layer e.g. i = 0 and connect
                // it to
                // the virtual top node
                if (i == 0) {
                    this.union(this.xy21D(i, j), 0);
                }

                // check if element is on the bottom layer e.g. i = N -1 and
                // connect
                // it to the virtual bottom node
                if (i == N - 1) {
                    this.union(this.xy21D(i, j), N * N + 1);
                }
                // check if right element is open and within bounds and union it
                // to
                // the
                // current one
                if (this.isWithinBounds(i, j + 1)) {
                    if (this.isOpen(row, col + 1)) {
                        this.union(this.xy21D(i, j + 1), this.xy21D(i, j));
                    }
                }
                // check if bottom element is open and within bounds and union
                // it to
                // the
                // current one
                if (this.isWithinBounds(i + 1, j)) {
                    if (this.isOpen(row + 1, col)) {
                        this.union(this.xy21D(i + 1, j), this.xy21D(i, j));
                    }
                }
                // check if left element is open and within bounds and union it
                // to
                // the
                // current one
                if (this.isWithinBounds(i, j - 1)) {
                    if (this.isOpen(row, col - 1)) {
                        this.union(this.xy21D(i, j - 1), this.xy21D(i, j));
                    }
                }
                // check if top element is open and within bounds and union it
                // to
                // the current
                // one if
                if (this.isWithinBounds(i - 1, j)) {
                    if (this.isOpen(row - 1, col)) {
                        this.union(this.xy21D(i - 1, j), this.xy21D(i, j));
                    }
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isOpen(int row, int col) {
        int i = row -1;
        int j = col -1;
        // is site (row i, column j) open?
        if (this.isWithinBounds(i, j)) {
            if (grid[i][j]) {
                return true;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
        return false;

    }

    public boolean isFull(int row, int col) {
        int i = row -1;
        int j = col -1;
        
        // is site (row i, column j) full?
        if (this.isWithinBounds(i, j)) {
            if (isOpen(row, col)) {
                if (this.isConnected(this.xy21D(i, j), 0))
                    return true;
            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
        return false;
    }

    public boolean percolates() {
        // does the system percolate?
        if (this.isConnected(0, N * N + 1)) {
            return true;
        }
        return false;
    }

    // Additional functions from WeightedQuickUnionUF
    
    private boolean isWithinBounds(int i, int j) {
        if ((i >= 0) && (i < N) && (j >= 0) && (j < N)) 
            return true;
        return false;
        
    }

    private int xy21D(int i, int j) {
        if (this.isWithinBounds(i, j)) {
            return i * N + j + 1;
        }
        return -1;
    }

    private int find(int p) {
        int copyP = p;
        while (copyP != id[copyP])
            copyP = id[copyP];
        return copyP;
    }

    
    private boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

   
    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;

        // make smaller root point to larger one
        if (sz[rootP] < sz[rootQ]) {
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        } else {
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
        

    }
}
