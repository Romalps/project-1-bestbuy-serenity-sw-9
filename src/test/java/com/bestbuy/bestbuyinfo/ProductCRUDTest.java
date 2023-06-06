package com.bestbuy.bestbuyinfo;

import com.bestbuy.betststeps.ProductSteps;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.ProductTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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
public class ProductCRUDTest extends ProductTestBase {

    static String name = "Duracell" + TestUtils.getRandomValue();
    static String type = "HardGood" + TestUtils.getRandomValue();
    static double price = 5.98;
    static String upc = " 123401456";
    static double shipping = 0;
    static String description = "Compatible with select electronic devices" + TestUtils.getRandomValue();
    static String manufacturer = " Duracell";
    static String model = "MN9876TF" + TestUtils.getRandomValue();
    static String url = " http://www.bestway.com/" + TestUtils.getRandomValue() + "/" + TestUtils.getRandomValue();
    static String image = " http://img.bbystatic.com/" + TestUtils.getRandomValue() + "/" + TestUtils.getRandomValue();
    static int productId;

    @Steps
    ProductSteps productSteps;

    // Create product
    @Title("This will create new product")
    @Test
    public void test001() {

        ValidatableResponse response = productSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);

        productId = response.extract().path("id");
        // System.out.println("productId is : " + productId);
    }

    //Get The Product
    @Title("This will get the product")
    @Test
    public void test002() {

        ValidatableResponse productMap = productSteps.getTheProduct(productId);
        productMap.log().all();


    }

    // Update the product
    @Title("This will update the product")
    @Test
    public void test003() {

        name = name + TestUtils.getRandomValue();
        description = "Kodak batteries" + TestUtils.getRandomValue();

        productSteps.updateTheProduct(productId, name, type, price, upc, shipping, description, manufacturer, model, url, image)
                .statusCode(200);
    }

    // Delete the product
    @Title("This will delete the product")
    @Test
    public void test004() {
        productSteps.deleteTheProduct(productId)
                .statusCode(200);

        productSteps.getTheProductAfterDeleting(productId)
                .statusCode(404);

    }


}
