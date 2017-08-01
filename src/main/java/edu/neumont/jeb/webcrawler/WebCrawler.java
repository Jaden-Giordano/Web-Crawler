package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.regex.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler w = new WebCrawler();
		w.run(args);
	}

	List<String> readURLs;

	private WebCrawler() {
		this.readURLs = new ArrayList<>();
	}

	private void run(String[] args) {
		for (int i = 0; i < args.length; i++) {
			args[i] = new RegexUtil().validateUrl(args[i]);
			if (args[i] == null) {
				throw new IllegalArgumentException("Invalid Website URL");
			}
			System.out.println(i + ": " + args[i]);
			crawlSite(args[i], 0);
		}
	}

	private boolean alreadyRead(String url) {
		return readURLs.contains(url);
	}
	
	private void crawlSite(String url, int depth) {
		if (depth >= 2) return;

		String source = HttpConnection.getInstance().getSource(url);
		if (source == null) {
			return;
		}
		
		RegexUtil r = new RegexUtil(); 
		
		String title = r.getHTMLTagContents(source, "title"); 
		
		String body = r.getHTMLTagContents(source, "body"); 
		
		String[] links = r.getHTMLLinkURL(body, false, false);

		for (String i : links) {
			if (!alreadyRead(i)) {
				crawlSite(i, depth++);
			}
		}
	}
	
	private void removeWords(String[] content) {
		String[] conjuctions = {" and ", " it ", " but ", " or ", " though "
				, " although ", " while ", " if ", " only ", " unless ", " until "
				, " that ", " than ", " in ", " whether ", " as ", " whereas " 
				, " by ", " the ", " till ", " when ", " whenever ", " because "
				, " since ", " so ", " what ", " whichever ", " whose ", " also "
				, " besides ", " furthermore ", " likewise ", " moreover ", " nevertheless "
				, " nonetheless ", " conversely ", " instead ", " otherwise ", " rahter "
				, " accordingly ", " consequently ", " hence ", " meanwhile "
				, " therefore ", " thus "}; 
		
		for (String string : content) {
			for (String conj : conjuctions) {
				string.replace(conj, "");
			}
		}
	}
	
	
}
