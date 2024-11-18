package com.backend.clothingstore.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    private JavaMailSender mailSender;
    @Override
    @Async
    public void sendPassChange(String to, String email){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Request for password change");
            helper.setFrom("fashonclothingstore@gmail.com");
            mailSender.send(message);
        }catch (MessagingException e) {
            LOGGER.error("fail to send email", e);
            throw new IllegalStateException("fail to send email");
        }
    }

    @Override
    @Async
    public void send(String to, String email) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("fashonclothingstore@gmail.com");
            mailSender.send(message);
        }catch (MessagingException e) {
            LOGGER.error("fail to send email", e);
            throw new IllegalStateException("fail to send email");
        }

    }


    @Override
    @Async
    public void sendConfirmOrder(String to, String email, byte[] pdfInvoice) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your order");
            helper.setFrom("fashonclothingstore@gmail.com");

            // Attach the PDF
            helper.addAttachment("Invoice.pdf", new ByteArrayDataSource(pdfInvoice, "application/pdf"));

            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }



}
