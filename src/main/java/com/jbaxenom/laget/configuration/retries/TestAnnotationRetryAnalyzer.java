package com.jbaxenom.laget.configuration.retries;

import org.testng.ITestResult;

/**
 * Enables a total of <code>DEFAULT_MAX_RUN_COUNT</code> times if TestNG's <code>@Test</code> annotation is present.
 *
 * @author chema.delbarco
 */
public class TestAnnotationRetryAnalyzer extends AbstractRetryAnalyzer {

    @Override
    public boolean retry(ITestResult result) {
        return processRetry(result);
    }
}
