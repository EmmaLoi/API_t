package HW_Hillel;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JiraTests {
  String ticketId;
  String commentURI;
  Response responseCreateIssue;
  Response responseGetCreatedIssue;
  Response responseDeleteIssue;
  Response responseCheckDeletedIssue;
  Response responseAddJiraComment;
  Response responseDeleteComment;
  Response responseGetDeletedComment;

  @Test
  public void jiraCreateIssue() {
    responseCreateIssue = JiraAPISteps.createIssue();
    ticketId = responseCreateIssue.path("id");
    assertTrue(responseCreateIssue.path("key").toString().contains("WEBINAR-"));
    responseGetCreatedIssue = JiraAPISteps.getCreatedIssue(ticketId);
    assertEquals(responseGetCreatedIssue.path("fields.summary"), "some summary");
    assertEquals(responseGetCreatedIssue.path("fields.creator.name"), "webinar5");
    responseDeleteIssue = JiraAPISteps.deleteIssue(ticketId);
    responseDeleteIssue.print();
    responseCheckDeletedIssue = JiraAPISteps.checkDeletedIssue(ticketId);
  }

  @Test
  public void addDeleteJiraComment(){
    responseAddJiraComment = JiraAPISteps.addJiraComment();
    commentURI = responseAddJiraComment.path("self");
    responseDeleteComment = JiraAPISteps.deleteComment(commentURI);
    responseGetDeletedComment = JiraAPISteps.getDeletedComment(commentURI);
  }
}