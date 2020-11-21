package com.branchen.schoolstarter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SchoolConfig.class)
public class SchoolAutoConfiguration {
}