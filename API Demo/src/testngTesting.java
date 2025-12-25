import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class testngTesting {
	
	@Test
	public void testngtest() {
		JsonPath js = new JsonPath(complexjson.json());
		int sum = 0;
		int count = js.getInt("courses.size()");
		for(int i=0; i<count ;i++) {
			sum = sum + (js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies")) ;
		}
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), sum);
	}

}
