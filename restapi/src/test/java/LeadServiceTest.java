import com.example.client.impl.LeadServiceClientImpl;
import com.example.client.LeadServiceClient;
import com.example.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Map;

@Disabled
public class LeadServiceTest {

    public static void testGetOAuthToken() {
        Assertions.assertNotNull(Utils.getOAuthToken());
    }

    @Test
    public void testCreatedLeadAccess() {
        LeadServiceClient leadServiceClient = new LeadServiceClientImpl();

        JsonObject leadReq = Utils.convertToJsonObject(Map.of("Name", "New Lead " + System.nanoTime()));
        Response resp = leadServiceClient.createLead(leadReq);

        String leadId = null;
        if( resp.getStatus() == Response.Status.CREATED.getStatusCode() ) {
            JsonObject createdLead = resp.readEntity(JsonObject.class);
            System.out.println("Successfully create new lead " + createdLead.getString("name"));
            leadId = createdLead.getJsonString("id").toString();
        } else {
            System.out.println("Unable to create new lead ! " + resp);
        }

        if( leadId != null ) {
            JsonObject leadHistory = leadServiceClient.getHistory("lead", leadId);
            Assertions.assertEquals(leadId, leadHistory.getString("id"));
        } else {
            Assertions.fail("Unable to create a new lead !");
        }
    }
}