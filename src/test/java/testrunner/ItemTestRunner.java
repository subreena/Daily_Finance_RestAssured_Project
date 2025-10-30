package testrunner;

import com.github.javafaker.Faker;
import config.ItemModel;
import config.Setup;
import controller.ItemController;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ItemTestRunner extends Setup {

    @Test(priority = 1, description = "Verifying if user can create item")
    public void createItem() throws ConfigurationException {
        ItemController itemController = new ItemController(prop);
        ItemModel itemModel = new ItemModel();
        Faker faker = new Faker();
        String itemName = faker.commerce().material();
        int quantity = Utils.generateRandomNumber(10,100);
        String amount = String.valueOf(Utils.generateRandomNumber(100,1000));

        Date startDate = java.sql.Date.valueOf("2020-01-01");
        Date endDate = java.sql.Date.valueOf("2025-12-31");
        Date randomDate = faker.date().between(startDate, endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String datePurchase = sdf.format(randomDate);


        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthName = monthFormat.format(randomDate);

        itemModel.setItemName(itemName);
        itemModel.setAmount(amount);
        itemModel.setQuantity(quantity);
        itemModel.setPurchaseDate(datePurchase);
        itemModel.setMonth(monthName);
        itemModel.setRemarks("This is test remarks for product: "+itemName);

        Response res = itemController.createItem(itemModel);
        JsonPath jsonPath = res.jsonPath();
        String itemId = jsonPath.get("_id");
        Utils.setEnv("itemId",itemId);
        Utils.setEnv("itemName", jsonPath.get("itemName"));
        res.prettyPrint();

    }
    @Test(priority = 2, description = "Verifying if user can create item using mandatory fields")
    public void createItemUsingMandatoryFields() throws ConfigurationException {
        ItemController itemController = new ItemController(prop);
        ItemModel itemModel = new ItemModel();
        Faker faker = new Faker();
        String itemName = faker.commerce().material();
        int quantity = Utils.generateRandomNumber(10,100);
        String amount = String.valueOf(Utils.generateRandomNumber(100,1000));

        Date startDate = java.sql.Date.valueOf("2020-01-01");
        Date endDate = java.sql.Date.valueOf("2025-12-31");
        Date randomDate = faker.date().between(startDate, endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String datePurchase = sdf.format(randomDate);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthName = monthFormat.format(randomDate);

        itemModel.setItemName(itemName);
        itemModel.setAmount(amount);
        itemModel.setQuantity(quantity);
        itemModel.setPurchaseDate(datePurchase);
        itemModel.setMonth(monthName);
//        itemModel.setRemarks("This is test remarks for product: "+itemName);

        Response res = itemController.createItem(itemModel);
        JsonPath jsonPath = res.jsonPath();
        System.out.println(res.statusCode());
        res.prettyPrint();
    }
    @Test(priority = 3, description = "Verifying if user can create item with invalid fields")
    public void createItemWithInvalidFields() throws ConfigurationException {
        ItemController itemController = new ItemController(prop);
        ItemModel itemModel = new ItemModel();
        Faker faker = new Faker();
        String itemName = faker.commerce().material();
        int quantity = Utils.generateRandomNumber(10,100);
        String amount = String.valueOf(Utils.generateRandomNumber(100,1000));

        Date startDate = java.sql.Date.valueOf("2020-01-01");
        Date endDate = java.sql.Date.valueOf("2025-12-31");
        Date randomDate = faker.date().between(startDate, endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String datePurchase = sdf.format(randomDate) +"st";

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthName = monthFormat.format(randomDate);

        itemModel.setItemName(itemName);
        itemModel.setAmount(amount);
        itemModel.setQuantity(quantity);
        itemModel.setPurchaseDate(datePurchase);
        itemModel.setMonth(monthName);
        itemModel.setRemarks("This is test remarks for product: "+itemName);
        Response res = itemController.createItem(itemModel);
     Assert.assertNotEquals(res.statusCode(),201);
        res.prettyPrint();
    }

    @Test(priority = 4, description = "Verifying user can view item list")
    public void getItemList(){
        ItemController itemController = new ItemController(prop);
        Response res = itemController.viewItemList();
        Assert.assertEquals(res.statusCode(),200);
        res.prettyPrint();
    }

    @Test(priority = 5, description = "Verifying user can update item details")
    public void updateItem() throws ConfigurationException {
        ItemController itemController = new ItemController(prop);
        ItemModel itemModel = new ItemModel();
        Response getRes = itemController.getitemById(prop.getProperty("itemId"));
        Map<String, Object> itemData = getRes.jsonPath().getMap("$");
        itemData.put("itemName", "Chocolate");
        itemData.put("amount", "950");
        itemData.put("quantity",25);

        Response updateRes = itemController.updateItemDetails(itemData,prop.getProperty("itemId"));
        System.out.println("Status "+updateRes.getStatusCode());
        updateRes.prettyPrint();
        JsonPath jsonPath = updateRes.jsonPath();
        Utils.setEnv("itemName",jsonPath.get("itemName"));
        Assert.assertEquals(updateRes.statusCode(),200);

    }
    @Test(priority = 6, description = "Verifying user can delete item by invalid id")
    public void deleteItembyInvalidId(){
        ItemController itemController = new ItemController(prop);
        Response res = itemController.deleteItem("123456");
        JsonPath jsonPath = res.jsonPath();
        String actual = jsonPath.get("message");
        String expected = "Cost not found";
        Assert.assertEquals(actual,expected);
        res.prettyPrint();
    }

    @Test(priority = 7, description = "Verifying user can delete item")
    public void deleteItem(){
        ItemController itemController = new ItemController(prop);
        Response res = itemController.deleteItem(prop.getProperty("itemId"));
        Assert.assertEquals(res.statusCode(),200);
        res.prettyPrint();
    }

}
