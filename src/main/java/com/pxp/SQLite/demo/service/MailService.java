package com.pxp.SQLite.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.pxp.SQLite.demo.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *
 * @author BijitMondal
 *
 */
@Service
public class MailService {

    /*
     * The Spring Framework provides an easy abstraction for sending email by using
     * the JavaMailSender interface, and Spring Boot provides auto-configuration for
     * it as well as a starter module.
     */
    private JavaMailSender javaMailSender;

    /**
     * @param javaMailSender
     */
    @Autowired
    public MailService(JavaMailSender javaMailSender, ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
    }

    private final ResourceLoader resourceLoader;

    /**
     * @param participant
     * @throws MailException
     */

    public void sendNotificationEmail(Participant participant) throws MailException, MessagingException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(participant.getEmail());
        helper.setSubject("Registration Successful to Neuroverse");

        String htmlContent = new String(resourceLoader.getResource("classpath:mail_template/Registration-Template.html").getInputStream().readAllBytes());
        htmlContent = htmlContent.replace("{{name}}", participant.getFirstName());

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}