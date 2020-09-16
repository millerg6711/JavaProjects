import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem20 {
    public static void main(String[] args) {
        List<String> strList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        List<String> strList2 = new ArrayList<>(Arrays.asList("1", "2", "3"));
        System.out.println(combine(strList, strList2));
    }

    public static <T> List<T> combine(final List<T> list1, final List<T> list2) {
        List<T> list = new ArrayList<>();
        int list1Size = list1.size();
        int list2Size = list2.size();
        int length = Math.max(list1Size, list2Size);
        for(int i = 0; i < length; i++) {
            if (i < list1Size) {
                list.add(list1.get(i));
            }
            if (i < list2Size) {
                list.add(list2.get(i));
            }
        }

        return list;
    }
}
