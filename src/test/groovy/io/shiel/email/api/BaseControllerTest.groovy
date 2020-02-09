package io.shiel.email.api

import io.shiel.email.api.controller.EmailController
import io.shiel.email.api.model.ApiEmailRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class BaseControllerTest extends Specification {

    @Autowired
    protected MockMvc mvc

    ResultActions doRequest(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
    }

    MockHttpServletRequestBuilder createJsonPostRequest(ApiEmailRequest request) {
        post(EmailController.EMAIL_API_PATH).contentType(APPLICATION_JSON).content(toJson(request))
    }

}