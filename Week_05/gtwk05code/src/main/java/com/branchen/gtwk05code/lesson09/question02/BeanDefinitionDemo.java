package com.branchen.gtwk05code.lesson09.question02;

import org.springframework.stereotype.Component;

/**
 * @author bran.chen
 * @description
 */
@Component
public class BeanDefinitionDemo {
    public BeanDefinitionDemo() {
        System.out.println("-------Construct------");
    }

    public void info() {
        System.out.println("---------bean definition DEMO---------");
    }
}
