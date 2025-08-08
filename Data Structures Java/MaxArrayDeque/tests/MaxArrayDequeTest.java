import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
    public void comparatorTest() {
        Deque<Integer> tester = new MaxArrayDeque<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (01 > 02) {
                    return 1;
                }
                else {return -1;}
            }
        });
        Deque<Integer> tester2 = new MaxArrayDeque<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (01 > 02) {
                    return 1;
                }
                else {return -1;}
            }
        });
        assertThat(tester.equals(tester2)).isEqualTo(null);
        tester.addFirst(3);
        tester2.addFirst(3);
        assertThat(tester.equals(tester2)).isEqualTo(true);
        tester.addFirst(4);
        assertThat(tester.equals(tester2)).isEqualTo(false);
    }
}
