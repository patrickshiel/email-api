package io.shiel.email.service;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.api.model.ApiEmailResponse;
import io.shiel.email.integration.model.EmailResponse;
import io.shiel.email.integration.model.ProviderException;
import io.shiel.email.integration.provider.ProviderAdapter;

import java.util.HashSet;
import java.util.Set;

import static io.shiel.email.util.HttpUtil.is2xxSuccessfulStatusCode;

public class MultiProviderEmailService implements EmailService {

    private Set<ProviderAdapter> emailProviderAdapters;

    public MultiProviderEmailService(Set<ProviderAdapter> emailProviderAdapters) {
        this.emailProviderAdapters = emailProviderAdapters;
    }

    @Override
    public ApiEmailResponse sendEmail(ApiEmailRequest request) {
        final Set<EmailResponse> emailProviderResponses = new HashSet<>();

        for (ProviderAdapter providerAdapter : emailProviderAdapters) {
            try {
                EmailResponse response = providerAdapter.adaptAndSend(request);
                emailProviderResponses.add(response);

                if (is2xxSuccessfulStatusCode(response.getResponseCode())) {
                    break;
                }
            } catch (ProviderException ex) {
                emailProviderResponses.add(providerAdapter.parseResponseFromException(ex));
            }
        }

        return new ApiEmailResponse(request, emailProviderResponses);
    }

}
