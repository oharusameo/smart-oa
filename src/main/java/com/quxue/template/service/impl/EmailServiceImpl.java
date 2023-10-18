package com.quxue.template.service.impl;

import com.quxue.template.domain.dto.EmailDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.service.EmailService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailServiceImpl implements EmailService {

/*    @Resource
    private JavaMailSender javaMailSender;*/

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.application.name}")
    private String fromName;


    @Override
    public Result send(EmailDTO emailDTO) {
        String send;
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setStartTLSEnabled(true);
            htmlEmail.setHostName(host);
            htmlEmail.setSmtpPort(port);
            htmlEmail.setAuthentication(from, password);
            htmlEmail.setFrom(from, fromName);
            htmlEmail.setSubject(emailDTO.getSubject());
            htmlEmail.setTextMsg(emailDTO.getMessage());
            htmlEmail.addTo(emailDTO.getTarget());
            send = htmlEmail.send();
        } catch (EmailException e) {
            throw new BusinessException("邮件发送失败");
        }
        return Result.success(send);
    /*        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(form);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(target);
        javaMailSender.send(simpleMailMessage);*/
    }
}
