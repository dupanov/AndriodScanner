package message;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by admin on 15.01.2016.
 */
public class MessageParser {

    public void handleMessage(Message message) throws IOException, MessagingException {
        Object content = message.getContent();
        if (content instanceof String)
        {
            // handle string
        }
        else if (content instanceof Multipart)
        {
            Multipart mp = (Multipart)content;
            handleMultipart(mp);
            // handle multi part
        }
    }

        public void handleMultipart(Multipart mp) throws MessagingException, IOException {
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
            {
                BodyPart bp = mp.getBodyPart(i);
                Object content = bp.getContent();
                if (content instanceof String)
                {
                    // handle string
                }
                else if (content instanceof InputStream)
                {
                    // handle input stream
                }
                else if (content instanceof Message)
                {
                    Message message = (Message)content);
                    handleMessage(message);
                }
                else if (content instanceof Multipart)
                {
                    Multipart mp2 = (Multipart)content;
                    handleMultipart(mp2);
                }
            }
        }

    public MailList getContent(Message message) throws MessagingException, IOException
    {
        String body = "";
        String from = "";
        ArrayList<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();
        String contentType = message.getContentType();
        Address[] addresses = message.getFrom();
        if(addresses.length == 1)
            from = addresses[0].toString();
        else
        {
            for(int num = 0; num < addresses.length - 1; num++)
                from += addresses[num].toString() + ", ";
            from += addresses[addresses.length].toString();
        }
        if(contentType.contains("TEXT/PLAIN"))
        {
            Object content = message.getContent();
            if(content != null)
                body += content.toString();
        }
        else if(contentType.contains("TEXT/HTML"))
        {
            Object content = message.getContent();
            if(content != null)
                body += Jsoup.parse((String) content).text();
        }
        else if(contentType.contains("multipart"))
        {
            Multipart mp = (Multipart)message.getContent();
            int numParts = mp.getCount();
            for(int count = 0; count < numParts; count++)
            {
                MimeBodyPart part = (MimeBodyPart)mp.getBodyPart(count);
                String content = part.getContent().toString();
                if(MimeBodyPart.ATTACHMENT.equalsIgnoreCase(part.getDisposition()))
                    attachments.add(part);
                else if(part.getContentType().contains("TEXT/HTML"))
                    body += Jsoup.parse(content).text();
                else
                    body += content;
            }
        }
        return new MailList(from, message.getSubject(), body,
                message.getSentDate().toString(), attachments);
    }

    /**
     * Created by admin on 15.01.2016.
     */

