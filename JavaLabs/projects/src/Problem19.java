import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem19 {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("a", "b", "c");
        List<String> strList2 = Arrays.asList("1", "2", "3");
        System.out.println(concat(strList, strList2));
    }

    public static <T> List<T> concat(final List<T> list1, final List<T> list2) {
        List<T> list3 = new ArrayList<>(list1.size() + list2.size());
        list3.addAll(list1);
        list3.addAll(list2);

        /*for (int i = 0; i < list1.size(); i++) {
            list3.add(list1.get(i));
        }
        for (int i = 0; i < list2.size(); i++) {
            list3.add(list2.get(i));
        }*/

        return list3;
    }
}
