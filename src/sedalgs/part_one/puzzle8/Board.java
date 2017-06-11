package sedalgs.part_one.puzzle8;

import edu.princeton.cs.algs4.LinkedQueue;
import java.util.Iterator;

/**
 * @author dsuhov
 */
public class Board {

    final int n;
    final int[][] blocks;
    final int[][] goal;

    /**
     * Funny constructor construct construction
     * @param blocks
     */
    public Board(int[][] blocks) {
        n = blocks.length;
        int[][] blocksNew = new int[n][n];
        int[][] blocksGoal = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                blocksNew[i][j] = blocks[i][j];
                blocksGoal[i][j] = (i*n) + j + 1;
            };
        blocksGoal[n-1][n-1] = 0;

        this.blocks = blocksNew;
        this.goal = blocksGoal;
    }

    public int dimension() {
        return n;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        int outOfPosition = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != goal[i][j]) outOfPosition++;
            }
        }
        return outOfPosition;
    }

    /**
     * @return the sum of the Manhattan distances (sum of the vertical
     * and horizontal distance) from the blocks to their goal positions.
     */
    public int manhattan() {
        int distances = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != goal[i][j]) {
                    distances += manhDiff(i, j);
                }
            }
        }
        return distances;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != goal[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int manhDiff(int row, int col) {
        int block = blocks[row][col];
        boolean flag = false;
        int diff = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (block == goal[i][j]) {
                    diff = Math.abs(row - i) + Math.abs(col - j);
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }
        return diff;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return Board
     */
    public Board twin() {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = blocks[i][j];
            }
        }
        int temp;
        temp = newBoard[0][0];
        newBoard[0][0] = newBoard[0][1];
        newBoard[0][1] = temp;

        return new Board(newBoard);
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        Board otherBoard = (Board) y;
        if (this.n != otherBoard.dimension()) return false;

        boolean flag = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != otherBoard.blocks[i][j]) flag = false;
            }
        }
        return flag;
    }

    /**
     *  all neighboring boards
     * @return Iterable<Board>
     */
    public Iterable<Board> neighbors() {
        int row = 0, col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        return new Neighbors(row, col);
    }

    private class Neighbors implements Iterable<Board> {
        private final int PASS = 4;
        LinkedQueue<Board> neighbors = new LinkedQueue<>();

        Neighbors(int zero_row, int zero_col) {
            swap(zero_row, zero_col, zero_row-1, zero_col); //top-down
            swap(zero_row, zero_col, zero_row, zero_col+1); //right-left
            swap(zero_row, zero_col, zero_row, zero_col-1); //left-right
            swap(zero_row, zero_col, zero_row+1, zero_col); //down-top
        }

        private void swap(int zero_row, int zero_col, int row, int col) {
            if ((row >= 0 && col >= 0) && (row < n && col < n)) {
                int[][] tempArr = new int[n][n];
                boolean flag = true;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        tempArr[i][j] = blocks[i][j];
                    }
                }
                tempArr[zero_row][zero_col] = tempArr[row][col];
                tempArr[row][col] = 0;
                neighbors.enqueue(new Board(tempArr));
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return neighbors.iterator();
        }
    }

    public static void main(String[] args) {
        Board b = new Board(new int[][] { {5, 2, 3}, {4, 0, 6}, {7, 8, 1} });
        System.out.println(b.hamming());
    }
}
