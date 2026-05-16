package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.MapsPayload;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;

public class MapsApiTest {

	public static void main(String[] args) {
		
		//given - all input details
		//when - Submit the API -resource, http method
		//then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json") //log().all() - logs into console
							.body(MapsPayload.addPlace()) // moved the payload into a separate class
						.when().post("maps/api/place/add/json")
						.then().log().all().assertThat().statusCode(200).body("scope", Matchers.equalTo("APP")) //assertThat(), org.hamcrest.Matchers.equalTo()
							.header("server", "Apache/2.4.52 (Ubuntu)")
							.extract().response().asString(); // extract response as string.
//		System.out.println(response);
		
		
		//JsonPath class - for parsing JSON
		JsonPath responseJson = new JsonPath(response);
		String placeId = responseJson.getString("place_id");
		System.out.println("Place ID: "+ placeId);
	}

}
