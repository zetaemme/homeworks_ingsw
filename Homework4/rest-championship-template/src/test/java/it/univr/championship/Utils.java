package it.univr.championship;

import static io.restassured.RestAssured.given;

public class Utils {

    /**
     * Generates the JSON string of the "match" object
     * @param teamA name of team A
     * @param teamB name of team B
     * @param teamAScore number of goals of team A in this match
     * @param teamBScore number of goals of team B in this match
     * @return the generated JSON string
     */
    public static String getMatchJson(String teamA, String teamB, int teamAScore, int teamBScore) {
        return  "{\n" +
                "    \"teamA\": \"" + teamA + "\",\n" +
                "    \"teamB\": \"" + teamB + "\",\n" +
                "    \"teamAScore\": " + teamAScore + ",\n" +
                "    \"teamBScore\": " + teamBScore + "\n" +
                "}";
    }

    /**
     * Initializes the database with a set of 9 matches
     */
    public static void initDatabase() {
        // Prima giornata
        given().contentType("application/json").body(getMatchJson("Inter", "Napoli", 2, 2)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Milan", "Juventus", 2, 1)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Roma", "Lazio", 0, 0)).when().post("/match").then().statusCode(200);

        // Seconda giornata
        given().contentType("application/json").body(getMatchJson("Napoli", "Juventus", 1, 0)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Milan", "Roma", 1, 0)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Lazio", "Inter", 0, 1)).when().post("/match").then().statusCode(200);

        // Terza giornata
        given().contentType("application/json").body(getMatchJson("Inter", "Milan", 2, 1)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Roma", "Juventus", 1, 3)).when().post("/match").then().statusCode(200);
        given().contentType("application/json").body(getMatchJson("Napoli", "Lazio", 1, 1)).when().post("/match").then().statusCode(200);
    }
}
