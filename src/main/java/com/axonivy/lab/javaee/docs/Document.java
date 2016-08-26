package com.axonivy.lab.javaee.docs;

import java.nio.file.Path;

public class Document {

	private Path path;
	private String base64Content;

	public Document(Path path, String content) {
		this.path = path;
		this.base64Content = content;
	}
	
	public Path getPath() {
		return path;
	}

	public String getContent() {
		return base64Content;
	}
}
