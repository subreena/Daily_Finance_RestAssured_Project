package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jshell.execution.Util;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.util.Map;

public class AdminTestRunner extends Setup {

    @Test(priority = 1, description = "Verifying admin can login with wrong password")
    public void adminLoginwithWrongPass() throws ConfigurationException {
        UserController  userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("wrongPassword");
        Response res = userController.doLogin(userModel);
        JsonPath jsonObj = res.jsonPath();
       String actual = jsonObj.get("message");
       String expected = "Invalid email or password";
        Assert.assertEquals(actual,expected);
    }

    @Test(priority = 2, description = "Verifying admin can login with wrong email")
    public void adminLoginwithWrongEmail() throws ConfigurationException {
        UserController  userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@WrongEmail.com");
        userModel.setPassword("admin123");
        Response res = userController.doLogin(userModel);
        JsonPath jsonObj = res.jsonPath();
        String actual = jsonObj.get("message");
        String expected = "Invalid email or password";
        Assert.assertEquals(actual,expected);
    }

    @Test(priority = 3, description = "Verifying admin can login")
    public void adminLogin() throws ConfigurationException {
        UserController  userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response res = userController.doLogin(userModel);
        JsonPath jsonObj = res.jsonPath();
        String accessToken = jsonObj.get("token");
        Assert.assertEquals(res.statusCode(),200);
        Utils.setEnv("accessToken", accessToken);
        System.out.println(accessToken);
    }

    @Test(priority = 4, description = "Verifying user can register")
    public void userCreate() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "teachercopilot123+"+ Utils.generateRandomNumber(100,999)+"@gmail.com";
        String password = "1234";
        String address = faker.address().fullAddress();
        String phoneNumber = "01905"+Utils.generateRandomNumber(100000,999999);
        String gender = "Female";
       Boolean termsAccepted = true;

       userModel.setFirstName(firstName);
       userModel.setLastName(lastName);
       userModel.setEmail(email);
       userModel.setPassword(password);
       userModel.setAddress(address);
       userModel.setPhoneNumber(phoneNumber);
       userModel.setGender(gender);
       userModel.setTermsAccepted(termsAccepted);

       Response res = userController.createUser(userModel);
       JsonPath jsonPath = res.jsonPath();
       String userId = jsonPath.get("_id");
       String userFirstName = jsonPath.get("firstName");
       String userLastName = jsonPath.get("lastName");
       String userEmail = jsonPath.get("email");
       String userToken = jsonPath.get("token");
        res.prettyPrint();

        Utils.setEnv("userId", userId);
        Utils.setEnv("userFirstName",userFirstName);
        Utils.setEnv("userLastName", userLastName);
        Utils.setEnv("userEmail",userEmail);
        Utils.setEnv("userPhoneNumber",phoneNumber);
        Utils.setEnv("userAddress",address);
        Utils.setEnv("userPassword",password);
        Utils.setEnv("userToken", userToken);
    }

    @Test(priority = 5, description = "Verifying duplicate user can register")
    public void duplicateUserCreate() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();

        userModel.setFirstName(prop.getProperty("userFirstName"));
        userModel.setLastName(prop.getProperty("userLastName"));
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword("1234");
        userModel.setAddress(prop.getProperty("userAddress"));
        userModel.setPhoneNumber(prop.getProperty("userPhoneNumber"));
        userModel.setGender("Female");
        userModel.setTermsAccepted(true);

        Response res = userController.createUser(userModel);
        JsonPath jsonPath = res.jsonPath();
        res.prettyPrint();
        String actual = jsonPath.get("message");
        String expected = "User already exists with this email address";
        Assert.assertEquals(actual,expected);
        System.out.println(actual);
    }

    @Test(priority = 6, description = "Verifying admin can view user list")
    public void viewUserList(){
        UserController userController = new UserController(prop);
        Response res = userController.viewUserList();
        Assert.assertEquals(res.statusCode(),200);
        res.prettyPrint();
    }
    @Test(priority = 7, description = "Verifying admin can search user by invalid id")
    public void searchUserbyInvalidId(){
        UserController userController = new UserController(prop);
        Response res = userController.searchUser("12345");
        JsonPath jsonPath = res.jsonPath();
        String actual = jsonPath.get("message");
        String expected = "User not found";
        Assert.assertEquals(actual,expected);
    }
    @Test(priority = 8, description = "Verifying admin can search user by id")
    public void searchUser(){
        UserController userController = new UserController(prop);
        Response res = userController.searchUser(prop.getProperty("userId"));
        Assert.assertEquals(res.statusCode(),200);
        res.prettyPrint();
    }


    @Test(priority = 9, description = "Verifying admin can update user ")
    public void updateUser() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();

        Response getRes = userController.searchUser(prop.getProperty("userId"));
        Map<String, Object> userData = getRes.jsonPath().getMap("$");

        userData.put("firstName", "updated random user");
        userData.put("lastName", "updated last name");
        userData.put("address", "Maijdee, Noakhali");

        Response res = userController.updateUser(userData, prop.getProperty("userId"));
        JsonPath jsonPath = res.jsonPath();
        String userFirstName = jsonPath.get("firstName");
        String userLastName = jsonPath.get("lastName");

        res.prettyPrint();

        Utils.setEnv("userFirstName",userFirstName);
        Utils.setEnv("userLastName", userLastName);
        Assert.assertEquals(res.statusCode(),200);
        res.prettyPrint();
    }


    @Test(priority = 10, description = "Verifying user can login with blank creds")
    public void userLoginWithBlankCreds(){
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail(" ");
        userModel.setPassword(" ");
        Response res = userController.doLogin(userModel);
        JsonPath jsonPath = res.jsonPath();
        String actual = jsonPath.get("message");
       String expected = "Invalid email or password";
       Assert.assertEquals(actual,expected);

    }

    @Test(priority = 11, description = "Verifying user can login")
    public void userLogin(){
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword(prop.getProperty("userPassword"));
        Response res = userController.doLogin(userModel);
        Assert.assertEquals(res.statusCode(),200);

    }

}
