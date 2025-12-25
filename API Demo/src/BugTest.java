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
		.header("Authorization" , pass()).multiPart("file",new File("C:\\Users\\004PEB744\\Pictures\\Screenshots\\Screenshot 2024-01-04 133149.png"))
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
		return "Basic c2V2YXNpNWM2QGdtYWlsLmNvbTpBVEFUVDN4RmZHRj"
				+ "BFT0RkdGZCQmdua0tHaVlxWmFaUVVnanB5YnpiRmFxSkNH"
				+ "RU5SUlBRXy00WkNkZEpQQUg0UE9PYmdadW02U3o1anViUGw"
				+ "2YlB5cUhSZFRXY3dTS21Lb1VkTUNCUFgwSXlPTzRESkRIcWJr"
				+ "SHhabm5neU0yQk44MnpsLWNpcVdxNE1xS3ExUER6QWZ2YjVNSHY"
				+ "yUmFFek10TFozbVdiX0JSbmNSLTdEck1qeTg9QjRBMzUwNzE=";
	}

}
