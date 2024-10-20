
package Unidad00.Unidad01.Recursividad;

/**
 *
 * @author kgv17
 */
public class Division {

    // Función para dividir dos números sin usar '/' ni '*'
    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        int quotient = 0;
        int sign = 1;

        // Ajustamos el signo del resultado si uno de los números es negativo
        if (dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0) {
            sign = -1;
        }

        // Hacemos que ambos números sean positivos para simplificar el cálculo
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        // Restar el divisor del dividendo sucesivamente hasta que no sea posible
        while (dividend >= divisor) {
            dividend -= divisor;
            quotient++;
        }

        return quotient * sign;
    }

    // Función para dividir tres números, reutilizando la función anterior
    public static int divide(int dividend, int divisor1, int divisor2) {
        // Primero dividimos dividend / divisor1, luego el resultado lo dividimos por divisor2
        int intermediateResult = divide(dividend, divisor1);
        return divide(intermediateResult, divisor2);
    }

    public static void main(String[] args) {
        // Ejemplo de uso: dividir tres números
        int dividend = 100;
        int divisor1 = 5;
        int divisor2 = 2;
        System.out.println("Resultado de dividir 100 entre 5 y luego entre 2: " + divide(dividend, divisor1, divisor2));
    }
}
