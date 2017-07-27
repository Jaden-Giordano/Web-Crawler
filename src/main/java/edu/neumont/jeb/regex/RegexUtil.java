package edu.neumont.jeb.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * Checks to see if the passed in url is valid
	 * @param url Website url
	 * @return boolean: depending on whether or not it is valid
	 */
	public boolean isValidUrl(String url) {
		String regex = "^(?:http(s)?://)([\\w.-]+(?:\\.[\\w.-]+)+[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]+)";
		return Pattern.matches(regex, url);
	}

	/**
	 * Takes the passed in https(or http) url and returns the http version
	 * @param url Website url
	 * @return String: http url
	 */
	public String httpsToHttp(String url) {
		String regex = "^(?:http(s)?://)([\\w.-]+(?:\\.[\\w.-]+)+[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]+)";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		m.find();

		String httpURL = "http://" + m.group(2);
		return httpURL;
	}

	/**
	 * Counts the number of times that the needle is in the haystack
	 * @param needle What you are searching for
	 * @param haystack What to search through
	 * @return int: count
	 */
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

	/**
	 * Finds the contents of the first instance of the passed in tag
	 * @param html html contents to search
	 * @param tagName name of html tag
	 * @return String: contents
	 */
	public String getHTMLTagContents(String html, String tagName) {
		String rawRegex = "(<" + tagName + "(\\s[^>]*)?>)((\\s|.)*?)?(<\\/" + tagName + ">)";
		Pattern p = Pattern.compile(rawRegex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		if (m.find()) {
			return m.group(3);
		} else {
			return null;
		}
	}

	/**
	 * Finds the contents of the all instances of the passed in tag
	 * @param html html contents to search
	 * @param tagName name of html tag
	 * @return String[]: contents
	 */
	public String[] getHTMLTagsContents(String html, String tagName) {
		String rawRegex = "(<" + tagName + "(\\s[^>]*)?>)((\\s|.)*?)?(</" + tagName + ">)";
		Pattern p = Pattern.compile(rawRegex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		List<String> list = new ArrayList<>();

		while(m.find()){
			list.add(m.group(3));
		}

		return (String[]) list.toArray();
	}

	/**
	 * Finds all a tag href values within the page that are not tel or mailto
	 * @param html html contents to search
	 * @param returnTelAndMailto boolean for whether or not you want to return tel and mailto a tag href values
	 * @return String[]: links
	 */
	public String[] getHTMLLinkURL(String html, boolean returnTelAndMailto) {
		String regex = "<a href=\"((?!(tel|mailto))[^\"]+)";
		if (returnTelAndMailto) {
			regex = "<a href=\"([^\"]+)";
		}

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		ArrayList<String> list = new ArrayList<>();

		while(m.find()){
			list.add(m.group(1));
		}

		return list.toArray(new String[0]);
	}

}
