package io.shiel.email.api

import io.shiel.email.api.controller.EmailController
import io.shiel.email.integration.model.EmailResponse
import io.shiel.email.api.model.ApiEmailRequest
import io.shiel.email.api.model.ApiEmailResponse
import io.shiel.email.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import spock.mock.DetachedMockFactory

import static java.util.Collections.singleton
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [EmailController])
class EmailControllerTest extends BaseControllerTest {

    @Autowired
    EmailService emailService

    def "Valid email requests can be fulfilled and return OK response"() {
        given:
        ApiEmailRequest request = new ApiEmailRequest(
                ["john.wayne@gmail.com"],
                ["cc.john.wayne@gmail.com"],
                ["bcc.john.wayne@gmail.com"],
                "bruce.wayne@gmail.com",
                "Subject",
                "Text"
        )

        and:
        emailService.sendEmail(_) >> new ApiEmailResponse(request, singleton(new EmailResponse("DummyApi", 200, "OK")))

        expect:
        doRequest(createJsonPostRequest(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.request.to').value('john.wayne@gmail.com'))
                .andExpect(jsonPath('$.request.from').value('bruce.wayne@gmail.com'))
    }

    def "Valid email requests with empty optional fields can be fulfilled and return OK response"() {
        given:
        ApiEmailRequest request = new ApiEmailRequest(
                ["john.wayne@gmail.com"],
                null,
                null,
                "bruce.wayne@gmail.com",
                "Subject",
                "Text"
        )

        and:
        emailService.sendEmail(_) >> new ApiEmailResponse(request, singleton(new EmailResponse("DummyApi", 200, "OK")))

        expect:
        doRequest(createJsonPostRequest(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.request.to').value('john.wayne@gmail.com'))
                .andExpect(jsonPath('$.request.from').value('bruce.wayne@gmail.com'))
    }

    def "Missing required fields in request will fail and return 400 response"() {
        given:
        ApiEmailRequest request = new ApiEmailRequest(
                ["john.wayne@gmail.com"],
                ["cc.john.wayne@gmail.com"],
                ["bcc.john.wayne@gmail.com"],
                null,
                "Subject",
                null
        )

        and:
        emailService.sendEmail(_) >> new ApiEmailResponse(request, new ArrayList<EmailResponse>())

        expect:
        doRequest(createJsonPostRequest(request)).andExpect(status().isBadRequest())
    }

    def "Invalid Format for Email field in request will fail and return 400 response"() {
        given:
        ApiEmailRequest request = new ApiEmailRequest(
                ["INVALID_EMAIL"],
                ["cc.john.wayne@gmail.com"],
                ["bcc.john.wayne@gmail.com"],
                "bruce.wayne@gmail.com",
                "Subject",
                "Text"
        )

        and:
        emailService.sendEmail(_) >> new ApiEmailResponse(request, new ArrayList<EmailResponse>())

        expect:
        doRequest(createJsonPostRequest(request)).andExpect(status().isBadRequest())
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        EmailService stubEmailService() {
            return detachedMockFactory.Stub(EmailService)
        }
    }

}