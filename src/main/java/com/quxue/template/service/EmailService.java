package com.quxue.template.service;

public interface EmailService {
    void send(String subject, String message,String target);
}
