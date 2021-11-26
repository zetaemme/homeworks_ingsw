package it.univr.championship;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdvancedOperationsTest {

    @BeforeAll
    /**
     * This method, with annotation @BeforeAll, is executed one time at the beginning of the testing session.
     */
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * Check if the ranking computation is correct according to the matches in the database.
     * For each match, the winning team gets 3 points, the losing team 0 point, and both teams get 1 point in case of tie
     */
    public void ranking() {

        Utils.initDatabase();

        when()
                .get("/ranking")

        .then()
                .statusCode(200)
                .body("1.team", Matchers.is("Inter"))
                .body("1.points", Matchers.is(7))
                .body("2.team", Matchers.is("Milan"))
                .body("2.points", Matchers.is(6))
                .body("3.team", Matchers.is("Napoli"))
                .body("3.points", Matchers.is(5))
                .body("4.team", Matchers.is("Juventus"))
                .body("4.points", Matchers.is(3))
                .body("5.team", Matchers.is("Lazio"))
                .body("5.points", Matchers.is(2))
                .body("6.team", Matchers.is("Roma"))
                .body("6.points", Matchers.is(1));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * Check if the winner of the championship is computed correctly, according to the matches in the database.
     */
    public void championshipWinner() {

        Utils.initDatabase();

        when()
                .get("/winner")

        .then()
                .statusCode(200)
                .body("winner", Matchers.is("Inter"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    /**
     * Check is the REST API returns the list of the teams taking part to the championship correctly
     */
    public void getTeams() {

        Utils.initDatabase();

        when()
                .get("/teams")

        .then()
                .statusCode(200)
                .body("teams", Matchers.containsInAnyOrder("Napoli", "Juventus", "Roma", "Lazio", "Milan", "Inter"));
    }
}
