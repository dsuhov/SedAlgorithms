package sedalgs.part_one.puzzle8;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {
    private int isSolvable = 0;
    private Solution solution;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();

        MinPQ<SearchNode> gameTree = new MinPQ<>();
        MinPQ<SearchNode> gameTreeSolveCheck = new MinPQ<>();

        SearchNode candidate = new SearchNode(initial, 0, null);
        SearchNode candidateSolveCheck = new SearchNode(initial.twin(), 0, null);
        SearchNode previous;

        gameTree.insert(candidate);
        gameTreeSolveCheck.insert(candidateSolveCheck);

        while (isSolvable == 0) {
            if (!(candidate = gameTree.delMin()).isSolved()) {
                //System.out.println(candidate.board +""+ candidate.moves + " " + candidate.board.manhattan());
                previous = candidate.previousSN;
                if (previous == null) {
                    for (Board board : candidate.board.neighbors()) { gameTree.insert(new SearchNode(board, candidate.moves + 1, candidate)); }
                } else {
                    for (Board board : candidate.board.neighbors()) {
                        if (!board.equals(previous.board)) gameTree.insert(new SearchNode(board, candidate.moves + 1, candidate));
                    }
                }
            } else {
                solution = new Solution(candidate);
                isSolvable = 1;
                break;
            }

            if (!(candidateSolveCheck = gameTreeSolveCheck.delMin()).isSolved()) {
                previous = candidateSolveCheck.previousSN;
                if (previous == null) {
                    for (Board board : candidateSolveCheck.board.neighbors()) { gameTreeSolveCheck.insert(new SearchNode(board, candidateSolveCheck.moves + 1, candidateSolveCheck)); }
                } else {
                    for (Board board : candidateSolveCheck.board.neighbors()) {
                        if (!board.equals(previous.board)) gameTreeSolveCheck.insert(new SearchNode(board, candidateSolveCheck.moves + 1, candidateSolveCheck));
                    }
                }
            } else {
                isSolvable = -1;
                break;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (isSolvable == 0) throw new IllegalArgumentException("Something went wrong!");
        return isSolvable == 1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {return  -1;}
        return solution.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previousSN;

        SearchNode (Board board, int moves, SearchNode previousSN) {
            this.board = board;
            this.moves = moves;
            this.previousSN = previousSN;
        }

        @Override
        public int compareTo(SearchNode o) {
            int thisPriority = pf();
            int oPriority = o.pf();
            if (thisPriority > oPriority) return +1;
            else if (thisPriority == oPriority) return 0;
            return -1;
        }

        int pf() {
            return this.board.manhattan() + this.moves;
        }

        boolean isSolved() {
            return this.board.isGoal();
        }
    }

    private class Solution implements Iterable<Board> {
        LinkedStack<Board> boards;
        int moves;
        Solution(SearchNode node) {
            boards = new LinkedStack<>();
            this.moves = node.moves;
            while (node.previousSN.previousSN != null) {
                boards.push(node.board);
                node = node.previousSN;
            }
        }
        int getMoves() {return moves;}

        @Override
        public Iterator<Board> iterator() {
            return boards.iterator();
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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