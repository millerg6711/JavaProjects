import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem15 {
    public static void main(String[] args) {
        System.out.println(getTotal(Arrays.asList(1.0, 2.2, null, 4.4, 5.5)));
    }
    
    public static double getTotal(final List<Double> list) {
        double total = 0;
        for (Double item : list) {
            if (item != null) { total += item; }
        }
        return total;
    }
}
