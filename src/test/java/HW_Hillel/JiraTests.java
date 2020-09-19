package HW_Hillel;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class JiraTests {
  String ticketId;
  Response responseCreateIssue;
  Response responseGetCreatedIssue;

  @Test
  public void jiraCreateIssue() {

    responseCreateIssue =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            body("{\n" +
                "   \"fields\":{\n" +
                "      \"summary\":\"Main order flow broken\",\n" +
                "      \"issuetype\":{\n" +
                "         \"id\":\"10105\",\n" +
                "         \"name\":\"test\"\n" +
                "      },\n" +
                "      \"project\":{\n" +
                "         \"id\":\"10508\"\n" +
                "      },\n" +
                "   \"reporter\": {\n" +
                "      \"name\": \"webinar5\"\n" +
                "    }\n" +
                "   }\n" +
                "}\u2029").
        when().post("https://jira.hillel.it/rest/api/2/issue").
        then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    ticketId = responseCreateIssue.path("id");

    responseGetCreatedIssue =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
        when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
        then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    assertEquals(responseGetCreatedIssue.path("fields.summary"), "Main order flow broken");
    assertEquals(responseGetCreatedIssue.path("fields.creator.name"), "webinar5");
  }
}