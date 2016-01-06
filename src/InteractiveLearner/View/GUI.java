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
	
	public GUI() {
		font1 = new Font("Arial", Font.BOLD, 16);
		font2 = new Font("Arial", Font.PLAIN, 14);
		frame = new JFrame();
		panel = new JPanel();
		sbutton = new JButton("Start training");
		cbutton = new JButton("Categorize");
		ybutton = new JButton("Yes");
		nbutton = new JButton("No");
		textarea = new JTextArea();
	}
	
	public void update() { 
		System.out.println("update");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    container = frame.getContentPane();
	    textarea.setFont(font2);
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
		panel.removeAll();
		sbutton.setFont(font1);
		panel.add(sbutton);
		container.add(panel);
        sbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//verander corpus naar onze documenten en train ze daarmee...
				tempbayes = naivebayes;
				tempcorpus = corpus;
				tempbayes.updateTrainer(tempcorpus, "../InteractiveLearner/TestFiles/mail", count+20);
				isInitialScreen = false;
				update();
			}
        });
	}
	
	public void categorizeScreen() {
		textarea.setEditable(true);
		container.removeAll();
		panel.removeAll();
        container.add(textarea, BorderLayout.CENTER);
        cbutton.setFont(font1);
        panel.add(cbutton);
        container.add(panel, BorderLayout.SOUTH);
        cbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Document d = new Document(" ", false, true, naivebayes);
				d.updateContents(textarea.getText());
				d.updateListContents(d.getContents());
				isCatScreen = false;
				update();
				textarea.setText("The text was classified as: " + tempbayes.ApplyMultinomialNaiveBayes(d) + "\n");
				//let the learner categorize
			}
        });
	}
	
	public void checkScreen() {
		container.removeAll();
		textarea.setEditable(false);
		panel.removeAll();
        container.add(textarea, BorderLayout.CENTER);
        ybutton.setFont(font1);
        nbutton.setFont(font1);
        panel.add(ybutton);
        panel.add(nbutton);
        container.add(panel, BorderLayout.SOUTH);
        
        ybutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//verander corpus naar onze 20 documenten
				naivebayes.updateTrainer(corpus, "../InteractiveLearner/TestFiles/mail", count);
				isCatScreen = true;
				update();
			}
        });
        
        nbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCatScreen = true;
				update();
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
		gui.update();
	}
}
