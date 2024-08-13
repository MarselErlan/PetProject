package utilities;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashWiseToken {


    public static String GetToken(){

        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("isakazy@gmail.com");
        requestBody.setPassword("isakazyamanbaev");

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);

        return response.jsonPath().getString("jwt_token");

    }

}
