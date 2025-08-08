import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.
    @Test
    public void isEmptyTest() {
        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.toList()).containsExactly();
        assertThat(lld2.isEmpty()).isEqualTo(true);
        lld2.addFirst(2);
        assertThat(lld2.isEmpty()).isEqualTo(false);
    }
    @Test
    public void sizeTest() {
        Deque<Integer> lld3 = new LinkedListDeque<>();
        assertThat(lld3.size()).isEqualTo(0);
        lld3.addFirst(4);
        lld3.addFirst(5);
        assertThat(lld3.size()).isEqualTo(2);
        lld3.removeLast();
        assertThat(lld3.size()).isEqualTo(1);
    }

    @Test
    public void removeFirstTest() {
        Deque<Integer> lld4 = new LinkedListDeque<>();
        assertThat(lld4.removeFirst()).isEqualTo(null);
        lld4.addFirst(4);
        lld4.addFirst(5);
        lld4.removeFirst();
        assertThat(lld4.toList()).containsExactly(4);
        assertThat(lld4.size()).isEqualTo(1);
        lld4.removeFirst();
        assertThat(lld4.isEmpty()).isEqualTo(true);
        assertThat(lld4.size()).isEqualTo(0);
        lld4.addFirst(7);
        assertThat(lld4.size()).isEqualTo(1);
        assertThat(lld4.removeFirst()).isEqualTo(7);
    }
    @Test
    public void removeLastTest() {
        Deque<Integer> lld5 = new LinkedListDeque<>();
        assertThat(lld5.removeFirst()).isEqualTo(null);
        lld5.addFirst(4);
        lld5.addFirst(5);
        lld5.removeLast();
        assertThat(lld5.toList()).containsExactly(5);
        assertThat(lld5.size()).isEqualTo(1);
        lld5.removeLast();
        assertThat(lld5.isEmpty()).isEqualTo(true);
        assertThat(lld5.size()).isEqualTo(0);
        lld5.addFirst(7);
        assertThat(lld5.size()).isEqualTo(1);
        assertThat(lld5.removeLast()).isEqualTo(7);

    }

    @Test
    public void getTest() {
        Deque<Integer> lld6 = new LinkedListDeque<>();
        assertThat(lld6.get(-1)).isEqualTo(null);
        assertThat(lld6.get(0)).isEqualTo(null);
        assertThat(lld6.get(1)).isEqualTo(null);
        lld6.addFirst(1);
        lld6.addFirst(2);
        assertThat(lld6.get(1)).isEqualTo(1);
        lld6.removeFirst();
        assertThat(lld6.get(0)).isEqualTo(1);
    }

    @Test
    public void getRecursiveTest() {
        Deque<Integer> lld7 = new LinkedListDeque<>();
        assertThat(lld7.get(-1)).isEqualTo(null);
        assertThat(lld7.getRecursive(0)).isEqualTo(null);
        assertThat(lld7.get(0)).isEqualTo(null);
        lld7.addFirst(1);
        lld7.addFirst(2);
        assertThat(lld7.getRecursive(1)).isEqualTo(1);
        lld7.removeFirst();
        assertThat(lld7.getRecursive(0)).isEqualTo(1);
    }
}