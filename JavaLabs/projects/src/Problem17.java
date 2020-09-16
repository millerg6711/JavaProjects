import java.util.Arrays;
import java.util.List;

public class Problem17 {
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(computeSumForLoop(intList));
        List<Double> doubleList = Arrays.asList(1.2, 2.5, 3.6, 4.2, 5.5);
        System.out.println(computeSumForLoop(doubleList));

        List<Integer> intList2 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(computeSumWhileLoop(intList2));
        List<Double> doubleList2 = Arrays.asList(1.2, 2.5, 3.6, 4.2, 5.5);
        System.out.println(computeSumWhileLoop(doubleList2));

        List<Integer> intList3 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(computeSumRecursive(intList3));
        List<Double> doubleList3 = Arrays.asList(1.2, 2.5, 3.6, 4.2, 5.5);
        System.out.println(computeSumRecursive(doubleList3));
    }

    public static <T extends Number> double computeSumForLoop(final List<T> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).doubleValue();
        }
        return sum;
    }

    public static <T extends Number> double computeSumWhileLoop(final List<T> list) {
        double sum = 0;
        int i = 0;
        while (i < list.size()) {
            sum += list.get(i).doubleValue();
            i++;
        }
        return sum;
    }

    public static <T extends Number> double computeSumRecursive(final List<T> list) {
        // sublist returns a view of the portion of this list between the specified
        return list.isEmpty() ? 0 :
                list.get(0).doubleValue() + computeSumRecursive(list.subList(1, list.size()));
    }
}
