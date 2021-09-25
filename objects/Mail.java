package com.alertslambda.objects;

import java.util.Map;

public class Mail {
    public String from;
    public String to;
    public String subject;
    public String content;
    public Map<String, String> substitution;

    public Mail(String from, String to, String subject, String content, Map<String, String> substitution) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.substitution = substitution;
    }
}