import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem14 {
    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4 ,5);
        System.out.println(getOddPositions(numList));
        List<String> strList = Arrays.asList("test1", "test1231234324", "test");
        System.out.println(getOddPositions(strList));
    }
    
    public static <T> List<T> getOddPositions(final List<T> l) {
        List<T> oddPositions = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if (i % 2 != 0) {
                oddPositions.add(l.get(i));
            }
        }
        return oddPositions;
    }
}
