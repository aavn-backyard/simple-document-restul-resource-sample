package com.axonivy.lab.javaee.docs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;

@javax.ws.rs.Path("documents")
public class DocumentResource {
	
	@Context
	private UriInfo currentUri;
	
	@Inject
	private DocumentRepository documentRepository;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveDocument(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		BodyPart partOne =  mimeMultipart.getBodyPart(0);
		BodyPart partTwo = mimeMultipart.getBodyPart(1);

		String firstPart = IOUtils.toString(partOne.getInputStream(), Charset.forName("UTF-8"));
		String secondPart = Base64.getEncoder().encodeToString(IOUtils.toByteArray(partTwo.getInputStream()));
		
		Document document = new Document(buildPath(firstPart), secondPart);
		documentRepository.save(document);
		
		return document.getPath().toString().replaceAll("\\\\", "/");
	}
	
	private Path buildPath(String filename) {
		List<String> allSegments = allPathSegments();
		allSegments.add(filename);
		return allSegments.stream()
			.map(s -> Paths.get(s))
			.reduce(Paths.get("/"), java.nio.file.Path::resolve);
	}

	private List<String> allPathSegments() {
		return currentUri.getPathSegments().stream()
			.map(s -> s.getPath())
			.collect(Collectors.toList());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getAllDocuments() {
		return allPathSegments();
	}

	
	@GET
	@javax.ws.rs.Path("{document-name}")
	public Response getDocumentByName(@PathParam("document-name") String documentName) throws IOException {
		Path path = buildPath(documentName);
		byte[] content = Optional.ofNullable(documentRepository.get(path))
				.map(d -> d.getContent())
				.map(c -> Base64.getDecoder().decode(c))
				.orElseThrow(() -> new NotFoundException("There is not document exists"));
		

		org.apache.tika.mime.MediaType mediaType = MimeTypes.getDefaultMimeTypes().detect(new ByteArrayInputStream(content), new Metadata());
		return Response.ok().type(mediaType.toString()).entity(content).build();
	}
	
}
