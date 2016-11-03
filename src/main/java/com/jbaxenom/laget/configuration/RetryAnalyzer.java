package com.jbaxenom.laget.configuration;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Implementation of TestNG's retry analyzer.
 * <p>Stores the logic for retrying test methods.
 *
 * @author chema.delbarco
 */
public abstract class RetryAnalyzer implements IRetryAnalyzer {

    protected static int MAX_RUN_COUNT;

    /**
     * stores the current count of test retries in a thread-safe variable to ensure it works in
     * concurrent test runs
     */
    protected final ThreadLocal<Integer> count = new ThreadLocal<Integer>() {

        @Override
        protected synchronized Integer initialValue() {
            return new Integer(0);
        }

    };

    public boolean retry(ITestResult result) {
        return false;
    }

    public boolean processRetry(ITestResult result) {
        // Increment counter
        count.set(count.get() + 1);

        if (count.get() < MAX_RUN_COUNT) {
            System.out.println("\nTest " + result.getName() + " Failed. Retrying! (count: " + count.get() + ")\n");
            return true;
        } else {
            System.out.println("\nTest " + result.getName() + " failed after " + count.get() + " runs. Marking it as FAILED\n");
            return false;
        }
    }
}
