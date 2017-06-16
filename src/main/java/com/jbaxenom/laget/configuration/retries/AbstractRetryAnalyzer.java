package com.jbaxenom.laget.configuration.retries;

import com.jbaxenom.laget.configuration.Configuration;
import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Thread-safe implementation of TestNG's retry analyzer.
 *
 * @author chema.delbarco
 */
public abstract class AbstractRetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = Logger.getLogger(AbstractRetryAnalyzer.class);

    protected int maxRunCount = Configuration.getMaxRunCount();

    protected final ThreadLocal<Integer> runCount = new ThreadLocal<Integer>() {
        @Override
        protected synchronized Integer initialValue() {
            return new Integer(0);
        }
    };

    protected final boolean processRetry(ITestResult result) {
        runCount.set(runCount.get() + 1);

        if (runCount.get() < maxRunCount) {
            log.trace(String.format("Test %s failed. Marking this run as SKIPPED. Retrying! (count: %s + )",
                result.getName(),
                runCount.get()));
            return true;
        } else {
            log.trace(String.format("Test %s failed after %s runs. Marking this run as FAILED",
                result.getName(),
                runCount.get()));
            return false;
        }
    }

}
