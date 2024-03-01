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
import static org.hamcrest.CoreMatchers.*;

public class MovieControllerRA {

    private String adminToken, clientToken, invalidToken;
    private String movieTitle;
    private Map<String, Object> postMovieInstance;

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

        movieTitle = "Matrix Resurrections";

        postMovieInstance = new HashMap<>();
        postMovieInstance.put("title", "Test Movie");
        postMovieInstance.put("score", 0.0);
        postMovieInstance.put("count", 0);
        postMovieInstance.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");
    }

    @Test
    public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
        given()
                .get("/movies?page=0")
                .then()
                .statusCode(200);
    }

    @Test
    public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
        given()
                .get("/movies?title={movieName}", movieTitle)
                .then()
                .statusCode(200)
                .body("content.id[0]", is(4))
                .body("content.title[0]", equalTo("Matrix Resurrections"))
                .body("content.score[0]", is(0.0F))
                .body("content.count[0]", is(0))
                .body("content.image[0]", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/hv7o3VgfsairBoQFAawgaQ4cR1m.jpg"));
    }

    @Test
    public void findByIdShouldReturnMovieWhenIdExists() {
        Long existingMovieId = 1L;

        given()
                .get("/movies/{id}", existingMovieId)
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("title", equalTo("The Witcher"))
                .body("score", is(4.5F))
                .body("count", is(2))
                .body("image", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
        Long nonExistingMovieId = 100L;

        given()
                .get("/movies/{id}", nonExistingMovieId)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("error", equalTo("Recurso não encontrado"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {
        postMovieInstance.put("title", "");
        JSONObject newMovie = new JSONObject(postMovieInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newMovie)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(422)
                .body("errors.message", hasItems("Campo requerido", "Tamanho deve ser entre 5 e 80 caracteres"));
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
        JSONObject newMovie = new JSONObject(postMovieInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .body(newMovie)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
        JSONObject newMovie = new JSONObject(postMovieInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(newMovie)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(401);
    }
}
