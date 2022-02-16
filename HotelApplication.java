import java.util.Scanner;

public class HotelApplication {
    public static void main(String[] args) {
        boolean keepRunning = true;

        try (Scanner sc = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    MainMenu.displayOptions();
                    int selection = Integer.parseInt(sc.nextLine());
                    keepRunning = MainMenu.executeOption(sc, selection);
                } catch (Exception e) {
                    System.out.println("Please enter a number between 1 and 5 \n");
                }
            }
        } catch (Exception e) {
            System.out.println("\nError\n");
        }
    }
}
