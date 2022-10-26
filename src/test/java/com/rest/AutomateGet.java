package com.rest;
import static io.restassured.RestAssured.*;

import groovy.json.JsonSlurper;
import io.restassured.RestAssured;
import io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class AutomateGet {

    @Test(priority = 0)
    public void validateGetResponseCode(){

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    @Test(priority = 1)
    public void validateGetResponseBody(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My workspace 1", "Deepika Workspace"),
                        "workspaces.type", hasItems("team", "personal"),
                        "workspaces[0].name", equalTo("My workspace 1"),
                        "workspaces[1].name", is(equalTo("Deepika Workspace")),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("Deepika Workspace"));

    }
    @Test(priority = 2)
    public void extractGetResponse(){
        Response response =
                given().
                        baseUri("https://api.postman.com").
                        header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                when().
                        get("/workspaces").
                then().
                        log().all().
                        assertThat().
                        statusCode(200).
                        extract().
                        response();
        System.out.print("Response is : "+response.asPrettyString());
    }
    @Test(priority = 3)
    public void extractSingleFieldUsingGetResponse1(){
        Response res =   given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
       String name = res.path("workspaces[0].name");
       System.out.println("Workspace name is : "+name);
    }

    @Test(priority = 4)
    public void extractSingleFieldUsingGetResponse2(){
        Response res =   given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        JsonPath jsonPath = new JsonPath(res.asString());//extracting Json Object and converting into string.
        String name = jsonPath.getString("workspaces[0].name");//fetching data from specified node from Json Object.
        System.out.println("Workspace name using JsonPath is : "+name);
    }

    @Test(priority = 5)
    public void extractSingleFieldUsingGetResponse3(){
        String res =   given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().asString();
        JsonPath res1 = JsonPath.from(res);
        String name = res1.getString("workspaces[0].name");
        System.out.println("Workspace name using JsonPath from method is : "+name);
    }
    @Test(priority = 6)
    public void extractSingleFieldUsingDirectMethod(){
        String name =   given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-62d47714fcc4873fb29b3f93-82ed14315e430328ae7ac94c4dc19e01e4").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        System.out.println("Workspace name using direct path method is : "+name);
    }
}
