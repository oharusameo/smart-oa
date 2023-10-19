package com.quxue.template.service;

import org.apache.commons.mail.HtmlEmail;

public interface EmailService {
    void send(String subject, String message, String target);

    void reSend(String subject, String message, String target);

}
