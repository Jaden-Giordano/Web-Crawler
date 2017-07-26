package edu.neumont.jeb.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
	
	private MyRegexUtility r = new MyRegexUtility(); 
	
	public void crawlURL(String address) throws IOException {
		String strURL = address;
		URL url = new URL(strURL);
		String pageContent = ""; 
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			try (InputStream in = con.getInputStream()) {
				try (BufferedReader inReader = new BufferedReader(new InputStreamReader(in))) {
					while (inReader.ready()) {
						pageContent += inReader.readLine();
					}
				}
			}
		} finally {
			con.disconnect();
		}
		// send page content to parser methods
		parse(pageContent); 
		System.out.println(pageContent);
		System.out.println("In crawl method");
	}
	
	//any text in the body of a p, span, div, li, label, option (but only if it's nested in a select) a, td, th, or font tag
	// any text in an alt attribute (for example in an img tag)
	private void parse(String html) {
		System.out.println("In parse method");
		// can't do it this way because it returns only the first instance of the tag. have to return the arrays
		// possibly use an array list to add all? 
		String[] body = r.getHTMLTagsContents(html, "body"); 
		// String data = null;
		for (String string : body) {
			System.out.println("Count");
			System.out.println(string);
		}
		// get other links, maybe separate methods so we can track level depth? 
	}

}
