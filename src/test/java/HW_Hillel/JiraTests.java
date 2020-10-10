package HW_Hillel;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
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

    responseCreateIssue = JiraAPISteps.createIssue(issue);
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
    JSONObject comment = new JSONObject();
    comment.put("body", "new comment");

    responseAddJiraComment = JiraAPISteps.addJiraComment(comment);
    commentURI = responseAddJiraComment.path("self");
    responseDeleteComment = JiraAPISteps.deleteComment(commentURI);
    responseGetDeletedComment = JiraAPISteps.getDeletedComment(commentURI);
  }
}