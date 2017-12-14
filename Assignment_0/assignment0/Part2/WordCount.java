import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class WordCount {
	public static HashMap<String, Integer> hm = new HashMap<>();
	public void downLoadFromUrl() throws Exception {
		URL url = new URL("https://www.gutenberg.org/files/98/98-0.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));		
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			wordCount(inputLine);
		}
		in.close();
	}
	
	private void wordCount(String inputLine) {
		String str = inputLine.replaceAll("\\pP", "");
		String[] words = str.split(" ");
		for (String word : words) {
			if (word == " ")
				continue;
			if (hm.containsKey(word)) {
				hm.put(word, hm.get(word) + 1);
			}
			else {
				hm.put(word, 1);
			}
		}
	}
	
	private void sortAndPrintHashMap() {
		List<Map.Entry<String, Integer>> list = new ArrayList<>(hm.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		for (Map.Entry<String, Integer> mapping : list) {
			System.out.println(mapping.getValue() + ", " + mapping.getKey());
		}
	}
	
	public static void main(String[] args) throws Exception {
		WordCount wc = new WordCount();
		wc.downLoadFromUrl();
		wc.sortAndPrintHashMap();
	}
}
