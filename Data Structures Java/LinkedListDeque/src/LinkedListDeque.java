import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private NODE sentinel;

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(4);

    }

    public class NODE {
        NODE prev;
        T val;
        NODE next;
        public NODE(NODE p, T v, NODE n) {
            prev = p;
            val = v;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new NODE(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        NODE first = new NODE(null, x, null);
        NODE sentnextPlaceholder = sentinel.next;
        sentinel.next = first;
        first.next = sentnextPlaceholder;
        sentnextPlaceholder.prev = first;
        first.prev = sentinel;
        size++;
    }

    @Override
    public void addLast(T x) {
        NODE last = new NODE(null, x, null);
        NODE sentprevPlaceholder = sentinel.prev;
        sentinel.prev = last;
        last.next = sentinel;
        last.prev = sentprevPlaceholder;
        sentprevPlaceholder.next = last;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int count = size;
        NODE placeholder2 = sentinel;
        while (count > 0) {
            returnList.add(placeholder2.next.val);
            placeholder2 = placeholder2.next;
            count--;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next.val == null) {
            return true;
        }
        return false;
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
        T first = sentinel.next.val;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return first;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T last = sentinel.prev.val;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return last;
    }

    @Override
    public T get(int index) {
        if ((size < 1) | (index >= size) | (index < 0)) {
            return null;
        }
        int count = index;
        NODE placeholder2 = sentinel.next;
        while (count > 0) {
            placeholder2 = placeholder2.next;
            count--;
        }
        return placeholder2.val;
    }

    @Override
    public T getRecursive(int index) {
        if ((size < 1) | (index >= size) | (index < 0)) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    public T getRecursiveHelper(NODE node, int index) {
        if (index == 0) {
            return node.val;
        }
        return getRecursiveHelper(node.next, index - 1);
    }
}
