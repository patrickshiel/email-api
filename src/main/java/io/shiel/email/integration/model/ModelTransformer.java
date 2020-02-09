package io.shiel.email.integration.model;

import io.shiel.email.api.model.ApiEmailRequest;

public interface ModelTransformer<ProviderReq, ProviderRes> {

    ProviderReq transformRequest(ApiEmailRequest request);

    EmailResponse transformResponse(ProviderRes response);

    EmailResponse transformExceptionToResponse(ProviderException ex);

}
