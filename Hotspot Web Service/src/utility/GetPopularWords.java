package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import databeans.*;

public class GetPopularWords {
	private static final String[] defaultStopWords = { "a", "about", "above",
			"above", "across", "after", "afterwards", "again", "against",
			"all", "almost", "alone", "along", "already", "also", "although",
			"always", "am", "among", "amongst", "amoungst", "amount", "an",
			"and", "another", "any", "anyhow", "anyone", "anything", "anyway",
			"anywhere", "are", "around", "as", "at", "back", "be", "became",
			"because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "behind", "being", "below", "beside", "besides",
			"between", "beyond", "bill", "both", "bottom", "but", "by", "call",
			"can", "cannot", "cant", "co", "con", "could", "couldnt", "cry",
			"de", "describe", "detail", "do", "did", "does", "done", "down",
			"due", "during", "each", "eg", "eight", "either", "eleven", "else",
			"elsewhere", "empty", "enough", "etc", "even", "ever", "every",
			"everyone", "everything", "everywhere", "except", "few", "fifteen",
			"fify", "fill", "find", "fire", "first", "five", "follow", "for",
			"former", "formerly", "forty", "found", "four", "from", "front",
			"fuck", "fucking", "fxxking", "full", "further", "get", "give",
			"go", "going", "had", "has", "hasnt", "have", "having", "he",
			"hence", "her", "here", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "him", "himself", "his", "how",
			"however", "hundred", "ie", "if", "in", "inc", "indeed",
			"interest", "into", "is", "it", "its", "itself", "just", "keep",
			"last", "latter", "latterly", "least", "less", "ltd", "make",
			"made", "many", "may", "me", "meanwhile", "might", "mill", "mine",
			"more", "moreover", "most", "mostly", "move", "much", "must", "my",
			"myself", "name", "namely", "neither", "never", "nevertheless",
			"next", "nine", "no", "nobody", "none", "noone", "nor", "not",
			"nothing", "now", "nowhere", "of", "off", "often", "on", "once",
			"one", "only", "onto", "or", "other", "others", "otherwise", "our",
			"ours", "ourselves", "out", "over", "own", "part", "per",
			"perhaps", "please", "put", "rather", "re", "same", "sex", "see",
			"seem", "seemed", "seeming", "seems", "serious", "several", "she",
			"should", "show", "side", "since", "sincere", "six", "sixty", "so",
			"some", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhere", "still", "such", "system", "take", "ten", "text",
			"than", "that", "the", "their", "them", "themselves", "then",
			"thence", "there", "thereafter", "thereby", "therefore", "therein",
			"thereupon", "these", "they", "thickv", "thin", "third", "this",
			"those", "though", "three", "through", "throughout", "thru",
			"thus", "to", "together", "too", "top", "toward", "towards",
			"twelve", "twenty", "two", "un", "under", "until", "up", "upon",
			"us", "very", "via", "was", "we", "well", "were", "what",
			"whatever", "when", "whence", "whenever", "where", "whereafter",
			"whereas", "whereby", "wherein", "whereupon", "wherever",
			"whether", "which", "while", "whither", "who", "whoever", "whole",
			"whom", "whose", "why", "will", "with", "within", "without",
			"would", "yet", "you", "your", "yours", "yourself", "yourselves",
			"the", "retweet" };
	private static final int MAX_RETURN = 30;

	public ArrayList<Mapping> mostPopularWords(String text, String query) {
		ArrayList<Mapping> ret = new ArrayList<Mapping>();
		HashSet<String> stopWords = new HashSet<String>();
		HashMap<String, Integer> freq = new HashMap<String, Integer>();
		for (String s : defaultStopWords)
			stopWords.add(s);
		StringTokenizer topicst = new StringTokenizer(query);
		while (topicst.hasMoreTokens())
			stopWords.add(topicst.nextToken().toLowerCase());

		StringTokenizer st = new StringTokenizer(text);
		while (st.hasMoreTokens()) {
			String word = st.nextToken().toLowerCase();

			word = word.replace("\\.|;|,|:|#", "");

			if (!word.matches("^[a-z]+"))
				continue;
			if (word.startsWith("http:"))
				continue;
			if (word.startsWith("rt"))
				continue;
			if (word.length() < 4)
				continue;

			if (stopWords.contains(word))
				continue;

			if (freq.get(word) != null)
				freq.put(word, freq.get(word) + 1);
			else
				freq.put(word, 1);
		}

		Set<String> keySet = freq.keySet();
		Object[] keyArray = keySet.toArray();
		ArrayList<Mapping> map = new ArrayList<Mapping>();
		for (Object s : keyArray)
			map.add(new Mapping((String) s, freq.get((String) s)));
		Collections.sort(map, new MapComparator());

		for (int i = 0; i < MAX_RETURN; i++)
			if (i < map.size()) {
				ret.add(map.get(i));
			}

		return ret;
	}

	private class MapComparator implements Comparator<Mapping> {
		public int compare(Mapping o1, Mapping o2) {
			return o2.value - o1.value;
		}
	}
}
