package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class FirstAPITest {
    @Test
    public void apiTests(){
        Response response = get("https://reqres.in/api/users?page=2");

        System.out.println(response.getStatusCode());
        System.out.println(response.getContentType());
        System.out.println(response.getStatusLine());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asPrettyString());

        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    public void api_test2(){
        baseURI = "https://reqres.in/api/";
        //given().get("users/2").then().statusCode(200);
        given().get("users/2").then().statusLine("HTTP/1.1 200 OK");
        given().get("users?page=2")
                .then()
                .body("data[2].id",equalTo(9))
                .log().all();
        given().get("users?page=2")
                .then()
                .body("data.first_name",hasItems("George","Rachel"));

    }

    @Test
    public void postTest(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("name","abcd");
//        map.put("email","abcd@gmail.com");
//
//        System.out.println(map);

        JSONObject request = new JSONObject();
        request.put("id",5);
        request.put("address","bangalore");
        System.out.println(request.toJSONString());

        baseURI = "https://reqres.in/api/";

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("users")
                .then().statusCode(201)
                .log().all();
    }
    @Test
    public void putPatchTest(){
        //we need the payload put
        JSONObject putRequest=new JSONObject();
        putRequest.put("name","Ken");
        //putRequest.put("hobby","gaming");
        putRequest.put("job","Athlete");

        System.out.println(putRequest.toJSONString());

        baseURI = "https://reqres.in/api/";
        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(putRequest.toJSONString())
                .when().patch("users/2")  //update
                .then().statusCode(200).log().all();
    }
    @Test
    public void deleteTest(){

        baseURI = "https://reqres.in/api/";
        when().delete("users/2").then().statusCode(204);
    }

}
