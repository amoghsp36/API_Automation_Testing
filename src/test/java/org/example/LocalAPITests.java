package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static io.restassured.RestAssured.*;

public class LocalAPITests {
    @Test
    public void getOperation(){

        baseURI = "http://localhost:3000/";
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("users?id=2")
                .then().statusCode(200)
                .log().all();
    }

    @Test
    public void postOperation(){

        baseURI = "http://localhost:3000/";
        JSONObject postReq = new JSONObject();
        postReq.put("name","Ai");
        postReq.put("id",3);

//        JSONObject postReq2 = new JSONObject();
//        postReq2.put("subjectcredits",3);
//        postReq2.put("subjectname","webautomation");
//        postReq2.put("subjectid",2);

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(postReq.toJSONString())
                .when()
                .post("users").then()
                .statusCode(201).log().all();
//        given().contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body(postReq2.toJSONString())
//                .when()
//                .post("subjects")
//                .then().statusCode(201)
//                .log().all();
        RestAssured.reset();
    }

    @Test
    public void putOperation(){
        baseURI = "http://localhost:3000/";
        JSONObject putReq = new JSONObject();
        putReq.put("name","Aichan");
        putReq.put("id",5);
        //putReq.put("subjectcredits",4);

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(putReq.toJSONString())
                .when()
                .put("users/3")
                .then()
                .statusCode(200).log().all();
    }
    @Test
    public void deleteOperation(){
        baseURI = "http://localhost:3000/";
        when().delete("subjects/3").then().statusCode(204);
    }

    @BeforeTest
    public void setUp(){
        baseURI = "http://localhost:3000/";
    }
    @Test
    public void patchOperation() throws IOException {
        JSONObject patchReq = new JSONObject();
        patchReq.put("firstname","json");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(patchReq.toJSONString())
                .when()
                .patch("users/1")
                .then()
                .statusCode(200)
                .log().all();
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new URL("http://localhost:3000/users"));
        ((ObjectNode)jsonNode).remove("name");
        //when().delete("users?name=ame").then().statusCode(204);
    }
    @AfterTest
    public void tearDown(){
        RestAssured.reset();
    }

}
