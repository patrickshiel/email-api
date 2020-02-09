package io.shiel.email.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.shiel.email.integration.model.EmailResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Collection;

public class ApiEmailResponse {

    @ApiModelProperty(notes = "The request for this response", name = "request", required = true, value = "request")
    private ApiEmailRequest request;

    private Collection<EmailResponse> providerResponses = new ArrayList<>();

    @JsonCreator
    public ApiEmailResponse(ApiEmailRequest request, Collection<EmailResponse> providerResponses) {
        this.request = request;
        this.providerResponses = providerResponses;
    }

    public ApiEmailRequest getRequest() {
        return request;
    }

    public void setRequest(ApiEmailRequest request) {
        this.request = request;
    }

    public Collection<EmailResponse> getProviderResponses() {
        return providerResponses;
    }

    public void setProviderResponses(Collection<EmailResponse> providerResponses) {
        this.providerResponses = providerResponses;
    }
}
