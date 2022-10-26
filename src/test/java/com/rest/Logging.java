package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import groovy.json.JsonSlurper;
import io.restassured.RestAssured.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Logging {
    @Test
    public void request_responseLogging() {
        Response res =
                given().
                        baseUri("https://api.postman.com").
                        header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                        log().all().
                        when().
                        get("/workspaces").
                        then().
                        log().body().
                        assertThat().
                        statusCode(200).
                        extract().
                        response();
        System.out.println("Response time is : " + res.getTimeIn(TimeUnit.SECONDS));

    }
    @Test
    public  void logOnlyIfErrorIsPresent(){
        given().
                baseUri("https://api.postman.com").
                /** giving a wrong API key as an error so that it will log the details */
                header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                log().all().
        when().
                get("/workspaces").
        then().
                log().ifError().
                assertThat().
                statusCode(200);
    }
    @Test
    public  void log_If_Validation_Fails(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                log().ifValidationFails().
        when().
                get("/workspaces").
        then().
                log().ifValidationFails().
                assertThat().
                statusCode(300);
    }
    @Test
    public  void log_Using_SingleMethod_If_Validation_Fails(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
           //     log().ifValidationFails().
                when().
                get("/workspaces").
                then().
             //   log().ifValidationFails().
                assertThat().
                statusCode(200);
    }
    @Test
    public  void log_single_header_as_blacklist(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).
                log().all().
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public  void log_multiple_headers_as_BlackList_Header(){
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().all().
        when().
                get("/workspaces").
         then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
