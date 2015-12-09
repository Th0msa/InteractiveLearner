package InteractiveLearner.Model;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Document {
	private BufferedReader in;
	private String filePath;
	private String contents;
	private List<String> listContents;
	
	public Document(String fileName) {
		this.filePath = fileName;
		this.contents = "";
		this.listContents = new ArrayList<String>();
		this.readFile(filePath); 
	}
	
	public String getContents() {
		return this.contents;
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
		this.updateListContents(contents);
	}
}
