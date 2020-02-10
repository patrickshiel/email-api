package io.shiel.email.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static io.shiel.email.api.model.ValidationConstants.EMAIL_REGEXP;

public class ApiEmailRequest {

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String from;
    private String subject;
    private String text;

    public ApiEmailRequest(
            @JsonProperty(value = "to", required = true) List<String> to,
            @JsonProperty(value = "cc") List<String> cc,
            @JsonProperty(value = "bcc") List<String> bcc,
            @JsonProperty(value = "from", required = true) String from,
            @JsonProperty(value = "subject") String subject,
            @JsonProperty(value = "text", required = true) String text) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.from = from;
        this.subject = subject;
        this.text = text;
    }

    @NotNull(message = "\"to\" field must be provided.")
    @Size(min = 1)
    public List<@Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address in \"to\" field.") String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    @NotNull(message = "\"from\" field must be provided.")
    @Size(min = 1)
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

    public List<@Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address in \"cc\" field.") String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<@Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address in \"bcc\" field.") String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }
}
