package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.httpconnect.HttpConnection;
import edu.neumont.jeb.parsing.ParseUtil;
import edu.neumont.jeb.storage.Database;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
			args[i] = new ParseUtil().validateUrl(args[i]);
			if (args[i] == null) {
				throw new IllegalArgumentException("Invalid Website URL");
			}
			System.out.println(i + ": " + args[i]);
			crawlSite(args[i], 0);
		}

		Database<Word> db = new Database<Word>(System.getProperty("user.dir") + File.separator + "storage", Word.class);
		for (Word i : words) {
			db.insert(i);
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

		ParseUtil r = new ParseUtil();

		String title = r.getHTMLTagContents(source, "title");

		String body = r.getTagContents(source, "body")[0];

		// Order of steps for parsing html.
		// 1. Read content of tag
		// 2. Remove nested tags and their data.
		// 3. Remove conjunctions from content.
		// 4. Add word to word list.
		// 5. If there were nested tags; go to step one for that tag.

		List<String> arr = new ArrayList<>();
		arr.addAll(Arrays.asList(r.getTagContents(body, "div")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "p")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "span")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "li")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "label")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "a")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "td")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "th")));
		arr.addAll(Arrays.asList(r.getTagContents(body, "font")));
		arr.addAll(Arrays.asList(r.getAltText(body)));

		String[] selectContents = r.getTagContents(body, "select");
		arr.addAll(Arrays.asList(selectContents));
		List<String> optionsList = new ArrayList<>();
		for (String i : selectContents) {
			optionsList.addAll(Arrays.asList(r.getTagContents(i, "option")));
		}
		arr.addAll(optionsList);

		for (String i : arr.toArray(new String[arr.size()])) {
			String content = getWordsInBase(i);
			content = replacePunctuation(content);

			String[] words = content.split("\\s+");
			for (String j : words) {
				if (isConjunction(i)) continue;
				addWord(url, j);
			}
		}

		String[] links = r.getHTMLLinkURL(body, false, false);

		for (String i : links) {
			if (!alreadyRead(i)) {
				crawlSite(i, depth++);
			}
		}
	}
	
	private boolean isConjunction(String word) {
		String[] conjuctions = {" and ", " it ", " but ", " or ", " though "
				, " although ", " while ", " if ", " only ", " unless ", " until "
				, " that ", " than ", " in ", " whether ", " as ", " whereas " 
				, " by ", " the ", " till ", " when ", " whenever ", " because "
				, " since ", " so ", " what ", " whichever ", " whose ", " also "
				, " besides ", " furthermore ", " likewise ", " moreover ", " nevertheless "
				, " nonetheless ", " conversely ", " instead ", " otherwise ", " rahter "
				, " accordingly ", " consequently ", " hence ", " meanwhile "
				, " therefore ", " thus "}; 

		for (String i : conjuctions) {
			if (word.equalsIgnoreCase(i)) return true;
		}
		return false;
	}
	
	private String replacePunctuation(String word) {
			return word.replaceAll("[^\\w\\s]", "");
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

		return sbresults.toString();
	}

	
}
