# simple-document-restul-resource-sample

This Java EE project is a sample of an approach to design & implement the `/documents` API

## Design

Suppose our application has:

- `/companies/{company-id}` for retrieving `Company`.
- `/companies/{company-id}/persons/{person-id}` for retrieving `Person` in the specified `Company`.

We want to support the client to upload files into either `Company` and `Person`. The API's `URI` willl be:

- `companies/{company-id}/documents`
- `companies/{company-id}/persons/{person-id}/documents`

To upload a document for a `Company` whose `id` is `33xc-324xd`, the client has to do:

```
POST /document-resource/api/companies/33xc-324xd/documents HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----blablablaAnyThingWillDo

------blablablaAnyThingWillDo
Content-Disposition: form-data; name="filename"

company-policy.pdf
------blablablaAnyThingWillDo
Content-Disposition: form-data; name="filecontent"; filename=""
Content-Type: 

(binary content goes here)
------blablablaAnyThingWillDo--
```

...and the response would be:

```
HTTP/1.1 200 OK
Content-Type: application/pdf

/companies/33xc-324xd/documents/company-policy.pdf
```

The URI to the newly uploaded file would be:

```
http://localhost:8080/document-resource/api/companies/33xc-324xd/documents/company-policy.pdf
```

# Implementation

The implementation follows the https://docs.jboss.org/resteasy/docs/1.0.2.GA/userguide/html/JAX-RS_Resource_Locators_and_Sub_Resources.html
