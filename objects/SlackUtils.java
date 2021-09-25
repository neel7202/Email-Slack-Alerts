package com.alertslambda.objects;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.alertslambda.exceptions.SlackMessageException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SlackUtils {
    private static final String slackWebhookUrl = "https://hooks.slack.com/services/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";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendMessage(SlackMessage message) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(slackWebhookUrl);

        String json = null;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (IOException e) {
            throw new SlackMessageException("Error writing slack message as a string", e);
        }

        StringEntity entity = null;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new SlackMessageException("Error creating the string entity", e);
        }

        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            throw new SlackMessageException("Error using httpPost method", e);
        }
    }
}
