package InteractiveLearner.Model;

import java.io.*;
import java.io.IOException;

public class Document {
	private BufferedReader in;
	private String filePath;
	private String contents;
	
	public Document(String fileName) {
		this.filePath = fileName;
		this.contents = "";
		this.readFile(filePath); 
	}
	
	public String getContents() {
		return this.contents;
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
