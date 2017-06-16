package com.jbaxenom.laget.configuration.retries;

import org.testng.ITestResult;

import java.lang.annotation.Annotation;

/**
 * Enables a total of <code>runCount</code> times if the <code>@Retry</code> annotation is present.
 *
 * @author chema.delbarco
 */
public class RetryAnnotationRetryAnalyzer extends AbstractRetryAnalyzer {

    @Override
    public boolean retry(ITestResult result) {

        // If it's the first time retrying, set maxRunCount to the "count" value in the Retry
        // annotation or default to 1 (no retry) if the annotation is not used

        Annotation retryAnnotation =
            result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Retry.class);

        if (runCount.get() == 0) {
            maxRunCount = (retryAnnotation == null) ? 1 : ((Retry) retryAnnotation).count();
        }

        return processRetry(result);
    }
}
