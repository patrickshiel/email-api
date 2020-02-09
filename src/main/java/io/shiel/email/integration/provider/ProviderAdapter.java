package io.shiel.email.integration.provider;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.integration.model.EmailResponse;
import io.shiel.email.integration.model.ProviderException;

public interface ProviderAdapter {

    EmailResponse adaptAndSend(ApiEmailRequest apiEmailRequest);

    EmailResponse parseResponseFromException(ProviderException ex);
}
