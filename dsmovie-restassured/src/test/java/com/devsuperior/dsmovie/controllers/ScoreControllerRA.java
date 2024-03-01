package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

public class ScoreControllerRA {
    private String adminToken, clientToken, invalidToken;

    private Map<String, Object> putScoreInstance;

    @BeforeEach
    public void setup() throws JSONException {
        baseURI = "http://localhost:8090";

        String clientUsername = "alex@gmail.com";
        String clientPassword = "123456";
        String adminUsername = "maria@gmail.com";
        String adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        putScoreInstance = new HashMap<>();
        putScoreInstance.put("movieId", 1L);
        putScoreInstance.put("score", 4.0F);
    }

    @Test
    public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
        putScoreInstance.put("movieId", 100L);
        JSONObject score = new JSONObject(putScoreInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(score)
                .when()
                .put("/scores")
                .then()
                .statusCode(404)
                .body("error", equalTo("Recurso não encontrado"))
                .body("status", equalTo(404));
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
        putScoreInstance.put("movieId", null);
        JSONObject score = new JSONObject(putScoreInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(score)
                .when()
                .put("/scores")
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos"))
                .body("status", equalTo(422))
                .body("errors.fieldName", hasItems("movieId"))
                .body("errors.message", hasItems("Campo requerido"));
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
        putScoreInstance.put("score", -4);
        JSONObject score = new JSONObject(putScoreInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(score)
                .when()
                .put("/scores")
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos"))
                .body("status", equalTo(422))
                .body("errors.fieldName", hasItems("score"))
                .body("errors.message", hasItems("Valor mínimo 0"));
    }
}
