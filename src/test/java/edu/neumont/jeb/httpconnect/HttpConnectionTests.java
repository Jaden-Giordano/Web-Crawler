package edu.neumont.jeb.httpconnect;

import org.junit.Assert;
import org.junit.Test;

public class HttpConnectionTests {

	@Test
	public void testExistingDomain() {
		String page = HttpConnection.getInstance().getSource("http://www.neumont.edu");
		Assert.assertFalse(page.isEmpty());
	}

	@Test
	public void testNonExistentDomain() {
		Assert.assertTrue(HttpConnection.getInstance().getSource("http://adf1234hjkdfai.com").isEmpty());
	}

}
