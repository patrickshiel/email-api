package io.shiel.email.integration.provider.mailgun;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shiel.email.integration.model.ProviderException;
import io.shiel.email.integration.provider.HttpEmailProvider;
import io.shiel.email.integration.provider.HttpEmailProviderConfig;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import static io.shiel.email.util.HttpUtil.getContentBodyAsString;
import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class MailGunEmailProvider extends HttpEmailProvider<MailGunRequest, MailGunResponse> {

    public MailGunEmailProvider(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            HttpEmailProviderConfig config) {
        super(httpClient, objectMapper, config);
    }

    @Override
    public MailGunResponse sendEmail(MailGunRequest providerRequest) {
        try {
            HttpPost httpPost = constructHttpRequest(providerRequest);
            return constructProviderResponse(httpclient.execute(httpPost));
        } catch (IOException e) {
            throw new ProviderException(e);
        }
    }

    private HttpPost constructHttpRequest(MailGunRequest providerRequest) {
        final String apiKeyPlain = "api" + ":" + emailProviderConfig.getProviderApiKey();
        final String authHeader = "Basic " + new String(Base64.getEncoder().encode(apiKeyPlain.getBytes(Consts.UTF_8)));

        HttpPost httpPost = new HttpPost(emailProviderConfig.getProviderUrl());
        httpPost.setHeader(AUTHORIZATION, authHeader);
        httpPost.setEntity(providerRequest.toFormData());

        return httpPost;
    }

    private MailGunResponse constructProviderResponse(CloseableHttpResponse response) throws IOException {
        HashMap<String, String> contentMap = jsonObjectMapper.readValue(
                getContentBodyAsString(response),
                new TypeReference<HashMap<String, String>>() {
                }
        );
        response.close();

        return new MailGunResponse(
                response,
                contentMap.getOrDefault("id", ""),
                contentMap.getOrDefault("message", "")
        );
    }

}
