//TODO delete package before submission
package sedalgs.part_one.collpoints;

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    //TODO write test for all cases;
    //TODO check memory test and timing for slope, maybe this code should be more optimised
    public double slopeTo(Point that) {
        double d;
        if ( (this.y == that.y) && (this.x == that.x)) return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y) return +0.0;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        d = (double)(that.y - this.y)/(double)(that.x - this.x);
        return d;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    // TODO write test
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.y == that.y) {
            return this.x < that.x ? -1 : 1;
        } else return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if ((x == p1.x && x == p2.x) || (y == p1.y && y == p2.y) ) return 0;
            if ( ( (double)(p1.y - y)/(double)(p1.x - x) ) < ( (double)(p2.y - y)/(double)(p2.x - x) ) ) return -1;
            return 1;
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        System.err.println("Test A1.\n\tCreate some points.");
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 0);
        Point p3 = new Point(0, 6);
        Point p4 = new Point(56, 44);
        Point p5 = new Point(44, 56);
        Point p6 = new Point(34, 234);
        Point p7 = new Point(34, 44);
        Point p8 = new Point(18, 98);
        Point p9 = new Point(78, 7);
        Point p10 = new Point(899, 789);
        System.err.println("p1:" + p1 + "; p2:" + p2 + "; p3:" + p3 + "; p4:" + p4 + "; p5:" + p5  + "; p6:" + p6
                + "; p7:" + p7  + "; p8:" + p8  + "; p9:" + p9  +  "; p10:" + p10);
        System.err.println("Input: " + p4 + ", " + p5 + " Вовзращает NEGATIVE_INFINITY? " + (Double.NEGATIVE_INFINITY == p4.slopeTo(p5)));
        System.err.println(p1.slopeTo(p3));
    }
}