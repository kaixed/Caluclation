package com.kaixed.caluculation.model;

import com.kaixed.caluculation.entity.Equation;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewFourNum {
    private final int countOperands = 4;
    private final List<Double> operands = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
    private final List<Character> operators = new ArrayList<>(Arrays.asList(' ', ' ', ' ', ' '));
    private final ArrayList<Integer> leftColumns = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private final ArrayList<Integer> rightColumns = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private final String patternOperand = "[+\\-*/]";
    private final String patternSign = "[+\\-*/()]";
    private final String patternDouble = "\\d+(\\.+\\d+)";
    private final List<Equation> equations = new ArrayList<>();

    public NewFourNum(int grade, int num) {
        generateEquations(grade, num);
    }


    public void generateEquations(int grade, int num) {
        if (grade == 1) {
            generateGradeOneEquations(num);
        } else if (grade == 2) {
            generateGradeTwoEquations(num);
        } else {
            Random random = new Random();
            // 处理其他年级的生成逻辑
            for (int q = 0; q < num; q++) {
                for (int i = 0; i < countOperands; i++) {
                    int maxNum = 10;
                    operands.set(i, random.nextDouble() * maxNum);
                }

                for (int i = 0; i < countOperands - 1; i++) {
                    char tempOperator;
                    if (grade == 1) {
                        tempOperator = random.nextBoolean() ? '+' : '-';
                    } else if (grade == 2) {
                        tempOperator = random.nextBoolean() ? '+' : (random.nextBoolean() ? '-' : '*');
                    } else {
                        int operatorIndex = random.nextInt(4);
                        tempOperator = getOperatorByIndex(operatorIndex);
                    }
                    operators.set(i, tempOperator);
                }
                generateParentheses();

                String equation = toStringQuestion();
                double result = result();
                // 添加元组到列表
                equations.add(new Equation(equation, String.valueOf(result), "", false));
            }
        }
    }

    private void generateGradeOneEquations(int num) {
        // 调用一年级的生成方法
        for (Equation equation : generateOneEquations(num)) {
            equations.add(equation);
        }

    }

    private void generateGradeTwoEquations(int num) {
        // 调用二年级的生成方法
        for (Equation equation : generateTwoEquations(num)) {
            equations.add(equation);
        }
    }

    public List<Equation> generateOneEquations(int count) {
        String[][] addition = GenerateTable.createAddition();
        String[][] subtraction = GenerateTable.createSubtraction();
        Random random = new Random();
        int addedCount = 0;
        List<Equation> calculation = new ArrayList<>();
        // 用于标记已使用的索引对
        boolean[][] used = new boolean[101][101];
        while (addedCount < count) {
            int randomI = random.nextInt(101);
            int randomJ = random.nextInt(101 - randomI);
            int result = 0;
            if (!used[randomI][randomJ] && !used[randomJ][randomI]) {
                int randomOperation = random.nextInt(2);
                String expression = "";
                if (randomOperation == 0) {
                    expression = addition[randomI][randomJ];
                    used[randomI][randomJ] = true;
                    result = randomI + randomJ;
                } else if (randomI >= randomJ) {
                    expression = subtraction[randomI][randomJ];
                    result = randomI - randomJ;
                } else {
                    continue; // 如果是减法且结果可能小于0，则重新生成
                }
                calculation.add(new Equation(expression, String.valueOf(result), "", false));
                addedCount++;
            }
        }
        return calculation;
    }

    public List<Equation> generateTwoEquations(int count) {
        String[][] addition = GenerateTable.createAddition();
        String[][] subtraction = GenerateTable.createSubtraction();
        String[][] mutiply = GenerateTable.createMutiply();
        Random random = new Random();
        int addedCount = 0;
        List<Equation> calculation = new ArrayList<>();
        // 用于标记已使用的索引对
        boolean[][] used = new boolean[101][101];
        while (addedCount < count) {

            int randomOperation = random.nextInt(3);
            String expression = "";
            if (randomOperation == 0) {
                int randomI = random.nextInt(11);
                int randomJ = random.nextInt(11);
                if (!used[randomI][randomJ] && !used[randomJ][randomI]) {
                    expression = mutiply[randomI][randomJ];
                    used[randomI][randomJ] = true;

                    int result = randomI * randomJ;
                    calculation.add(new Equation(expression, String.valueOf(result), "", false));
                    addedCount++;
                }

            } else if (randomOperation == 1) {
                int randomI = random.nextInt(101);
                int randomJ = random.nextInt(101 - randomI);
                if (!used[randomI][randomJ] && !used[randomJ][randomI]) {
                    expression = addition[randomI][randomJ];
                    used[randomI][randomJ] = true;

                    int result = randomI + randomJ;
                    calculation.add(new Equation(expression, String.valueOf(result), "", false));
                    addedCount++;
                }
            } else {
                int randomI = random.nextInt(101);
                int randomJ = random.nextInt(101 - randomI);
                if (!used[randomI][randomJ] && !used[randomJ][randomI]) {
                    if (randomI >= randomJ) {
                        expression = subtraction[randomI][randomJ];
                    } else {
                        continue;
                    }
                    int result = randomI - randomJ;
                    calculation.add(new Equation(expression, String.valueOf(result), "", false));
                    addedCount++;
                }
            }
        }


        return calculation;
    }

    private void generateParentheses() {
        int minCountColumns = 0;
        int maxCountColumns = 3;
        int countColumns = ThreadLocalRandom.current().nextInt(minCountColumns, maxCountColumns);
        for (int i = 0; i < countColumns; i++) {
            int left = ThreadLocalRandom.current().nextInt(0, countOperands - 1);
            leftColumns.set(left, leftColumns.get(left) + 1);
            int right = ThreadLocalRandom.current().nextInt(left + 1, countOperands);
            rightColumns.set(right, rightColumns.get(right) + 1);
        }

        for (int i = 0; i < countOperands; i++) {
            if (rightColumns.get(i) > 0 && leftColumns.get(i) > 0) {
                rightColumns.set(i, rightColumns.get(i) - 1);
                leftColumns.set(i, leftColumns.get(i) - 1);
            }
        }

        if (hasOverlappingLeftColumn() && hasOverlappingRightColumn()) {
            leftColumns.set(leftColumns.indexOf(2), 1);
            rightColumns.set(rightColumns.indexOf(2), 1);
        }
    }

    private boolean hasOverlappingLeftColumn() {
        return leftColumns.contains(2);
    }

    private boolean hasOverlappingRightColumn() {
        return rightColumns.contains(2);
    }

    private char getOperatorByIndex(int index) {
        switch (index) {
            case 0:
                return '+';
            case 1:
                return '-';
            case 2:
                return '*';
            case 3:
                return '/';
            default:
                return ' ';
        }
    }

    private boolean validColumns(String equation) {
        Stack<Character> columnStack = new Stack<>();
        for (int i = 0; i < equation.length(); i++) {
            char currentChar = equation.charAt(i);
            if (currentChar == '(') {
                columnStack.push('(');
            } else if (currentChar == ')' && !columnStack.isEmpty()) {
                columnStack.pop();
            } else if (currentChar == ')') {
                return false;
            }
        }
        return columnStack.isEmpty();
    }

    private boolean closedInColumns(String equation) {
        return equation.charAt(0) == '(' && equation.charAt(equation.length() - 2) == ')';
    }

    private void deleteColumn(StringBuilder equation) {
        Random random = new Random();
        boolean deleteFromRight = random.nextBoolean();
        if (deleteFromRight && (equation.lastIndexOf(")") - equation.indexOf(")") != 1)) {
            equation.deleteCharAt(equation.lastIndexOf(")"));
            equation.deleteCharAt(equation.lastIndexOf("("));
        } else if (!deleteFromRight && (equation.lastIndexOf("(") - equation.indexOf("(")) != 1) {
            equation.deleteCharAt(equation.indexOf("("));
            equation.deleteCharAt(equation.indexOf(")"));
        } else {
            equation.deleteCharAt(equation.indexOf("("));
            equation.deleteCharAt(equation.lastIndexOf(")"));
        }
    }

    public String toStringQuestion() {
        StringBuilder equation = new StringBuilder();
        for (int i = 0; i < countOperands; i++) {
            for (int j = 0; j < leftColumns.get(i); j++) {
                equation.append("(");
            }
            equation.append(String.format("%.1f", (double) operands.get(i)));
            for (int j = 0; j < rightColumns.get(i); j++) {
                equation.append(")");
            }
            equation.append(operators.get(i).toString());
        }

        if (closedInColumns(equation.toString())) {
            StringBuilder tempEquation = new StringBuilder();
            tempEquation.append(equation);
            tempEquation.deleteCharAt(0);
            tempEquation.deleteCharAt(tempEquation.length() - 2);
            if (validColumns(tempEquation.toString())) {
                deleteColumn(equation);
            }
        }
        return equation.toString();
    }

    private int priority(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    private String toSuffix() {
        String equation = this.toStringQuestion();
        Pattern pattern = Pattern.compile(patternDouble + "|" + patternSign);
        Matcher matcher = pattern.matcher(equation);
        Stack<String> operatorStack = new Stack<>();
        StringBuilder tempEquation = new StringBuilder();
        while (matcher.find()) {
            String currentOperand = matcher.group();
            if (currentOperand.matches(patternOperand)) {
                if (operatorStack.isEmpty()) {
                    operatorStack.push(currentOperand);
                } else if (priority(currentOperand) > priority(operatorStack.lastElement())) {
                    operatorStack.push(currentOperand);
                } else {
                    while (!operatorStack.isEmpty() && priority(currentOperand) <= priority(operatorStack.lastElement())) {
                        String operator = operatorStack.pop();
                        tempEquation.append(operator);
                        tempEquation.append(" ");
                    }
                    operatorStack.push(currentOperand);
                }
            } else if (currentOperand.matches("\\(")) {
                operatorStack.push(currentOperand);
            } else if (currentOperand.matches("\\)")) {
                while (!operatorStack.lastElement().matches("\\(")) {
                    String operator = operatorStack.pop();
                    tempEquation.append(operator);
                    tempEquation.append(" ");
                }
                operatorStack.pop();
            } else {
                tempEquation.append(currentOperand);
                tempEquation.append(" ");
            }
        }
        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            tempEquation.append(operator);
            tempEquation.append(" ");
        }
        return tempEquation.toString();
    }

    public Double result() {
        String equationSuffix = toSuffix();
        Pattern pattern = Pattern.compile(patternDouble + "?|" + patternOperand);
        Matcher matcher = pattern.matcher(equationSuffix);
        Stack<Double> operandStack = new Stack<>();
        while (matcher.find()) {
            String currentOperand = matcher.group();
            if (currentOperand.matches(patternOperand)) {
                double rightOperand = operandStack.pop();
                double leftOperand = operandStack.pop();
                double inter;
                switch (currentOperand) {
                    case "+":
                        inter = leftOperand + rightOperand;
                        break;
                    case "-":
                        inter = leftOperand - rightOperand;
                        break;
                    case "*":
                        inter = leftOperand * rightOperand;
                        break;
                    case "/":
                        inter = leftOperand / rightOperand;
                        break;
                    default:
                        inter = 0.0;
                        break;
                }
                operandStack.push(inter);
            } else {
                operandStack.push(Double.valueOf(currentOperand));
            }
        }
        return operandStack.pop();
    }

    public List<Equation> getEquations() {
        return equations;
    }

//    public static void main(String[] args) {
//
//
//        // 打印生成的算式
//        for (Equation equation : equations) {
//            System.out.println("Equation: " + equation.getEquation());
//            System.out.println("Result: " + equation.getResult());
//            System.out.println("User Input: " + equation.getInPutValue());
//            System.out.println("Is True: " + equation.isTrue());
//            System.out.println();
//        }
//    }
}
