package com.branchen.schoolstarter.bean;

import lombok.Data;

import java.util.List;

@Data
public class Klass {
    private int id;
    private String name;
    List<Student> students;

    public Klass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void dong(){
        System.out.println(this.getStudents());
    }


}
