package InteractiveLearner.Controller;

import InteractiveLearner.Model.Corpus;
import InteractiveLearner.View.GUI;

public class Controller implements Runnable{
	private Thread thread;
	private NaiveBayes bayes;
	private boolean running = false;
	private GUI gui;
	
	public Controller() {
		gui = new GUI();
		this.thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while (running) {
		}
		this.stop();
	}
	
	public synchronized void start() {
        if(!running) {
            running = true;
            System.out.println("running");
            bayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail");
            System.out.println("training");
            bayes.TrainMultinomialNaiveBayes();
            System.out.println("trained");
            gui.addNaiveBayes(bayes);
            gui.addCorpus(bayes.getCorpus());
            gui.update();
            //this.run();
        }
    }
	
	public synchronized void stop() {
        if(running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
}
