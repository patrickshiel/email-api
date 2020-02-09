package io.shiel.email.api.controller;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.api.model.ApiEmailResponse;
import io.shiel.email.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static io.shiel.email.api.controller.EmailController.EMAIL_API_PATH;
import static org.springframework.http.HttpStatus.valueOf;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api("EmailController - REST API to send Email to Email Provider Services")
@RestController
@RequestMapping(path = EMAIL_API_PATH)
public class EmailController {

    public static final String EMAIL_API_PATH = "/email";

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ApiOperation(
            value = "Send Email Request",
            response = ApiEmailResponse.class,
            tags = "email"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiEmailResponse> sendEmail(@RequestBody @Valid ApiEmailRequest apiEmailRequest) {
        ApiEmailResponse apiEmailResponse = emailService.sendEmail(apiEmailRequest);
        return new ResponseEntity<>(apiEmailResponse, getHttpStatus(apiEmailResponse));
    }

    private HttpStatus getHttpStatus(ApiEmailResponse apiEmailResponse) {
        return valueOf(apiEmailResponse.getProviderResponses().stream()
                .sorted((r1, r2) -> r2.getResponseTime().compareTo(r1.getResponseTime()))
                .findFirst().get().getResponseCode());
    }

}