package com.company;


import java.util.LinkedList;

public class Main {

    static boolean isSpace(char c) { // проверка формулы на пробелы
        return c == ' ';
    }
    static boolean isOperator(char c) { // определение знака операции
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }
    static int prioritet(char op) {
        switch (op) { // определение приоритета знака операции при + или - возврат 1, при * / % 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Double> st, char op) {
        double r_operand = st.removeLast(); // получение правого операнда
        double l_operand = st.removeLast(); // получение левого операнда
        switch (op) { // выполнение действия между операндами и результат записывается в st
            case '+':
                st.add(l_operand + r_operand);
                break;
            case '-':
                st.add(l_operand - r_operand);
                break;
            case '*':
                st.add(l_operand * r_operand);
                break;
            case '/':
                st.add(l_operand / r_operand);
                break;
            case '%':
                st.add(l_operand % r_operand);
                break;
        }
    }
    public static Double eval(String s) {
        LinkedList<Double> st = new LinkedList<Double>(); // контейнер для операндов в порядке поступления
        LinkedList<Character> op = new LinkedList<Character>(); // контейнер для знаков операции в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсинг формулы
            char c = s.charAt(i);
            if (isSpace(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st,op.removeLast());
                op.removeLast();
            } else if (isOperator(c)) {
                while (!op.isEmpty() && prioritet(op.getLast()) >= prioritet(c))
                    processOperator(st, op.removeLast());
                op.add(c);
            } else {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i)))
                    operand += s.charAt(i++);
                --i;
                st.add(Double.parseDouble(operand)); // для чисел, которые содержат больше одной цифры
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        System.out.println("Результат вычисления: ");
        System.out.println(st.get(0)); // возврат результата
        return st.get(0);  // возврат результата
    }

    public static void main  (String[] args) throws Exception {
        Main obj = new Main();
        String formula = "(10 + 2) * 2 * (8-6)";
        obj.eval(formula);
    }
}
