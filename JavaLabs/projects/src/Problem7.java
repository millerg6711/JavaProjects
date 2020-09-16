public class Problem7 {
    public static final int NUM_COLUMNS = 12;
    public static final int NUM_ROWS = 12;

    public static void main(String[] args) {
        StringBuilder tableHeader = new StringBuilder();
        System.out.printf(" %6s", "|");
        for (int i = 1; i <= NUM_COLUMNS; i++) {
            tableHeader.append(String.format("%-6d|", i));
        }
        System.out.println(tableHeader);
        for (int i = 0; i < tableHeader.length() + 6; i++) {
            System.out.print("-");
        }
        System.out.println("|");
        for (int i = 1; i <= NUM_ROWS; i++) {
            System.out.printf("%-6d|", i);
            for (int j = 1; j <= NUM_COLUMNS; j++) {
                System.out.printf("%-6d|", i * j);
            }
            System.out.println();
        }
    }
}
