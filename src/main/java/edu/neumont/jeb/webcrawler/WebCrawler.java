package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.parsing.RegexUtil;

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

		source = source.split("<body>")[1].split("</body>")[0];

		System.out.println("source = " + source);
		
	}
	
	
}
