package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.JOptionPane;

public class NumberGame {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        boolean[] enteredNumbers = new boolean[101]; // Array to track numbers 1-100
        String input;
        int uniqueCount = 0; // Counter for unique numbers

        while (true) {

            // Check if the user has reached the limit of unique numbers
            if (uniqueCount >= 100) {
                JOptionPane.showMessageDialog(null, "You have entered all unique numbers from 1 to 100. Game over!");
                break; // End the game
            }

            input = JOptionPane.showInputDialog("Enter a number between 1 and 100:");

            // Check if the user pressed "Cancel"
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Game canceled! Thanks for playing.");
                break; // End the game
            }

            try {

                int number = Integer.parseInt(input);

                if (number < 1 || number > 100) {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 100.");
                    continue; // Ask for input again
                }

                if (enteredNumbers[number]) {

                    JOptionPane.showMessageDialog(null, "The number " + number + " has already been entered.");

                } else {
                    enteredNumbers[number] = true; // Mark the number as entered
                    uniqueCount++; // Increment the unique count
                    JOptionPane.showMessageDialog(null, "Number " + number + " entered successfully.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }

        }

    }

}
