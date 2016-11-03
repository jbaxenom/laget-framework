package com.jbaxenom.laget.configuration;

import org.testng.ITestResult;

/**
 * Implementation of TestNG's retry analyzer.
 * <p>Stores the logic for retrying test methods.
 *
 * @author chema.delbarco
 */
public class TestAnnotationRetryAnalyzer extends RetryAnalyzer {

    @Override
    public boolean retry(ITestResult result) {

        // Change this value to the max amount of times you want the test to run (original + retry)
        MAX_RUN_COUNT = 2;

        return processRetry(result);
    }
}
