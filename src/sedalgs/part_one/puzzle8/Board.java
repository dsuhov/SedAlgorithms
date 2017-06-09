package sedalgs.part_one.puzzle8;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

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
                if (blocks[i][j] != goal[i][j]) outOfPosition++;
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
                if (blocks[i][j] != goal[i][j]) {
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

        StringBuilder string = new StringBuilder();

        string.append(n);
        string.append("\n");
        for (int i = 0; i < n; i++) {
            string.append(" ");
            for (int j = 0; j < n; j++) {
                string.append(blocks[i][j]);
                string.append(" ");
            }
            if (i != n) string.append("\n");
        }
        return string.toString();
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

    public static void main(String[] args) {
        System.out.println("Board building test.");
        //Board board = new Board(new int[][] { {0,2,3}, {6,5,4}, {7,8,1}});
        Board board = new Board(new int[][] { {1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}});

        /*
        System.out.println("Hamming test.");
        board = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,8,0}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 0: " + board.hamming());
        board = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,8,9}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 1: " + board.hamming());
        board = new Board(new int[][] { {1,3,2}, {4,5,6}, {7,8,0}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 3: " + board.hamming());

        System.out.println("Manhattan test.");
        board = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,8,0}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 0: " + board.manhattan() + "\n");
        board = new Board(new int[][] { {1,2,0}, {4,5,7}, {8,6,3}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 8: " + board.manhattan() + "\n");
        board = new Board(new int[][] { {1,3,2}, {4,8,6}, {7,5,0}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 4: " + board.manhattan() + "\n");
        board = new Board(new int[][] { {8,7,6}, {5,4,3}, {2,0,1}});
        System.out.println("Input:\n" + board);
        System.out.println("Should be 16: " + board.manhattan() + "\n");
        */

    }
}
