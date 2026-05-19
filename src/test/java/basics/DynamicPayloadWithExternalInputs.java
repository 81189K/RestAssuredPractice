package basics;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.LibraryPayload;
import utils.EnvReader;

import static io.restassured.RestAssured.*;

public class DynamicPayloadWithExternalInputs {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = EnvReader.get("LIB_BASE_URL");
		String response = given()./*log().all().*/header("ContentType", "appplication/json").body(LibraryPayload.addBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then()./*log().all().*/assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookID = js.get("ID");
		Assert.assertEquals(bookID, isbn+aisle);
		System.out.println("PASS: Successfully added book with ID: '"+ bookID + "'");
	}
	
	@Test(dataProvider="BooksData")
	public void deleteBook(String isbn, String aisle) {
		RestAssured.baseURI = EnvReader.get("LIB_BASE_URL");
		String response = given().header("ContentType","appliction/json").body(LibraryPayload.deleteBook(isbn, aisle))
		.when().delete("/Library/DeleteBook.php")
		.then().assertThat().statusCode(200).extract().response().asPrettyString();
		
//		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String msg = js.get("msg");
		Assert.assertEquals(msg, "book is successfully deleted");
		System.out.println("PASS: " + msg + " with isbn: '"+ isbn + "' and aisle: '"+ aisle +"'");
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getBooksData() {
		return new Object[][] { {"asdf", "1010"}, {"qwer", "2121"}, {"zxcv", "3131"} }; // creation & initialization
	}
}
