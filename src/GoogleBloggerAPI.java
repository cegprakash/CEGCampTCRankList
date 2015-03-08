import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Entry;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


public class GoogleBloggerAPI {
	
	GoogleService service;
	
	GoogleBloggerAPI() throws AuthenticationException{
		service = new GoogleService("blogger", "exampleCo-exampleApp-1");
	    service.setUserCredentials(Constants.GOOGLE_ACCOUNT_USERNAME, Constants.GOOGLE_ACCOUNT_PASSWORD);	
	}
	
	public Entry createPost(String content) throws ServiceException, IOException {
		Entry myEntry = new Entry();
		myEntry.setTitle(new PlainTextConstruct(Constants.RANKLIST_BLOG_TITLE));
		myEntry.setContent(new PlainTextConstruct(content));
		URL postUrl = new URL("http://www.blogger.com/feeds/" + Constants.GOOGLE_BLOG_ID + "/posts/default/" + Constants.RANKLIST_BLOGPOST_ID);
		return service.update(postUrl, myEntry);
	}
}
