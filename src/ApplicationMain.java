import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gdata.util.ServiceException;

public class ApplicationMain {
	
	public static void main(String args[]) throws IOException, ServiceException, InterruptedException{
		RankListGenerator rankListGenerator = new RankListGenerator();
		int c=0;
		while(true){
			try{
				rankListGenerator.generate();
				System.out.println(c+++" Generated !");
				TimeUnit.SECONDS.sleep(30);
			}
			catch(Exception e){
				System.err.println(e.toString());
			}
		}
	}
}