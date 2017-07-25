package edu.neumont.jeb.webcrawler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegexUtility {

	public int countContains(String needle, String haystack) {
		int count = 0; 
		String regex = needle;
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(haystack);
		while(m.find()) {
			count++; 
		}
		return count;
	}

	public String getHTMLTagContents(String html, String tagName) {
		String rawRegex = "<" + tagName + ">(.*?)<\\/" + tagName + ">"; 
		Pattern p = Pattern.compile(rawRegex); 
		Matcher m = p.matcher(html); 
		if (m.find()) {
			return m.group(1);
		} else {
			return null; 
		}
	}

	public String[] getHTMLTagsContents(String html, String tagName) {
		String regex = "<" + tagName + ">(.*?)<\\/" + tagName + ">"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(html);
		ArrayList<String> list = new ArrayList<String>(); 
		int count = 0; 
		while(m.find()){
			list.add(m.group(1));
			count++; 
		}
		String[] array = new String[count]; 
		for (int i = 0; i < count; i++) {
			array[i] = list.get(i); 
		}
		return array;
	}

	public String[] getHTMLLinkURL(String html) {
		String regex = "<a href=\"(.*?)\">.*<\\/a>"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(html);
		ArrayList<String> list = new ArrayList<String>(); 
		int count = 0; 
		while(m.find()){
			list.add(m.group(1));
			count++; 
		}
		String[] array = new String[count]; 
		for (int i = 0; i < count; i++) {
			array[i] = list.get(i); 
		}
		return array;
	}

}
