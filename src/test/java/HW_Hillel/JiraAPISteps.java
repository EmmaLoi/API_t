package HW_Hillel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class JiraAPISteps {
  static String newIssueJSON = JiraJSONObjects.newIssueJSON();
  static String newCommentJSON = JiraJSONObjects.newCommentJSON();

  public static Response createIssue(){
    Response responseCreateIssue =
    given().
        auth().preemptive().basic(Credential.username, Credential.password).
        contentType(ContentType.JSON).
        body(newIssueJSON).
        when().post(APIPathes.issue).
        then().
        contentType(ContentType.JSON).
        statusCode(201).
        extract().response();
    return responseCreateIssue;
  }

  public static Response getCreatedIssue(String ticketId){
    Response responsegetCreatedIssue =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    return responsegetCreatedIssue;
  }

  public static Response deleteIssue(String ticketId){
    Response responseDeleteIssue =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            contentType(ContentType.JSON).
            when().
            delete(APIPathes.issue + ticketId).
            then().
            statusCode(204).
            extract().response();
    return responseDeleteIssue;
  }

  public static Response checkDeletedIssue(String ticketId){
    Response responseCheckDeletedIssue =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            statusCode(404).
            extract().response();
    return responseCheckDeletedIssue;
  }

  public static Response addJiraComment(){
    Response responseAddJiraComment =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            contentType(ContentType.JSON).
            body(newCommentJSON).
            when().
            post(APIPathes.commentWebinar13562).
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            time(lessThan(3L), TimeUnit.SECONDS).
            extract().response();
    return responseAddJiraComment;
  }

  public static Response deleteComment(String commentURI){
    Response responseDeleteComment =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            delete(commentURI).
            then().
            statusCode(204).
            extract().response();
    return responseDeleteComment;
  }

  public static Response getDeletedComment(String commentURI){
    Response responseGetDeletedComment =
        given().
            auth().preemptive().basic(Credential.username, Credential.password).
            get(commentURI).
            then().
            statusCode(404).
            extract().response();
    return responseGetDeletedComment;
  }
}