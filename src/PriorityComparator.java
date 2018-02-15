import java.util.Comparator;

/**
 * Created by Anass on 2018-01-31.
 */
public class PriorityComparator implements Comparator {
    float maxSlices;

    public PriorityComparator(float maxSlices) {
        this.maxSlices = maxSlices;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Slice s1 = (Slice) o1;
        Slice s2 = (Slice) o2;
        if(s1.getPriority(maxSlices) == s2.getPriority(maxSlices))
            return 0;
        if(s1.getPriority(maxSlices) > s2.getPriority(maxSlices))
            return -1;
        return 1;
    }
}
