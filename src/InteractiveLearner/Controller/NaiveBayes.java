package InteractiveLearner.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import InteractiveLearner.Model.Corpus;
import InteractiveLearner.Model.Document;
import InteractiveLearner.Model.Tuple;
import InteractiveLearner.Model.Vocabulary;

public class NaiveBayes {
	
	private List<String> categories;
	private Map<String, Double> priorClassProbabilities; //prior in psuedo
	private Map<Tuple<String, String>, Double> condProbPerClassPerWord; //condprob in psuedo
	private Vocabulary currentVocab; //V in psuedo
	private Corpus crps;
	private static final double MINIMALPROB = 0.005; 
	
	public NaiveBayes(String corpusFolderPath) {
		this.crps = new Corpus(corpusFolderPath);
		this.categories = this.crps.getCategories();
		this.priorClassProbabilities = new HashMap<String, Double>();
		this.condProbPerClassPerWord = new HashMap<Tuple<String, String>, Double>();
		this.currentVocab = new Vocabulary();
	}
	
	/**
	 * trains the classifier with a corpus of documents and a list of possible categories
	 */
	public void TrainMultinomialNaiveBayes() {
		
		//extract the whole vocabulary from the corpus
		currentVocab = crps.extractVocabulary();
		
		//count the total number of documents in the corpus
		int noDocsTotal = crps.countNumberOfDocs();
		
		//for every class...
		for (String category: categories) {
			//count the number of documents in the corpus for a certain class
			double noDocsClass = crps.countDocsInClass(category);
			
			//determine what the priority of the class is
			double priorProbOfCategory = noDocsClass / noDocsTotal;
			this.priorClassProbabilities.put(category, priorProbOfCategory);
			
			//concatenate all the text of the documents in the corpus for a certain class
			List<String> textDocsOfClass = crps.ConcatenateAllTextsOfDocsInClass(category);
			
			boolean denomCalculated = false;
			int denominator = -1;
			
			if(!denomCalculated) {
				denominator = calcDenominator(textDocsOfClass);
				denomCalculated = true;
			}
			//for every token t (word) in the vocabulary...
			for (String t : currentVocab.getWords()) {
				//amount of occurences of a certain term in the text given a certain class
				double occurenceCountT = currentVocab.CountTokensOfTerm(textDocsOfClass, t);
				//calculate conditional probability
				double condProb = (occurenceCountT + 1) / denominator;
				//store the combination of class and token with conditional probability
				Tuple<String, String> classTokenCombi = new Tuple<String, String>(category, t);
				
				//if (condProb < NaiveBayes.MINIMALPROB) {
					condProbPerClassPerWord.put(classTokenCombi, condProb);
				//}
			}
		}
	}
	
	/**
	 * determines which class a given document belongs to
	 * @param document the document that will be classified
	 * @return the determined class of the document
	 */
	public String ApplyMultinomialNaiveBayes(Document document) {
		String possibleClass = "";
		List<String> documentVocab = currentVocab.extractTokensInVocFromDoc(document);
		Map<String, Double> classTotalScores = new HashMap<String, Double>();
		
		//for every class...
		for (String cls : categories) {
			//retrieve the prior probability for the class 
			double classScore = this.priorClassProbabilities.get(cls);
			
			//for every token in the documents vocabulary
			for (String t : documentVocab) {
				classScore += this.condProbPerClassPerWord.get(
						new Tuple<String, String>(cls, t));
			}
			
			classTotalScores.put(cls, classScore);
		}
		possibleClass = this.calculateMaxScore(classTotalScores);
		System.out.println(classTotalScores);
		return possibleClass;
	}
	
	/**
	 * calculate the denominator for the conditional probability fraction
	 * for a given vocabulary and all text from a certain class
	 * @param vocab the vocabulary
	 * @param textDocsOfClass all text that has to do with a certain class
	 * @return the denominator
	 */
	private int calcDenominator(List<String> textDocsOfClass) {
		int denominator = 0;
		
		for (String tApo : currentVocab.getWords()) {
			int occurenceCountTApo = currentVocab.CountTokensOfTerm(textDocsOfClass, tApo); 
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
		double currentMax = 0;
		for (String cls : scoreMap.keySet()) {
			if (scoreMap.get(cls) > currentMax) {
				currentMax = scoreMap.get(cls);
				maxScoreClass = cls;
			}
		}
		
		return maxScoreClass;
	}
	
	public void updateCorpus(Corpus c) {
		this.crps = c;
	}
	
	/**
	public static void main(String[] args) {
		System.out.println("~~~~~~MAIL~~~~~~");
		NaiveBayes bayesMail = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail");
		bayesMail.TrainMultinomialNaiveBayes();
		try {
			Files.walk(Paths.get("../InteractiveLearner/TestFiles/mail")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
			    	Document d = new Document(filePath.toString(), false, false);
					String[] temp = filePath.toString().split("\\\\");
			    	System.out.println("Class " + temp[temp.length - 2] + " is classified as: " + bayesMail.ApplyMultinomialNaiveBayes(d) + "\n");
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("~~~~~~BLOGS~~~~~~");
		NaiveBayes bayesBlogs = new NaiveBayes("../InteractiveLearner/TrainingFiles/blogs");
		bayesBlogs.TrainMultinomialNaiveBayes();
		try {
			Files.walk(Paths.get("../InteractiveLearner/TestFiles/blogs")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
			    	Document d = new Document(filePath.toString(), false, false);
					String[] temp = filePath.toString().split("\\\\");
			    	System.out.println("Class " + temp[temp.length - 2] + " is classified as: " + bayesBlogs.ApplyMultinomialNaiveBayes(d) + "\n");
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/

}
