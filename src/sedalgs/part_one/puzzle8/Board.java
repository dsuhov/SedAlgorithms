package sedalgs.part_one.puzzle8;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class Board {

    final int n;
    final int[][] blocks;

    public Board(int[][] blocks) {
        n = blocks.length;
        int[][] blocksNew = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                blocksNew[i][j] = blocks[i][j];
            }
            this.blocks = blocksNew;
        // what a fucking syntax
    }


    public void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(blocks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] arr = new int[][] { {4, 5, 6, 8, 7}, {1, 2, 3, 88, 99}, {55, 77, 88, 85, 475}, {4, 5, 6, 8, 7}, {1, 2, 3, 88, 99} };
        Board bb = new Board(arr);
        bb.print();
    }
}
