package com.axonivy.lab.javaee.docs;

import java.nio.file.Path;

interface DocumentRepository {
	public void save(Document document);
	public void delete(Path path);
	public Document get(Path path);
}
