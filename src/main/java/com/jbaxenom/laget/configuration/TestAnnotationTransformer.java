package com.jbaxenom.laget.configuration;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Implementation of TestNG's annotation transformer to include automatic retry on error in any test
 * using the @Test annotation
 *
 * @author chema.delbarco
 */
public class TestAnnotationTransformer implements IAnnotationTransformer {

  public synchronized void transform(ITestAnnotation annotation, Class testClass,
                                     Constructor testConstructor, Method testMethod) {

      if (testMethod.isAnnotationPresent(Retry.class)) {
          annotation.setRetryAnalyzer(TestAnnotationRetryAnalyzer.class);
      }

  }
}
