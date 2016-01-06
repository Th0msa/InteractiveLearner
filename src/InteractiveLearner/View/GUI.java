package InteractiveLearner.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import InteractiveLearner.Controller.NaiveBayes;
import InteractiveLearner.Model.Corpus;
import InteractiveLearner.Model.Document;

public class GUI {
	private NaiveBayes naivebayes;
	private NaiveBayes tempbayes;
	private Corpus corpus;
	private Corpus tempcorpus;
	private boolean isCatScreen = true;
	private boolean isInitialScreen = true;
	private final static Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame frame;
	private Container container;
	private JPanel panel;
	private JButton sbutton;
	private JButton cbutton;
	private JButton ybutton;
	private JButton nbutton;
	private JTextArea textarea;
	private Font font1;
	private Font font2;
	private int count = 0;
	private Document document;
	private String previousClass = "";
	
	public GUI() {
		font1 = new Font("Arial", Font.BOLD, 16);
		font2 = new Font("Arial", Font.PLAIN, 14);
		frame = new JFrame();
		panel = new JPanel();
		sbutton = new JButton("Start training");
		sbutton.setVisible(false);
		cbutton = new JButton("Categorize");
		cbutton.setVisible(false);
		ybutton = new JButton("Yes");
		ybutton.setVisible(false);
		nbutton = new JButton("No");
		nbutton.setVisible(false);
		textarea = new JTextArea();
	}
	
	public void create() { 
		System.out.println("update");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    container = frame.getContentPane();
	    textarea.setFont(font2);
	    panel.add(sbutton);
	    panel.add(cbutton);
	    panel.add(ybutton);
	    panel.add(nbutton);
	    if (isInitialScreen) {
		    initialScreen();
	    } else {
	        if (isCatScreen) {
	        	categorizeScreen();
	        } else {
	        	checkScreen();
	        }
	    }
		frame.pack();
		frame.setLocation(SCREEN.width/2-frame.getSize().width/2, SCREEN.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	public void initialScreen() {
		container.removeAll();
		sbutton.setFont(font1);
		sbutton.setVisible(true);
		container.add(panel);
        sbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//verander corpus naar onze documenten en train ze daarmee...
				//tempbayes = naivebayes;
				//tempcorpus = corpus;
				//tempbayes.updateTrainer(tempcorpus, "../InteractiveLearner/TestFiles/corpus", count+20);
				isInitialScreen = false;
				create();
			}
        });
	}
	
	public void categorizeScreen() {
		sbutton.setVisible(false);
		ybutton.setVisible(false);
		nbutton.setVisible(false);
		textarea.setText("");
		textarea.setEditable(true);
		container.removeAll();
        container.add(textarea, BorderLayout.CENTER);
        textarea.requestFocus();
        cbutton.setFont(font1);
        cbutton.setVisible(true);
        container.add(panel, BorderLayout.SOUTH);
        cbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCatScreen = false;
				Document d = new Document(" ", false, true, naivebayes);
				d.updateContents(textarea.getText());
				d.updateListContents(d.getContents());
				document = d;
				previousClass = naivebayes.ApplyMultinomialNaiveBayes(d);
				textarea.setText("The text was classified as: " + previousClass + "\n");
				create();
				//let the learner categorize
			}
        });
	}
	
	public void checkScreen() {
		cbutton.setVisible(false);
		container.removeAll();
		textarea.setEditable(false);
        container.add(textarea, BorderLayout.CENTER);
        ybutton.setFont(font1);
        nbutton.setFont(font1);
        ybutton.setVisible(true);
        nbutton.setVisible(true);
        container.add(panel, BorderLayout.SOUTH);
        
        ybutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCatScreen = true;
				//verander corpus naar onze 20 documenten
				//naivebayes.updateTrainer(corpus, "../InteractiveLearner/TestFiles/mail", count);
				naivebayes.getCorpus().putDocument(document, previousClass);
				naivebayes.TrainMultinomialNaiveBayes();
				create();
			}
        });
        
        nbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCatScreen = true;
				create();
			}
        });
	}
	
	public void addNaiveBayes(NaiveBayes nb) {
		this.naivebayes = nb;
	}
	
	public void addCorpus(Corpus crps) {
		this.corpus = crps;
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.create();
	}
}
