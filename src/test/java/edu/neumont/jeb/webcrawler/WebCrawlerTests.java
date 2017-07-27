package edu.neumont.jeb.webcrawler;

import org.junit.Test;

public class WebCrawlerTests {

	@Test
	public void test() {
		Parser p = new Parser();
		p.crawlURL("http://www.neumont.edu");
	}

}
