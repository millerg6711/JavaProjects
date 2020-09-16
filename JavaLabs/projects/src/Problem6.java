import java.util.Scanner;

public class Problem6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number:");
        int number = scanner.nextInt();

        boolean valid;
        String sign;
        double sum = 0;
        scanner.nextLine();
        do {
            System.out.printf("Would you like to compute sum with (+) or compute product with (*) of 1,...,%d.\n", number);
            sign = scanner.nextLine().trim();
            switch (sign) {
                case "+":
                    sum = 0;
                    for (int i = 1; i <= number; i++) {
                        sum += i;
                    }
                    valid = true;
                    break;
                case "*":
                    sum = 1;
                    for (int i = 1; i <= number; i++) {
                        sum *= i;
                    }
                    valid = true;
                    break;
                default:
                    valid = false;
            }
        } while (!valid);

        System.out.printf("Sum: %.2f", sum);
    }
}