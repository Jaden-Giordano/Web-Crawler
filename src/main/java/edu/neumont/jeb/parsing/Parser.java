package edu.neumont.jeb.parsing;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.parsing.RegexUtil;

public class Parser {
	
	public void crawlURL(String address) {
		String pageContent = HttpConnection.getInstance().getSource(address);
		if (pageContent == null) return;
		// send page content to parser methods
		parse(pageContent);
	}
	
	//any text in the body of a p, span, div, li, label, option (but only if it's nested in a select) a, td, th, or font tag
	// any text in an alt attribute (for example in an img tag)
	private void parse(String html) {
		// can't do it this way because it returns only the first instance of the tag. have to return the arrays
		// possibly use an array list to add all? 
		String data = new RegexUtil().getHTMLTagContents(html, "li");
		// get other links, maybe separate methods so we can track level depth? 
	}

}
