package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.MapsPayload;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.Assert;

public class MapsApiTest {

	public static void main(String[] args) {
		
		//given - all input details
		//when - Submit the API -resource, http method
		//then - validate the response
		
		
		//AddPlace
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
		System.out.println("***************************************************");
		System.out.println("Added place with place_id: "+ placeId);
		System.out.println("***************************************************");
		
		
		//UpdatePlace
		String newAddress = "70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"place_id\": \""+placeId+"\",\r\n"	// insert the place id here
				+ "    \"address\": \""+newAddress+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", Matchers.equalTo("Address successfully updated"));
		System.out.println("***************************************************");
		System.out.println("Updated address of place_id: "+ placeId);
		System.out.println("***************************************************");
		
		//GetPlace
		response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId) // insert the place id here
		.when().get("maps/api/place/get/json")
//		.then().log().all().assertThat().statusCode(200).body("address", Matchers.equalTo(newAddress));
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		String actualAddress = (new JsonPath(response)).getString("address");
		Assert.assertEquals(actualAddress, newAddress);
		System.out.println("***************************************************");
		System.out.println("Successfully verified the updated address using GET");
		System.out.println("***************************************************");
	}

}
