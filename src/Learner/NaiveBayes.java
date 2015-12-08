package Learner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {
	
	private List<String> categories;
	private Map<String, Double> priorClassProbabilities;
	private Map<Tuple<String, String>, Double> condProbPerClassPerWord;
	private Vocabulary currentVocab;
	
	public NaiveBayes(List<String> categories) {
		this.categories = categories;
		this.priorClassProbabilities = new HashMap<String, Double>();
		this.currentVocab = new Vocabulary();
	}
	
	/**
	 * trains the classifier with a corpus of documents and a list of possible categories
	 * @param crps labeled corpus of documents such that every document in the corpus is assigned one of the categories 
	 */
	public void TrainMultinomialNaiveBayes(Corpus crps) {
		//extract the whole vocabulary from the corpus
		currentVocab  = Corpus.extractVocabulary(crps);
		
		//count the total number of documents in the corpus
		int noDocsTotal = Corpus.countNumberOfDocs(crps);
		
		//for every class...
		for (String cls: categories) {
			//count the number of documents in the corpus for a certain class
			double noDocsClass = Corpus.countDocsInClass(crps, cls);
			
			//determine what the priority of the class is
			double priorProbOfClass = noDocsClass / noDocsTotal;
			this.priorClassProbabilities.put(cls, priorProbOfClass);
			
			//concatenate all the text of the documents in the corpus for a certain class
			String textDocsOfClass = Corpus.ConcatenateAllTextsOfDocsInClass(crps, cls);
			
			//for every token t (word) in the vocabulary...
			List<String> vocabWords = currentVocab.getWords();
			for (String t : vocabWords) {
				//amount of occurences of a certain term in the text given a certain class
				int occurenceCountT = currentVocab.CountTokensOfTerm(textDocsOfClass, t);
				
				//for every token t' (denoted by tApo) (word) in the vocabulary...
				for (String tApo : vocabWords) {
					//calculate conditional probability
					//TODO the calculation of the denominator should only be once per class
					double condProb = (occurenceCountT + 1) / calcDenominator(vocabWords, textDocsOfClass);
					
					//store the combination of class and token with conditional probability
					Tuple<String, String> classTokenCombi = new Tuple<String, String>(cls, tApo);
					this.condProbPerClassPerWord.put(classTokenCombi, condProb);
				}
			}
		}
	}
	
	/**
	 * determines which class a given document belongs to
	 * @param document the document that will be classified
	 * @return the determined class of the document
	 */
	public String ApplyMultinomialNaiveBayes(String document) {
		String possibleClass = "";
		Vocabulary documentVocab = currentVocab.extractTokensFromDoc(document);
		List<String> documentVocabWords = currentVocab.getWords();
		Map<String, Double> classTotalScores = new HashMap<String, Double>();
		
		//for every class...
		for (String cls : categories) {
			//retrieve the prior probability for the class 
			double classScore = Math.log(this.priorClassProbabilities.get(cls));
			
			//for every token in the documents vocabulary
			for (String t : documentVocabWords) {
				classScore += Math.log(this.condProbPerClassPerWord.get(
						new Tuple<String, String>(cls, t)));
			}
			
			classTotalScores.put(cls, classScore);
		}
		
		possibleClass = this.calculateMaxScore(classTotalScores);
		return possibleClass;
	}
	
	/**
	 * calculate the denominator for the conditional probability fraction
	 * for a given vocabulary and all text from a certain class
	 * @param vocab the vocabulary
	 * @param textDocsOfClass all text that has to do with a certain class
	 * @return the denominator
	 */
	private int calcDenominator(List<String> vocabWords, String textDocsOfClass) {
		int denominator = 0;
		
		for (String t : vocabWords) {
			int occurenceCountTApo = currentVocab.CountTokensOfTerm(textDocsOfClass, t); 
			denominator += (occurenceCountTApo + 1);
		}
		
		return denominator;
	}
	
	/**
	 * calculates which class has the highest score given a score map
	 * @param scoreMap map containing all the scores
	 * @return the class with the highest score
	 */
	private String calculateMaxScore(Map<String, Double> scoreMap) {
		String maxScoreClass = "";
		double currentMax= 0;
		
		for (String cls : scoreMap.keySet()) {
			if (scoreMap.get(cls) > currentMax) {
				maxScoreClass = cls;
			}
		}
		
		return maxScoreClass;
	}

}