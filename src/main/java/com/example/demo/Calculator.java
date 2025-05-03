package com.example.demo;

/**
 * A utility class that provides various mathematical operations.
 */
public class Calculator {

    /**
     * Adds two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts the second integer from the first.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the result of a - b
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides the first integer by the second.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the result of a / b
     * @throws ArithmeticException if b is 0
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    /**
     * Calculates the square of an integer.
     *
     * @param a the integer
     * @return the square of a
     */
    public int square(int a) {
        return a * a;
    }

    /**
     * Calculates the square root of an integer.
     *
     * @param a the integer
     * @return the square root of a
     * @throws IllegalArgumentException if a is negative
     */
    public double squareRoot(int a) {
        if (a < 0) {
            throw new IllegalArgumentException("Cannot calculate the square root of a negative number.");
        }
        return Math.sqrt(a);
    }

    /**
     * Calculates the power of a base raised to an exponent.
     *
     * @param base the base
     * @param exponent the exponent
     * @return the result of base^exponent
     */
    public double power(int base, int exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Calculates the factorial of a non-negative integer.
     *
     * @param n the integer
     * @return the factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot calculate the factorial of a negative number.");
        }
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Calculates the modulus of two integers.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the remainder of a / b
     * @throws ArithmeticException if b is 0
     */
    public int modulus(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a % b;
    }

    /**
     * Calculates the average of an array of integers.
     *
     * @param numbers the array of integers
     * @return the average of the numbers
     * @throws IllegalArgumentException if the array is empty
     */
    public double average(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.length;
    }

    /**
     * Returns the maximum of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the larger of a and b
     */
    public int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Returns the minimum of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the smaller of a and b
     */
    public int min(int a, int b) {
        return a < b ? a : b;
    }

    /**
     * Calculates the absolute value of an integer.
     *
     * @param a the integer
     * @return the absolute value of a
     */
    public int absolute(int a) {
        return a < 0 ? -a : a;
    }

    /**
     * Calculates the percentage of a part relative to a total.
     *
     * @param total the total value
     * @param part the part value
     * @return the percentage of part relative to total
     * @throws ArithmeticException if total is 0
     */
    public double percentage(int total, int part) {
        if (total == 0) {
            throw new ArithmeticException("Total cannot be zero.");
        }
        return (double) part / total * 100;
    }

    /**
     * Calculates the nth Fibonacci number.
     *
     * @param n the position in the Fibonacci sequence
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot calculate the Fibonacci of a negative number.");
        }
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
