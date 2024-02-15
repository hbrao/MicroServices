package com.example.api.dispatcher;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/{path:.*}")
@ApplicationScoped
public class StaticContentResource {

    @GET
    @Produces({"text/html", "application/javascript", "text/css", "image/png", "image/jpeg", "image/gif"})
    public Response staticResource(@PathParam("path") String path) {
        if (path.isEmpty() || "/".equals(path)) {
            path = "index.html"; // default to index.html if no file is specified
        }

        InputStream fileStream = StaticContentResource.class.getClassLoader()
                .getResourceAsStream("META-INF/resources/" + path);

        if (fileStream == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String contentType = getContentType(path);
        return Response.ok(fileStream).type(contentType).build();
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        } else if (path.endsWith(".js")) {
            return "application/javascript";
        } else if (path.endsWith(".css")) {
            return "text/css";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream"; // default binary content type
    }
}
