import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
//import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void addFirstTestBasic() {
        Deque<Integer> ad = new ArrayDeque<>();

        ad.addFirst(1);
        assertThat(ad.toList()).containsExactly(1).inOrder();

        ad.addFirst(2);
        assertThat(ad.toList()).containsExactly(1, 2);

        ad.addFirst(3);
        assertThat(ad.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void addLastTestBasic() {
        Deque<Integer> ad = new ArrayDeque<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        assertThat(ad.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void addFirstAndAddLastTest() {
        Deque<Integer> ad = new ArrayDeque<>();

        ad.addLast(0);
        ad.addLast(1);
        ad.addFirst(-1);
        ad.addLast(2);
        ad.addFirst(-2);
        ad.addFirst(5);
        ad.addFirst(5);
        ad.addFirst(5);
        ad.addFirst(5);
        ad.addFirst(5);

        assertThat(ad.toList()).containsExactly(5, 5, 5, 5, 5, -2, -1, 0, 1, 2).inOrder();
        assertThat(ad.size()).isEqualTo(10);
    }

    @Test
    public void isEmptyTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.toList()).containsExactly();
        assertThat(ad.isEmpty()).isEqualTo(true);
        ad.addFirst(2);
        assertThat(ad.isEmpty()).isEqualTo(false);
    }

    @Test
    public void sizeTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.size()).isEqualTo(0);
        ad.addFirst(4);
        ad.addFirst(5);
        assertThat(ad.size()).isEqualTo(2);
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(1);
    }

    @Test
    public void getTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.get(-1)).isEqualTo(null);
        assertThat(ad.get(0)).isEqualTo(null);
        assertThat(ad.get(1)).isEqualTo(null);
        ad.addLast(0);
        ad.removeFirst();
        ad.addFirst(2);
        ad.get(0);
        ad.removeLast();
        ad.addLast(5);
        ad.addFirst(6);
        ad.addFirst(1);
        ad.addFirst(2);
        assertThat(ad.get(1)).isEqualTo(1);
        ad.removeFirst();
        assertThat(ad.get(0)).isEqualTo(1);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(6);
        ad.addFirst(7);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(10);
        ad.addFirst(11);
        ad.toList();
        assertThat(ad.get(5)).isEqualTo(6);
    }

    @Test
    public void removeFirstTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.removeFirst()).isEqualTo(null);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(4);
        assertThat(ad.size()).isEqualTo(1);
        ad.removeFirst();
        assertThat(ad.isEmpty()).isEqualTo(true);
        assertThat(ad.size()).isEqualTo(0);
        assertThat(ad.removeFirst()).isEqualTo(null);
        ad.addLast(7);
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.removeFirst()).isEqualTo(7);
        ad.addFirst(3);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(5);
        assertThat(ad.removeFirst()).isEqualTo(3);
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.removeFirst()).isEqualTo(4);
    }

    @Test
    public void removeLastTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.removeFirst()).isEqualTo(null);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(5);
        assertThat(ad.size()).isEqualTo(1);
        ad.removeLast();
        assertThat(ad.isEmpty()).isEqualTo(true);
        assertThat(ad.size()).isEqualTo(0);
        assertThat(ad.removeFirst()).isEqualTo(null);
        ad.addLast(7);
        assertThat(ad.size()).isEqualTo(1);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(5);
        assertThat(ad.removeLast()).isEqualTo(7);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.removeLast()).isEqualTo(5);
    }
}
