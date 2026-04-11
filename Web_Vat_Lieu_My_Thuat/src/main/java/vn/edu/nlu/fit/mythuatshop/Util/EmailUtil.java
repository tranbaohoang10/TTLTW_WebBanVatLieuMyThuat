package vn.edu.nlu.fit.mythuatshop.Util;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class EmailUtil {

    private static final Properties config = new Properties();

    static {
        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file cấu hình");
            }
            config.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc file", e);
        }
    }

    private static final String email = config.getProperty("mail.username");
    private static final String password = config.getProperty("mail.password");

    public static String getAdminEmail() {
        String adminEmail = config.getProperty("mail.admin");
        return (adminEmail != null && !adminEmail.isBlank()) ? adminEmail : email;
    }

    public static void send(String to, String subject, String content) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email, "MyThuatShop", StandardCharsets.UTF_8.name()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject(subject, StandardCharsets.UTF_8.name());
            message.setText(content, StandardCharsets.UTF_8.name());

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendHtml(String to, String subject, String htmlContent) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email, "MyThuatShop", StandardCharsets.UTF_8.name()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject(subject, StandardCharsets.UTF_8.name());
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}