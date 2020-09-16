public class Problem8 {
    public static void main(String[] args) {
        for (int i = 2; i < 1_000_000; i++) {
            if(isPrime(i)) {
                System.out.println(i);
            }
        }
    }

    public static boolean isPrime(final int num) {
        double sqrtNum = Math.sqrt(num);
        for (int i = 2; i <= sqrtNum; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
