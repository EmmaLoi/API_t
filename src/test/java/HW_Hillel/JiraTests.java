package HW_Hillel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;

public class JiraTests {
  String ticketId;
  String commentURI;
  Response responseCreateIssue;
  Response responseGetCreatedIssue;
  Response deleteIssueResponse;
  Response checkDeletedIssueResponse;
  Response addJiraCommentResponse;
  Response deleteCommentResponse;
  Response getDeletedCommentResponse;

  @Test
  public void jiraCreateIssue() {

    JSONObject issue = new JSONObject();
    JSONObject fields = new JSONObject();
    JSONObject reporter = new JSONObject();
    JSONObject project = new JSONObject();
    JSONObject issuetype = new JSONObject();

    issuetype.put("id", "10105");
    issuetype.put("name", "test");
    project.put("id", "10508");
    reporter.put("name", "webinar5");
    fields.put("summary", "some summary");
    fields.put("issuetype", issuetype);
    fields.put("reporter", reporter);
    fields.put("project", project);
    issue.put("fields", fields);

    responseCreateIssue =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            body(issue.toJSONString()).
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
    assertEquals(responseGetCreatedIssue.path("fields.summary"), "some summary");
    assertEquals(responseGetCreatedIssue.path("fields.creator.name"), "webinar5");

    deleteIssueResponse =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    deleteIssueResponse.print();

    checkDeletedIssueResponse =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(404).
            extract().response();
  }

  @Test
  public void addDeleteJiraComment(){
    addJiraCommentResponse =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            body("{\"body\" : \"New_Comment\"}").
            when().
            post("https://jira.hillel.it/rest/api/2/issue/WEBINAR-13562/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            time(lessThan(2L), TimeUnit.SECONDS).
            extract().response();
    commentURI = addJiraCommentResponse.path("self");

    deleteCommentResponse =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            delete(commentURI).
            then().
            statusCode(204).
            extract().response();

    getDeletedCommentResponse =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            get(commentURI).
            then().
            statusCode(404).
            extract().response();
  }
}