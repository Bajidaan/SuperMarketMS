package com.bajidan.supermarketms.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailUtil {


    private final JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> mailList) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("admincontrol@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        if (mailList != null && mailList.size() > 0) {
            simpleMailMessage.setCc(listToArray(mailList));
        }

        mailSender.send(simpleMailMessage);
    }

    public void sendPasswordMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("admincontrol@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String text = "<h6>Your login credentials</h6>" +
                "<p>email:  </b>" + to + "</p>"
                + " <br><b>Password: </b> " + password
                + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        
        message.setContent(text, "text/html");
        mailSender.send(message);
    }

    private String[] listToArray(List<String> list) {
        String[] array = new String[list.size()];

        int index = 0;
        for (String item : list) {
            array[index++] = item;
        }
        return array;
    }
}
