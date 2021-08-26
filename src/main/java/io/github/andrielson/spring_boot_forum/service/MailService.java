package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.exceptions.ForumException;
import io.github.andrielson.spring_boot_forum.model.NotificationEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(@NonNull final NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            var messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springbootforum@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException exception) {
            throw new ForumException("Exception occurred when sending email to " + notificationEmail.getRecipient());
        }

    }
}
