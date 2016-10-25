package com.jbaxenom.laget.configuration;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Implementation of TestNG's retry analyzer.
 * <p>Stores the logic for retrying test methods.
 *
 * @author chema.delbarco
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    // Change this value to enable retries on failure
    public static final int MAX_RETRY_COUNT = 1;

    private final ThreadLocal<Integer> count = new ThreadLocal<Integer>() {

        @Override
        protected synchronized Integer initialValue() {
            return new Integer(0);
        }

    };

    public boolean retry(ITestResult result) {
        count.set(count.get() + 1);

        if (count.get() <= MAX_RETRY_COUNT) {
            System.out.println("Retrying test: " + result.getName() + ". Retry count: " + count.get());
            return true;
        } else {
            return false;
        }
    }
}
