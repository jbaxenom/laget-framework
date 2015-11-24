package com.jbaxenom.laget.api.test_data;

/**
 * Created by jbaxenom on 2014-07-23.
 */
public class Luhn {

    /**
     * Computes and returns the Luhn check digit for the given numeric string.
     *
     * @param numberString the numeric string to calculate the digit for
     * @return
     */
    public static String generateDigit(String numberString) {
        int sum = 0, checkDigit = 0;

        boolean isDouble = true;
        for (int i = numberString.length() - 1; i >= 0; i--) {
            int k = Integer.parseInt(String.valueOf(numberString.charAt(i)));
            sum += sumToSingleDigit((k * (isDouble ? 2 : 1)));
            isDouble = !isDouble;
        }

        if ((sum % 10) > 0) {
            checkDigit = (10 - (sum % 10));
        }

        return Integer.toString(checkDigit);
    }

    private static int sumToSingleDigit(int k) {
        if (k < 10) {
            return k;
        }
        return sumToSingleDigit(k / 10) + (k % 10);
    }
}
