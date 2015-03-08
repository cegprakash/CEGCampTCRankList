import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class GoogleSpreadSheetAPI {
	
	SpreadsheetService service;
	GoogleSpreadSheetAPI() throws AuthenticationException{
		service = new SpreadsheetService("Google Spreadsheet Demo");
	    service.setUserCredentials(Constants.GOOGLE_ACCOUNT_USERNAME, Constants.GOOGLE_ACCOUNT_PASSWORD);
	}
	
	List<Participant> getParticipants() throws IOException, ServiceException{
		List<Participant> participants = new ArrayList<Participant>();
	    URL metafeedUrl = new URL(Constants.PARTICIPANTS_SPREADSHEET_URL);
	    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
	    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();

	    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
	    for(ListEntry entry : feed.getEntries())
	    {
	    	Participant participant = new Participant();
	    	participant.name = entry.getCustomElements().getValue("Name").trim();
	    	participant.college = entry.getCustomElements().getValue("College").trim();
	    	participant.branch = entry.getCustomElements().getValue("Branch").trim();
	    	participant.year = entry.getCustomElements().getValue("Year").trim();
	    	participant.email = entry.getCustomElements().getValue("Email").trim();
	    	participant.spojProfileUrl = entry.getCustomElements().getValue("Link to Spoj profile").trim();
	    	participant.topcoderProfileUrl = entry.getCustomElements().getValue("Link to Topcoder profile").trim();
	    	participant.participationMode = entry.getCustomElements().getValue("Participation Mode").trim();
	    	participants.add(participant);
	    }
		return participants;
	}
	
	List<Submission> getSubmissions() throws IOException, ServiceException{
		List<Submission> submissions = new ArrayList<Submission>();
	    URL metafeedUrl = new URL(Constants.CONTEST_SUBMISSION_SPREADSHEET_URL);
	    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
	    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();

	    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
	    for(ListEntry entry : feed.getEntries())
	    {
	    	Submission submission = new Submission();
	    	submission.email = entry.getCustomElements().getValue("Email").trim();
	    	submission.problemsSolved = entry.getCustomElements().getValue("Passed system test").trim();
	    	submissions.add(submission);
	    }
		return submissions;
	}	
}