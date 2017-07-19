# Web Crawler

## First App
### Requirements:

1. Accept a list of base URLs as command line arguments as indexing starting points.  Crawl each base URL (site) one at a time.

2. Follow all internal site links (relative links or absolute links that go to the same domain), but be careful not to crawl the same page twice.

3. Your crawler must follow all external site links and index those external sites as well.  However, it will stop after two external link hops from the starting domain.

4. You are allowed to use Java's built in HTTP request classes.

5. You must create your own HTML parsing with regular expressions.  Index all of the following:

    1. any text in the body tag but not inside any other tag
    2. any text in the body of a p, span, div, li, label, option (but only if it's nested in a select) a, td, th, or font tag
    3. any text in an alt attribute (for example in an img tag)
    4. any text that is in the body of the title tag
6. Do NOT index tag names, attribute names, attribute values, javascript, css, other HTML/CSS/JavaScript structures, or anything not relevant to the content of the page.

7. You must design a Random Access File structure that will hold the results of your indexing for search purposes.  Be sure the search time to lookup a word is constant!  O(1) of very close.  You are NOT allowed to store numbers as strings in this structure!!

8. I strongly recommend using recursion to simplify your code.

## Second App

Create a second application that, give a search string, will print out a list of matching URLs sorted in order of most hits to least hits.  I recommend using object streams to handle the sorting.