import java.time.Year;

public class Problem9 {
   public static final int CURRENT_YEAR = Year.now().getValue();
   public static final int NEXT_LEAP_YEARS = 20;

    public static void main(String[] args) {
        for (int leapYearCount = 0, i = CURRENT_YEAR + 1; leapYearCount < NEXT_LEAP_YEARS; i++) {
            if (isLearYear(i)) {
                System.out.println(i);
                leapYearCount++;
            }
        }
    }

    public static boolean isLearYear(final int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}
