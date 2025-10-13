package murach.checkout.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUtil {
    private static final String FROM = "yourgmail@gmail.com";      // đổi
    private static final String APP_PASSWORD = "your_app_password"; // 16 ký tự (App Password)

    private static Session session() {
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        return Session.getInstance(p, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, APP_PASSWORD);
            }
        });
    }

    public static void send(String to, String subject, String html) throws MessagingException {
        Message msg = new MimeMessage(session());
        msg.setFrom(new InternetAddress(FROM));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }
}
