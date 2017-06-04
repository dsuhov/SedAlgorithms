/**
 * Created by dsuhov on 03.06.2017.
 */

package sedalgs.part_one.collpoints;
//TODO delete package and In-Out classes before submit

import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

        Point[] sortedPoints = points.clone();

        //sorting array of points make unnecessary  min-max arrangement in {@code for}
        Arrays.sort(sortedPoints);
        final int N = points.length;

        // list will contain found segments
        LinkedList<LineSegment> ls = new LinkedList<>();


        for (int a = 0; a < N - 3; a++) {
            Point APoint = sortedPoints[a];

            for (int b = a + 1; b < N - 2; b++) {
                Point BPoint = sortedPoints[b];
                double slopeAB = APoint.slopeTo(BPoint);

                for (int c = b + 1; c < N - 1; c++) {
                    Point CPoint = sortedPoints[c];
                    double slopeAC = APoint.slopeTo(CPoint);

                    if (slopeAB == slopeAC) {
                        for (int d = c + 1; d < N - 1; d++) {
                            Point DPoint = sortedPoints[d];
                            double slopeAD = APoint.slopeTo(DPoint);
                            if (slopeAB == slopeAD) {
                                ls.add(new LineSegment(APoint, DPoint));
                            }
                        }
                    }
                }

            }
        }
        lineSegments = ls.toArray(new LineSegment[0]);
    }


    public int numberOfSegments() { return lineSegments.length; }

    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}