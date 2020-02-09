package io.shiel.email.integration.provider.sendgrid;

import io.shiel.email.integration.model.ProviderRequest;

import java.util.ArrayList;
import java.util.List;

public class SendGridRequest implements ProviderRequest {

    private List<Personalization> personalizations = new ArrayList<>();
    private Email from;
    private String subject;
    private List<Content> content = new ArrayList<>();

    public List<Personalization> getPersonalizations() {
        return personalizations;
    }

    public void setFrom(Email from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Content> getContent() {
        return content;
    }

    public static class Personalization {
        private List<Email> to = new ArrayList<>();

        public List<Email> getTo() {
            return to;
        }
    }

    public static class Email {
        private String email;

        public Email(String email) {
            this.email = email;
        }
    }

    public static class Content {
        private String type;
        private String value;

        public Content(String value) {
            this.type = "text/plain";
            this.value = value;
        }
    }
}
