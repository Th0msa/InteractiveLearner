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
	private Vocabulary vocabulary;
	
	public Corpus(String path) {
		vocabulary = new Vocabulary();
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
	
	public Vocabulary extractVocabulary() {
		//TODO extracts all the words from a corpus and adds them in a vocabulary
		for(Document d : this.getDocuments()) {
			vocabulary.extractTokensFromDoc(d);
		}
		return vocabulary;
	}
	
	public int countNumberOfDocs() {
		return 0;
	}
	
	public int countDocsInClass(String cls) {
		return 0;
	}
	
	public String ConcatenateAllTextsOfDocsInClass(String cls) {
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
