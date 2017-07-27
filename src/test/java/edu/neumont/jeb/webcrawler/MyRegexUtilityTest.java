package edu.neumont.jeb.webcrawler;

import static org.junit.Assert.*;
import org.junit.Test;

public class MyRegexUtilityTest {
	@Test
	public void TestInvalidUrl() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		assertFalse(r.isValidUrl("si0lewk9i3l"));
	}

	@Test
	public void TestValidUrl() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		assertTrue(r.isValidUrl("https://google.com"));
	}

	@Test
	public void validCountContains() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		assertEquals(6, r.countContains("i", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}

	@Test
	public void invalidCountContains() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		assertNotEquals(4, r.countContains("i", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}

	@Test
	public void getHTMLTagContents() throws Exception {
	}

	@Test
	public void getHTMLTagsContents() throws Exception {
	}

	@Test
	public void validGetHTMLLinkURL() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		String[] expected = new String[] {"#hero", "#news", "#servers", "#roster", "#about", "#contact"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<div id=\"sidecar_nav\"><div class=\"link_container\"><a href=\"#hero\">Home</a> <a href=\"#news\">News</a> <a href=\"#servers\">Servers</a> <a href=\"#roster\">Roster</a> <a href=\"#about\">About Us</a> <a href=\"#contact\">Contact</a></div></div>"));
	}

	@Test
	public void invalidGetHTMLLinkURL() throws Exception {
		MyRegexUtility r = new MyRegexUtility();
		String[] expected = new String[] {"#roster", "#about", "#contact"};
		assertNotEquals(expected, r.getHTMLLinkURL("<div id=\"sidecar_nav\"><div class=\"link_container\"><a href=\"#hero\">Home</a> <a href=\"#news\">News</a> <a href=\"#servers\">Servers</a> <a href=\"#roster\">Roster</a> <a href=\"#about\">About Us</a> <a href=\"#contact\">Contact</a></div></div>"));
	}

}
