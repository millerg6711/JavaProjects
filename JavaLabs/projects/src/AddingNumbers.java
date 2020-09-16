import java.util.Scanner;

public class AddingNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double sum = 0;
        double num;
        do {
            System.out.println("Please enter a number");
            num = scanner.nextDouble();
            sum += num;
        } while (num != 0);

        System.out.printf("The total is: %.3f\n", sum);

        System.out.println(String.format("The total is: %.3f", sum));
    }
}
