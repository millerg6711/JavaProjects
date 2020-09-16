import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Problem18 {
    public static void main(String[] args) {
        List<Number> numbers = new ArrayList<>();

        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        apply(numbers, Problem18::isPerfectSquare);
    }

    public static boolean isPerfectSquare(Number n) {
        double num = n.doubleValue();
        for (int i = 0; i * i <= num; ++i) {
            if (i * i == num) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Number> void apply(final List<T> list, final Function<T, Boolean> func) {
        for (int index = 0, count = 0; index < list.size() && count < 20; index++) {
            T num = list.get(index);
            if (func.apply(num))
            {
                System.out.println(num);
                count++;
            }
        }
    }
}
