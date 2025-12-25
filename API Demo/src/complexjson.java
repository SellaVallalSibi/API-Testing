import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class complexjson {

	public static void main(String[] args) {
		
		
		JsonPath js = new JsonPath(json());
		
		// Task 1: Print number courses returned by API
		System.out.println("No of courses = " + js.getInt("courses.size()"));
		System.out.println();
		
		// Task 2: Print purchase amount returned by API
		System.out.println("Purchase amount = " + js.getInt("dashboard.purchaseAmount"));
		System.out.println();
		
		// Task 3: Print title of first course returned by API
		System.out.println("Title of first course = " + js.getString("courses[0].title"));
		System.out.println();
		
		// Task 4: Print all course title with price returned by API
		int count = js.getInt("courses.size()");
		for(int i=0; i<count ;i++) {
			System.out.println("Title: \""+js.getString("courses["+i+"].title")+"\" and Price: "+js.getInt("courses["+i+"].price"));
		}
		System.out.println();
		
		// Task 5: Print no.of copies sold by RPA Course returned by API
		for(int i=0; i<count ;i++) {
			String title = js.getString("courses["+i+"].title");
			if (title.equalsIgnoreCase("RPA")) {
				System.out.println("Copies sold by RPA: "+js.get("courses["+i+"].copies"));
				break;
			}
		}
		System.out.println();
		
		//Task 6: Sum of all courses match with purchase amount returned by API
		int sum = 0;
		for(int i=0; i<count ;i++) {
			sum = sum + (js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies")) ;
		}
		System.out.print(sum);
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), sum);
		
		
	}
	
	public static String json() {  //mocked API Response
		return "{\r\n"
				+ "\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\r\n"
				+ "\"website\": \"google.com\"\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "\"courses\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\r\n"
				+ "\"price\": 50,\r\n"
				+ "\r\n"
				+ "\"copies\": 6\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\r\n"
				+ "\"price\": 40,\r\n"
				+ "\r\n"
				+ "\"copies\": 4\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\r\n"
				+ "\"price\": 45,\r\n"
				+ "\r\n"
				+ "\"copies\": 10\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "}";
	}

}
