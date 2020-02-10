package io.shiel.email.integration.provider.mailgun;

import io.shiel.email.integration.model.ProviderRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.Consts.UTF_8;
import static org.springframework.util.StringUtils.isEmpty;

public class MailGunRequest implements ProviderRequest {

    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;
    private final String text;

    private MailGunRequest(String from, String to, String cc, String bcc, String subject, String text) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.text = text;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UrlEncodedFormEntity toFormData() {
        List<NameValuePair> form = new ArrayList<>();
        addFormData("from", this.from, form);
        addFormData("to", this.to, form);
        addFormData("cc", this.cc, form);
        addFormData("bcc", this.bcc, form);
        addFormData("subject", this.subject, form);
        addFormData("text", this.text, form);
        return new UrlEncodedFormEntity(form, UTF_8);
    }

    private void addFormData(String key, String value, List<NameValuePair> formData) {
        if (!isEmpty(value)) {
            formData.add(new BasicNameValuePair(key, value));
        }
    }

    public static class Builder {
        private String from;
        private String to;
        private String cc;
        private String bcc;
        private String subject;
        private String text;

        Builder() {
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder cc(String cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(String bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public MailGunRequest build() {
            return new MailGunRequest(this.from, this.to, this.cc, this.bcc, this.subject, this.text);
        }

    }
}