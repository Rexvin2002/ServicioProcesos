
package Unidad00.Unidad01.Recursividad;

/**
 *
 * @author kgv17
 */
public class RecursiveFactorialMultiplication {
    
    // Función recursiva para calcular la multiplicación con sumas
    public static int multiply(int a, int b) {
        // Caso base: Si b o a es 0
        if (a == 0 || b == 0) {
            return 0;
        }
        
        // Si a o b son negativos, cambiamos el signo y llamamos recursivamente
        else if (a < 0 || b < 0) {
            return -multiply(a, b);
        }
        
        // Llamada recursiva: suma a con el resultado de multiplicar a por (b-1)
        else {
            return a + multiply(a, b - 1);
        }
    }

    // Función recursiva para calcular el factorial de un número
    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return multiply(n, factorial(n - 1));  // Uso de la función de multiplicación
        }
    }

    // Función para calcular la multiplicación de los factoriales de dos números
    public static int multiplyFactorials(int num1, int num2) {
        int factorial1 = factorial(num1);
        int factorial2 = factorial(num2);
        return multiply(factorial1, factorial2);  // Multiplica los factoriales sin usar *
    }

    public static void main(String[] args) {
        int num1 = 4;
        int num2 = 3;
        int result = multiplyFactorials(num1, num2);
        System.out.println("La multiplicación del factorial de " + num1 + " y " + num2 + " es: " + result);
    }
}

