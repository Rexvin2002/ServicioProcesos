package Unidad00.Unidad01.Recursividad;

import java.util.*;

public class Calculator {
    
    // Prioridad de los operadores
    private static final Map<Character, Integer> precedence = new HashMap<>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    // Método para evaluar la expresión
    public static double evaluate(String expression) {
        // Conversión a RPN (notación polaca inversa)
        List<String> rpn = toRPN(expression);
        // Evaluar la RPN
        return evaluateRPN(rpn);
    }

    // Conversión de infijo a RPN
    private static List<String> toRPN(String expression) {
        List<String> output = new ArrayList<>();
        Stack<Character> operators = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/ ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            }
            if (isNumeric(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.add(String.valueOf(operators.pop()));
                }
                operators.pop(); // Eliminar '('
            } else if (precedence.containsKey(token.charAt(0))) {
                while (!operators.isEmpty() && precedence.get(token.charAt(0)) <= precedence.get(operators.peek())) {
                    output.add(String.valueOf(operators.pop()));
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            output.add(String.valueOf(operators.pop()));
        }

        return output;
    }

    // Evaluación de RPN
    private static double evaluateRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                double result = switch (token.charAt(0)) {
                    case '+' -> a + b;
                    case '-' -> a - b;
                    case '*' -> a * b;
                    case '/' -> a / b;
                    default -> 0;
                };
                stack.push(result);
            }
        }

        return stack.pop();
    }

    // Verifica si una cadena es un número
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce una expresión matemática:");
        String expression = scanner.nextLine();
        double result = evaluate(expression);
        System.out.println("El resultado es: " + result);
        scanner.close();
    }
}
