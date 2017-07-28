package edu.neumont.jeb.regex;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUtilTest {
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
	public void validGetHTMLLinkURL() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"#hero", "#news", "#servers", "#roster", "#about", "#contact"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<div id=\"sidecar_nav\"><div class=\"link_container\"><a href=\"#hero\">Home</a> <a href=\"#news\">News</a> <a href=\"#servers\">Servers</a> <a href=\"#roster\">Roster</a> <a href=\"#about\">About Us</a> <a href=\"#contact\">Contact</a></div></div><a href=\"mailto:test@mailinator.net\" title=\"test email address\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", false));
	}

	@Test
	public void validGetHTMLLinkURLWithTelAndMailto() throws Exception {
		RegexUtil r = new RegexUtil();
		String[] expected = new String[] {"#hero", "#news", "#servers", "#roster", "#about", "#contact", "mailto:test@mailinator.net", "tel:18886386668"};
		assertArrayEquals(expected, r.getHTMLLinkURL("<div id=\"sidecar_nav\"><div class=\"link_container\"><a href=\"#hero\">Home</a> <a href=\"#news\">News</a> <a href=\"#servers\">Servers</a> <a href=\"#roster\">Roster</a> <a href=\"#about\">About Us</a> <a href=\"#contact\">Contact</a></div></div><a href=\"mailto:test@mailinator.net\" title=\"test email address\">test@mailinator.net</a><a href=\"tel:18886386668\">1-888-638-6668</a>", true));
	}

}
