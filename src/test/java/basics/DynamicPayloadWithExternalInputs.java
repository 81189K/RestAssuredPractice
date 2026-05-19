package basics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.LibraryPayload;

import static io.restassured.RestAssured.*;

public class DynamicPayloadWithExternalInputs {

	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("ContentType", "appplication/json").body(LibraryPayload.addBook("asdf", "1234"))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookID = js.get("ID");
		System.out.println("Book ID: " + bookID);
	}
	
	@DataProvider(name="AddBooksData")
	public Object[][] getBooksData() {
		return new Object[][] { {"asdf", "1010"}, {"qwer", "2121"}, {"zxcv", "3131"} }; // creation & initialization
	}
}
