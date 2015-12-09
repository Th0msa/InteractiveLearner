package Learner;

import java.util.ArrayList;
import java.util.List;

import InteractiveLearner.Model.Document;
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
	
	public int CountTokensOfTerm(String textC, String word) {
		int count = 0;
		String[] split = textC.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			if (split[i].equals(word)) {
				count++;
			}
		}
		System.out.println("No of " + word + " in the input is : " + count);
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
	
	public static void main(String[] args) {
		Vocabulary v = new Vocabulary();
		v.updateVocab("Hallo");
		v.updateVocab("ben");
		v.CountTokensOfTerm("Hallo ik ben ik en ik ben en ik", "en");
	}
}
