package com.bestbuy.betststeps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseOptionsGroovyImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ProductSteps {


    @Step("Creating product with name : {0},type :{1}, price : {2}, upc : {3}, shipping : {4}, description : {5}, manufacturer : {6}, model : {7}, url : {8}, image : {9}")
    public ValidatableResponse createProduct(String name, String type, double price, String upc, double shipping, String description,
                                             String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post()
                .then();

    }


    @Step("Getting the product information with productId : {0}")
    public ValidatableResponse getTheProduct(int productId) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Updating the product information productId : {0},name : {1},type :{2}, price : {3}, upc : {4}, shipping : {5}, description : {6}, manufacturer : {7}, model : {8}, url : {9}, image : {10}")
    public ValidatableResponse updateTheProduct(int productId, String name, String type, double price, String upc, double shipping, String description,
                                                String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);


        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Deleting product information with productId : {0}")
    public ValidatableResponse deleteTheProduct(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting the product information with productId : {0}")
    public ValidatableResponse getTheProductAfterDeleting(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

}
