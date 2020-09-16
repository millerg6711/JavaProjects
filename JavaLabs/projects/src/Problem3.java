import java.util.Scanner;

public class Problem3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name = scanner.nextLine();
        if ("Alice".equalsIgnoreCase(name) || "Bob".equalsIgnoreCase(name)) {
            System.out.printf("Hello %s!", name);
        }
    }
}
