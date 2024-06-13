package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender sender;
    private Configuration mailConfiguration; //freemarker template package
    private ConfirmationService confirmationService;

    public EmailServiceImpl(JavaMailSender sender, Configuration mailConfiguration, ConfirmationService confirmationService) {
        this.sender = sender;
        this.mailConfiguration = mailConfiguration;
        this.confirmationService = confirmationService;

        mailConfiguration.setDefaultEncoding("UTF-8");
        mailConfiguration.setTemplateLoader(
                new ClassTemplateLoader(EmailServiceImpl.class, "/mail/")
        );
    }

    @Override
    public void sendConfirmationEmail(User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateMessageText(user);

        try {
            helper.setFrom("leafgrow.project@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Registration");
            helper.setText(text, true); // text has html format
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sender.send(message);
    }

    @Override
    public void sendImportantEmail(User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateImportantMessageText(user);

        try {
            helper.setFrom("leafgrow.project@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Advice!");
            helper.setText(text, true); // text has html format
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sender.send(message);
    }

    private String generateMessageText(User user) {
        try {
            Template template = mailConfiguration
                    .getTemplate("confirm_registration_mail.ftlh"); //freemarker template package
            String code = confirmationService.generateConfirmationCode(user);

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());
            //model.put("link", "http://localhost:8080/api/register/confirm?code=" + code); //name server
            model.put("link", "https://leafgrow-app-foign.ondigitalocean.app/#/betweenpage/register/confirm?code=" + code); //for Iren
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateImportantMessageText(User user) {
        try {
            Template template = mailConfiguration
                    .getTemplate("important_message_mail.ftlh"); //freemarker template package
            String code = confirmationService.generateConfirmationCode(user);

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}