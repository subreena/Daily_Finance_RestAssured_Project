package controller;

import config.ItemModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ItemController {
    Properties prop;
    public ItemController(Properties prop){
        RestAssured.baseURI = "https://dailyfinanceapi.roadtocareer.net";
        this.prop = prop;
    }

    public Response createItem(ItemModel itemModel){
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("userToken"))
                .body(itemModel)
                .when()
                .post("/api/costs");
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }

    public Response viewItemList(){
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("userToken"))
                .when().get("/api/costs");
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }
    public Response getitemById(String itemId){
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("userToken"))
                .when().get("/api/costs/"+itemId);
        return res;
    }

    public Response updateItemDetails(Map itemData, String itemId){
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("userToken"))
                .body(itemData)
                .when().put("/api/costs/"+itemId);
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }

    public Response deleteItem(String itemId){
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("userToken"))
                .when().delete("/api/costs/"+itemId);
        System.out.println("Status Code: " + res.getStatusCode());
        return res;
    }
}
