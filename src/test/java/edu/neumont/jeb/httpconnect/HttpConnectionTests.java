package edu.neumont.jeb.httpconnect;

import org.junit.Assert;
import org.junit.Test;

public class HttpConnectionTests {

	@Test
	public void testExistingDomain() {
		Assert.assertFalse(HttpConnection.getInstance().getSource("http://google.com").isEmpty());
	}

	@Test
	public void testNonExistentDomain() {
		Assert.assertTrue(HttpConnection.getInstance().getSource("http://adf1234hjkdfai.com").isEmpty());
	}

}
