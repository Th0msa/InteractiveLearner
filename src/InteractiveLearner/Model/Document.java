package InteractiveLearner.Model;

import java.io.*;
import java.io.IOException;

public class Document {
	private BufferedReader in;
	public void readFile(String filename) {
		String l;
		try {
			in = new BufferedReader(new FileReader(filename));
			while ((l = in.readLine()) != null) {
				//TODO waar zetten we dit in?
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
