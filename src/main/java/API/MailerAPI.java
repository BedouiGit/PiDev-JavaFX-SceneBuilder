package API;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailerAPI {

    public static void sendMail(String username, String password, String recipientEmail, String subject, String content) {
        // Mailtrap SMTP server configuration
        String host = "smtp.mailtrap.io";
        int port = 2525;

        // Mailtrap credentials
        String user = username;
        String pass = password;

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create Session
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });

        try {
            // Create MimeMessage object
            Message message = new MimeMessage(session);

            // Set From, To, Subject, and Content
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(content);

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
