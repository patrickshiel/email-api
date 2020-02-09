package io.shiel.email.integration.provider.mailgun;

import io.shiel.email.integration.model.ProviderResponse;
import org.apache.http.HttpResponse;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class MailGunResponse implements ProviderResponse {

    private final int responseCode;
    private final String messageId;
    private final String message;

    private HttpResponse httpResponse;
    private Exception exception;

    public MailGunResponse(HttpResponse httpResponse, String messageId, String message) {
        this.httpResponse = httpResponse;
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
        this.messageId = messageId;
        this.message = message;
    }

    public MailGunResponse(Exception ex) {
        this.exception = ex;
        this.responseCode = SC_INTERNAL_SERVER_ERROR;
        this.messageId = ex.getMessage();
        this.message = ex.getCause().toString();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

}
