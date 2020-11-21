package com.branchen.schoolstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.branchen.schoolstarter.bean.Klass;
import com.branchen.schoolstarter.bean.School;
import com.branchen.schoolstarter.bean.Student;

@Configuration
@ConditionalOnProperty(prefix = "myschool", name = "enabled", havingValue = "true",
        matchIfMissing = true)
public class SchoolConfig implements EnvironmentAware {

    private Environment environment;

    @Bean
    @ConditionalOnMissingBean(Klass.class)
    @ConditionalOnProperty(prefix="myschool.school", name="klass", matchIfMissing = true)
    public Klass klass() {
        String idStr = environment.getProperty("myschool.school.klass.id");
        String name = environment.getProperty("myschool.school.klass.name");
        return new Klass(Integer.parseInt(idStr),name);
    }

    @Bean("student100")
    @ConditionalOnProperty(prefix="myschool.school", name="student100", matchIfMissing = true)
    public Student student100(){
        String idStr = environment.getProperty("myschool.school.student100.id");
        String name = environment.getProperty("myschool.school.student100.name");
        return new Student(Integer.parseInt(idStr),name);
    }

    @Bean
    @ConditionalOnMissingBean(School.class)
    @ConditionalOnProperty(prefix="myschool",name="school",matchIfMissing = true)
    public School school(){
        School school = new School();

        return school;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
