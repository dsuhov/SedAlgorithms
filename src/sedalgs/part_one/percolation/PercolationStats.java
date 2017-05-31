package sedalgs.part_one.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double[] xt;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        xt = new double[trials];

        for (int i = 0; i < trials; i++) {
            double openSites = 0.0;
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int p, q;
                p = StdRandom.uniform(n)+1;
                q = StdRandom.uniform(n)+1;
                if (!perc.isOpen(p, q)) {
                    perc.open(p, q);
                    openSites++;
                }

            }
            xt[i] = openSites/(n*n);
        }
    }

    public double mean() {
        return StdStats.mean(xt);
    }

    public double stddev() {
        if (xt.length == 1) return Double.NaN;
        return StdStats.stddev(xt);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(xt.length);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(xt.length);
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("5% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
        System.out.println(sw.elapsedTime());
    }
}
