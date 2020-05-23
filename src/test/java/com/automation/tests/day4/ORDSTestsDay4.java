package com.automation.tests.day4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestsDay4 {
    /**
     * Warmup!
     * Given accept type is JSON
     * When users sends a GET request to “/employees”
     * Then status code is 200
     * And Content type is application/json
     * And response time is less than 3 seconds
     */
@BeforeAll
    public static void setup(){
    baseURI="http://54.224.118.38:1000/ords/hr/";
}

@Test
@DisplayName("Verify status code, content type and response time")
    public void employeesTest1(){
    given().
            accept(ContentType.JSON).
    when().
            get("/employees").prettyPeek().
    then().
            assertThat().statusCode(200).
            contentType(ContentType.JSON).
            time(lessThan(3L), TimeUnit.SECONDS);
}

/**
 *
 Given accept type is JSON
 And parameters: q = {"country_id":"US"}
 When users sends a GET request to "/countries"
 Then status code is 200
 And Content type is application/json
 And country_name from payload is "United States of America"
 *
 */

@Test
@DisplayName("Verify country name, content type and status code for country with ID US")
public void verifyCountriesTest1() {
    given().
            accept(ContentType.JSON).
            queryParam("q", "{\"country_id\":\"US\"}").
            when().
            get("/countries").
            then().assertThat().
            statusCode(200).
            contentType(ContentType.JSON).
            body("items[0].country_name", is("United States of America"));
    ///SECOND REQUEST
    //accept(ContentType.JSON). - to request JSON from the web service.
    Response response = given().
            accept(ContentType.JSON).
            when().
            get("/countries").prettyPeek();
    String countryName = response.jsonPath().getString("items.find{it.country_id == 'US'}.country_name");
    Map<String, Object> countryUS = response.jsonPath().get("items.find{it.country_id == 'US'}");
    //find all country names from region 2
    //collectionName.findAll{it.propertyName == 'Value'} -- to get collection objects where property equals to some value
    //collectionName.find{it.propertyName == 'Value'} -- to object where property equals to some value
    // to get collection properties where property equals to some value
    //collectionName.findAll{it.propertyName == 'Value'}.propertyName
    List<String> countryNames = response.jsonPath().getList("items.findAll{it.region_id == 2}.country_name");
    System.out.println("Country name: " + countryName);
    System.out.println(countryUS);
    System.out.println(countryNames);
    for (Map.Entry<String, Object> entry : countryUS.entrySet()) {
        System.out.printf("key = %s, value = %s\n", entry.getKey(), entry.getValue());
    }
}

@Test
public void getEmployeeTest(){
    Response response = when().get("/employees").prettyPeek();
    Map<String,?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
    Map<String, ?> poorGuy = response.jsonPath().get("items.min{it.salary}");
    System.out.println(bestEmployee);
    System.out.println(poorGuy);
    }


}
