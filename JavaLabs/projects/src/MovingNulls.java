import java.util.Arrays;

public class MovingNulls {
    private static final String[] array = {null, "four", null, "good", null, "dogs", null, "swam", null, null, null};

    public static void main(String[] args) {
        /*String[] newArray = new String[array.length];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (array[i] != null) {
                newArray[j++] = array[i];
            }
        }
        System.out.println(Arrays.toString(newArray));*/
        int toPosition = 0;
        for (int fromPosition = 0; fromPosition < array.length; ++fromPosition) {
            if (array[fromPosition] != null) {
                array[toPosition++] = array[fromPosition];
            }
        }
        Arrays.fill(array, toPosition, array.length, null);
        System.out.println(Arrays.toString(array));
    }
}
