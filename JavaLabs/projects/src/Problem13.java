import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem13 {
    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4 ,5);
        System.out.println(numList);
        System.out.println(contains(1, numList));
        System.out.println(contains(7, numList));
        System.out.println(contains(null, numList));
        System.out.println(contains(3, numList));
    }

    public static <T> boolean contains(final T o, final List<T> l) {
        if (o == null) {
            for (T t : l) {
                if (t == null) {
                    return true;
                }
            }
        } else {
            for (T t : l) {
                if (o.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }
}
