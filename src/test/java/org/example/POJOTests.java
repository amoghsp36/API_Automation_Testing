package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.when;

public class POJOTests extends DataProviderClass{
    @Test
    public void convertClassObjectToJsonObject() throws JsonProcessingException {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.baseURI = "http://httpbin.org/post";
        //RestAssured.requestSpecification = "";

        POJOClass classObj = new POJOClass();
        classObj.setFirstname("abcd");
        classObj.setLastname("xyz");
        classObj.setAge(20);
        classObj.setSalary(40000.00);

        POJOClass classObj2 = new POJOClass();
        classObj2.setFirstname("jcec");
        classObj2.setLastname("infw");
        classObj2.setAge(22);
        classObj2.setSalary(78343.343);

        List<POJOClass> jsonArray = new ArrayList<>();
        jsonArray.add(classObj);
        jsonArray.add(classObj2);

        //using jackson api to convert class obj to json obj as string(serialization)
        ObjectMapper objectMapper = new ObjectMapper();

        String jasonArray = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
        System.out.println(jsonArray);

        // creating request specification
        RequestSpecification requestSpecification = RestAssured.given();

        requestSpecification
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jasonArray);
        Response response = requestSpecification.post();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200, "status code is not matching");

        ResponseBody responseBody = response.getBody();

        JsonPath jasonPathView = responseBody.jsonPath();
        List<POJOClass> allObjects = jasonPathView.getList("json", POJOClass.class);

        for(POJOClass obj:allObjects){
            System.out.println(obj.getFirstname());
        }
//        POJOClass deserialization = objectMapper.readValue(jasonArray, POJOClass.class);
//        System.out.println(deserialization.getFirstname());

        //System.out.println("firstname: "+classObj.getFirstname());
    }


    @Test
    public void convertNestedJsonToClassObject() throws JsonProcessingException {
        POJOClass nestedPojo = new POJOClass();
        nestedPojo.setFirstname("quon");
        nestedPojo.setLastname("kisha");
        nestedPojo.setAge(25);
        nestedPojo.setSalary(46363.55);

        NestedPojoClass nestedPojoClass = new NestedPojoClass();
        nestedPojoClass.setStreet("abcd");
        nestedPojoClass.setCity("bangalore");
        nestedPojoClass.setPinCode(560091);

        nestedPojo.setAddress(nestedPojoClass);

        //serialization
        ObjectMapper objectMapper = new ObjectMapper();
        String nestedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nestedPojo);
        System.out.println(nestedJson);

        RestAssured.baseURI = "http://httpbin.org/post";
        RequestSpecification reqSpec = RestAssured.given();

        reqSpec.contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(nestedJson);
        Response response = reqSpec.post();
        response.getBody().prettyPrint();
        Assert.assertEquals(response.statusCode(),200);

    }

    @Test(dataProvider = "dataForPojoClasses")
    public void convertComplexJsonObjectToClassObject(String firstname, String lastname, int age, double salary) throws JsonProcessingException {

        ComplexPojoSubClass complexPojoSubClass = new ComplexPojoSubClass();
        complexPojoSubClass.setCompanyName("API_Testing");

        NestedPojoClass nested = new NestedPojoClass();
        nested.setStreet("abdf");
        nested.setCity("wufin");
        nested.setPinCode(89322);
        complexPojoSubClass.setAddress(nested);

        ArrayList<String> abc = new ArrayList<>();
        abc.add("hdfc");
        abc.add("icici");
        abc.add("axis");
        complexPojoSubClass.setBankAssociates(abc);

        POJOClass pojoClassForComplexJson = new POJOClass();
        pojoClassForComplexJson.setFirstname(firstname);
        pojoClassForComplexJson.setLastname(lastname);
        pojoClassForComplexJson.setAge(age);
        pojoClassForComplexJson.setSalary(salary);

//        POJOClass pojoClassForComplexJson2 = new POJOClass();
//        pojoClassForComplexJson.setFirstname(firstname);
//        pojoClassForComplexJson.setLastname(lastname);
//        pojoClassForComplexJson.setAge(age);
//        pojoClassForComplexJson.setSalary(salary);

//        List<POJOClass> complexJsonArray = new ArrayList<>();    //json array is the nature
//        complexJsonArray.add(pojoClassForComplexJson);         //no need of list because of the usage of dataprovider
//        complexJsonArray.add(pojoClassForComplexJson2);


        ComplexPojoClass complexPojoClass = new ComplexPojoClass();
        complexPojoClass.setArrayOfJsonObjects(pojoClassForComplexJson);
        //complexPojoClass.setArrayOfJsonObjects(complexJsonArray);
        complexPojoClass.setCompanyDetails(complexPojoSubClass);
        //Action

        ObjectMapper objectMapper = new ObjectMapper();
        String complexJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(complexPojoClass);

        System.out.println(complexJson);

        RestAssured.baseURI = "http://httpbin.org/post";
        RequestSpecification requestSpecification = RestAssured.given();

        requestSpecification.contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(complexJson);
        Response response = requestSpecification.post();
        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(dataProvider = "deleteData")
    public void deleteJsonObjects(int userId){
        when().delete("users/"+userId);
    }


}
