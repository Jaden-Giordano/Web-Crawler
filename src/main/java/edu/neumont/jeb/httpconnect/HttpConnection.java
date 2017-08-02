package edu.neumont.jeb.httpconnect;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
	private static HttpConnection ourInstance = new HttpConnection();

	/**
	 * Returns the static instance created with the addition of this class
	 * @return HttpConnection instance
	 */
	public static HttpConnection getInstance() {
		return ourInstance;
	}

	private HttpConnection() {
	}

	/**
	 * Get the html from the url
	 * @param sUrl url as a string
	 * @return String: source html
	 */
	public String getSource(String sUrl) {
		String source = "";

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet req = new HttpGet(sUrl);

			HttpResponse res = client.execute(req);

			try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent()))) {
				StringBuilder lines = new StringBuilder();
				while (br.ready()) {
					lines.append(br.readLine());
				}
				source = lines.toString();
			}
		} catch (Exception ignored) { }

		return source;
	}
}
