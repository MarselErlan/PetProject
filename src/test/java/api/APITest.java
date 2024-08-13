package api;

import entities.RequestBody;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

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

    @Test
    public void GetSingleSeller(){
        String url = Config.getProperty("cashWiseAPIUrl") + "api/myaccount/sellers/" + 4623;
        String token = CashWiseToken.GetToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);
//        response.prettyPrint();

        String expectedEmail = response.jsonPath().getString("email");
        Assert.assertTrue(expectedEmail.endsWith(".com"));
        Assert.assertNotEquals(null, expectedEmail);




    }








}
