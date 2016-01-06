package InteractiveLearner.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import InteractiveLearner.Controller.NaiveBayes;
import InteractiveLearner.Model.Document;

public class TUI {

	private NaiveBayes naivebayes;

	public void addNaiveBayes(NaiveBayes bayes) {
		this.naivebayes	= bayes;	
	}

	public void create() {
		for (int i = 0; i < 3; i++) {
			Document d = new Document(" ", false, true, naivebayes);
			d.updateContents("hello there");
			d.updateListContents(d.getContents());
			System.out.println("created document with hello there as text");
			String previousClass = naivebayes.ApplyMultinomialNaiveBayes(d);
			System.out.println("The text was classified as: " + previousClass + "\n");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				if(in.readLine().equals("yes")) {
					naivebayes.getCorpus().putDocument(d, previousClass);
			        System.out.println("training");
			        naivebayes.TrainMultinomialNaiveBayes();
			        System.out.println("trained");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
