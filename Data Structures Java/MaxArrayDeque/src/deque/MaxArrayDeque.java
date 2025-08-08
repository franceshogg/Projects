package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;
    public MaxArrayDeque(Comparator<T> c) {
        this.comp = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T maxi = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            if (this.comp.compare(this.get(i), maxi) > 0) {
                maxi = this.get(i);
            }
        }
        return maxi;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxi = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(this.get(i), maxi) > 0) {
                maxi = this.get(i);
            }
        }
        return maxi;
    }
}
