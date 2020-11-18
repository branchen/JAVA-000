package com.branchen.gtwk05code.lesson09.question02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bran.chen
 * @description
 */
@Configuration
public class BeanDefinitionConfig {

    @Bean
    public BeanDefinitionDemo beanDefinitionDemo() {
        return new BeanDefinitionDemo();
    }
}
