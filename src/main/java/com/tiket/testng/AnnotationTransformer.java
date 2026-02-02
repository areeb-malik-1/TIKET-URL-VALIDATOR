package com.tiket.testng;

import com.tiket.annotation.Scope;
import com.tiket.model.Platform;
import com.tiket.testbase.Config;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        Platform[] platforms = testMethod.getAnnotation(Scope.class).platforms();
        for(Platform platform : platforms) {
            if (platform == Config.PLATFORM) {
                annotation.setEnabled(true);
                return;
            }
        }
    }
}