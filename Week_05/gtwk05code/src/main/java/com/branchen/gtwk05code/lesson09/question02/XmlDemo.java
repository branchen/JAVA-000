package com.branchen.gtwk05code.lesson09.question02;

import org.springframework.stereotype.Component;

/**
 * XML 注解方式
 * @author bran.chen
 * @description
 */
@Component
public class XmlDemo {
    public XmlDemo() {
        System.out.println("-------Construct------");
    }

    public void info() {
        System.out.println("---------XML DEMO---------");
    }
}
