package controller;

import config.ItemModel;
import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        RestAssured.baseURI = "https://dailyfinanceapi.roadtocareer.net";
       this.prop = prop;
    }

    public Response doLogin(UserModel userModel){
        Response res = given().contentType("application/json").body(userModel).when().post("/api/auth/login");
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }

    public Response viewUserList(){
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer "+ prop.getProperty("accessToken"))
        .when().get("/api/user/users").then().extract().response();
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }

    public Response createUser(UserModel userModel){
        Response res = given().contentType("application/json")
                .body(userModel)
                .header("Authorization","Bearer "+prop.getProperty("accessToken"))
                .when().post("/api/auth/register");
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }

    public Response searchUser(String userId){
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer "+prop.getProperty("accessToken"))
                .when().get("/api/user/"+userId);
        System.out.println("Status Code: " + res.getStatusCode());
        return res;

    }

    public Response updateUser(Map userData, String userId) {
        Response res = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("accessToken"))
                .body(userData)
                .when()
                .put("/api/user/" + userId);


        System.out.println("Status Code: " + res.getStatusCode());
//        System.out.println("Response: " + res.asString());
        res.prettyPrint();
        return res;
    }





}
