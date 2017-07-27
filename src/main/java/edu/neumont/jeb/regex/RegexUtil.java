package edu.neumont.jeb.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public int countContains(String needle, String haystack) {
		String regex = needle;
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(haystack);
		int count = 0;
		while(m.find()) {
			count++;
		}
		return count;
	}

	public String getHTMLTagContents(String html, String tagName) {
		String rawRegex = "(<body(\\s[^>]*)?>)((\\s|.)*?)?(<\\/body>)";
		Pattern p = Pattern.compile(rawRegex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		if (m.find()) {
			return m.group(3);
		} else {
			return null; 
		}
	}

	public String[] getHTMLTagsContents(String html, String tagName) {
		String rawRegex = "(<body(\\s[^>]*)?>)((\\s|.)*?)?(<\\/body>)";
		Pattern p = Pattern.compile(rawRegex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		List<String> list = new ArrayList<String>();
		while(m.find()){
			list.add(m.group(3));
		}
		return (String[]) list.toArray();
	}

	public String[] getHTMLLinkURL(String html) {
		String regex = "<a href=\"(.*?)\">.*<\\/a>"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(html);
		ArrayList<String> list = new ArrayList<String>();
		while(m.find()){
			list.add(m.group(1));
		}
		return (String[]) list.toArray();
	}

}
