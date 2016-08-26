package com.axonivy.lab.javaee.person;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.axonivy.lab.javaee.docs.DocumentResource;

@Path("persons")
public class PersonResource {

	@Inject
	private DocumentResource documentResource;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPersons() {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	@Path("{person-id}/documents")
	public DocumentResource getDocuments() {
		return documentResource;
	}
	
}
