package io.shiel.email.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static io.shiel.email.api.model.ValidationConstants.EMAIL_REGEXP;

public class ApiEmailRequest {

    private String to;
    private String from;
    private String subject;
    private String text;

    public ApiEmailRequest(
            @JsonProperty(value = "to", required = true) String to,
            @JsonProperty(value = "from", required = true) String from,
            @JsonProperty(value = "subject") String subject,
            @JsonProperty(value = "text", required = true) String text) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
    }

    @NotNull(message = "\"to\" field must be provided.")
    @Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address in \"to\" field.")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @NotNull(message = "\"from\" field must be provided.")
    @Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address in \"from\" field.")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
