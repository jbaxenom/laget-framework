package com.jbaxenom.laget.configuration.retries;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by jdelbarc on 02/11/16.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, CONSTRUCTOR})
public @interface Retry {

    /**
     * The maximum amount of times the test should be run (original + retries).
     */
    int count() default AbstractRetryAnalyzer.DEFAULT_MAX_RUN_COUNT;

}
