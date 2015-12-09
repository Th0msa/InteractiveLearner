package InteractiveLearner.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
		this.categories = new ArrayList<String>();
		this.vocabulary = new Vocabulary();
		
		try {
			Files.walk(Paths.get(path)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {	
			        Document tempD = new Document(filePath.toString(), true);
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
	
	public Vocabulary extractVocabulary() {
		for(Document d : this.getDocuments()) {
			vocabulary.extractTokensFromDoc(d);
		}
		return vocabulary;
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
	
	public List<String> ConcatenateAllTextsOfDocsInClass(String category) {
		List<String> allWordsOfCategory = new ArrayList<String>();
		
		for (Document d : this.allDocuments) {
			if (d.getDocumentClass().equals(category)) {
				allWordsOfCategory.addAll(d.getListContents());
			}
		}
		return allWordsOfCategory;
	}
	
	public void addCategory(String newCategory) {
		if (!this.categories.contains(newCategory)) {
			this.categories.add(newCategory);
		}
	}

}
