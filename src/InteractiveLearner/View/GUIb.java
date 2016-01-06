package InteractiveLearner.View;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import InteractiveLearner.Controller.NaiveBayes;
import InteractiveLearner.Model.Document;

public class GUIb {
	private NaiveBayes naivebayes;
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
	
	public GUIb() {
		font1 = new Font("Arial", Font.BOLD, 16);
		font2 = new Font("Arial", Font.PLAIN, 14);
		frame = new JFrame();
		panel = new JPanel();
		sbutton = new JButton("Start training");
		sbutton.setVisible(false);
		sbutton.setFont(font1);
		cbutton = new JButton("Categorize");
		cbutton.setVisible(false);
		cbutton.setFont(font1);
		ybutton = new JButton("Yes");
		ybutton.setVisible(false);
		ybutton.setFont(font1);
		nbutton = new JButton("No");
		nbutton.setVisible(false);
		nbutton.setFont(font1);
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
		frame.setLocation(SCREEN.width/2-frame.getSize().width/2, SCREEN.height/2-frame.getSize().height/2);
		createHome();
	}
	
	public void addNaiveBayes(NaiveBayes bayes) {
		this.naivebayes	= bayes;	
	}
	
	public void createHome() {
		sbutton.setVisible(true);
		container.add(panel);
		frame.pack();
		frame.setVisible(true);
		sbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sbutton.removeActionListener(this);
				createInputScreen();
			}
		});
	}
	
	public void createInputScreen() {
		sbutton.setVisible(false);
		ybutton.setVisible(false);
		nbutton.setVisible(false);
		cbutton.setVisible(true);
		textarea.setText("");
		textarea.setEditable(true);
        container.add(textarea, BorderLayout.CENTER);
        textarea.requestFocus();
		container.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		cbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Document d = new Document(" ", false, true, naivebayes);
				System.out.println(naivebayes);
				d.updateContents(textarea.getText());
				d.updateListContents(d.getContents());
				String previousClass = naivebayes.ApplyMultinomialNaiveBayes(d);
				textarea.setText("The text was classified as: " + previousClass + "\n");
				
				cbutton.removeActionListener(this);
				createCheckScreen(previousClass, d);
			}
		});
	}
	
	public void createCheckScreen(String cls, Document d) {
		cbutton.setVisible(false);
		ybutton.setVisible(true);
		nbutton.setVisible(true);
		textarea.setEditable(false);
		container.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		ybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				naivebayes.getCorpus().putDocument(d, cls);
				naivebayes.TrainMultinomialNaiveBayes();
				
				nbutton.removeActionListener(nbutton.getActionListeners()[0]);
				ybutton.removeActionListener(this);
				createInputScreen();
			}
		});
		nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				ybutton.removeActionListener(ybutton.getActionListeners()[0]);
				nbutton.removeActionListener(this);
				createInputScreen();
			}
		});
	}
}
