package com.branchen.gtwk05code.lesson09.question02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author bran.chen
 * @description
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AnnotationDemo.class)
public class AnnotationDemoTest {
    @Autowired
    private AnnotationDemo demo;

    @Test
    public void test() {
        demo.info();
    }
}
