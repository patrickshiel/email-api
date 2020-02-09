package io.shiel.email.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;
import static org.apache.http.HttpStatus.SC_MULTIPLE_CHOICES;
import static org.apache.http.HttpStatus.SC_OK;

public class HttpUtil {

    public static boolean is2xxSuccessfulResponse(HttpResponse response) {
        return nonNull(response) && nonNull(response.getStatusLine())
                && is2xxSuccessfulStatusCode(response.getStatusLine().getStatusCode());
    }

    public static boolean is2xxSuccessfulStatusCode(int statusCode) {
        return statusCode >= SC_OK && statusCode < SC_MULTIPLE_CHOICES;
    }

    public static String getContentBodyAsString(HttpResponse response) throws IOException {
        return IOUtils.toString(response.getEntity().getContent(), UTF_8);
    }

}
