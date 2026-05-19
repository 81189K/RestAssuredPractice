package basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import payloads.CoursesMockResponse;

public class ComplexJsonParse {
	
	@Test
	public void test() {
		JsonPath js = new JsonPath(CoursesMockResponse.courseDetails());
		
		// Print no. of courses
		int courseCount = js.getInt("courses.size()");
		System.out.println("No. of courses: "+ courseCount);
		
		// Print purchaseAmount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+ purchaseAmount);
		
		// Print title of the first course
		String firstTitle = js.get("courses[0].title");
		System.out.println("First Course Title: "+ firstTitle);
		
		// Print all courses and its price details
		for(int i=0; i<courseCount; i++) {
			System.out.println("{ Title: "+ js.get("courses["+i+"].title") + ", Price: "+ js.getInt("courses["+i+"].price") + " }");
		}
		
		// Print no. copies sold by RPA
		for(int i=0; i<courseCount; i++) {
			String title = js.get("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println("RPA copies sold: "+ js.getInt("courses["+i+"].copies"));
				break;
			}
				
		}
		
		// Verify total sum matches purchaseAmount
		int totalSum =0;
		for(int i=0; i<courseCount; i++) {
			totalSum+=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
		}
		Assert.assertEquals(totalSum, purchaseAmount);
		System.out.println("Total sum matched the purchaseAmount");
	}

}
