package com.pxp.SQLite.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.pxp.SQLite.demo.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.pxp.SQLite.demo.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Mukuljaiswal
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
     *
     * @param javaMailSender
     */
    @Autowired
    public MailService(JavaMailSender javaMailSender, ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
    }

    private final ResourceLoader resourceLoader;

    /**
     *
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
    /**
     * This function is used to send mail that contains a attachment.
     *
     * @param user
     * @throws MailException
     * @throws MessagingException
     */
    public void sendEmailWithAttachment(User user) throws MailException, MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmailAddress());
        helper.setSubject("Testing Mail API with Attachment");
        helper.setText("Please find the attached document below.");


        ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
        helper.addAttachment(classPathResource.getFilename(), classPathResource);

        javaMailSender.send(message);
    }

}