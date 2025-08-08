package deque;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private static final int SIXTEEN = 16;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length / 2;
        nextLast = items.length / 2 + 1;
    }

    public T[] resize() {
        T[] returnArray = (T[]) new Object[8];
        if (size >= 2) {
            returnArray = (T[]) new Object[size * 2];
        }
        int startPoint = returnArray.length / 2 - size / 2;
        for (int i = 0; i < size; i++) {
            returnArray[startPoint + i] = items[nextFirst + 1 + i];
        }
        items = returnArray;
        nextFirst = startPoint - 1;
        nextLast = startPoint + size;
        return items;
    }

    @Override
    public void addFirst(T x) {
        if (nextFirst == -1) {
            items = resize();
        }
        items[nextFirst] = x;
        nextFirst--;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (nextLast == items.length) {
            items = resize();
        }
        items[nextLast] = x;
        nextLast++;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int sizeProp = (size - 1) * 4;
        if ((items.length > sizeProp) & (items.length >= SIXTEEN)) {
            items = resize();
        }
        T ret = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst++;
        size--;
        return ret;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int sizeProp = (size - 1) * 4;
        if ((items.length > sizeProp) & (items.length >= SIXTEEN)) {
            items = resize();
        }
        T ret = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast--;
        size--;
        return ret;
    }

    @Override
    public T get(int index) {
        if ((index >= size) | (index > items.length) | (index < 0)) {
            return null;
        }
        return items[nextFirst + 1 + index];
    }

    public T getRecursive(int index) {
        return get(index);
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int position;
        public ArrayIterator() {
            position = 0;
        }
        public boolean hasNext() {
            return position < size;
        }
        public T next() {
            T returnItem = get(position);
            position++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object n) {
        if (this == n) {
            return true;
        }
        if (n instanceof Deque otherN) {
            if (otherN.size() != this.size) {
                return false;
            }
            for (int x = 0; x < this.size; x++) {
                if (!(otherN.get(x).equals(this.get(x)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        List lst = this.toList();
        return lst.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        Deque<Integer> da = new ArrayDeque<Integer>();
        ad.addLast(3);
        ad.addFirst(2);
        da.addFirst(2);
        da.addLast(3);
        if (ad.equals(da)) {
            ad.addLast(5);
        }
    }
}
