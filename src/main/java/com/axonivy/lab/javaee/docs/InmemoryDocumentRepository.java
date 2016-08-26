package com.axonivy.lab.javaee.docs;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class InmemoryDocumentRepository implements DocumentRepository {

	private Map<Path, Document> documents = new ConcurrentHashMap<>();
	
	@Override
	public void save(Document document) {
		documents.put(document.getPath(), document);
	}

	@Override
	public void delete(Path path) {
		documents.remove(path);
	}

	@Override
	public Document get(Path path) {
		return documents.get(path);
	}

}
