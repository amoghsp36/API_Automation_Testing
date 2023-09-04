package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

//DataDrivenTests
public class DataProviderTests extends DataProviderClass{

    @BeforeTest
    public void setUp(){
        baseURI = "http://localhost:3000/";
    }

    @Test(dataProvider = "testDataForPost")
    public void postRequest(String firstname, String lastname, int id, int subjectid){
//        JSONObject req = new JSONObject();
//        req.put("firstname", firstname);
//        req.put("lastname", lastname);
//        req.put("id", id);
//        req.put("subjectid", subjectid);
        JSONObject subReq = new PayloadBuilder()
                .withFirstName(firstname).withLastName(lastname).withId(id).withSubjectId(subjectid).build();
        JSONObject req=new PayloadBuilder()
                .withFirstName(firstname).withId(id).withSubData(subReq).build();

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
                .when()
                .post("users")
                .then()
                .statusCode(201)
                .log().all();              //response
    }
    @Test
    public void assertTests(){
        String lastname = given().get("users/9").then().extract().response().asString();
        JsonPath jsonPath = new JsonPath(lastname);  //query the json structure to id
        String assertLastname = jsonPath.getString("anotherPayload.lastname");    //provide accurate path if there are nested structures
        int id = jsonPath.getInt("anotherPayload.id");
        Assert.assertEquals(assertLastname,"idol");
        Assert.assertEquals(id, 9);
        //given().get("users").then().body("req[6].id",equalTo(9));
    }

    @Test(dataProvider = "deleteData")
    public void deleteUser(int userId){
        when().delete("users/"+userId);
    }
    @Test
    public void getProperty(){
        String res= given().get("users/5/").then().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(res);
        System.out.println(jsonPath.getString("firstname"));
        String name = given().get("users/9").then().statusCode(200).extract().response().asString();
        JsonPath jsonPath1 = new JsonPath(name);
        System.out.println(jsonPath1.getString("anotherPayload.lastname"));
    }

    @Test(dataProvider = "arrayData")
    public void subProperties(String firstname, String lastname, int id, int subjectid){
        JSONObject subReq = new PayloadBuilder()
                .withFirstName(firstname).withLastName(lastname).withId(id).withSubjectId(subjectid).build();
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(subReq.toJSONString())
                .when()
                .post("users")
                .then()
                .statusCode(201)
                .log().all();

    }

    @AfterTest
    public void tearDown(){
        RestAssured.reset();
    }


}
