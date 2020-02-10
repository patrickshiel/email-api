package io.shiel.email.integration.provider.mailgun;

import io.shiel.email.integration.model.ProviderResponse;
import org.apache.http.HttpResponse;

public class MailGunResponse implements ProviderResponse {

    private final int responseCode;
    private final String messageId;
    private final String message;

    private HttpResponse httpResponse;

    public MailGunResponse(HttpResponse httpResponse, String messageId, String message) {
        this.httpResponse = httpResponse;
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
        this.messageId = messageId;
        this.message = message;
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
