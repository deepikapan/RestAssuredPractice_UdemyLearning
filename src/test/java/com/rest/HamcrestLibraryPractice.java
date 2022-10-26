package com.rest;

import static io.restassured.RestAssured.*;

import groovy.json.JsonSlurper;
import io.restassured.RestAssured.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class HamcrestLibraryPractice {

    @Test
    public void checkContainsMatchers() {
        given().
                baseUri("https://datausa.io").
                queryParams("drilldowns", "Nation").
                queryParams("measures", "Population").
                when().
                get("/api/data").
                then().
                assertThat().
                statusCode(200).
                body("data.Nation", contains("United States", "United States", "United States", "United States",
                        "United States", "United States", "United States"));

    }

    @Test
    public void checkHasItemsMatchers() {
        given().
                baseUri("https://datausa.io").
                queryParams("drilldowns", "Nation").
                queryParams("measures", "Population").
                when().
                get("/api/data").
                then().
                assertThat().
                statusCode(200).
                body("data.Nation", hasItems("United States"));

    }

    @Test
    public void checkEmptyMatchers() {
                given().
                        baseUri("https://api.zippopotam.us/us/33162").
                when().
                        get("").
                then().

                        assertThat().
                        statusCode(200);



    }


}
