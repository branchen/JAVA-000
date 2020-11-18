package com.branchen.gtwk05code.lesson09.question02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author bran.chen
 * @description
 */
public class BeanDefinitionTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(BeanDefinitionConfig.class);
        BeanDefinitionDemo example = (BeanDefinitionDemo) configApplicationContext.getBean("beanDefinitionDemo");
        example.info();
    }
}
