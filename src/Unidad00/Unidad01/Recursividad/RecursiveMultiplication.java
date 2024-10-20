
package Unidad00.Unidad01.Recursividad;
    
public class RecursiveMultiplication {
    
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

    public static void main(String[] args) {
        int result = multiply(4, 3);
        System.out.println(result);  // Salida: 12
    }
}

