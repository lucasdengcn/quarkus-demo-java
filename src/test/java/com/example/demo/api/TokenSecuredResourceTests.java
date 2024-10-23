package com.example.demo.api;

import com.example.demo.security.GenerateTestToken;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TokenSecuredResourceTests {

    @Test
    void permit_all() {
        given()
                .when().get("/secured/v1/permit-all")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("anonymous"))
                .body("hasJWT", equalTo(false));

    }

    @Test
    void roles_allowed_without_jwt_will_401() {
        given()
                .when().get("/secured/v1/roles-allowed")
                .then()
                .log().all()
                .statusCode(401);

    }

    @Test
    void roles_allowed_with_jwt_200() {
        String uid = "lucas2@example.com";
        String testToken = GenerateTestToken.signTestToken(uid);
        given()
                .auth().oauth2(testToken)
                .when().get("/secured/v1/roles-allowed")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(uid))
                .body("hasJWT", equalTo(true));

    }
}