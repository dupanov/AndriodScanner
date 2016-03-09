package message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by admin on 15.01.2016.
 */
public class MailFiles {
    public void mail() {
        File[] mailFiles = getPertinentMailFiles();
        String host = "host.com";
        java.util.Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        for (File tmpFile : mailFiles) {
            MimeMessage email = null;
            try {
                FileInputStream fis = new FileInputStream(tmpFile);
                email = new MimeMessage(session, fis);
                System.out.println("content type: " + email.getContentType());
                System.out.println("\nsubject: " + email.getSubject());
                System.out.println("\nrecipients: " + Arrays.asList(email.getRecipients(Message.RecipientType.TO)));
            } catch (MessagingException e) {
                throw new IllegalStateException("illegal state issue", e);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("file not found issue issue: " + tmpFile.getAbsolutePath(), e);
            }
        }
    }
}
