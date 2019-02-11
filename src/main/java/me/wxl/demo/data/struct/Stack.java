package me.wxl.demo.data.struct;

import java.util.EmptyStackException;

/**
 * @author wxl
 * @date 2019-02-10
 */
public class Stack<T> {
    private Object[] elementData;
    private int top = 0;

    public Stack() {
        this(10);
    }

    public Stack(int capacity) {
        this.elementData = new Object[capacity];
    }

    public void push(T element) {
        if (top >= elementData.length) {
            throw new StackOverflowError();
        }
        elementData[top++] = element;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (top == 0) {
            throw new EmptyStackException();
        }
        return (T) elementData[top - 1];
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) {
            throw new EmptyStackException();
        }
        T ret = (T) elementData[--top];
        elementData[top] = null;
        return ret;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    public static void main(String[] args) {
        int size = 5;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            System.out.printf("%d, ", stack.pop());
        }
        System.out.println();

//        String expression = "( 3 + 4 ) * 2 + ( 12 - 2 * 3 ) / 3 * 2 - 3";
//        System.out.printf("%s = %d\n", expression, StackTest.eval(expression));
//        expression = "1 + 2 + 6 * 4 * 5 / 4 / 2 * 3 + 1 - 2 + 3 + 2 * 5";
//        System.out.printf("%s = %d\n", expression, StackTest.eval(expression));
    }

    private static class StackTest {
        // 简单计算：expression只包含+-*/()，数字只能是正整数和0，数字与操作符用空格隔开，如："( 3 + 4 ) * 2 + ( 12 - 2 * 3 ) / 3 * 2 - 3"
        private static int eval(String expression) {
            String[] strings = expression.split(" ");
            Stack<String> operators = new Stack<>(strings.length);
            Stack<Integer> numbers = new Stack<>(strings.length);

            Integer a, b;
            for (int i = 0; i < strings.length; i++) {
                String str = strings[i];
                switch (str) {
                    case "+":
                        // fallthrough
                    case "-":
                        // fallthrough
                    case "*":
                        // fallthrough
                    case "/":
                        // fallthrough
                    case "(":
                        operators.push(str);
                        break;
                    case ")":
                        calculate(operators, numbers);
                        break;
                    default:
                        Integer n = Integer.valueOf(str);
                        numbers.push(n);
//                        if (!operators.isEmpty()) {
//                            String operator = operators.peek();
//                            if ("*".equals(operator)) {
//                                operators.pop();
//                                a = numbers.pop();
//                                b = numbers.pop();
//                                numbers.push(b * a);
//                            }
//                            if ("/".equals(operator)) {
//                                operators.pop();
//                                a = numbers.pop();
//                                b = numbers.pop();
//                                numbers.push(b / a);
//                            }
//                        }
                        break;
                }
            }
            calculate(operators, numbers);
            return numbers.pop();
        }

        private static void calculate(Stack<String> operators, Stack<Integer> numbers) {
            while (!operators.isEmpty()) {
                Stack<String> opertors2 = new Stack<>(operators.size());
                Stack<Integer> numbers2 = new Stack<>(numbers.size());
                numbers2.push(numbers.pop());

                int priority = getPriority(operators.peek());
                boolean done = false;
                while (!operators.isEmpty()) {
                    String operator = operators.peek();
                    if (operator.equals("(")) {
                        operators.pop();
                        done = true;
                        break;
                    }
                    if (getPriority(operator) == priority) {
                        opertors2.push(operators.pop());
                        numbers2.push(numbers.pop());
                    } else {
                        break;
                    }
                }
                int n = calculateSamePriority(opertors2, numbers2);
                numbers.push(n);

                if (done) {
                    break;
                }
            }
        }

        private static int calculateSamePriority(Stack<String> operators, Stack<Integer> numbers) {
            while (!operators.isEmpty()) {
                Integer a = numbers.pop();
                Integer b = numbers.pop();
                String operator = operators.pop();
                switch (operator) {
                    case "+":
                        numbers.push(a + b);
                        break;
                    case "-":
                        numbers.push(a - b);
                        break;
                    case "*":
                        numbers.push(a * b);
                        break;
                    case "/":
                        numbers.push(a / b);
                        break;
                }
            }
            return numbers.pop();
        }

        private static int getPriority(String operator) {
            switch (operator) {
                case "*":
                    return 2;
                case "/":
                    return 2;
                case "+":
                    return 1;
                case "-":
                    return 1;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

}
