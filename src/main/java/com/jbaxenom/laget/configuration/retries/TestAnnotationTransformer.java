package com.jbaxenom.laget.configuration.retries;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Enables automatic retry on error in any test method or class using TestNG's <code>@Test</code> annotation.
 *
 * To change the total amount of test runs, change the <code>MAX_RUN_COUNT</code> attribute value in
 * {@link TestAnnotationRetryAnalyzer}.
 *
 * @author chema.delbarco
 */
public class TestAnnotationTransformer implements IAnnotationTransformer {

    public synchronized void transform(ITestAnnotation annotation, Class testClass,
        Constructor testConstructor, Method testMethod) {

        annotation.setRetryAnalyzer(TestAnnotationRetryAnalyzer.class);
    }
}
