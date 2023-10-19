package com.quxue.template.service.impl;

import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import com.quxue.template.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    @Value("${spring.mail.retry-times}")
    private Integer retryTimes;

    @Value("${spring.application.name}")
    private String fromName;


    @Async("emailTaskExecutor")
    @Override
    public void send(String subject, String message, String target) {
        log.info("{}正在执行发送邮件任务", Thread.currentThread().getName());
        try {
            HtmlEmail htmlEmail = getHtmlEmail(subject, message, target);
            htmlEmail.send();
        } catch (EmailException e) {
            try {
                HtmlEmail htmlEmail = getHtmlEmail(subject, message, target);
                reSend(htmlEmail, retryTimes);
            } catch (EmailException ex) {
                throw new SystemException("系统出错，重新发送邮件失败");
            }
        }
    /*        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(form);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(target);
        javaMailSender.send(simpleMailMessage);*/
    }

    @Override
    public void reSend(String subject, String message, String target) {
        log.info("正在执行重新发送邮件任务");
        try {
            getHtmlEmail(subject, message, target).send();
        } catch (EmailException e) {
            reSend(subject, message, target);
        }
    }

    /**
     * EmailService内部重发邮件方法
     *
     * @param htmlEmail
     * @param retryTimes
     */

    private void reSend(HtmlEmail htmlEmail, Integer retryTimes) {
        log.info("正在执行重新发送邮件任务");
        try {
            htmlEmail.send();
        } catch (EmailException e) {
            if (retryTimes == 0) {
                throw new BusinessException("发送邮件失败");
            }
            reSend(htmlEmail, --retryTimes);
        }
    }

    private HtmlEmail getHtmlEmail(String subject, String message, String target) throws EmailException {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setStartTLSEnabled(true);
        htmlEmail.setHostName(host);
        htmlEmail.setSmtpPort(port);
        htmlEmail.setAuthentication(from, password);
        htmlEmail.setFrom(from, fromName);
        htmlEmail.setSubject(subject);
        htmlEmail.setTextMsg(message);
        htmlEmail.addTo(target);
        return htmlEmail;
    }

}
