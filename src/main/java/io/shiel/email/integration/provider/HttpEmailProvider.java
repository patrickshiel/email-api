package io.shiel.email.integration.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shiel.email.integration.model.ProviderRequest;
import io.shiel.email.integration.model.ProviderResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

public abstract class HttpEmailProvider<Req extends ProviderRequest, Res extends ProviderResponse>
        implements EmailProvider<Req, Res> {

    protected final CloseableHttpClient httpclient;

    protected final ObjectMapper jsonObjectMapper;

    protected final HttpEmailProviderConfig emailProviderConfig;

    public HttpEmailProvider(
            CloseableHttpClient httpclient,
            ObjectMapper jsonObjectMapper,
            HttpEmailProviderConfig emailProviderConfig) {
        this.httpclient = httpclient;
        this.jsonObjectMapper = jsonObjectMapper;
        this.emailProviderConfig = emailProviderConfig;
    }

}
