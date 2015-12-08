package Learner;

import java.util.List;
/**
 * a looooong list of words without multiple occurances of a single word
 * @author wessel
 *
 */
public class Vocabulary {
	private List<String> vocabWords;
	
	public Vocabulary() {
		
	}
	public List<String> getWords() {
		return vocabWords;
	}
	
	public void updateVocab(String s) {
		vocabWords.add(s);
	}
	
	public int CountTokensOfTerm(String textC, String word) {
		//TODO telt nu ook subwoorden van woorden (dus 'en' in 'ben' wordt ook geteld)
		int count = 0;
		int index = textC.indexOf(word);
		while (index != -1) {
		    count++;
		    textC = textC.substring(index + 1);
		    index = textC.indexOf(word);
		}
		System.out.println("No of " + word + " in the input is : " + count);
		return count;
	}
	
	public Vocabulary extractTokensFromDoc(String document) {
		Vocabulary newVocab = new Vocabulary();
		int index = 0;
		String[] split = document.split("\\s+");
		for(int i = 0; i < split.length; i++) {
			for (int j = 0; j < vocabWords.size(); i++) {
				if (split[i].equals(vocabWords.get(j))) {
					newVocab.updateVocab(split[i]);
				}
			}
		}
		//TODO waarom doen we dit?
		return newVocab;
	}
	
	public static void main(String[] args) {
		Vocabulary v = new Vocabulary();
		v.CountTokensOfTerm("Hallo ik ben ik en ik ben en ik", "en");
	}
}
