package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.AssertionSupport;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import utilities.APIRunner;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoTest {


    @Test
    public void CreateCategory() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();

        Faker faker = new Faker();

        RequestBody requestBody = new RequestBody();


        requestBody.setCategory_title(faker.name().title());
        requestBody.setCategory_description(faker.address().buildingNumber());
       requestBody.setFlag(true);




        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.getStatusCode();

        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        System.out.println(customResponse.getCategory_id());

    }


    @Test
    public void CreateCategory2() throws JsonProcessingException {

        String url2 = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/categories";
        String token2 = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();

        requestBody.setCategory_title("Erlan!!!");
        requestBody.setCategory_description("Erlan_Description");
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token2).contentType(ContentType.JSON)
                .body(requestBody).post(url2);

        int status2 = response.getStatusCode();
        Assert.assertEquals(201, status2);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse2 = mapper.readValue(response.asString(), CustomResponse.class);

        System.out.println(customResponse2.getCategory_id());

        int id2 = customResponse2.getCategory_id();

        String url22 = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/categories/" + id2;

        Response response1 = RestAssured.given().auth().oauth2(token2).get(url22);

        CustomResponse customResponse22 = mapper.readValue(response1.asString(), CustomResponse.class);

        int id22 = customResponse22.getCategory_id();
        Assert.assertEquals(id2, id22);


    }


    @Test
    public void CreateSeller2(){
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


    @Test
    public void getAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 24);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();

        for(int i = 0; i < size; i++){
            String email2 = customResponse.getResponses().get(i).getEmail();
            Assert.assertFalse(email2.isEmpty());
            System.out.println(email2);
        }
        #
    }


    @Test
    public void CreateSellerNoEmail(){
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name("AnyCompanyName");
        requestBody.setSeller_name("AnySellerName");
        requestBody.setPhone_number("123456789");
        requestBody.setAddress("Anyplace");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201, status);

    }

    @Test
    public void ArchivedSeller(){
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();

        params.put("sellersIdsForArchive", 4724);
        params.put("archive", true);

        Response response = RestAssured.given().auth().oauth2(token).params(params)
                .post(url);
        response.prettyPrint();

        int status = response.getStatusCode();
        System.out.println(status);
        Assert.assertEquals(200, status);

    }


/*    @Test
    public void AllSellerArchived(){

        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 24);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        response.prettyPrint();

        int statusCode = response.statusCode();

//        Assert.assertEquals(200, statusCode);

        List<Integer> listAllSellerID = response.jsonPath().getList("responses.seller_id");

        for(Integer seller_id : listAllSellerID){
            System.out.println(seller_id);
        }

        String url2 = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token2 = CashWiseToken.GetToken();


        Map<String, Object> params2 = new HashMap<>();

        for(int i = 0; i < listAllSellerID.size(); i++){

            params2.put("sellersIdsForArchive", listAllSellerID.get(i));
            params2.put("archive", true);



        Response response2 = RestAssured.given().auth().oauth2(token2).params(params)
                .post(url2);
        response.prettyPrint();

        int status = response2.getStatusCode();
        System.out.println(status);
//        Assert.assertEquals(200, status);

        }

    }*/


    @Test
    public void ArchiveAll() throws JsonProcessingException {
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1 );
        params.put("size", 110 );

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();

        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        String urlToArchive = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();




        for(int i = 0; i < size; i ++ ){

            int id = customResponse.getResponses().get(i).getSeller_id();

            Map<String, Object> paramsToArchive = new HashMap<>();

            String notNullEmail = customResponse.getResponses().get(i).getEmail();

                if(notNullEmail != null && customResponse.getResponses().get(i).getEmail().endsWith("hotmail.com")){
                    paramsToArchive.put("sellersIdsForArchive",id );
                    paramsToArchive.put("archive", true);



                    Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

                    int status1 = response1.statusCode();

                    Assert.assertEquals(200, status1);
                }

        }


    }




    @Test
    public void CreateSellerAndVerify() throws JsonProcessingException {

        String url = Config.getProperty("cashWiseAPIUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();
        String ToCheckEmail = "toverifye2@mail.com";

        requestBody.setCompany_name("ToVerifyCompany");
        requestBody.setSeller_name(ToCheckEmail);
        requestBody.setEmail("toverifye2@mail.com");
        requestBody.setPhone_number("1234567898764");
        requestBody.setAddress("AnyPlace");


        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.getStatusCode();

        Assert.assertEquals(201, status);

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response1 = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status2 = response1.getStatusCode();
        Assert.assertEquals(200, status2);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();

        boolean isVerify = false;

        for(int i = 0; i < size; i++){
            if(ToCheckEmail.equals(customResponse.getResponses().get(i).getEmail())){
                isVerify = true;
            }

        }

        Assert.assertTrue(isVerify);


    }

//    @Test
//    public void testGet(){
//        APIRunner.runGET("api/myaccount/sellers/2344");
//        String email = APIRunner.getCustomResponse().getEmail();
//
//    }












}
