package ru.netology.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static io.restassured.RestAssured.given;

@UtilityClass
public class RestHelper {

    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public String sentForm(DataHelper.FormFields form, String path) {
        Response response =
                given()
                        .spec(requestSpec)
                        .body(form)
                        .when()
                        .post(path)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return response.path("status");
    }

    public void sentInvalidForm(DataHelper.FormFields form, String path) {
        given()
                .spec(requestSpec)
                .body(form)
                .when()
                .post(path)
                .then()
                .statusCode(400);
    }
}
