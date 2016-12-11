import java.util.HashSet;

public class Submission implements Comparable<Submission>{
	public String email;
	public String problemsSolved;
	
	private HashSet<Integer> solvedIds;
	public int score;
	
	Submission(){
		solvedIds = null;
	}
	
	void findSolvedIds(){
		score = 0;
		solvedIds = new HashSet<Integer>();
		if(problemsSolved!=null && !problemsSolved.contains("None") && !problemsSolved.contains("none")){
			problemsSolved = problemsSolved.replaceAll(" ", "");
			String[] solvedIdStrings = problemsSolved.split(",");
			for(int i=0;i<solvedIdStrings.length;i++){
				solvedIds.add(Integer.parseInt(solvedIdStrings[i]));		
				score += Constants.problems[Integer.parseInt(solvedIdStrings[i])-1].score;
			}
		}	
	}
	
	public boolean didSolve(int problemId){
		return solvedIds.contains(problemId);
	}

	@Override
	public int compareTo(Submission other) {
		return other.score - score;
	}
}