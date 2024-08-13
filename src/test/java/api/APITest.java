package api;

import entities.RequestBody;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class APITest {

    @Test
    public void testToken(){
    String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("isakazy@gmail.com");
        requestBody.setPassword("isakazyamanbaev");
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);

        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        response.prettyPrint();
        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }








}
