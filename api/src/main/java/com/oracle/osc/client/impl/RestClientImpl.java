package com.oracle.osc.client.impl;

import com.oracle.osc.client.FARestApi;
import com.oracle.osc.client.FASpectraApi;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class RestClientImpl implements FARestApi, FASpectraApi {

    private static final String FA_SERVER ="fusion_monolith.restapi.server";
    private static final String DEFAULT_FA_SERVER= "https://fuscdrmsmc217-fa-ext.us.oracle.com";

    private static final String FA_BASE_CONTEXT_ROOT_KEY="fusion_monolith.restapi.contextRoot";
    private static final String DEFAULT_FA_CRM_REST_CONTEXT_ROOT="/crmRestApi/resources/11.13.18.05/";

    private static final String SPECTRA_API ="fusion_spectra.api.server";
    private static final String DEFAULT_SPECTRA_API = "http://localhost:8081/crmRestApi/resources/11.13.18.05";

    private static final String CONNECT_TIMEOUT_KEY="fusion_monolith.restapi.connectTimeout";
    private static final Long DEFAULT_CONNECT_TIMEOUT= TimeUnit.MINUTES.toMillis(1);

    private static final String READ_TIMEOUT_KEY="fusion_monolith.restapi.readTimeout";
    private static final Long DEFAULT_READ_TIMEOUT=TimeUnit.MINUTES.toMillis(1);

    private FARestApi faRestApiClient;
    private FASpectraApi faSpectraApiClient;

    public FARestApi getFARestApiClient() {
        if( faRestApiClient == null ) constructFARestApiClient();
        return faRestApiClient;
    }

    public FASpectraApi getFARSpectraApiClient() {
        if( faSpectraApiClient == null ) constructFASpectraApiClient();
        return faSpectraApiClient;
    }

    private synchronized  void constructFARestApiClient() {
        if( faRestApiClient == null ) {
            final Config config = ConfigProvider.getConfig();

            String faServer = config.getOptionalValue(FA_SERVER, String.class).orElse(DEFAULT_FA_SERVER);
            String faRestApiCtxRoot = config.getOptionalValue(FA_BASE_CONTEXT_ROOT_KEY, String.class).orElse(DEFAULT_FA_CRM_REST_CONTEXT_ROOT);

            System.out.println("Fusion apps environment "+ faServer + faRestApiCtxRoot);

            faRestApiClient = RestClientBuilder.newBuilder()
                                             .baseUri(URI.create(faServer + faRestApiCtxRoot))
                                             .connectTimeout(config.getOptionalValue(CONNECT_TIMEOUT_KEY, Long.class).orElse(DEFAULT_CONNECT_TIMEOUT), TimeUnit.MICROSECONDS)
                                             .readTimeout(config.getOptionalValue(READ_TIMEOUT_KEY, Long.class).orElse(DEFAULT_READ_TIMEOUT), TimeUnit.MILLISECONDS)
                                             .build(FARestApi.class);
        }
    }

    private synchronized  void constructFASpectraApiClient() {
        if( faSpectraApiClient == null ) {
            final Config config = ConfigProvider.getConfig();

            String spectraServer = config.getOptionalValue(SPECTRA_API, String.class).orElse(DEFAULT_SPECTRA_API);
            System.out.println("Spectra environment "+ spectraServer);

            faSpectraApiClient = RestClientBuilder.newBuilder()
                    .baseUri(URI.create(spectraServer))
                    .connectTimeout(config.getOptionalValue(CONNECT_TIMEOUT_KEY, Long.class).orElse(DEFAULT_CONNECT_TIMEOUT), TimeUnit.MICROSECONDS)
                    .readTimeout(config.getOptionalValue(READ_TIMEOUT_KEY, Long.class).orElse(DEFAULT_READ_TIMEOUT), TimeUnit.MILLISECONDS)
                    .build(FASpectraApi.class);
        }
    }

    @Override
    public JsonObject getLeadById(String id) {
        return getFARestApiClient().getLeadById(id);
    }

    @Override
    public Response createLead(JsonObject leadRequest) {
        return getFARestApiClient().createLead(leadRequest);
    }

    @Override
    public JsonObject getHistory(String object, String objectId) {
        return getFARSpectraApiClient().getHistory(object, objectId);
    }
}
