package sedalgs.part_one.collpoints;
//TODO delete package
/**
 * Created by dsuhov on 03.06.2017.
 */

import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    LinkedQueue<LineSegment> ls;
    public BruteCollinearPoints(Point[] points) {

        ls = new LinkedQueue<>();
        for (int a = 0; a < points.length - 3; a++) {
            for (int b = a + 1; b < points.length - 2; b++) {
                for (int c = b + 1; c < points.length - 1; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        if ( (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])) &&
                                (points[a].slopeTo(points[c]) == points[a].slopeTo(points[d]))) {
                            //System.err.println(points[a] + " " + points[b] + " " + points[c] + " " + points[d] );
                            //System.err.println(points[a].slopeTo(points[b]) + " " + points[a].slopeTo(points[c]) + " " + points[a].slopeTo(points[d]) + "\n");
                            Point[] points4 = new Point[4];
                            Point min, max;
                            points4[0] = points[a];
                            points4[1] = points[b];
                            points4[2] = points[c];
                            points4[3] = points[d];

                            min = points4[0];
                            max = points4[0];
                            //если первый элемент массива больше любого из следующих, добавить его в min
                            for (int i = 1; i < 4; i++) {
                                if (min.compareTo(points4[i]) == 1) min = points4[i];
                            }
                            for (int i = 1; i < 4; i++) {
                                if (max.compareTo(points4[i]) == -1) max = points4[i];
                            }


                            ls.enqueue(new LineSegment(min, max));
                        }
                    }
                }

            }
        }
    }
    public int numberOfSegments() { return ls.size(); }

    public LineSegment[] segments() {
        int num = numberOfSegments();
        LineSegment[] lss = new LineSegment[num];
        for (int i = 0; i < num; i++) {
            lss[i] = ls.dequeue();
        }
        return lss;
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