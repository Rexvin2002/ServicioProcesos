package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.JOptionPane;
import java.util.Arrays;

public class DeleteValueFromArray {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Create an array with 10 elements, some of which may be repeated
        int[] numbers = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        // Display the original array
        JOptionPane.showMessageDialog(null, "Original Array: " + Arrays.toString(numbers));

        while (true) {

            // Ask the user for the value to delete
            String input = JOptionPane.showInputDialog("Enter the value to delete:");

            // Check if the user pressed "Cancel" or closed the dialog
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

            // Count how many times the value appears in the array
            int count = 0;

            for (int number : numbers) {

                if (number == valueToDelete) {
                    count++;
                }

            }

            // If the value is not found, inform the user
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "The value " + valueToDelete + " was not found in the array.");
                continue; // Continue the loop to ask for input again
            }

            // Create a new array to hold the remaining elements
            int[] newNumbers = new int[numbers.length - count];
            int index = 0;

            // Copy elements to the new array, skipping the value to delete
            for (int number : numbers) {

                if (number != valueToDelete) {
                    newNumbers[index++] = number;
                }

            }

            if (newNumbers.length == 0) {
                return; // Exit the program
            }

            // Display the updated array
            JOptionPane.showMessageDialog(null, "Updated Array: " + Arrays.toString(newNumbers));

            // Update the original array to be the new array for the next iteration
            numbers = newNumbers;

        }

    }

}
