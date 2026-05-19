package jiratests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.JiraPayload;
import utils.EnvReader;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	String bugID;

	@Test
	public void createBug() {
		RestAssured.baseURI = EnvReader.get("JIRA_BASE_URL");
		String response = given()
			.header("Content-Type","application/json")
			.header("Authorization", "Basic "+EnvReader.get("JIRA_API_TOKEN"))
			.body(JiraPayload.issue())
//			.log().all()
		.when()
			.post("rest/api/3/issue")
		.then()
//			.log().all()
			.assertThat().statusCode(201)
			.extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		bugID = js.get("id");
		System.out.println("******************************");
		System.out.println("created bug id: "+ bugID);
		System.out.println("******************************");
	}
	
	@Test(dependsOnMethods = "createBug")
	public void addAttachment() {
		RestAssured.baseURI = EnvReader.get("JIRA_BASE_URL");
		given()
			.header("X-Atlassian-Token","no-check")
			.header("Authorization", "Basic "+EnvReader.get("JIRA_API_TOKEN"))
			.pathParam("key", bugID)
			.multiPart("file", new File("src/test/resources/files/bugAttachment.jfif"))
//			.log().all()
		.when()
			.post("rest/api/3/issue/{key}/attachments") //pathParam
		.then()
//			.log().all()
			.assertThat().statusCode(200);
	}
}
