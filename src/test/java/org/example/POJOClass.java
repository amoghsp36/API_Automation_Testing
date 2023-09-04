package org.example;

import lombok.Data;

import java.util.List;

@Data
public class POJOClass {
    private String firstname;
    private String lastname;
    private int age;
    private double salary;
    //private List<POJOClass> jsonArray;
    private NestedPojoClass address;
}
