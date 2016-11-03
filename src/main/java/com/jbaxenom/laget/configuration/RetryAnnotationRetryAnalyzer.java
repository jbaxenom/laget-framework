package com.jbaxenom.laget.configuration;

import org.testng.ITestResult;

import java.lang.annotation.Annotation;

/**
 * Implementation of TestNG's retry analyzer.
 * <p>Stores the logic for retrying test methods.
 *
 * @author chema.delbarco
 */
public class RetryAnnotationRetryAnalyzer extends RetryAnalyzer {

    public boolean retry(ITestResult result) {

        // If it's the first time retrying, set MAX_RUN_COUNT to the "count" value in the Retry
        // annotation or default to 1 (no retry) if the annotation is not used

        Annotation retryAnnotation =
            result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Retry.class);

        if (count.get() == 0) {
            MAX_RUN_COUNT = (retryAnnotation == null)
                ? 1
                : ((Retry) retryAnnotation).count();
        }

        return processRetry(result);
    }
}
