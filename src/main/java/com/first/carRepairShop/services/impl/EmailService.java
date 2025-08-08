package com.first.carRepairShop.services.impl;
import com.first.carRepairShop.entity.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")  // This will be injected from application.yml
    private String fromEmail;


    public void sendEmail(String to, NotificationType type, String message) throws MessagingException {

            // 1️⃣ Create a new email message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);//help set email detail

            // 2️⃣ Set sender, recipient, subject, and content
            helper.setFrom(fromEmail);  // Sender (your email)
            helper.setTo(to);  // Recipient's email
            helper.setSubject(type.getDescription());  // Email subject
            helper.setText(message, true);  // Email content (true = HTML format)

            // 3️⃣ Send the email
            mailSender.send(mimeMessage);
            System.out.println(" Email sent successfully to " + to);

    }
}
