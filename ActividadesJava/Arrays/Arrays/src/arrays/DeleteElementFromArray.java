package arrays;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import javax.swing.JOptionPane;
import java.util.Arrays;

public class DeleteElementFromArray {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Create an array with 10 elements
        int[] numbers = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        // Display the original array
        JOptionPane.showMessageDialog(null, "Original Array: " + Arrays.toString(numbers));

        // Ask the user for the index to delete
        String input = JOptionPane.showInputDialog("Enter the index (0-9) of the element to delete:");

        // Check if the user pressed "Cancel" or closed the dialog
        if (input == null) {
            return; // Exit the program
        }

        int indexToDelete;

        try {

            indexToDelete = Integer.parseInt(input);

            // Check if the index is valid
            if (indexToDelete < 0 || indexToDelete >= numbers.length) {
                JOptionPane.showMessageDialog(null, "Invalid index! Please enter a number between 0 and 9.");
                return; // Exit the program
            }

            // Create a new array of size 9 (one less than the original)
            int[] newNumbers = new int[numbers.length - 1];

            // Copy elements to the new array, skipping the index to delete
            for (int i = 0, j = 0; i < numbers.length; i++) {

                if (i != indexToDelete) {
                    newNumbers[j++] = numbers[i];
                }

            }

            // Display the updated array
            JOptionPane.showMessageDialog(null, "Updated Array: " + Arrays.toString(newNumbers));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid index.");
        }

    }

}
