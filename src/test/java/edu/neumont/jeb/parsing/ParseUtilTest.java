package edu.neumont.jeb.parsing;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParseUtilTest {

	@Test
	public void localUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/news"));
	}

	@Test
	public void localRelativeUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("news"));
	}

	@Test
	public void localDocumentUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/text.pdf"));
	}

	@Test
	public void localRelativeDocumentUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("academics.html"));
	}

	@Test
	public void localRelativePageUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("test.aspx"));
	}

	@Test
	public void localFolderedDocumentUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/docs/text.pdf"));
	}

	@Test
	public void localFolderedRelativeDocumentUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("docs/text.pdf"));
	}

	@Test
	public void shortUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = "http://neumont.edu";
		assertEquals(expected, r.validateUrl("neumont.edu"));
	}

	@Test
	public void fullHttpsUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = "http://google.com";
		assertEquals(expected, r.validateUrl("https://google.com"));
	}

	@Test
	public void subdomainUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = "http://mail.google.com";
		assertEquals(expected, r.validateUrl("mail.google.com"));
	}

	@Test
	public void queryUrl() throws Exception {
		ParseUtil r = new ParseUtil();
		String expected = "http://www.google.com/search?q=puppies&rlz=1C1CHBF_enUS740US740&oq=puppies&aqs=chrome..69i57j0l5.7307j0j7&sourceid=chrome&ie=UTF-8";
		assertEquals(expected, r.validateUrl("https://www.google.com/search?q=puppies&rlz=1C1CHBF_enUS740US740&oq=puppies&aqs=chrome..69i57j0l5.7307j0j7&sourceid=chrome&ie=UTF-8"));
	}

	@Test
	public void validCountContains() throws Exception {
		ParseUtil r = new ParseUtil();
		assertEquals(6, r.countContains("i", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}

	@Test
	public void invalidCountContains() throws Exception {
		ParseUtil r = new ParseUtil();
		assertNotEquals(4, r.countContains("i", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}

	@Test
	public void getHTMLTagContents() throws Exception {
	}

	@Test
	public void getHTMLTagsContents() throws Exception {
	}

	@Test
	public  void getAltAttributeText() throws Exception {
		ParseUtil r = new ParseUtil();
		String[] expected = new String[] {"Neumont University's top 10 advantages.", "This is an image :)"};
		assertArrayEquals(expected, r.getAltText("<img alt=\"Neumont University's top 10 advantages.\" width=\"530\" height=\"375\"><img alt=\"\" width=\"530\" height=\"375\"><img alt=\"This is an image :)\" width=\"530\" height=\"375\">"));
	}

	@Test
	public void validGetHTMLLinkURLNoAnchorsMailtoOrTel() throws Exception {
		ParseUtil r = new ParseUtil();
		String[] expected = new String[] {"/news", "https://servers.com", "http://roster.com"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", false, false));
	}

	@Test
	public void validGetHTMLLinkURLWithAnchorsNoMailtoAndTel() throws Exception {
		ParseUtil r = new ParseUtil();
		String[] expected = new String[] {"#hero", "/news", "https://servers.com", "http://roster.com"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", true, false));
	}

	@Test
	public void validGetHTMLLinkURLWithMailtoAndTelNoAnchors() throws Exception {
		ParseUtil r = new ParseUtil();
		String[] expected = new String[] {"/news", "https://servers.com", "http://roster.com", "mailto:test@mailinator.net", "tel:18886386668"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", false, true));
	}

	@Test
	public void validGetHTMLLinkURLWithAnchorsMailtoAndTel() throws Exception {
		ParseUtil r = new ParseUtil();
		String[] expected = new String[] {"#hero", "/news", "https://servers.com", "http://roster.com", "mailto:test@mailinator.net", "tel:18886386668"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", true, true));
	}

	@Test
	public void parseHTML() {
		ParseUtil r = new ParseUtil();
		String html = "<body><div><div>wow</div><p>hi_there</p></div></body>";
		String[] expected = new String[] {"<div>wow</div><p>hi_there</p>", "wow"};
		assertArrayEquals(expected, r.getTagContents(html, "div"));

		assertTrue(r.getTagContents(html, "p")[0].equals("hi_there"));
	}

}
