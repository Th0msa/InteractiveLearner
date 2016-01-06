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
	private int noSubDirectories = 10;
	private NaiveBayes naivebayes;
	private NaiveBayes tempbayes;
	private Corpus corpus;
	private Corpus tempcorpus;
	private boolean isCatScreen = true;
	private boolean isInitialScreen = true;
	private boolean isReady = false;
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
	private int count = 1;
	private Document previousSegment;
	
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
	
	public void update() { 
		System.out.println("update ");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    container = frame.getContentPane();
	    textarea.setFont(font2);
	    panel.add(sbutton);
	    panel.add(cbutton);
	    panel.add(ybutton);
	    panel.add(nbutton);
	    if (count > noSubDirectories) {
	    	isReady = true;
	    	categorizeScreen();
	    } else if (isInitialScreen) {
		    initialScreen();
	    } else if (isCatScreen) {
	        categorizeScreen();
	    } else {
	        checkScreen();
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
				tempbayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail", naivebayes.getPrio(),
						naivebayes.getCondprob(), naivebayes.getVoc());
				tempbayes.updateTrainer("../InteractiveLearner/TestFiles/mail/corpus/part" + count);
				isInitialScreen = false;
				update();
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
				//Document d = new Document(" ", false, true, naivebayes);
				Document d = new Document(" ", false, true, tempbayes);
				d.updateContents(textarea.getText());
				d.updateListContents(d.getContents());
				previousSegment = d;
				//textarea.setText("The text was classified as: " + naivebayes.ApplyMultinomialNaiveBayes(d) + "\n");
				textarea.setText("The text was classified as: " + tempbayes.ApplyMultinomialNaiveBayes(d) + "\n");
				//let the learner categorize
				isCatScreen = false;
				update();
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
				//verander corpus naar onze 20 documenten
				naivebayes.getCorpus().putDocument(previousSegment);
				if (!isReady) {
					naivebayes.updateTrainer("../InteractiveLearner/TestFiles/mail/corpus/part" + count);
					count++;
					tempbayes.updateTrainer("../InteractiveLearner/TestFiles/mail/corpus/part" + count);
				}
				isCatScreen = true;
				update();
			}
        });
        
        nbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isReady) {
					count++;
					tempbayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/mail", naivebayes.getPrio(),
							naivebayes.getCondprob(), naivebayes.getVoc());
					tempbayes.updateTrainer("../InteractiveLearner/TestFiles/mail/corpus/part" + count);
				}
				isCatScreen = true;
				update();
			}
        });
	}
	
	public void addNaiveBayes(NaiveBayes nb) {
		this.naivebayes = nb;
	}
}
