package Unidad00.Unidad01.Recursividad;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static List<Integer> fibonacciLessThan(int n) {
        List<Integer> fibSequence = new ArrayList<>();
        int a = 0, b = 1;

        while (a < n) {
            fibSequence.add(a);
            int next = a + b;
            a = b;
            b = next;
        }

        return fibSequence;
    }

    public static void main(String[] args) {
        int n = 100;
        List<Integer> fibNumbers = fibonacciLessThan(n);
        
        System.out.println("Fibonacci numbers less than " + n + ": " + fibNumbers);
    }
}
