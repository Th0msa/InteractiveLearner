package InteractiveLearner.Controller;

import InteractiveLearner.Model.Corpus;
import InteractiveLearner.View.GUI;
import InteractiveLearner.View.GUIb;
import InteractiveLearner.View.TUI;

public class Controller {
	private NaiveBayes bayes;
	private GUIb gui;
	
	public Controller() {
		gui = new GUIb();
        System.out.println("running");
        bayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail");
	    gui.addNaiveBayes(bayes);
        System.out.println("training");
        bayes.TrainMultinomialNaiveBayes();
        System.out.println("trained");
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
}
