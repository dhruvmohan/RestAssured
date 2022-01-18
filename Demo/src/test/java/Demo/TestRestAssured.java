package Demo;

import org.testng.Assert;
import org.testng.annotations.*;



import static io.restassured.RestAssured.*;

import java.util.List;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;

import org.hamcrest.Matchers.*;

@Test
public class TestRestAssured {

	String first_name;

	String last_name;


	@BeforeTest
	public void BeforeTest_test()  
	{
		System.out.println("Before Test Function");
		System.out.println("Database connectivity or Startup items like initialization");

		baseURI = "https://reqres.in";


	}


	@Test(priority=1)
	public void Get_test()  
	{

		given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.param("page", "2")
		.when()
		.get("/api/users")
		.then()
		.statusCode(200)
		;

		// 		.log().all()

		Response response = 
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.param("page", "2")
				.when()
				.get("/api/users");

		int statusCode = response.getStatusCode();	

		Assert.assertEquals(statusCode, 200);

		//		System.out.println(response.getBody().asString());

		String PageNo = response.jsonPath().getString("page");
		System.out.println(PageNo);


		first_name = response.jsonPath().getString("data.first_name[0]");
		System.out.println(first_name);


		last_name = response.jsonPath().getString("data.last_name[0]");
		System.out.println(last_name);

		List<String> ID_count = response.jsonPath().getList("data.id");
		System.out.println(ID_count.size());

	}


	@Test(priority=2)
	public void Post_test()  
	{

		String requestBody = "{\r\n    \"name\": \""+first_name+"\",\r\n    \"job\": \"leader\"\r\n}";

		given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.put("/api/users/2")
		.then()
		.statusCode(200)
		.log().all();

	}


	@Test(priority=3)
	public void Put_test()  
	{

		String requestBody = "{\r\n    \"name\": \""+first_name+"\",\r\n    \"job\": \"updatedjob\"\r\n}";

		given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.put("/api/users")
		.then()
		.statusCode(200)
		.log().all();

	}


	@Test(priority=4)
	public void Delete_test()  
	{


		given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
		.delete("/api/users/2")
		.then()
		.statusCode(204)
		.log().all();


	
	}

	@AfterTest
	public void AfterTest_test()  
	{
		System.out.println("After Test Function");

		System.out.println("Connection closure/ Variables cleanup");
	}


}
