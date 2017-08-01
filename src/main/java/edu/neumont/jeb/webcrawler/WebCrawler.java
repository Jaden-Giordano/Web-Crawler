package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.regex.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler w = new WebCrawler();
		w.run(args);
	}

	List<String> readURLs;

	List<Word> words;

	private WebCrawler() {
		this.readURLs = new ArrayList<>();
		this.words = new ArrayList<>();
	}

	private void run(String[] args) {
		for (int i = 0; i < args.length; i++) {
			args[i] = new RegexUtil().validateUrl(args[i]);
			if (args[i] == null) {
				throw new IllegalArgumentException("Invalid Website URL");
			}
			System.out.println(i + ": " + args[i]);
			crawlSite(args[i], 0);
		}
	}

	private boolean alreadyRead(String url) {
		return readURLs.contains(url);
	}

	private void addWord(String url, String word) {
		for (Word i : words) {
			if (i.getUrl().equals(url)) {
				if (i.getWord().equalsIgnoreCase(word)) {
					i.pushOccurances();
					return;
				}
			}
		}
		words.add(new Word(url, word, 1));
	}
	
	private void crawlSite(String url, int depth) {
		if (depth >= 2) return;

		String source = HttpConnection.getInstance().getSource(url);
		if (source == null) {
			return;
		}
		
		RegexUtil r = new RegexUtil();
		
		String title = r.getHTMLTagContents(source, "title");
		
		String body = r.getHTMLTagContents(source, "body");

		// Order of steps for parsing html.
		// 1. Read content of tag
		// 2. Remove nested tags and their data.
		// 3. Remove conjunctions from content.
		// 4. Add word to word list.
		// 5. If there were nested tags; go to step one for that tag..
		
		String[] links = r.getHTMLLinkURL(body, false, false);

		for (String i : links) {
			if (!alreadyRead(i)) {
				crawlSite(i, depth++);
			}
		}
	}
	
	private void removeWords(String[] content) {
		String[] conjuctions = {"and", "it", "but", "or", "though"
				, "although", "while", "if", "only", "unless", "until"
				, "that", "than", "in", "whether", "as", "whereas" 
				, "by", "the", "till", "when", "whenever", "because"
				, "since", "so", "what", "whichever", "whose", "also"
				, "besides", "furthermore", "likewise", "moreover", "nevertheless"
				, "nonetheless", "conversely", "instead", "otherwise", "rahter"
				, "accordingly", "consequently", "hence", "meanwhile"
				, "therefore", "thus"}; 
		
		for (String string : content) {
			for (String conj : conjuctions) {
				string.replace(conj, "");
			}
		}
	}


	public static String getWordsInBase(String html) {
		int level = 0;
		String currentSpot;
		boolean checkOpen = false;
		boolean checkClose = false;
		boolean inClosingTag = false;
		boolean isClosingBracket = false;

		StringBuilder sbresults = new StringBuilder();

		for (int i = 0; i < html.length(); i++) {
			currentSpot = Character.toString(html.charAt(i));

			isClosingBracket = false;
			// Check and change Level
			if (currentSpot.equals("<")) {
				//< can be both open <div> or close </div>
				checkOpen = true;
				checkClose = true;
			} else if (currentSpot.equals("/")) {
				// / can be both after </ and /> to close, other / should do nothing.
				// if </ we know we are in closing tag, dont want to read till after >
				if (checkOpen && checkClose) {
					level--;
					checkOpen = false;
					checkClose = false;
					inClosingTag = true;
				} else {
					checkClose = true;
				}
			} else if (currentSpot.equals(">")) {
				isClosingBracket = true;
				// if after / close, if </ text > set closed to true;
				if (checkClose) {
					level--;
					checkClose = false;
				} else if (inClosingTag) {
					inClosingTag = false;
				}
			} else {
				// if not / after < increase level
				if (checkOpen) {
					level++;
					checkOpen = false;
				} else if (checkClose) {
					// If not > after / not closing, probably in link or src or href.
					checkClose = false;
				}
			}

			// Only add on base Level
			if (level == 0 && !checkOpen && !checkClose && !inClosingTag && !isClosingBracket) {
				sbresults.append(currentSpot);
			}
		}

		System.out.println(sbresults.toString());
		return sbresults.toString();
	}

	
}
