package edu.neumont.jeb.websearch;

import edu.neumont.jeb.storage.Database;
import edu.neumont.jeb.webcrawler.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WebSearch {

	public static void main(String[] args) {
		WebSearch s = new WebSearch();
		s.run(args);
	}

	private void run(String[] args) {
		Database<Word> db = new Database<>();
		ArrayList<Word> webpages;
		for (int i = 0; i < args.length; i++) {
			System.out.println("Word Searched: " + args[i]);
			webpages = (ArrayList<Word>) db.searchWord(args[i]);
			Object[] sortedWebpages = webpages.stream().sorted(Comparator.comparing(Word::getOccurances)).toArray();

			System.out.println("array: " + Arrays.toString(sortedWebpages));
		}
	}
}
