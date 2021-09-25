package com.alertslambda.objects;

import java.util.Map;

public class MailWrapper {
    public String type;
    public Map<String, Object> mail;

    public MailWrapper(String type, Map<String, Object> mail) {
        this.type = type;
        this.mail = mail;
    }
}