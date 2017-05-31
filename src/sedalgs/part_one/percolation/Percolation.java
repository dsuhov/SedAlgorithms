package sedalgs.part_one.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF model;
    private boolean[] openSites;
    private int n;
    // Конструктор инициализирует структуру данных объекта класса WeightedQuickUnionUF n*n+2 элементов
    // вводим вспомогательную константу n
    // инициализирует массив n*n элементов, на которых отображаются открытые и закрытые ячейки
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Argument does not fit the condition n > 0");
        this.n = n;
        model = new WeightedQuickUnionUF(n*n+2);
        openSites = new boolean[n*n];
        for (int i = 0; i < openSites.length; i++) openSites[i] = false;
    }


    public void open(int row, int col) {
        validateIndices(row, col);  // валидация аргументов
        int siteIndex = (row-1)*n+col;
        if (!isOpen(row, col)) {    //если ячейка не открыта
            openSite(row, col);
        }
        if (col == 1) {
            connectToNeighbors(siteIndex, siteIndex+1);
            connectToNeighbors(siteIndex, siteIndex+n);
            connectToNeighbors(siteIndex, siteIndex-n);
        }
        else if (col == n) {
            connectToNeighbors(siteIndex, siteIndex - 1);
            connectToNeighbors(siteIndex, siteIndex + n);
            connectToNeighbors(siteIndex, siteIndex - n);
        } else {
            connectToNeighbors(siteIndex, siteIndex-1);
            connectToNeighbors(siteIndex, siteIndex+1);
            connectToNeighbors(siteIndex, siteIndex+n);
            connectToNeighbors(siteIndex, siteIndex-n);
        }

    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openSites[(row-1)*n+col-1];
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return model.connected((row-1)*n+col, 0);
    }

    public boolean percolates() {
        if (n == 1) return openSites[n-1];
        return model.connected(n*n+1, 0);
    }

    // метод корректный
    private void validateIndices(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IndexOutOfBoundsException();
    }

    private void openSite(int row, int col) {
        openSites[(row-1)*n+col-1] = true;
    }

    private void connectToNeighbors(int sInd, int neigh) {
        if (neigh < 1)
            model.union(sInd, 0);
        else if (neigh > n*n)
            model.union(sInd, n*n+1);
        else if ((neigh <= n*n && openSites[neigh-1] && !model.connected(sInd, neigh)))
            model.union(sInd, neigh);
    }
}