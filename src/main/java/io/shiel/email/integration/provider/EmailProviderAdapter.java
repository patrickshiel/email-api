package io.shiel.email.integration.provider;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.integration.model.*;

public class EmailProviderAdapter<ProviderReq extends ProviderRequest, ProviderRes extends ProviderResponse>
        implements ProviderAdapter {

    private EmailProvider<ProviderReq, ProviderRes> emailProvider;

    private ModelTransformer<ProviderReq, ProviderRes> modelTransformer;

    public EmailProviderAdapter(
            EmailProvider<ProviderReq, ProviderRes> emailProvider,
            ModelTransformer<ProviderReq, ProviderRes> modelTransformer) {
        this.emailProvider = emailProvider;
        this.modelTransformer = modelTransformer;
    }

    @Override
    public EmailResponse adaptAndSend(ApiEmailRequest apiEmailRequest) {
        final ProviderReq providerRequest = modelTransformer.transformRequest(apiEmailRequest);
        final ProviderRes providerResponse = emailProvider.sendEmail(providerRequest);
        return modelTransformer.transformResponse(providerResponse);
    }

    @Override
    public EmailResponse parseResponseFromException(ProviderException ex) {
        return modelTransformer.transformExceptionToResponse(ex);
    }

}
