package basics;

import static io.restassured.RestAssured.given;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import utils.EnvReader;

public class ExternalJsonFile {
	
	@Test
    public void externalJsonTest() throws Exception {

        // Read external JSON file: [JSON > Bytes[] > String]
        String requestBody = new String(
                Files.readAllBytes(
                        Paths.get("src/test/resources/payloads/addPlace.json")
                )
        );

        RestAssured.baseURI = EnvReader.get("BASE_URL");

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .queryParam("key", "qaclick123")
                .body(requestBody)

        .when()
                .post("maps/api/place/add/json")

        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("scope", Matchers.equalTo("APP"));
    }

}
