package com.automation.tests.day4;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
 *
 */



}
