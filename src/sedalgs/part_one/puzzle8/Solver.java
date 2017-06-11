package sedalgs.part_one.puzzle8;

import edu.princeton.cs.algs4.*;
import java.util.Iterator;

public class Solver {
    private MinPQ<SearchNode> gameTree;
    private MinPQ<SearchNode> gameTreeSolveCheck;
    private SearchNode solution;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        gameTree.insert(new SearchNode(null, 0, initial));
        gameTreeSolveCheck.insert(new SearchNode(null, 0, initial.twin()));

        SearchNode sNodeOne;
        SearchNode sNodeTwo;
        while (solution == null) {
            if ((sNodeOne = gameTree.min()).isSolved()) {
                solution = sNodeOne;
                isSolvable = true;
                break;
            }
            if ((sNodeTwo = gameTreeSolveCheck.min()).isSolved()) {
                solution = sNodeTwo;
                isSolvable = false;
                break;
            }
            makeMove(sNodeOne);
            makeMove(sNodeTwo);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return solution.getMoves();
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;
        return new Solution();
    }

    private void makeMove(SearchNode sn) {
        SearchNode prevSN = sn.gerPrvious();
        for (Board board : sn.getNeighbors()) {
            if (!board.equals(sn.getBoard())) {
                gameTree.insert(new SearchNode(sn, sn.getMoves() + 1, board));
                gameTreeSolveCheck.insert(new SearchNode(sn, sn.getMoves() + 1, board));
            }
        }
    }

    private class SearchNode implements Comparable<Board> {

        SearchNode previousNode;
        int moves;
        Board board;

        SearchNode(SearchNode previousNode, int moves, Board board) {
            this.previousNode = previousNode;
            this.moves = moves;
            this.board = board;
        }

        boolean isSolved() {
            return this.board.isGoal();
        }

        @Override
        public int compareTo(Board o) {
            return this.board.manhattan() > o.manhattan() ? 1 :
                    this.board.manhattan() < o.manhattan() ? -1 : 0;
        }

        public SearchNode gerPrvious() {
            return previousNode;
        }

        public int getMoves() {
            return moves;
        }

        public Iterable<Board> getNeighbors() {
            return board.neighbors();
        }

        public Board getBoard() {
            return board;
        }
    }

    private class Solution implements Iterable<Board> {
        LinkedQueue<Board> boards = new LinkedQueue<>();
        SearchNode sn = solution;

        Solution() {
            while (sn != null) {
                boards.enqueue(sn.getBoard());
                sn = sn.gerPrvious();
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return boards.iterator();
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        /*In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);*/
        Board initial = new Board(new int[][] { {5, 2, 3}, {4, 0, 6}, {7, 8, 1} });
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
