public class Problem16 {
    public static void main(String[] args) {
        System.out.println(isPalindrome("racecar"));
    }

    public static boolean isPalindrome(final String w) {
        StringBuilder word = new StringBuilder();
        for (int i = w.length() - 1; i >= 0; i--) {
            //TODO: comparison
            word.append(w.charAt(i));
        }
        return w.equals(word.toString());
    }
}

