package InteractiveLearner.View;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;

import InteractiveLearner.Controller.Controller;
import InteractiveLearner.Controller.NaiveBayes;
import InteractiveLearner.Model.Document;

public class GUIb {
	private NaiveBayes naivebayes;
	private final static Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame frame;
	private Container container;
	private JPanel panel;
	private JSplitButton sbutton;
	private JButton cbutton;
	private JButton ybutton;
	private JButton tbutton;
	private JButton bbutton;
	private JSplitButton nbutton;
	private JTextArea textarea;
	private Font font1;
	private Font font2;
	private Controller controller;
	private String currentClass;
	private JScrollPane scroll;
	
	public GUIb() {
		//test
		currentClass = "";
		tbutton = new JButton("Test");
		tbutton.setVisible(false);
		tbutton.setFont(new Font("Arial", Font.BOLD, 16));
		//IL
		font1 = new Font("Arial", Font.BOLD, 16);
		font2 = new Font("Arial", Font.PLAIN, 14);
		frame = new JFrame();
		panel = new JPanel();
		sbutton = new JSplitButton("Start training");
		sbutton.setVisible(false);
		sbutton.setFont(font1);
		cbutton = new JButton("Categorize");
		cbutton.setVisible(false);
		cbutton.setFont(font1);
		ybutton = new JButton("Yes");
		ybutton.setVisible(false);
		ybutton.setFont(font1);
		nbutton = new JSplitButton("No");
		nbutton.setVisible(false);
		nbutton.setFont(font1);
		bbutton = new JButton("Return");
		bbutton.setVisible(false);
		bbutton.setFont(font1);
		textarea = new JTextArea();
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    container = frame.getContentPane();
	    textarea.setFont(font2);
	    panel.add(sbutton);
	    panel.add(cbutton);
	    panel.add(ybutton);
	    panel.add(nbutton);
	    panel.add(tbutton);
	    panel.add(bbutton);
		frame.setLocation(SCREEN.width/2-frame.getSize().width/2, SCREEN.height/2-frame.getSize().height/2);
		scroll = new JScrollPane(textarea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		createHome();
	}
	
	public void addNaiveBayes(NaiveBayes bayes) {
		this.naivebayes	= bayes;	
	}
	
	public JTextArea getTextArea() {
		return textarea;
	}
	 
	public void createHome() {
		sbutton.setVisible(true);
		container.add(panel);
		frame.pack();
		frame.setVisible(true);
		JPopupMenu popupHome = new JPopupMenu();
		//determine all classes:
		try {
			Files.walk(Paths.get("../InteractiveLearner/TrainingFiles/"), 1).forEach(filePath -> {
				String line = filePath.toString();
					String betterLine = line.substring(35);
					String cls = betterLine.replaceAll("\\\\", "");
					if (!cls.equals("")) {
						popupHome.add(new JMenuItem(new AbstractAction(cls) {
				            public void actionPerformed(ActionEvent e) {
				            	currentClass = cls;
				            	controller.startTraining("../InteractiveLearner/TrainingFiles/" + cls);
								createInputScreen();
				            }
				        }));
					}
				
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sbutton.setPopupMenu(popupHome);
	}
	
	public void createInputScreen() {
		bbutton.setVisible(false);
		sbutton.setVisible(false);
		ybutton.setVisible(false);
		nbutton.setVisible(false);
		cbutton.setVisible(true);
		tbutton.setVisible(true);
		textarea.setText("Type own input to categorize here or click on Test \n" +"to test files in the TestFiles directory");
		textarea.setEditable(true);
        container.add(scroll, BorderLayout.CENTER);
        textarea.requestFocus();
		container.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		cbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Document d = new Document(" ", false, true, naivebayes);
				d.updateContents(textarea.getText());
				d.updateListContents(d.getContents());
				String previousClass = naivebayes.ApplyMultinomialNaiveBayes(d);
				textarea.setText("The text was classified as: " + previousClass + "\n");
				
				tbutton.removeActionListener(tbutton.getActionListeners()[0]);
				cbutton.removeActionListener(this);
				createCheckScreen(previousClass, d);
			}
		});
		
		tbutton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				controller.testDocument("../InteractiveLearner/TestFiles/" + currentClass, naivebayes);
				cbutton.removeActionListener(cbutton.getActionListeners()[0]);
				tbutton.removeActionListener(this);	
				createCheckScreen();
			}
		});
	}
	
	public void createCheckScreen() {
		cbutton.setVisible(false);
		tbutton.setVisible(false);
		bbutton.setVisible(true);
		textarea.setEditable(false);
        container.add(scroll, BorderLayout.CENTER);
        container.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		bbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bbutton.removeActionListener(this);
				createInputScreen();
			}
		});
	}
	
	public void createCheckScreen(String cls, Document d) {
		JPopupMenu popupCheck = new JPopupMenu();
        for (int i = 0; i < naivebayes.getCorpus().getCategories().size(); i++) {
        	String temp = naivebayes.getCorpus().getCategories().get(i);
	        popupCheck.add(new JMenuItem(new AbstractAction(temp) {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println(temp);
	            	naivebayes.getCorpus().putDocument(d, temp);
					naivebayes.TrainMultinomialNaiveBayes();
					if (ybutton.getActionListeners().length > 1) {
						ybutton.removeActionListener(ybutton.getActionListeners()[0]);
					}
					createInputScreen();
	            }
	        }));
        }
        nbutton.setPopupMenu(popupCheck);
		cbutton.setVisible(false);
		tbutton.setVisible(false);
		ybutton.setVisible(true);
		nbutton.setVisible(true);
		textarea.setEditable(false);
	    container.add(scroll, BorderLayout.CENTER);
		container.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		ybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				naivebayes.getCorpus().putDocument(d, cls);
				naivebayes.TrainMultinomialNaiveBayes();
				if (nbutton.getActionListeners().length > 1) {
					nbutton.removeActionListener(nbutton.getActionListeners()[0]);
				}
				ybutton.removeActionListener(this);
				createInputScreen();
			}
		});
	}

	public void addController(Controller controller) {
		this.controller = controller;
	}
}
