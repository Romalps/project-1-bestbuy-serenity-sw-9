package com.bestbuy.betststeps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Title;

import static io.restassured.RestAssured.given;

public class StoresSteps {


    @Step("Create the store with name : {0}, type : {1}, address : {2}, address1 : {3}, city : {4}, state : {5}, zip : {6}, lat : {7}, lng : {8}, hours : {9}")
    public ValidatableResponse createTheStore(String name, String type, String address, String address2, String city, String state, String zip, double lat, double lng, String hours) {

        StorePojo storePojo = StorePojo.getStore(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();

    }

    @Step("Getting the store details with storeId : {0}")
    public ValidatableResponse getStoreDetails(int storeId) {

        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }

    @Step("Upadte the store details with storeId : {0}, name : {1}, type : {2}, address : {3}, address1 : {4}, city : {5}, state : {6}, zip : {7}, lat : {8}, lng : {9}, hours : {10}")

    public ValidatableResponse updateTheStoreDetails(int storeId, String name, String type, String address, String address2, String city, String state, String zip, double lat, double lng, String hours) {
        StorePojo storePojo = StorePojo.getStore(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .pathParam("storeID", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }

    @Step("Deleting the store detail with storeId : {0}")
    public ValidatableResponse deleteTheStoreDetail(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();

    }
}
