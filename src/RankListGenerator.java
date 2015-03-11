import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gdata.util.ServiceException;


public class RankListGenerator {
	
	GoogleSpreadSheetAPI spreadSheetAPI;
	GoogleBloggerAPI bloggerAPI;
	List<Participant> participants;
	Map<String, Integer> IDs;
	
	RankListGenerator() throws IOException, ServiceException{
		spreadSheetAPI = new GoogleSpreadSheetAPI();
		bloggerAPI = new GoogleBloggerAPI();
		participants = spreadSheetAPI.getParticipants();
		System.out.println("Participants count : "+participants.size());
		
		IDs = new HashMap<String, Integer>();
		for(int i=0;i<participants.size();i++)
			IDs.put(participants.get(i).email, i);
	}

	void generate() throws IOException, ServiceException{
		List<Submission> submissions = spreadSheetAPI.getSubmissions();
		Collections.sort(submissions);
		bloggerAPI.createPost(getContent(submissions));
	}
	
	String getContent(List<Submission> submissions){
		Calendar calendar = GregorianCalendar.getInstance();		
		String answer = "Last updated at "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+" IST\n\n";
		answer += "<table style=\"width:100%; border-spacing: 8px;\">";
		answer += "<colgroup>";
		answer += "<col span=\"1\" style =\"width: 10%;\">";
		answer += "<col span=\"1\" style =\"width: 25%;\">";
		for(int i=0;i<Constants.problemNames.length;i++){
			answer += "<col span=\"1\" style =\"width: "+String.valueOf(50/Constants.problemNames.length)+"%;\">";
		}		
		answer += "<col span=\"1\" style =\"width: 15%;\">";
		answer += "</colgroup>";
		answer += "<thead>";
		answer+= "<tr>";
		answer += "<th style=\"text-align:center\";>";  answer += "Rank"; answer += "</th>";
		answer += "<th>";  answer += "Name"; answer += "</th>";
		for(int i=0;i<Constants.problemNames.length;i++){
			answer += "<th style=\"text-align:center\";>";  answer += Constants.problemNames[i]; answer += "</th>";
		}
		answer += "<th style=\"text-align:center\";>";  answer += "Points"; answer += "</th>";
		answer += "<tr/>";		
		answer += "</thead>";
		
		
		answer += "<tbody>";
		for(int i=0; i<submissions.size(); i++){
			answer += "<tr>";
			answer += "<td style=\"text-align:center\";>"; answer += String.valueOf(i+1); answer +="</td>";
			String name = IDs.containsKey(submissions.get(i).email)? participants.get((int) IDs.get(submissions.get(i).email)).name : "Unknown";
			answer += "<td>"; answer += name; answer +="</td>";
			int points = 0;
			for(int j=0;j<Constants.problemNames.length;j++){
				answer += "<td style=\"text-align:center\"; bgcolor=\""; answer+=submissions.get(i).didSolve(j+1)?Constants.GREEN:Constants.WHITE; answer +="\"></td>";
				points += submissions.get(i).didSolve(j+1)? 3 : 0;
			}
			answer += "<td style=\"text-align:center\";>"; answer += String.valueOf(points); answer +="</td>";
			answer += "<tr/>";
		}		
		answer += "</tbody>";
		answer += "</table>";
		return answer;
	}
	
	
}