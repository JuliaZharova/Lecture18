
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAPItest {

    @Test
    public void postRequestCreateUserTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post("https://reqres.in/api/users");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        System.out.println("The status code recieved: " + statusCode);

    }

    @Test
    public void testGetListUser() {
        Response response = when()
                   .get("https://reqres.in/api/users?page=2");
        response.then().statusCode(200);
        System.out.println(response.asString());
    }

    @Test
    public void testGetSchemaListUser() {
        String jsonSchema = "schema_j.json";
        Response response = when()
                .get("https://reqres.in/api/users?page=2");
        response.then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath(jsonSchema));
    }

    @Test
    public void testGetListResource() {
        Response response = when()
                .get("https://reqres.in/api/unknown");
        response.then().statusCode(200);
        System.out.println(response.asString());
    }

    @Test
    public void testGetSingleUserNotFound() {
        Response response = when()
                .get("https://reqres.in/api/users/23");
        response.then().statusCode(404);
        System.out.println(response.asString());
    }

}
