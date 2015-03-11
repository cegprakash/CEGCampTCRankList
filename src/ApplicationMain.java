import java.io.IOException;

import com.google.gdata.util.ServiceException;

public class ApplicationMain {
	
	public static void main(String args[]) throws IOException, ServiceException{
		RankListGenerator rankListGenerator = new RankListGenerator();
		rankListGenerator.generate();
	}
}