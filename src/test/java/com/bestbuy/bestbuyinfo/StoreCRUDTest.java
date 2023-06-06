package com.bestbuy.bestbuyinfo;

import com.bestbuy.betststeps.StoresSteps;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.StoreTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends StoreTestBase {

    static String name = "Budgens" + TestUtils.getRandomValue();
    static String type = "Groceries" + TestUtils.getRandomValue();
    static String address = "1458" + TestUtils.getRandomValue() + "High Road";
    static String address2 = TestUtils.getRandomValue() + "Station Road";
    static String city = "London";
    static String state = "Middlesex";
    static String zip = "WE9 6LG";
    static double lat = 65.5689123;
    static double lng = -89.215466;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-5";
    static int storeId;

    @Steps
    StoresSteps storesSteps;

    // Create store
    @Title("This will create the store")
    @Test
    public void test001() {
        ValidatableResponse response = storesSteps.createTheStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);

        storeId = response.extract().path("id");
        System.out.println("Store id is : " + storeId);
        HashMap<String, Object> storeMap = response.extract().path("");
        Assert.assertThat(storeMap, hasValue(storeId));

    }

    // Get The Store Details
    @Title("This will get the store details")
    @Test
    public void test002() {

        ValidatableResponse response = storesSteps.getStoreDetails(storeId);
        response.log().all().statusCode(200);
        HashMap<String, Object> storeMap = response.extract().path("");
        Assert.assertThat(storeMap, hasValue(storeId));
    }

    // Update the Store Details
    @Title("This will update the store details")
    @Test
    public void test003() {
        name = "BestWay " + TestUtils.getRandomValue();
        city = "Birmingham" + TestUtils.getRandomValue();

        ValidatableResponse response = storesSteps.updateTheStoreDetails(storeId, name, type, address, address2, city, state, zip, lat, lng, hours);
        response.statusCode(200);

        HashMap<String, Object> storeMap = response.extract().path("");
        Assert.assertThat(storeMap, hasValue(storeId));
    }

    // Delete the Store Detail
    @Title("This will delete the store detail")
    @Test
    public void test004() {
        storesSteps.deleteTheStoreDetail(storeId)
                .statusCode(200);

        storesSteps.getStoreDetails(storeId)
                .statusCode(404);
    }


}
