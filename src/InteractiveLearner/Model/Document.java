package InteractiveLearner.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Document {
	private BufferedReader in;
	private String filePath;
	private String contents;
	private String documentClass;
	private List<String> listContents;
	
	public Document(String fileName, boolean isTrainingData) {
		this.filePath = fileName;
		this.listContents = new ArrayList<String>();
		this.contents = "";
		if (isTrainingData) {
			String[] temp = fileName.split("\\\\");
			this.documentClass = temp[temp.length - 2];
		} else {
			//TODO voor het interactive learning gedeelte, niet de classifier
			this.documentClass = "";
		}
		this.readFile(filePath);
		this.updateListContents(contents);
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public String getDocumentClass() {
		return this.documentClass;
	}
	
	public List<String> getListContents() {
		return this.listContents;
	}
	
	public void updateListContents(String s) {
		String[] words = s.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			listContents.add(words[i]);
		}
	}
	
	public void readFile(String filename) {
		String l;
		try {
			in = new BufferedReader(new FileReader(filename));
			while ((l = in.readLine()) != null) {
				//this.contents = l;
				this.contents = contents.concat(l);
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
