package sedalgs.part_one.permutation;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemsArr;
    private int size = 0;
    private int n = 0;

    public boolean isEmpty()  { return size == 0; }

    public int size() { return size; }

    public void enqueue(Item item) {
        if (item == null) { throw new java.lang.NullPointerException(); }

        if (itemsArr == null) {
            itemsArr = (Item[]) new Object[1];
            itemsArr[0] = item;
            n++;
            size++;
        } else if (n == itemsArr.length) {
            enlargeArray();
            itemsArr[n] = item;
            n++;
            size++;
        } else {
            itemsArr[n] = item;
            n++;
            size++;
        }
    }

    public Item dequeue() {
        if (size == 0) { throw new java.util.NoSuchElementException(); }

        Item item;
        int position = StdRandom.uniform(itemsArr.length);

        while ((item = itemsArr[position]) == null) { position = StdRandom.uniform(itemsArr.length); }
        itemsArr[position] = null;
        size--;
        if (size == itemsArr.length/4) shrinkArray();
        return item;
    }

    public Item sample() {
        if (size == 0) { throw new java.util.NoSuchElementException(); }

        Item item;
        int position = StdRandom.uniform(itemsArr.length);

        while ((item = itemsArr[position]) == null) { position = StdRandom.uniform(itemsArr.length); }

        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void enlargeArray() {
        Item[] newArray =  (Item[]) new Object[n * 2];
        Item item;
        for (int i = 0, j = 0; i < itemsArr.length; i++) {
            while ((item = itemsArr[j]) == null) { j++; }
            newArray[i] = itemsArr[j++];
        }
        itemsArr = newArray;
        n = size;
    }

    private void shrinkArray() {
        Item[] newArray =  (Item[]) new Object[itemsArr.length/4];
        Item item;
        for (int i = 0, j = 0; i < newArray.length; i++) {
            while ((item = itemsArr[j]) == null) { j++; }
            newArray[i] = itemsArr[j];
        }
        itemsArr = newArray;
        n = itemsArr.length;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] items = (Item[]) new Object[size];
        private int current = 0;

        RandomizedQueueIterator() {
            for (int i = 0; i < items.length; i++) {
                items[i] = sample();
            }
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return items[current++];
        }

        public boolean hasNext() { return current != items.length; }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }
}
