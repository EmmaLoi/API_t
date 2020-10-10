package HW_Hillel;
import org.json.simple.JSONObject;

public class JiraJSONObjects {

  public static String newIssueJSON(){
    JSONObject newIssueJSON = new JSONObject();
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
    newIssueJSON.put("fields", fields);
    return newIssueJSON.toJSONString();
  }

  public static String newCommentJSON(){
    JSONObject newCommentJSON = new JSONObject();
    newCommentJSON.put("body", "new newCommentJSON");
    return newCommentJSON.toJSONString();
  }
}