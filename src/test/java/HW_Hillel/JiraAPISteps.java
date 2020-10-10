package HW_Hillel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class JiraAPISteps {

  public static Response createIssue(JSONObject issue){
    Response response =
    given().
        auth().preemptive().basic("webinar5", "webinar5").
        contentType(ContentType.JSON).
        body(issue.toJSONString()).
        when().post("https://jira.hillel.it/rest/api/2/issue").
        then().
        contentType(ContentType.JSON).
        statusCode(201).
        extract().response();
    return response;
  }

  public static Response getCreatedIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    return response;
  }

  public static Response deleteIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response checkDeletedIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(404).
            extract().response();
    return response;
  }

  public static Response addJiraComment(JSONObject comment){
    Response response =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            contentType(ContentType.JSON).
            body(comment.toJSONString()).
            when().
            post("https://jira.hillel.it/rest/api/2/issue/WEBINAR-13562/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            time(lessThan(2L), TimeUnit.SECONDS).
            extract().response();
    return response;
  }

  public static Response deleteComment(String commentURI){
    Response response =
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            delete(commentURI).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getDeletedComment(String commentURI){
    Response response=
        given().
            auth().preemptive().basic("webinar5", "webinar5").
            get(commentURI).
            then().
            statusCode(404).
            extract().response();
    return response;
  }
}