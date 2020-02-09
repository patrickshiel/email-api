package io.shiel.email.integration.provider.sendgrid;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.integration.model.EmailResponse;
import io.shiel.email.integration.model.ModelTransformer;
import io.shiel.email.integration.model.ProviderException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class SendGridModelTransformer implements ModelTransformer<SendGridRequest, SendGridResponse> {

    public static final String API_NAME = "SendGrid";

    @Override
    public SendGridRequest transformRequest(ApiEmailRequest apiRequest) {
        SendGridRequest request = new SendGridRequest();

        SendGridRequest.Personalization personalization = new SendGridRequest.Personalization();
        personalization.getTo().add(new SendGridRequest.Email(apiRequest.getTo()));

        request.getPersonalizations().add(personalization);
        request.setFrom(new SendGridRequest.Email(apiRequest.getFrom()));
        request.setSubject(apiRequest.getSubject());
        request.getContent().add(new SendGridRequest.Content(apiRequest.getText()));

        return request;
    }

    @Override
    public EmailResponse transformResponse(SendGridResponse response) {
        return EmailResponse.builder()
                .emailProviderName(API_NAME)
                .responseCode(response.getResponseCode())
                .message(response.getMessage())
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
