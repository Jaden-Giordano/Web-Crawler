package edu.neumont.jeb.webcrawler;

import edu.neumont.jeb.regex.RegexUtil;

public class WebCrawler {

	public static void main(String[] args) {
		Parser p = new Parser(); 
		RegexUtil r = new RegexUtil();
//			try {
//				p.crawlURL("https://www.google.com/");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
			String[] contents = r.getHTMLTagsContents("<comma>hello<.comma><body>wtf</body><body class\"=path\">hellooo blah  hellopo </body>", "body");
			for (String string : contents) {
				System.out.println(string);
			}
            // send each url through the parser
			// index
			 //count 
			// then send all of the parsed info into a RAF
		//create second app that searches the RAF for the urls that hit the most
        
		
	}
	
}
