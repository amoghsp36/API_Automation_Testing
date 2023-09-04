package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

public class ReqResSpecifications {

    static RequestSpecification getCommonSpec(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:3000/");
        builder.setBasePath("users/");
        RequestSpecification requestSpecification = builder.build();
        return requestSpecification;
    }

    @Test
    public void reqSpecificationTesting(){
        given().spec(getCommonSpec()).when().get("9").getBody().prettyPrint();
        given().spec(getCommonSpec()).queryParam("id","8").get().getBody().prettyPrint();
    }

    static ResponseSpecification commonSpec(){
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectContentType(ContentType.JSON);

        ResponseSpecification responseSpecification = builder.build();
        return responseSpecification;
    }

    @Test
    public void resSpecificationTesting(){
        given().spec(getCommonSpec()).get("9").then().spec(commonSpec()).body("9",is(anything())).log().everything();
    }
}
