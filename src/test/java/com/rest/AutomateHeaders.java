package com.rest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import groovy.json.JsonSlurper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

public class AutomateHeaders {

    @Test
    public void multiple_headers_in_request(){
        given().
                baseUri("https://319cfa63-4110-4d05-8b38-bc0cd1616e30.mock.pstmn.io").
                header("header","header1").
                header("x-mock-match-request-headers","header").
                header("Accept","application/json").
        when().
                get("/get").
        then().
                log().all().assertThat().statusCode(200);
    }
    @Test
    public void multiple_headers_creating_object__in_request(){
        Header header = new Header("header", "header1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        given().
                baseUri("https://319cfa63-4110-4d05-8b38-bc0cd1616e30.mock.pstmn.io").
                header(header).
                header(matchHeader).
        when().
                get("/get").
        then().
                log().all().assertThat().statusCode(200);
    }

    @Test
    public void multiple_headers_using_headers_class_in_request(){
        Header header = new Header("header", "header1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");

        Headers headers = new Headers(header,matchHeader);
        given().
                baseUri("https://319cfa63-4110-4d05-8b38-bc0cd1616e30.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().assertThat().statusCode(200);
    }
    @Test
    public void multiple_headers_using_headers_HashMap_in_request(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "header1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://319cfa63-4110-4d05-8b38-bc0cd1616e30.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                log().all().assertThat().statusCode(200);
    }

    @Test
    public void multi_value_headers_in_request(){
        given().
                baseUri("https://319cfa63-4110-4d05-8b38-bc0cd1616e30.mock.pstmn.io").
//                header("header", "header1").
//                header("x-mock-match-request-headers", "header").
             header("multiValueHeader","header1","header2").
                log().headers().
        when().
                get("/get").

        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
