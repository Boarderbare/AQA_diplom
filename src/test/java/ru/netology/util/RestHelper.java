package ru.netology.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RestHelper {
    public RestHelper() {
    }

    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public String sentFormBuy(DataHelper.FormFields form) {
        Response response =
                given()
                        .spec(requestSpec)
                        .body(form)
                        .when()
                        .post("/api/v1/pay")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return response.path("status");
    }
    public String sentFormCredit(DataHelper.FormFields form) {
        Response response =
                given()
                        .spec(requestSpec)
                        .body(form)
                        .when()
                        .post("api/v1/credit")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return response.path("status");
    }
}
