import java.util.Random;
import java.util.Scanner;

public class Problem10 {
    public static final int MAX_GUESS = 100;

    public static void main(String[] args) {
        Random random = new Random();
        int randNum = random.nextInt(MAX_GUESS + 1);
        int numGuesses = 0;
        int guess;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Guess Number in between 0 - 100: ");
            guess = scanner.nextInt();
            numGuesses++;
            if (guess < randNum) {
                System.out.println("Too low! Please try again.");
            } else if(guess > randNum) {
                System.out.println("Too high! Please try again.");
            } else {
                System.out.printf("Guess %s correct in %s guesses.", randNum, numGuesses);
            }
        } while (randNum != guess);
    }
}
