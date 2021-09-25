package com.alertslambda.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.alertslambda.infrastructure.retry.AsyncRetryWrapper;
import com.alertslambda.infrastructure.retry.Retry;
import com.alertslambda.infrastructure.web.RestQuery;
import com.alertslambda.infrastructure.web.SimpleRestClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.alertslambda.infrastructure.ConstsUtils.*;

/**
 * Sending emails to be sent to the Mailer Lambda
 */
@Slf4j
public class MailerService {
    private final SimpleRestClient restClient;
    private final ObjectMapper mapper;
    private final Retry retry;
    private final String endpoint = "https://o5vhu598g1.execute-api.us-east-1.amazonaws.com/dev//mailerHandlerLambda/events";


    public MailerService() {
        this.restClient = new SimpleRestClient();
        this.mapper = new ObjectMapper();
        this.retry = new AsyncRetryWrapper();
    }

    @SneakyThrows(IOException.class)
    public boolean send(String email, Map<String, String> main, Map<String, String> substitution) {
        Map<String, String> headers = buildMailHeaders();

        MailWrapper finalMail = buildMailerRequest(email, main, substitution);

        RestQuery query = RestQuery.builder()
                .url(endpoint)
                .payload(mapper.writeValueAsString(finalMail))
                .headers(headers)
                .build();

        Response response = executePostQuery(query);
        System.out.println(response.code());
        return response.code() == HttpStatus.SC_OK;
    }

    private MailWrapper buildMailerRequest(String email, Map<String, String> main, Map<String, String> substitution) {
        Map<String, Object> body = new HashMap<>();
        body.put(FROM, HI_MAIL);
        body.put(TO, email);
        body.put(SUBJECT, main.get(SUBJECT));
        body.put("content", main.get("content"));
        body.put("substitutions", new HashMap<>());

        return new MailWrapper("SENDGRID_TEXT", body);
    }

    private Map<String, String> buildMailHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(X_API_KEY, "l0J2VNmYiT3X6eM9NRoY7Ath152pyGD6DBstOYIa");
        header.put(CONTENT_TYPE, "text/html");

        return header;
    }

    public Response executePostQuery(RestQuery query) {
        return retry.retryGetNow(() -> restClient.postQuery(query));
    }
}
