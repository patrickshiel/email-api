package io.shiel.email.integration.model;

import static java.lang.System.currentTimeMillis;

public class EmailResponse implements ProviderResponse {

    private final String emailProviderName;
    private final int responseCode;
    private String message;
    private Long responseTime;

    private EmailResponse(String emailProviderName, int responseCode, String message) {
        this.emailProviderName = emailProviderName;
        this.responseCode = responseCode;
        this.message = message;
        this.responseTime = currentTimeMillis();
    }

    public static EmailResponse.Builder builder() {
        return new EmailResponse.Builder();
    }

    public String getEmailProviderName() {
        return emailProviderName;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public static class Builder {
        private String emailProviderName;
        private int responseCode;
        private String message;

        Builder() {
        }

        public EmailResponse.Builder emailProviderName(String emailProviderName) {
            this.emailProviderName = emailProviderName;
            return this;
        }

        public EmailResponse.Builder message(String message) {
            this.message = message;
            return this;
        }

        public EmailResponse.Builder responseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public EmailResponse build() {
            return new EmailResponse(this.emailProviderName, this.responseCode, this.message);
        }
    }

}
