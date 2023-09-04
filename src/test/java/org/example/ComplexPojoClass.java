package org.example;

import lombok.Data;

import java.util.List;

@Data
public class ComplexPojoClass {
    private POJOClass arrayOfJsonObjects;
    private ComplexPojoSubClass companyDetails;
}
