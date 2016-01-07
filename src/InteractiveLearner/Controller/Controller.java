package InteractiveLearner.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import InteractiveLearner.Model.Document;
import InteractiveLearner.View.GUIb;

public class Controller {
	private NaiveBayes bayes;
	private GUIb gui;
	
	public Controller(String trainingFilePath) {
		gui = new GUIb();
        System.out.println("running");
	    gui.addController(this);
	}
	
	public void startTraining(String trainingFilePath) {
        System.out.println("training");
        bayes = new NaiveBayes(trainingFilePath);
	    gui.addNaiveBayes(bayes);
        bayes.TrainMultinomialNaiveBayes();
        System.out.println("trained");
	}
	
	public void testDocument(String testFilePath, NaiveBayes bayes) {
		try {
			List<String> results = new ArrayList<String>();
			Files.walk(Paths.get(testFilePath)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					Document d = new Document(filePath.toString(), false, true, bayes);
					d.readFile(filePath.toString());
					d.updateListContents(d.getContents());
					
					String test = "File " + filePath.toString().substring(31) 
							+ " classified as " + bayes.ApplyMultinomialNaiveBayes(d) + "\n";
					results.add(test);
			
			    }
			});
			String display = "";
			for (String test : results) {
				display = display.concat(test);
			}
			gui.getTextArea().setText(display);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//Controller mailController = new Controller("../InteractiveLearner/TrainingFiles/mail");
		Controller blogsController = new Controller("../InteractiveLearner/TrainingFiles/blogs");
	}
}
