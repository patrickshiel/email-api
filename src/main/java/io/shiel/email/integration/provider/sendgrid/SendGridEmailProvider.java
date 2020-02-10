package io.shiel.email.integration.provider.sendgrid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shiel.email.integration.model.ProviderException;
import io.shiel.email.integration.provider.HttpEmailProvider;
import io.shiel.email.integration.provider.HttpEmailProviderConfig;
import io.shiel.email.util.HttpUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static io.shiel.email.util.HttpUtil.getContentBodyAsString;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

public class SendGridEmailProvider extends HttpEmailProvider<SendGridRequest, SendGridResponse> {

    public SendGridEmailProvider(CloseableHttpClient httpClient,
                                 ObjectMapper objectMapper,
                                 HttpEmailProviderConfig config) {
        super(httpClient, objectMapper, config);
    }

    @Override
    public SendGridResponse sendEmail(SendGridRequest providerRequest) {
        try {
            HttpPost httpPost = constructHttpRequest(providerRequest);
            return constructProviderResponse(httpclient.execute(httpPost));
        } catch (IOException e) {
            throw new ProviderException(e);
        }
    }

    private HttpPost constructHttpRequest(SendGridRequest providerRequest)
            throws JsonProcessingException, UnsupportedEncodingException {

        HttpPost httpPost = new HttpPost(emailProviderConfig.getProviderUrl());
        httpPost.setHeader(AUTHORIZATION, "Bearer " + emailProviderConfig.getProviderApiKey());
        httpPost.setHeader(CONTENT_TYPE, "application/json");
        String string = jsonObjectMapper.writeValueAsString(providerRequest);
        httpPost.setEntity(new StringEntity(string));

        return httpPost;
    }

    private SendGridResponse constructProviderResponse(CloseableHttpResponse response) throws IOException {
        return new SendGridResponse(response, getContentBodyAsString(response));
    }

}
