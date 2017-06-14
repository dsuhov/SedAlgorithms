package sedalgs.part_one.puzzle8;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;

public class Solver {
    int isSolvable = 0;
    Solution solution;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> gameTree = new MinPQ<>();
        MinPQ<SearchNode> gameTreeSolveCheck = new MinPQ<>();

        SearchNode candidate = new SearchNode(initial, 0, null);
        SearchNode candidateSolveCheck = new SearchNode(initial.twin(), 0, null);
        SearchNode previous;

        gameTree.insert(candidate);
        gameTreeSolveCheck.insert(candidateSolveCheck);

        while (isSolvable == 0) {
            if (!(candidate = gameTree.delMin()).isSolved()) {
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
//    public Iterable<Board> solution()

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
        SearchNode solution;
        Solution(SearchNode solution) {
            this.solution = solution;
        }
        int getMoves() {return solution.moves;}

        @Override
        public Iterator<Board> iterator() {
            return new Iterator<Board>() {
                SearchNode current;

                @Override
                public boolean hasNext() {
                    return (current = current.previousSN) != null;
                }

                @Override
                public Board next() {
                    return current.board;
                }
            };
        }
    }

    public static void main(String[] args) {
        Board b = new Board(new int[][] { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} });
//        Board b2 = new Board(new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} });
        Solver s = new Solver(b);

    }

}