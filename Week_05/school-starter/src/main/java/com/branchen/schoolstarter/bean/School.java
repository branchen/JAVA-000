package com.branchen.schoolstarter.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    @Autowired(required = true)
    Klass klass;
    
    @Resource(name = "student100")
    Student student100;
    
    @Override
    public void ding(){
        System.out.println("klass have " + this.klass.getStudents().size() + " students and one is " + this.student100);
        
    }
    
}
