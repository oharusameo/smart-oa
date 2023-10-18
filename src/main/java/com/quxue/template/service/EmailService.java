package com.quxue.template.service;

import com.quxue.template.domain.dto.EmailDTO;
import com.quxue.template.domain.pojo.Result;

public interface EmailService {
    Result send(EmailDTO emailDTO);
}
