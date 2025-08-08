package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.JOptionPane;
import java.util.Arrays;

public class DeleteElementFromSortedArray {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Ask for the user's name
        String name = JOptionPane.showInputDialog("¿Cómo te llamas?");

        // Create a sorted array
        int[] numbers = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        if (name == null) {
            name = "Usuario";
        }

        // Display the original array
        JOptionPane.showMessageDialog(null, name + ", Original Array: " + Arrays.toString(numbers));

        // Ask the user for the value to delete
        while (true) {

            String input = JOptionPane.showInputDialog("Enter the value to delete:");

            // Check if the user closed the dialog
            if (input == null) {
                return; // Exit the program
            }

            int valueToDelete;

            try {

                valueToDelete = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                continue; // Continue the loop to ask for input again
            }

            // Check if the value is in the array
            boolean found = false;

            for (int number : numbers) {

                if (number == valueToDelete) {
                    found = true;
                    break;
                }

            }

            // If the value is not found, inform the user
            if (!found) {
                JOptionPane.showMessageDialog(null, "The value " + valueToDelete + " was not found in the array.");
                continue; // Continue the loop to ask for input again
            }

            // Create a new array to hold the remaining elements
            int[] newNumbers = new int[numbers.length - 1];
            int index = 0;

            // Copy elements to the new array, skipping the value to delete
            for (int number : numbers) {

                if (number != valueToDelete || index > 0) {
                    newNumbers[index++] = number;
                }

            }

            if (newNumbers.length == 0) {
                return; // Exit the program
            }

            // Display the updated array
            JOptionPane.showMessageDialog(null, name + ", Updated Array: " + Arrays.toString(newNumbers));

            // Update the original array to be the new array for the next iteration
            numbers = newNumbers;

        }

    }

}
