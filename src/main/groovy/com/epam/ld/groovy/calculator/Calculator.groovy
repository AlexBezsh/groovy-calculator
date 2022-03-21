package com.epam.ld.groovy.calculator

class Calculator {

    private static final String ADD = '+'
    private static final String DIVIDE = '/'
    private static final String MULTIPLY = '*'
    private static final String SUBTRACT = '-'
    private static final String LEFT_BRACE = '('
    private static final String RIGHT_BRACE = ')'

    static String calculate(String expression) {
        while (expression.contains(LEFT_BRACE)) {
            String subExpression = extractSubExpression(expression)
            expression = replaceWithResult(expression, subExpression)
        }
        new BigDecimal(calculateSimple(expression)).stripTrailingZeros().toPlainString()
    }

    private static String extractSubExpression(String expression) {
        String subExpression = expression.substring(expression.lastIndexOf(LEFT_BRACE))
        subExpression = subExpression.substring(0, subExpression.indexOf(RIGHT_BRACE) + 1)
        subExpression
    }

    private static String replaceWithResult(String expression, String subExpression) {
        expression.replace(subExpression, calculateSimple(subExpression.substring(1, subExpression.length() - 1)))
    }

    private static String calculateSimple(String expression) {
        expression = expression.replaceAll('[-\\+\\*/]+', {" ${it}" }).trim()
        List<String> operations = prioritizeOperations(expression.split(' '))
        NumOperation start = new NumOperation(operations[0])
        NumOperation last = start
        for (int i = 1; i < operations.size(); i++) {
            last.next = new NumOperation(operations[i])
            last = last.next
        }
        start.calculate()
    }

    private static List<String> prioritizeOperations(String[] operations) {
        List<String> lowPriority = new ArrayList<>()
        List<String> result = new ArrayList<>()
        int operationsNum = operations.size()
        String current
        String next
        for (int i = 0; i < operationsNum; i++) {
            current = operations[i]
            next = i + 1 < operationsNum ? operations[i + 1] : null
            if (next != null && (next.startsWith(MULTIPLY) || next.startsWith(DIVIDE))
                || current.startsWith(MULTIPLY) || current.startsWith(DIVIDE)) {
                result.add(current)
            } else {
                lowPriority.add(current)
            }
        }
        result.addAll(lowPriority)
        result
    }

    private static class NumOperation {

        String sign
        BigDecimal num
        NumOperation next

        NumOperation(String operation) {
            String numString = operation.substring(1)
            switch (operation.charAt(0)) {
                case ADD:
                    num = new BigDecimal(numString)
                    break
                case SUBTRACT:
                    num = new BigDecimal(numString).negate()
                    break
                case MULTIPLY:
                    sign = MULTIPLY
                    num = new BigDecimal(numString)
                    break
                case DIVIDE:
                    sign = DIVIDE
                    num = new BigDecimal(numString)
                    break
                default:
                    num = new BigDecimal(operation)
            }
        }

        BigDecimal calculate() {
            if (next == null) {
                return num
            }
            if (next.sign == MULTIPLY) {
                next.num = this * next
                return next.calculate()
            }
            if (next.sign == DIVIDE) {
                next.num = this / next
                return next.calculate()
            }
            this.num.add(next.calculate())
        }

        private BigDecimal multiply(NumOperation other) {
            this.num.multiply(other.num)
        }

        private BigDecimal div(NumOperation other) {
            this.num.divide(other.num)
        }

    }

}
