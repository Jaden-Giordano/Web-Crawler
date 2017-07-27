package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.regex.RegexUtil;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler w = new WebCrawler();
		w.run(args);
	}

	private void run(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (! new RegexUtil().isValidUrl(args[i])) {
				throw new IllegalArgumentException("Invalid Website URL");
			}
			System.out.println(i + ": " + args[i]);
			crawlSite(args[i]);
		}
	}
	
	private void crawlSite(String url) {
		String source = HttpConnection.getInstance().getSource(url);
		if (source == null) {
			return;
		}
		
		RegexUtil r = new RegexUtil(); 
		
		String title = r.getHTMLTagContents(source, "title"); 
		
		String body = r.getHTMLTagContents(source, "body"); 
		
		String[] links = r.getHTMLLinkURL(body, false); 
		
		
	}
	
	
}
