package InteractiveLearner.Controller;

import InteractiveLearner.Model.Corpus;
import InteractiveLearner.View.GUI;

public class Controller {
	private Thread thread;
	private NaiveBayes bayes;
	private boolean running = false;
	private GUI gui;
	
	public Controller() {
		gui = new GUI();
	}
	
	public synchronized void start() {
        System.out.println("running");
        bayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail");
        System.out.println("training");
        bayes.TrainMultinomialNaiveBayes();
        System.out.println("trained");
        gui.addNaiveBayes(bayes);
        gui.update();
    }
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
}
