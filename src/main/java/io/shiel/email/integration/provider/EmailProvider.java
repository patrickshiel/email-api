package io.shiel.email.integration.provider;

import io.shiel.email.integration.model.ProviderRequest;
import io.shiel.email.integration.model.ProviderResponse;

public interface EmailProvider<
        EmailProviderRequest extends ProviderRequest,
        EmailProviderResponse extends ProviderResponse> {

    EmailProviderResponse sendEmail(EmailProviderRequest providerRequest);

}
