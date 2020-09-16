import java.util.*;

public class Problem12 {
    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4);
        reverseList(numList);
        System.out.println(numList);
    }

    public static <T> void reverseList(List<T> l) {
        for (int i = 0, j = l.size() - 1; i < j; i++) {
            //TODO: SWAP
            l.add(i, l.remove(j));
        }
    }
}
