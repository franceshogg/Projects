import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class ArrayDequeTest {
    @Test
    public void equalsTest() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        Deque<Integer> da = new LinkedListDeque<>();
        ad.addLast(3);
        ad.addFirst(2);
        da.addFirst(5);
        da.addLast(3);
        assertThat(ad.equals(da)).isEqualTo(false);
        da.removeFirst();
        da.addFirst(2);
        assertThat(ad.equals(da)).isEqualTo(true);
    }
}
