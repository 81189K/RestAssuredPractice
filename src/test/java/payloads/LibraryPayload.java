package payloads;

public class LibraryPayload {

	public static String addBook(String isbn, String aisle) {
		return "{\r\n"
				+ "    \"name\": \"Learn Appium Automation with Java\",\r\n"
				+ "    \"isbn\": \""+isbn+"\",\r\n"
				+ "    \"aisle\": \""+aisle+"\",\r\n"
				+ "    \"author\": \"Johnson\"\r\n"
				+ "}";
	}
}
