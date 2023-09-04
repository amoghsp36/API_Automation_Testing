package org.example;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.containsExactly;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;

//contains, equalTo, containsInAnyOrder, is(same as equalTo), hasItems, everyItem, greaterThan, lessThan, allOf, anyOf
public class HamcrestTests {

    @BeforeTest
    public void setUp(){
        baseURI = "http://localhost:3000/";
    }
    @Test
    public void shouldAssert(){
        //Arrange
        List<Integer> list= Arrays.asList(1,2,3,4);
        Integer[] intArray = {1,2,3,4};
        int a=5;


        //Act

        //Assert
        assertThat(list,containsInAnyOrder(1,3,2,4));
        //MatcherAssert.assertThat(list,contains(2)); //fails
        assertThat(intArray,arrayContainingInAnyOrder(2,3,1,4));
        //MatcherAssert.assertThat(list,containsInAnyOrder(2));
        //MatcherAssert.assertThat(list,containsInAnyOrder(2,3));
        assertThat(list,hasSize(4));
        //MatcherAssert.assertThat(list,allOf());
        //MatcherAssert.assertThat(a,is(not(equalTo(list))));
        //MatcherAssert.assertThat(list,not(contains(8)));
        assertThat(list,everyItem(greaterThanOrEqualTo(1)));

    }

    @AfterTest
    public void tearDown(){
        RestAssured.reset();
    }

}
