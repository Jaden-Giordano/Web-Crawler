package edu.neumont.jeb.webcrawler;

import org.junit.Test;
import static org.junit.Assert.*;

public class WebCrawlerTests {

	@Test
	public  void getBaseLevelText() throws Exception {
		String expected = "testtesttext";
		assertTrue(expected.equals(WebCrawler.getWordsInBase("test<div><img src=\"/images/fun.jpg\"/></div>test<img src=\"/fun/img.jpg/>text")));
	}

	@Test
	public  void getBaseLevelTextWithLineReturns() throws Exception {
		String expected = "test\n\ntest\n\ntext\n";
		System.out.println("expected = " + expected);
		assertTrue(expected.equals(WebCrawler.getWordsInBase("test\n" +
				"<div>\n" +
				"\t<img src=\"/images/fun.jpg\"/>\n" +
				"</div>\n" +
				"test\n" +
				"<img src=\"/fun/img.jpg/>\n" +
				"text\n")));
	}
}
