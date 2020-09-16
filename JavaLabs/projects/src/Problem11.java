import java.util.*;

public class Problem11 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3452435, 123);
        System.out.println("Largest element : " + getLargestElem(list));
        List<String> list2 = Arrays.asList("test1", "test1231234324", "test");
        System.out.println("Largest element : " + getLargestElem(list2));
        List<String> none = new ArrayList<>();
        System.out.println("Largest element : " + getLargestElem(none));
    }

    public static <T extends Comparable<T>> T getLargestElem(final Iterable<T> list) {
        Iterator<T> itr = list.iterator();
        if (itr.hasNext()) {
            T largest = itr.next();
            while (itr.hasNext()) {
                T e = itr.next();
                if (e.compareTo(largest) > 0) {
                    largest = e;
                }
            }
            return largest;
        }
        return null;
    }
}
