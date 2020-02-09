package io.shiel.email.integration.provider.mailgun;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.integration.model.EmailResponse;
import io.shiel.email.integration.model.ModelTransformer;
import io.shiel.email.integration.model.ProviderException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class MailGunModelTransformer implements ModelTransformer<MailGunRequest, MailGunResponse> {

    public static final String API_NAME = "MailGun";

    @Override
    public MailGunRequest transformRequest(ApiEmailRequest apiRequest) {
        return MailGunRequest.builder()
                .from(apiRequest.getFrom())
                .to(apiRequest.getTo())
                .subject(apiRequest.getSubject())
                .text(apiRequest.getText())
                .build();
    }

    @Override
    public EmailResponse transformResponse(MailGunResponse response) {
        return EmailResponse.builder()
                .emailProviderName(API_NAME)
                .responseCode(response.getResponseCode())
                .message(response.getMessageId() + " | " + response.getMessage())
                .build();
    }

    @Override
    public EmailResponse transformExceptionToResponse(ProviderException ex) {
        return EmailResponse.builder()
                .emailProviderName(API_NAME)
                .responseCode(INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
    }

}
