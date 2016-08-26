package com.axonivy.lab.javaee.company;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.axonivy.lab.javaee.docs.DocumentResource;

@Path("companies")
public class CompanyResource {

	@Inject
	private DocumentResource documentResource;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getAllCompanies() {
		return Arrays.asList("some", "companies");
	}
	
	@Path("{company-id}/documents")
	public DocumentResource getDocuments() {
		return documentResource;
	}
	
}
