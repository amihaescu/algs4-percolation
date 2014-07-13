public class PercolationStats {
    private double[] counts;
    private Percolation p;

    public PercolationStats(int N, int T) {
        // perform T independent computational experiments on an N-by-N grid
        if ((N <= 0) || (T <= 0)) {
            throw new IllegalArgumentException();
        }
        counts = new double[T];
        for (int i = 0; i < T; i++) {
            p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                int m = StdRandom.uniform(N) + 1;
                int n = StdRandom.uniform(N) + 1;
                if (!p.isOpen(m, n)) {
                    count++;
                    p.open(m, n);
                }

            }
            counts[i] = (double) count / (N * N);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        double sum = 0;
        for (int i = 0; i < counts.length; i++) {
            sum += counts[i];
        }

        return (double) sum / counts.length;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        double result = 0;
        double mean = this.mean();
        for (int i = 0; i < counts.length; i++) {
            result += ((counts[i] - mean) * (counts[i] - mean));
        }
        return (double) result / (counts.length - 1);
    }

    public double confidenceLo() {
        // returns lower bound of the 95% confidence interval
        return (double) this.mean() - 1.96 * this.stddev()
                / Math.sqrt(counts.length);
    }

    public double confidenceHi() {
        // returns upper bound of the 95% confidence interval
        return (double) this.mean() + 1.96 * this.stddev()
                / Math.sqrt(counts.length);
    }

    public static void main(String[] args) {
        // test client, described below
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        // PercolationStats ps = new PercolationStats(200,100);
        System.out.println("mean=" + ps.mean());
        System.out.println("stddev=" + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo()
                + ", " + ps.confidenceHi());
    }
}
