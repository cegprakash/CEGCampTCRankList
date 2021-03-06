import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
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
	
	RankListGenerator() throws IOException, ServiceException, GeneralSecurityException{
		spreadSheetAPI = new GoogleSpreadSheetAPI();
		bloggerAPI = new GoogleBloggerAPI();
		participants = spreadSheetAPI.getParticipants();
		System.out.println("Participants count : "+participants.size());
		
		IDs = new HashMap<String, Integer>();
		for(int i=0;i<participants.size();i++)
			IDs.put(participants.get(i).email, i);
	}

	void generate() throws IOException, ServiceException, GeneralSecurityException{
		for (int i=0;i<Constants.contests.length;i++) {
			List<Submission> submissions = spreadSheetAPI.getSubmissions(i);
			Collections.sort(submissions);
			bloggerAPI.udpatePost(getContent(submissions,i),i);
			System.out.println("Updated Contest #"+(i+1));
		}
	}
	
	String getContent(List<Submission> submissions, int contestId){
		DecimalFormat formatter = new DecimalFormat("00");
		Format monthFormat = new SimpleDateFormat("MMM");
		Calendar calendar = GregorianCalendar.getInstance();		
		String answer = "";
		//answer +=  "<meta content='60' http-equiv='refresh'/>";
		answer += "<b>Contest ends at </b>"+Constants.contests[contestId].endTime+"<br><br>";
		
		answer +="<b>Problems</b><br><br>";
		answer += "<table>";
		for(int i=0; i<Constants.contests[contestId].problems.length; i++){
			answer += "<tr>";
			answer+= "<td>"+(i+1)+". </td><td>"+Constants.contests[contestId].problems[i].title+"</td><td>"+" - "+"</td><td>"+Constants.contests[contestId].problems[i].source+ "</td><td>"+ " ("+Constants.contests[contestId].problems[i].score+ (Constants.contests[contestId].problems[i].score > 1?" points)":" point)") +"</td>";
			answer += "<tr/>";
		}
		answer += "</table>";
		answer+="\n";
		answer +="<b>Submit</b> (Google sign-in required to view the form below)\n";
		answer+= "<iframe src=\"https://docs.google.com/forms/d/"+Constants.contests[contestId].CONTEST_SUBMISSION_FORM_ID+"/viewform?embedded=true\" width=\"760\" height=\"300\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\">Loading...</iframe>\n";
		
		answer +="<b>Leaderboard</b>";
		answer += " (Last updated on "+formatter.format(calendar.get(Calendar.DATE))+" "+monthFormat.format(calendar.get(Calendar.MONTH))+" at "+formatter.format(calendar.get(Calendar.HOUR_OF_DAY))+":"+formatter.format(calendar.get(Calendar.MINUTE))+" IST)\n\n";
		answer += "<table style=\"width:100%; border-spacing: 8px;\">";
		answer += "<colgroup>";
		answer += "<col span=\"1\" style =\"width: 10%;\">";
		answer += "<col span=\"1\" style =\"width: 25%;\">";
		for(int i=0;i<Constants.contests[contestId].problems.length;i++){
			answer += "<col span=\"1\" style =\"width: "+String.valueOf(50/Constants.contests[contestId].problems.length)+"%;\">";
		}		
		answer += "<col span=\"1\" style =\"width: 15%;\">";
		answer += "</colgroup>";
		answer += "<thead>";
		answer+= "<tr>";
		answer += "<th style=\"text-align:center\";>";  answer += "Rank"; answer += "</th>";
		answer += "<th>";  answer += "Name"; answer += "</th>";
		for(int i=0;i<Constants.contests[contestId].problems.length;i++){
			answer += "<th style=\"text-align:center\";>";  answer += Constants.contests[contestId].problems[i].title; answer += "</th>";
		}
		answer += "<th style=\"text-align:center\";>";  answer += "Points"; answer += "</th>";
		answer += "<tr/>";		
		answer += "</thead>";
		
		
		answer += "<tbody>";
		for(int i=0; i<submissions.size(); i++){
			answer += "<tr>";
			answer += "<td style=\"text-align:center\";>"; answer += String.valueOf(i+1); answer +="</td>";
			String name = IDs.containsKey(submissions.get(i).email)? participants.get((int) IDs.get(submissions.get(i).email)).name : "Unknown";
			if(name.length() >= 20)
				name = name.substring(0, 18) + "..";
			
			answer += "<td>"; answer += name; answer +="</td>";
			int points = 0;
			for(int j=0;j<Constants.contests[contestId].problems.length;j++){
				if(submissions.get(i).didSolve(j+1)){
					answer += "<td style=\"text-align:center; font-size:12px; color:"+Constants.DARK_GREEN+"\"; bgcolor=\""; answer+=Constants.GREEN; answer +="\">"
							+ "&#10004;"
							+ "</td>";					
					points += Constants.contests[contestId].problems[j].score;
				}
				else{
					answer += "<td>";  answer += "</td>";
				}


			}
			answer += "<td style=\"text-align:center\";>"; answer += String.valueOf(points); answer +="</td>";
			answer += "<tr/>";
		}		
		answer += "</tbody>";
		answer += "</table>";
		return answer;
	}
	
	
}