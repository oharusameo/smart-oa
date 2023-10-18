package com.quxue.template.service;

import com.quxue.template.domain.pojo.Result;

public interface EmailService {
    Result send(String subject, String message,String target);
}
