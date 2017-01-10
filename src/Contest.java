public class Contest {
	public String CONTEST_SUBMISSION_SPREADSHEET_URL;	
	public String CONTEST_SUBMISSION_FORM_ID;
	public String RANKLIST_BLOGPOST_ID;
	public String RANKLIST_BLOG_TITLE;
	public Problem[] problems;
	public String endTime = "23:00 PM";
	
	
	public Contest(String spreadsheetUrl, String formId, String blogPostId, String title, Problem[] problems, String endTime){
		CONTEST_SUBMISSION_SPREADSHEET_URL = spreadsheetUrl;
		CONTEST_SUBMISSION_FORM_ID = formId;
		RANKLIST_BLOGPOST_ID = blogPostId;
		RANKLIST_BLOG_TITLE = title;		
		this.problems = problems;	
		this.endTime = endTime;
	}	
}