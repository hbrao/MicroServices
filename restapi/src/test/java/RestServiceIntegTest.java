import com.oracle.osc.client.impl.RestServiceClientImpl;
import com.oracle.osc.client.RestServiceClient;
import com.oracle.osc.service.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Map;

@Disabled
public class RestServiceIntegTest {

    public static void testGetOAuthToken() {
        Assertions.assertNotNull(Utils.getOAuthToken());
    }

    @Test
    public void testCreatedLeadAccess() {
        RestServiceClient restServiceClient = new RestServiceClientImpl();

        JsonObject leadReq = Utils.convertToJsonObject(Map.of("Name", "New Lead " + System.nanoTime()));
        Response resp = restServiceClient.createLead(leadReq);

        String leadId = null;
        if( resp.getStatus() == Response.Status.CREATED.getStatusCode() ) {
            JsonObject createdLead = resp.readEntity(JsonObject.class);
            System.out.println("Successfully create new lead " + createdLead.getString("name"));
            leadId = createdLead.getJsonString("id").toString();
        } else {
            System.out.println("Unable to create new lead ! " + resp);
        }

        if( leadId != null ) {
            JsonObject leadHistory = restServiceClient.getHistory("lead", leadId);
            Assertions.assertEquals(leadId, leadHistory.getString("id"));
        } else {
            Assertions.fail("Unable to create a new lead !");
        }
    }
}