import java.util.Scanner;

public class Problem4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number:");
        int number = scanner.nextInt();
        double sum = 0;
        for (int i = 1; i <= number; i++) {
            sum += i;
        }
        System.out.printf("Sum: %.2f", sum);
    }
}
