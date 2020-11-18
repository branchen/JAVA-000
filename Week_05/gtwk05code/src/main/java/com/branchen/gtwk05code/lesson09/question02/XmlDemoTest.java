package com.branchen.gtwk05code.lesson09.question02;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author bran.chen
 * @description
 */
public class XmlDemoTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml-bean.xml");
        XmlDemo demo = (XmlDemo) context.getBean("XmlDemo");
        demo.info();
    }
}
