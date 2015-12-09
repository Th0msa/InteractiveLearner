package Learner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import InteractiveLearner.Model.Document;

/**
 * this is a file containing all the documents
 * this file can be initially generated or replaced by a file input stream
 * @author wessel
 *
 */
public class Corpus {
	
	private List<Document> allDocuments;
	
	public Corpus(String path) {
		this.allDocuments = new ArrayList<Document>();
		
		try {
			Files.walk(Paths.get(path)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        allDocuments.add(new Document(filePath.toString()));
			    }
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Document> getDocuments() {
		return this.allDocuments;
	}
	
	public static Vocabulary extractVocabulary(Corpus crps) {
		//TODO extracts all the words from a corpus and adds them in a library
		return null;
	}
	
	public static int countNumberOfDocs(Corpus crps) {
		return 0;
	}
	
	public static int countDocsInClass(Corpus crps, String cls) {
		return 0;
	}
	
	public static String ConcatenateAllTextsOfDocsInClass(Corpus crps, String cls) {
		return null;
	}
	
//	public static void main(String[] args) {
//		Corpus c =  new Corpus("C:/Users/wessel/Documents/School/2015-2016"
//				+ "/Module 6 - Intelligent Interaction Design/Artificial Intelligence"
//				+ "/Interactive Learner/corpus/part1");
//		int i = 0;
//		for (Document d : c.getDocuments()) {
//			i++;
//			System.out.println(i + " " + d.getContents());
//		}
//	}
}
