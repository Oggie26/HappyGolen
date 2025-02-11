package store.makejewelry.BE.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import store.makejewelry.BE.model.Email.EmailDetail;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailTemplate(EmailDetail emailDetail) {
        try {
            Context context = new Context();
            context.setVariable("name", emailDetail.getFullName());
            context.setVariable("button", emailDetail.getButton());
            context.setVariable("link", emailDetail.getLink());

            String text = templateEngine.process("emailtemplate", context);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            // Setting up necessary details
            mimeMessageHelper.setFrom("admin@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }

}