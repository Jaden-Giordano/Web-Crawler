package edu.neumont.jeb.webcrawler;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler w = new WebCrawler();
		w.run(args);
	}

	private void run(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (! new MyRegexUtility().isValidUrl(args[i])) {
				throw new IllegalArgumentException("Invalid Website URL");
			}
			System.out.println(i + ": " + args[i]);
		}
	}
}
