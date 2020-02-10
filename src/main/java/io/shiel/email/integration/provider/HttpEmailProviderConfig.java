package io.shiel.email.integration.provider;

import org.apache.http.client.config.RequestConfig;

public class HttpEmailProviderConfig {
    private String providerUrl;
    private String providerApiKey;
    private RequestConfig requestConfig;

    public HttpEmailProviderConfig(String providerUrl, String providerApiKey, RequestConfig config) {
        this.providerApiKey = providerApiKey;
        this.providerUrl = providerUrl;
        this.requestConfig = config;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public String getProviderApiKey() {
        return providerApiKey;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }
}
