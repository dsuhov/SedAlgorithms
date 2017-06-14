package sedalgs.part_one.puzzle8;

import edu.princeton.cs.algs4.LinkedQueue;
import java.util.Iterator;

/**
 * @author dsuhov
 */
public class Board {

    private final int n;
    private final int[][] blocks;

    /**
     * Funny constructor construct construction
     * @param blocks
     */
    public Board(int[][] blocks) {
        n = blocks.length;

        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = blocks[i][j];
            }
        }
        this.blocks = newBoard;

    }

    public int dimension() {
        return n;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        int outOfPosition = 0;
        int block = 0;

        //if zero is out of place, some block will be on its place, so, count it
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ((i == n-1) ? n-1 : n); j++) {
                if (blocks[i][j] != ++block) {
                    outOfPosition++;
                }
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
        int block = 0;
        int row, col;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ((i == n-1) ? n-1 : n); j++) {
                if (blocks[i][j] != ++block && blocks[i][j] != 0) {
                    row = (blocks[i][j] - 1)/n;
                    col = (blocks[i][j] - 1)%n;
                    distances += (row > i ? row - i : i - row) + (col > j ? col - j : j - col);
                }
            }
        }
        if (blocks[n-1][n-1] != 0) {
            row = (blocks[n-1][n-1] - 1)/n;
            col = (blocks[n-1][n-1] - 1)%n;
            distances += 2*(n-1) - row - col;
        }
        return distances;
    }

    public boolean isGoal() {
        int block = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ((i == n-1) ? n-1 : n); j++) {
                if (blocks[i][j] != ++block) {
                    return false;
                }
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

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return Board
     */
    public Board twin() {

        int[][] newBoard = new int[n][n];
        int[] blockForSwap = new int[2];
        boolean check = false;
        // create and fill new board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = blocks[i][j];
            }
        }

        // make interchange
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (newBoard[i][j] != 0 && check == false) {
                    blockForSwap[0] = i;
                    blockForSwap[1] = j;
                    check = true;
                } else if (newBoard[i][j] != 0 && check == true) {
                    int block = newBoard[blockForSwap[0]][blockForSwap[1]];
                    newBoard[blockForSwap[0]][blockForSwap[1]] = newBoard[i][j];
                    newBoard[i][j] = block;
                    break outer;
                }
            }
        }

        return new Board(newBoard);
    }


    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        Board otherBoard = (Board) y;
        if (this.n != otherBoard.dimension()) return false;

        int block = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ((i == n-1) ? n-1 : n); j++) {
                if (blocks[i][j] != otherBoard.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
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
                    return new Neighbors(i, j);
                }
            }
        }
        return new Neighbors(n-1, n-1);
    }

    private class Neighbors implements Iterable<Board> {

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
}
