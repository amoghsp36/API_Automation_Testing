package org.example;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthenticationTests {

    @BeforeTest
    public void setUp(){
        RestAssured.authentication = RestAssured.preemptive().basic("ToolsQA","TestPassword");
        RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
    }
    @Test
    public void preemptiveAuthTest(){
        given()
                .get()
                .then().statusCode(200);
    }
    @Test
    public void oauth2(){
        Response response = given().formParam("client_id","dummyApplication")
                .formParam("client_secret","4f5468c0ce9bcfd55bcd9c1b7d10f5ef9ac817b2")
                .formParam("grant_type","client_credentials")
                .post("https://api.imgur.com/oauth2/token");
        System.out.println(response.getBody().asPrettyString());
        System.out.println((char[]) response.jsonPath().get("access_token"));
        String token = response.jsonPath().get("access_token");

        Response response1 = given()
                .auth()
                .oauth2(token)
                .post("baseURI/userid/scopeURL");
        Assert.assertEquals(response1.getStatusCode(),200);

    }

    @AfterTest
    public void tearDown(){
        RestAssured.reset();
    }
}
