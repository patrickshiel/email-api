package io.shiel.email.service;

import io.shiel.email.api.model.ApiEmailRequest;
import io.shiel.email.api.model.ApiEmailResponse;


public interface EmailService {

    ApiEmailResponse sendEmail(ApiEmailRequest request);

}
