package org.example;

import lombok.Data;

import java.util.List;
@Data
public class ComplexPojoSubClass {
    private String companyName;
    private NestedPojoClass address;
    private List<String> bankAssociates;
}
