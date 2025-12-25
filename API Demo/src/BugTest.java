import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {
	
	public static void main(String[] args) {
		
		
		RestAssured.baseURI = "https://sevasi5c6.atlassian.net/";
		
		String response =given().body(addbug()).header("Content-Type","application/json").header("Authorization" , pass()).log().all()
		.when().post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String IssueId = js.getString("id");
		
		
		given().header("X-Atlassian-Token","no-check").pathParam("key", IssueId)
		.header("Authorization" , pass()).multiPart("file",new File("<image path>"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().assertThat().statusCode(200);
	}
	
	public static String addbug() {
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"API Automation Test sample issue\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}";
	}
	
	public static String pass() {
		return "Basic <API TOKEN>";
	}

}

