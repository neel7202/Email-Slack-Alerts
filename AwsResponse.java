package com.alertslambda.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AWS Lambda response object
 */
@Data
@AllArgsConstructor
public class AwsResponse {
    private int statusCode;
    private String body;

    public AwsResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public AwsResponse() {
        this.statusCode = 200;
    }
}