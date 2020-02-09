package io.shiel.email.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shiel.email.integration.model.ModelTransformer;
import io.shiel.email.integration.provider.EmailProviderAdapter;
import io.shiel.email.integration.provider.HttpEmailProviderConfig;
import io.shiel.email.integration.provider.ProviderAdapter;
import io.shiel.email.integration.provider.mailgun.MailGunEmailProvider;
import io.shiel.email.integration.provider.mailgun.MailGunModelTransformer;
import io.shiel.email.integration.provider.mailgun.MailGunRequest;
import io.shiel.email.integration.provider.mailgun.MailGunResponse;
import io.shiel.email.integration.provider.sendgrid.SendGridEmailProvider;
import io.shiel.email.integration.provider.sendgrid.SendGridModelTransformer;
import io.shiel.email.integration.provider.sendgrid.SendGridRequest;
import io.shiel.email.integration.provider.sendgrid.SendGridResponse;
import io.shiel.email.service.EmailService;
import io.shiel.email.service.MultiProviderEmailService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.google.common.collect.Sets.newHashSet;

@Configuration
public class ApplicationConfig {

    @Value("${provider.sendgrid.url}")
    String sendGridUrl;
    @Value("${provider.sendgrid.apikey}")
    String sendGridApikey;
    @Value("${provider.sendgrid.connecttimeout}")
    int sendGridConnectTimeout;
    @Value("${provider.sendgrid.connectiontimeout}")
    int sendGridConnectionTimeout;
    @Value("${provider.sendgrid.sockettimeout}")
    int sendGridSocketTimeout;

    @Value("${provider.mailgun.url}")
    String mailGunUrl;
    @Value("${provider.mailgun.apikey}")
    String mailGunApikey;
    @Value("${provider.mailgun.connecttimeout}")
    int mailGunConnectTimeout;
    @Value("${provider.mailgun.connectiontimeout}")
    int mailGunConnectionTimeout;
    @Value("${provider.mailgun.sockettimeout}")
    int mailGunSocketTimeout;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(FIELD, ANY);
        return objectMapper;
    }

    @Bean
    public HttpEmailProviderConfig mailGunRequestConfig() {
        return new HttpEmailProviderConfig(mailGunUrl, mailGunApikey, RequestConfig.custom()
                .setConnectionRequestTimeout(mailGunConnectionTimeout)
                .setConnectTimeout(mailGunConnectTimeout)
                .setSocketTimeout(mailGunSocketTimeout)
                .build());
    }

    @Bean
    public HttpEmailProviderConfig sendGridRequestConfig() {
        return new HttpEmailProviderConfig(sendGridUrl, sendGridApikey, RequestConfig.custom()
                .setConnectionRequestTimeout(sendGridConnectionTimeout)
                .setConnectTimeout(sendGridConnectTimeout)
                .setSocketTimeout(sendGridSocketTimeout)
                .build());
    }

    @Bean
    public ProviderAdapter mailGunAdapter() {
        MailGunEmailProvider mailGunProvider = new MailGunEmailProvider(
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(mailGunRequestConfig().getRequestConfig())
                        .build(),
                objectMapper(),
                mailGunRequestConfig());

        ModelTransformer<MailGunRequest, MailGunResponse> mailGunTransformer = new MailGunModelTransformer();
        return new EmailProviderAdapter<>(mailGunProvider, mailGunTransformer);
    }

    @Bean
    public ProviderAdapter sendGridAdapter() {
        SendGridEmailProvider sendGridEmailProvider = new SendGridEmailProvider(
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(mailGunRequestConfig().getRequestConfig())
                        .build(),
                objectMapper(),
                sendGridRequestConfig());

        ModelTransformer<SendGridRequest, SendGridResponse> sendGridTransformer = new SendGridModelTransformer();
        return new EmailProviderAdapter<>(sendGridEmailProvider, sendGridTransformer);
    }

    @Bean
    public EmailService emailService() {
        return new MultiProviderEmailService(newHashSet(
                mailGunAdapter(),
                sendGridAdapter()
        ));
    }

}