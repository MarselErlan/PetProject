package api;

import com.github.javafaker.Faker;
import entities.RequestBody;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Assert.assertFalse(expectedEmail.isEmpty());

    }


    @Test
    public void GetAllSellers(){
        String url = Config.getProperty("cashWiseAPIUrl") + "api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page", 1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

        String email2 = response.jsonPath().getString("responses[1].email");
        Assert.assertFalse(email2.isEmpty());
//        response.prettyPrint();

        String email3 = response.jsonPath().getString("responses[2].email");
        Assert.assertFalse(email3.isEmpty());

    }


    @Test
    public void GetAllSellerForPractice(){

        String url = Config.getProperty("cashWiseAPIUrl") + "api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        Map<String, Object> parPractice= new HashMap<>();
        parPractice.put("isArchived", false);
        parPractice.put("page", 1);
        parPractice.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token).params(parPractice).get(url);

        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);

        List<String> listOfEmails = response.jsonPath().getList("responses.email");

        for(String emails : listOfEmails){
            Assert.assertFalse(emails.isEmpty());
        }

    }

    @Test
    public void CreateSeller(){
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();

        for(int i = 0; i < 15; i++) {

            requestBody.setCompany_name(faker.name().title());
            requestBody.setSeller_name(faker.name().name());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
            requestBody.setAddress(faker.address().fullAddress());

            Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                    .body(requestBody).post(url);

            int status = response.statusCode();

            Assert.assertEquals(201, status);

            String id = response.jsonPath().getString("seller_id");
            String url2 = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers/" + id;

            Response response1 = RestAssured.given().auth().oauth2(token).get(url2);

            int status2 = response1.getStatusCode();

            Assert.assertEquals(200, status2);
        }





    }







}
