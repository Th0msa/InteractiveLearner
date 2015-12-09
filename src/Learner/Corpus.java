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
	private List<String> categories;
	private Vocabulary vocabulary;
	
	public Corpus(String path) {
		this.allDocuments = new ArrayList<Document>();
		
		try {
			Files.walk(Paths.get(path)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {	
			        Document tempD = new Document(filePath.toString());
			        this.addCategory(tempD.getDocumentClass());
			    	allDocuments.add(tempD);
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getCategories() {
		return this.categories;
	}
	
	public List<Document> getDocuments() {
		return this.allDocuments;
	}
	
	public static Vocabulary extractVocabulary(Corpus crps) {
		//TODO extracts all the words from a corpus and adds them in a library
		return null;
	}
	
	public int countNumberOfDocs() {
		return this.allDocuments.size();
	}
	
	public int countDocsInClass(String cls) {
		int docCount = 0;
		
		for (Document d : this.allDocuments) {
			if (d.getDocumentClass().equals(cls)) {
				docCount++;
			}
		}
		
		return docCount;
	}
	
	public static String ConcatenateAllTextsOfDocsInClass(Corpus crps, String cls) {
		return null;
	}
	
	public void addCategory(String newCategory) {
		if (!this.categories.contains(newCategory)) {
			this.categories.add(newCategory);
		}
	}

}
