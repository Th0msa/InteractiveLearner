package InteractiveLearner.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
/**
 * a looooong list of words without multiple occurances of a single word
 * @author wessel
 *
 */
public class Vocabulary {
	private List<String> vocabWords;
	private List<String> standardTokens;
	
	public Vocabulary() {
		vocabWords = new ArrayList<String>();
		
		//create stopword list
		standardTokens = new ArrayList<String>();
		String line = null;
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("stopWords.txt"));
	        while((line = reader.readLine()) != null){
	            standardTokens.add(line);
	        }
	        reader.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<String> getWords() {
		return vocabWords;
	}
	
	public void updateVocab(String s) {
		vocabWords.add(s);
	}
	
	public int CountTokensOfTerm(List<String> textC, String word) {
		int count = 0;
		for (String t : textC) {
			if (t.equals(word)) {
				count++;
			}
		}
		return count;
	}
	
	public void extractTokensFromDoc(Document document) {
		String normalized = document.getContents().replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
		String[] split = normalized.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			//filter on stopwords
			if (!standardTokens.contains(split[i])) {
				//filter on duplicants
				if (!vocabWords.contains(split[i])) {
					this.updateVocab(split[i]);
				}
			}
		}
	}
	
	public List<String> extractTokensInVocFromDoc(Document document) {
		List<String> list = new ArrayList<String>();
		String[] split = document.getContents().split("\\s+");
		for (int i = 0; i < split.length; i++) {
			if (!list.contains(split[i])) {
				if (vocabWords.contains(split[i])) {	
					list.add(split[i]);
				}
			}
		}
		return list;
	}

}
