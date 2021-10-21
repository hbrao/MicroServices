import com.oracle.osc.client.FASpectraApi;
import com.oracle.osc.client.impl.RestClientImpl;
import com.oracle.osc.client.FARestApi;
import com.oracle.osc.service.Main;
import com.oracle.osc.service.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Map;


public class AccessGroupSecurityIntegTest {

    @BeforeAll
    public void startServer() {

    }

    @Test
    public void testGetOAuthToken() {
        Assertions.assertNotNull(Utils.getOAuthToken());
    }

    @Test
    public void testCreatedLeadAccess() {
        Object restClient = new RestClientImpl();

        JsonObject leadReq = Utils.convertToJsonObject(Map.of("Name", "New Lead " + System.nanoTime()));
        Response resp = ((FARestApi) restClient).createLead(leadReq);

        String leadId = null;
        if( resp.getStatus() == Response.Status.CREATED.getStatusCode() ) {
            JsonObject createdLead = resp.readEntity(JsonObject.class);
            System.out.println("Successfully create new lead " + createdLead.getString("Name"));
            leadId = createdLead.getJsonNumber("LeadId").toString();
        } else {
            System.out.println("Unable to create new lead ! " + resp);
        }

        if( leadId != null ) {
            JsonObject leadHistory = ((FASpectraApi) restClient).getHistory("Lead", leadId);
            Assertions.assertEquals(leadId, leadHistory.getString("Lead"));
        } else {
            Assertions.fail("Unable to create a new lead !");
        }
    }
}