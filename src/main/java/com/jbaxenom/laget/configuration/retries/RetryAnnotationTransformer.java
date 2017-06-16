package com.jbaxenom.laget.configuration.retries;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Enables automatic retry on error in any test method or class is using the @Retry annotation.
 *
 * @author chema.delbarco
 */
public class RetryAnnotationTransformer implements IAnnotationTransformer {

  public synchronized void transform(ITestAnnotation annotation, Class testClass,
                                     Constructor testConstructor, Method testMethod) {

      if (testMethod.isAnnotationPresent(Retry.class)) {
          annotation.setRetryAnalyzer(RetryAnnotationRetryAnalyzer.class);
      }

  }
}
