package InteractiveLearner.Model;

import java.util.ArrayList;
import java.util.List;
/**
 * a looooong list of words without multiple occurances of a single word
 * @author wessel
 *
 */
public class Vocabulary {
	private List<String> vocabWords;
	
	public Vocabulary() {
		vocabWords = new ArrayList<String>();
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
		String[] split = document.getContents().split("\\s+");
		for (int i = 0; i < split.length; i++) {
			if (!vocabWords.contains(split[i])) {
				this.updateVocab(split[i]);
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
