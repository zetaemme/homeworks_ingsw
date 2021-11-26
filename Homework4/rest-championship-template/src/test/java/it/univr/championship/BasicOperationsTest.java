package it.univr.championship;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BasicOperationsTest {

    @BeforeAll
    /*
     * This method, with annotation @BeforeAll, is executed one time at the beginning of the testing session.
     */
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /*
     * This test case is meant to create a "match" in the database of the REST API. It checks that returned JSON
     * actually contains the newly created object with the provided values.
     */
    public void addMatch() {

        final String jsonBody =
                "{\n" +
                        "    \"teamA\": \"Napoli\",\n" +
                        "    \"teamB\": \"Juventus\",\n" +
                        "    \"teamAScore\": 3,\n" +
                        "    \"teamBScore\": 1\n" +
                        "}";

        given()
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post("/match")

        .then()
                .statusCode(200)
                .body("teamA", Matchers.is("Napoli"))
                .body("teamB", Matchers.is("Juventus"))
                .body("teamAScore", Matchers.is(3))
                .body("teamBScore", Matchers.is(1));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /*
     * After initializing the database with some testing data, this test case will try to retrieve the match with id 2.
     * It also checks that the retrieved data corresponds with the provided data.
     */
    public void getMatch() {

        Utils.initDatabase();

        // Get match with id = 2
        given()
                .pathParam("matchId", 2)

        .when()
                .get("/match/{matchId}")

        .then()
                .statusCode(200)
                .body("id", Matchers.is(2))
                .body("teamA", Matchers.is("Milan"))
                .body("teamB", Matchers.is("Juventus"))
                .body("teamAScore", Matchers.is(2))
                .body("teamBScore", Matchers.is(1));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /*
     * This test case tries to remove a match from the database and checks the received status code to assess the
     * execution of the deletion.
     */
    public void removeMatch() {

        Utils.initDatabase();

        // Remove match
        given()
                .pathParam("matchId", 2)

        .when()
                .delete("/match/{matchId}")

        .then()
                .statusCode(200);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * This test case deletes a match from the database, and tries to retrieve that match with a consequent GET request.
     * The REST API should return the string "null" in the body, meaning that the match was correctly deleted.
     */
    public void removeMatchAndCheck() {

        Utils.initDatabase();

        // Remove match
        given()
                .pathParam("matchId", 2)

        .when()
                .delete("/match/{matchId}")

        .then()
                .statusCode(200);


        // Check that retrieving the match, is it actually deleted (body text is "null")
        given()
                .pathParam("matchId", 2)

        .when()
                .get("/match/{matchId}")

        .then()
                .body(Matchers.is("null"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * This test case tries to update a match, by changing the score of one team. The response is expected to contain
     * the updated match.
     */
    public void updateMatch() {

        Utils.initDatabase();

        // Update match
        given()
                .pathParam("matchId", 1)
                .contentType("application/json")
                .body(Utils.getMatchJson("Inter", "Napoli", 3, 3))

        .when()
                .put("/match/{matchId}")

        .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("teamA", Matchers.is("Inter"))
                .body("teamB", Matchers.is("Napoli"))
                .body("teamAScore", Matchers.is(3))
                .body("teamBScore", Matchers.is(3));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * This test case tries to update a match with a new score. The consequent GET request for that match should
     * return the updated data.
     */
    public void updateMatchAndCheck() {

        Utils.initDatabase();

        // Update match
        given()
                .pathParam("matchId", 1)
                .contentType("application/json")
                .body(Utils.getMatchJson("Inter", "Napoli", 3, 3))

        .when()
                .put("/match/{matchId}")

        .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("teamA", Matchers.is("Inter"))
                .body("teamB", Matchers.is("Napoli"))
                .body("teamAScore", Matchers.is(3))
                .body("teamBScore", Matchers.is(3));


        // Check that retrieving the match, is it actually updated
        given()
                .pathParam("matchId", 1)

        .when()
                .get("/match/{matchId}")

        .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("teamA", Matchers.is("Inter"))
                .body("teamB", Matchers.is("Napoli"))
                .body("teamAScore", Matchers.is(3))
                .body("teamBScore", Matchers.is(3));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * This test case will check if the operations returning the list of all the matches actually contains all the
     * matches.
     */
    public void getAllMatches() {

        Utils.initDatabase();

        when()
                .get("/matches")

        .then()
                .statusCode(200)

                // With initDatabase, we fill the database with 9 matches
                .body("matches", Matchers.iterableWithSize(9))

                // Each returned match is a map with 5 items (id, team A name, team B name, team A score, and team B score.
                .body("matches", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * This test case checks that the operation meant to return all the matches of a given team, returns them all.
     */
    public void getMatchesByTeam() {

        Utils.initDatabase();

        given()
                .pathParam("teamName", "Inter")

        .when()
                .get("/matches/{teamName}")

        .then()
                .statusCode(200)

                // Only 3 matches involve the team "Inter"
                .body("matches", Matchers.iterableWithSize(3))

                // Each returned match is a map with 5 items (id, team A name, team B name, team A score,
                // and team B score).
                .body("matches", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }
}
