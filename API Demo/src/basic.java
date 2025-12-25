import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;

public class basic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		// Three Principles of Automation API
		// Given: All input data | When: Submit API | Then: Validate response
		
		// Task 1: Validate Add_place API is working as expected.
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").body(payload.contract())      		// instead of giving full contract here it is called from method of class payload from file package
		.when().post("maps/api/place/add/json")													// .log().all() to display log files
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)");
		
				
		// Task 2: Automate Add place -> update address -> get place details
		// ---> Add Place and capture place_id
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").body(payload.contract()).log().all()     		
						  .when().post("maps/api/place/add/json")													
						  .then().assertThat().statusCode(200)
						  	.body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		// To use response string to Json need to use class called JsonPath
		JsonPath js = new JsonPath(response);
		String placeId =js.getString("place_id");
		
		System.out.println();
		System.out.println("***************************************************************************");
				
		
		// ---> Update place Details and validate with inbuilt assertion
		String Uaddress = "70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").body(payload.UpdatedContract(placeId, Uaddress))
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println();
		System.out.println("***************************************************************************");
		
		// --> Get Place Details and validate with testng assertions
		String updated_place = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(updated_place);
		String new_address = js1.getString("address");
		
		Assert.assertEquals(Uaddress,new_address); // testNg assert
		
		
		
				

	}

}
