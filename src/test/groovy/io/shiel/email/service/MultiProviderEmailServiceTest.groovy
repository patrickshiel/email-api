package io.shiel.email.service

import io.shiel.email.api.model.ApiEmailRequest
import io.shiel.email.integration.model.EmailResponse
import io.shiel.email.integration.provider.EmailProviderAdapter
import io.shiel.email.integration.provider.ProviderAdapter
import spock.lang.Specification

class MultiProviderEmailServiceTest extends Specification {

    MultiProviderEmailService emailService;

    def "MultiProviderEmailService will try next provider until success"() {
        given: "Multiple Providers"

        EmailProviderAdapter mockEmailProviderAdapter1 = Mock(EmailProviderAdapter)
        mockEmailProviderAdapter1.adaptAndSend(_) >> EmailResponse.builder()
                .emailProviderName("Provider1")
                .responseCode(400)
                .message("BadRequest")
                .build()

        EmailProviderAdapter mockEmailProviderAdapter2 = Mock(EmailProviderAdapter)
        mockEmailProviderAdapter2.adaptAndSend(_) >> EmailResponse.builder()
                .emailProviderName("Provider2")
                .responseCode(200)
                .message("Ok Sent")
                .build()

        Set<ProviderAdapter> emailProviderAdapters = [mockEmailProviderAdapter1, mockEmailProviderAdapter2]

        emailService = new MultiProviderEmailService(emailProviderAdapters)

        ApiEmailRequest request = Mock()

        when:
        def apiEmailResponse = emailService.sendEmail(request)

        then:
        apiEmailResponse.providerResponses.size() == 2
        apiEmailResponse.request == request
    }

    def "MultiProviderEmailService will return when a successful request is sent"() {
        given:

        EmailProviderAdapter mockEmailProviderAdapter1 = Mock(EmailProviderAdapter)
        mockEmailProviderAdapter1.adaptAndSend(_) >> EmailResponse.builder()
                .emailProviderName("Provider1")
                .responseCode(503)
                .message("Not Available")
                .build()

        EmailProviderAdapter mockEmailProviderAdapter2 = Mock(EmailProviderAdapter)
        mockEmailProviderAdapter2.adaptAndSend(_) >> EmailResponse.builder()
                .emailProviderName("Provider2")
                .responseCode(200)
                .message("Ok Sent")
                .build()

        EmailProviderAdapter mockEmailProviderAdapter3 = Mock(EmailProviderAdapter)
        mockEmailProviderAdapter3.adaptAndSend(_) >> EmailResponse.builder()
                .emailProviderName("Provider3")
                .responseCode(400)
                .message("Bad Request")
                .build()

        Set<ProviderAdapter> emailProviderAdapters = [
                mockEmailProviderAdapter1,
                mockEmailProviderAdapter2,
                mockEmailProviderAdapter3]

        emailService = new MultiProviderEmailService(emailProviderAdapters)

        ApiEmailRequest request = Mock()

        when:
        def apiEmailResponse = emailService.sendEmail(request)

        then:
        def successfulResponse = apiEmailResponse.providerResponses.find {
            r -> r.responseCode == 200 && r.emailProviderName == "Provider2"
        }

        successfulResponse.message == "Ok Sent"
        apiEmailResponse.request == request
    }

}