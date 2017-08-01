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

		Database<Word> db = new Database<Word>(System.getProperty("user.dir") + File.separator + "data", Word.class);
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
		
		String body = r.getHTMLTagContents(source, "body");

		// Order of steps for parsing html.
		// 1. Read content of tag
		// 2. Remove nested tags and their data.
		// 3. Remove conjunctions from content.
		// 4. Add word to word list.
		// 5. If there were nested tags; go to step one for that tag.

		String[] divContents = r.getTagContents(body, "div");
		String[] pContents = r.getTagContents(body, "p");
		String[] spanContents = r.getTagContents(body, "span");
		String[] liContents = r.getTagContents(body, "li");
		String[] labelContents = r.getTagContents(body, "label");
		String[] aContents = r.getTagContents(body, "a");
		String[] tdContents = r.getTagContents(body, "td");
		String[] thContents = r.getTagContents(body, "th");
		String[] fontContents = r.getTagContents(body, "font");
		String[] alts = r.getAltText(body);
		String[] selectContents = r.getTagContents(body, "select");
		List<String> optionsList = new ArrayList<>();
		for (String i : selectContents) {
			optionsList.addAll(Arrays.asList(r.getTagContents(i, "option")));
		}
		String[] optionContents = optionsList.toArray(new String[optionsList.size()]);

		for (String i : (String[]) ArrayUtils.addAll(divContents, pContents, spanContents, liContents, labelContents, aContents, tdContents, thContents, fontContents, alts, selectContents, optionContents)) {
			String content = i;
			content = replacePunctuation(content);

			String[] words = content.split(" ");
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
			return word.replaceAll("([[:punct:]])", "");
	}
	
	
}
