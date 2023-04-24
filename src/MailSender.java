import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailSender {

    public static void sendMail(String sender, String password, String receiver, Menu menu) {

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", true);

        // Get the Session and pass the username and the password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(sender, password);

            }

        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            message.setSubject(menu.getEmailSubject());

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            //tries to send file
            try {
                File f =new File("WeatherReport.csv");
                attachmentPart.attachFile(f);
                textPart.setText(menu.getEmailBody());
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println(menu.getSendingEmail());
            // Send message
            Transport.send(message);
            System.out.println(menu.getEmailSent());

        } catch (MessagingException e) {
            System.out.println(menu.getEmailNotSent());
        }

    }

}