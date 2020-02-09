package io.shiel.email.integration.provider.sendgrid;

import io.shiel.email.integration.model.ProviderResponse;
import org.apache.http.HttpResponse;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class SendGridResponse implements ProviderResponse {

    private final int responseCode;
    private final String message;

    private HttpResponse httpResponse;
    private Exception exception;

    public SendGridResponse(HttpResponse httpResponse, String message) {
        this.httpResponse = httpResponse;
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
        this.message = message;
    }

    public SendGridResponse(Exception ex) {
        this.exception = ex;
        this.responseCode = SC_INTERNAL_SERVER_ERROR;
        this.message = ex.getCause().toString();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

}
