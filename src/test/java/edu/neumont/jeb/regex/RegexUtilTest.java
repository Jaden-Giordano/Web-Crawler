package edu.neumont.jeb.regex;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUtilTest {

	@Test
	public void localUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/news"));
	}

	@Test
	public void localRelativeUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("news"));
	}

	@Test
	public void localDocumentUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/text.pdf"));
	}

	@Test
	public void localRelativeDocumentUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("text.pdf"));
	}

	@Test
	public void localFolderedDocumentUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("/docs/text.pdf"));
	}

	@Test
	public void localFolderedRelativeDocumentUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = null;
		assertEquals(expected, r.validateUrl("docs/text.pdf"));
	}

	@Test
	public void shortUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = "http://neumont.edu";
		assertEquals(expected, r.validateUrl("neumont.edu"));
	}

	@Test
	public void fullHttpsUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = "http://google.com";
		assertEquals(expected, r.validateUrl("https://google.com"));
	}

	@Test
	public void subdomainUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = "http://mail.google.com";
		assertEquals(expected, r.validateUrl("mail.google.com"));
	}

	@Test
	public void queryUrl() throws Exception {
		RegexUtil r = new RegexUtil();
		String expected = "http://www.google.com/search?q=puppies&rlz=1C1CHBF_enUS740US740&oq=puppies&aqs=chrome..69i57j0l5.7307j0j7&sourceid=chrome&ie=UTF-8";
		assertEquals(expected, r.validateUrl("https://www.google.com/search?q=puppies&rlz=1C1CHBF_enUS740US740&oq=puppies&aqs=chrome..69i57j0l5.7307j0j7&sourceid=chrome&ie=UTF-8"));
	}

	@Test
	public void validCountContains() throws Exception {
		RegexUtil r = new RegexUtil();
		assertEquals(6, r.countContains("i", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
	}

	@Test
	public void invalidCountContains() throws Exception {
		RegexUtil r = new RegexUtil();
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
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"/news", "https://servers.com", "http://roster.com"};
		assertArrayEquals(expected, r.getAltText("<img title=\"Neumont University's top 10 advantages.\" src=\"cmsimages/Home_Top10x.png\" alt=\"Neumont University's top 10 advantages.\" width=\"530\" height=\"375\">"));
	}

	@Test
	public void validGetHTMLLinkURLNoAnchorsMailtoOrTel() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"/news", "https://servers.com", "http://roster.com"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", false, false));
	}

	@Test
	public void validGetHTMLLinkURLWithAnchorsNoMailtoAndTel() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"#hero", "/news", "https://servers.com", "http://roster.com"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", true, false));
	}

	@Test
	public void validGetHTMLLinkURLWithMailtoAndTelNoAnchors() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"/news", "https://servers.com", "http://roster.com", "mailto:test@mailinator.net", "tel:18886386668"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", false, true));
	}

	@Test
	public void validGetHTMLLinkURLWithAnchorsMailtoAndTel() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"#hero", "/news", "https://servers.com", "http://roster.com", "mailto:test@mailinator.net", "tel:18886386668"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<a href=\"#hero\">Home</a><a href=\"/news\">News</a><a href=\"https://servers.com\">Servers</a><a href=\"http://roster.com\">Roster</a><a href=\"mailto:test@mailinator.net\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", true, true));
	}

}
