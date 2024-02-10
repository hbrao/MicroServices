package com.oracle.osc.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.osc.client.OAuthClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.RestClientDefinitionException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static JsonObject convertToJsonObject(Map<String, Object> queryResultMap) {
        JsonObject response = null;
        if (queryResultMap != null) {
            JsonObjectBuilder responseBuilder = null;
            if (queryResultMap.size() > 0) {
                responseBuilder = Json.createObjectBuilder();
                for(Map.Entry<String, Object> entry : queryResultMap.entrySet()) {
                    responseBuilder.add(entry.getKey(), entry.getValue().toString());
                }

                response = responseBuilder != null ? responseBuilder.build() : null;
            }
        }

        return response;
    }

    public static String getOAuthToken() {
        String token = null ;

        FileSystem fs = FileSystems.getDefault();
        java.nio.file.Path authRespFile = fs.getPath("src", "test", "resources", "oauth_response.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            if( Files.exists(authRespFile) && ! isExpiredToken(authRespFile, mapper.readValue(authRespFile.toFile(), OAuthResponse.class)) ) {
                System.out.println("Fetching auth token from file ..... ");
                OAuthResponse prevResp = mapper.readValue(authRespFile.toFile(), OAuthResponse.class);
                token = prevResp.getAccess_token();
            } else {
                try {
                    System.out.println("Fetching auth token from IDCS ..... ");
                    OAuthClient idcsRestClient = RestClientBuilder
                            .newBuilder()
                            //TODO Point to the OAuth server
                            .baseUri(URI.create("https://oauthserver/"))
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .build(OAuthClient.class);

                    JsonObject respObj = idcsRestClient.getToken(
                            "Basic " + Base64.getEncoder().encodeToString(("client_id:secret").getBytes(StandardCharsets.UTF_8)),
                            "grant_type=password&username=uname&password=pwd&scope=some:value/"
                    );

                    if (respObj != null && respObj.getString("access_token") != null) {
                        token = respObj.getString("access_token");
                        System.out.println("Obtained access token " + token);
                        mapper.writeValue(authRespFile.toFile(), new OAuthResponse( respObj.getString("access_token"),
                                                                                    respObj.getString("token_type"),
                                                                                    respObj.getJsonNumber("expires_in").toString()
                                                                 )
                        );
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (RestClientDefinitionException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static Boolean isExpiredToken(java.nio.file.Path authRespFile, OAuthResponse authResponse) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(authRespFile, BasicFileAttributes.class);
            Long tokenValidUntil = attributes.lastModifiedTime().toMillis()
                    + TimeUnit.SECONDS.toMillis(Long.parseLong(authResponse.getExpires_in()));
            Long currentTime = System.currentTimeMillis() ;

            return tokenValidUntil < currentTime ;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class OAuthResponse {
        String access_token;
        String token_type;
        String expires_in;

        public OAuthResponse() {
        }

        public OAuthResponse(String access_token, String token_type, String expires_in) {
            this.access_token = access_token;
            this.token_type = token_type;
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }
    }
}
