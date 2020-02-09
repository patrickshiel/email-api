package io.shiel.email.util


import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.*

@Unroll
class HttpUtilTest extends Specification {

    def "is2xxSuccessfulResponse will correctly identify successful HTTP Status"(HttpStatus status, boolean expectedSuccess) {
        given:
        HttpResponse response = Mock()
        response.getStatusLine() >> Mock(StatusLine)
        response.getStatusLine().getStatusCode() >> status.value()

        when:
        def isSuccess = HttpUtil.is2xxSuccessfulResponse(response)

        then:
        isSuccess == expectedSuccess

        where:
        status                | expectedSuccess
        OK                    | true
        ACCEPTED              | true
        MULTIPLE_CHOICES      | false
        BAD_REQUEST           | false
        INTERNAL_SERVER_ERROR | false
    }

    def "is2xxSuccessfulStatusCode will correctly identify successful HTTP Status"(int statusCode, boolean expectedSuccess) {
        when:
        def isSuccess = HttpUtil.is2xxSuccessfulStatusCode(statusCode)

        then:
        isSuccess == expectedSuccess

        where:
        statusCode                    | expectedSuccess
        OK.value()                    | true
        ACCEPTED.value()              | true
        MULTIPLE_CHOICES.value()      | false
        BAD_REQUEST.value()           | false
        INTERNAL_SERVER_ERROR.value() | false
    }

}