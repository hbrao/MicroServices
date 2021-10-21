package com.oracle.osc.service.resources;

import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Map;

import com.oracle.osc.datasecurity.AccessGroupSecurityService;
import com.oracle.osc.datasecurity.impl.ApplcoreDataSecurityServiceImpl;
import com.oracle.osc.datasecurity.request.DataSecurityRequest;
import com.oracle.osc.service.util.Utils;
import io.helidon.security.annotations.Authenticated;
import io.helidon.security.annotations.Authorized;
import oracle.spectra.providers.security.helidon.annotations.AuthenticationRoleMapper;
import oracle.spectra.providers.security.helidon.annotations.AuthorizationResourcePermission;

@Path("/crmRestApi/resources/11.13.18.05/feeds")
@RequestScoped
public class SampleResource {

    AccessGroupSecurityService agSecImpl = new ApplcoreDataSecurityServiceImpl();

    @GET
    @Path("/{object}/{objectId}/history")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Authorized
    @AuthorizationResourcePermission(resourceName = "feeds", action = "get", serviceStripe = "crm", resourceType = "RestServiceResourceType")
    @AuthenticationRoleMapper(enabled = true)
    public Response history(@PathParam("object") final String objectName,
                            @PathParam("objectId") final String objectId,
                            @Context UriInfo uriInfo,
                            @Context HttpHeaders httpHeaders ) {

        System.out.println("Data security check " + objectName + "," + objectId);
        Long startTime = System.currentTimeMillis();
        DataSecurityRequest req = DataSecurityRequest.getBuilder().setContext(objectName, objectId).build();
        Map<DataSecurityRequest, Boolean> resultMap = agSecImpl.checkDataSecurity(req);
        System.out.println("Result (AGSec Library) = " + resultMap.get(req));
        Long endTime = System.currentTimeMillis();
        System.out.println("Access group data security took " + ( endTime - startTime ) + " milli seconds.");

        JsonObject responseObject = null;
        if( resultMap.get(req) ) {
            System.out.println("Fetching " + objectName + " history .... ");
            responseObject = Utils.convertToJsonObject(Map.of(objectName, Long.valueOf(objectId)));
        } else {
            responseObject = Utils.convertToJsonObject(Map.of("DATA_SECURITY_CHECK", "NO_ACCESS"));
        }
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }
}
