import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class DynamicJason {

/* *********************************************************** Task 1 : Static PlayLoad **************************************************************************************** */
	
  /*@Test // Static Payload
	public static void Dypayload() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().body(payload.libpost())
						  .when().post("Library/Addbook.php")
						  .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookid = js.getString("ID");
		System.out.println(bookid); 
	} */
	
	/* The above code will fail if we try to run because of same book is getting added. 
	   To give different books we are using parameters to change the values of book Json contract, 
	   so that we can add new book every time and test with different set of data */
	
	
/* *********************************************************** Task 2: Parameters with external PayLoad **************************************************************************************** */
		
	
  /*@Test  // Parameter with extenal call PayLoad
	public static void Dypayload() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().body(payload.addbook("avatar", "20262")).log().all()
						  .when().post("Library/Addbook.php")
						  .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookid = js.getString("ID");
		System.out.println(bookid);
		
		// delete book 
		given().body(payload.delbook(bookid))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200);
	} */
	

/* *********************************************************** Task 3: Dynamic Data with external PayLoad **************************************************************************************** */
	
	
	// TestNg Data Provider Annotation
  /*@DataProvider(name = "booknames")  // Created an "DataProvider" utility of name booknames 
	public static Object[][] data() {
		// create array of object object 
		Object[][] testdata = new Object[][] {{"jo","202605"},{"sibi","202606"},{"dp","202607"}};
		return testdata;
	}
	
	@Test(dataProvider="booknames")  //  Referred to @DataProvider "booknames"
	public static void Dypayload(String code,String numb) {  // Passes no. of elements from data set
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().body(payload.addbook(code, numb)).log().all()
						  .when().post("Library/Addbook.php")
						  .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookid = js.getString("ID");
		System.out.println("book created by id: "+bookid);
		
		// delete book 
		given().body(payload.delbook(bookid))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200);
		System.out.println("book deleted by id: "+bookid);
	} */
	
	
/* *********************************************************** Task 4: Using PayLoad File **************************************************************************************** */
	
	// To use file data as input to .body() we need to 1.fetch file, 2.convert the file to bytes and then 3. convert to string
	
	@Test
	public static void Dypayload() throws IOException {  
		
		//Pass file to string so that fetched byte will be converted as string
		String input = new String(Files.readAllBytes(Paths.get("C:\\Users\\004PEB744\\Desktop\\Selenium\\sample.json")));
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().body(input).log().all()
						  .when().post("Library/Addbook.php")
						  .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String bookid = js.getString("ID");
		System.out.println("book created by id: "+bookid);
		
		// delete book 
		given().body(payload.delbook(bookid))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200);
		System.out.println("book deleted by id: "+bookid);
	}


}
