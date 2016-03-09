package message;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by admin on 15.01.2016.
 */
public class Message {

   // private static final Provider IMAP_STORE = ;
    Context ctx = new InitialContext();
    Session mailSession = (javax.mail.Session) ctx.lookup("java:comp/env/mail/Session");
    Store store = mailSession.getStore(IMAP_STORE);
    store.connect();
    Folder inbox = store.getFolder(INBOX_FOLDER);
    inbox.open(Folder.READ_ONLY);

    Message[] messages = inbox.geTMessages();
// do somethinf with messages
    this.content = EmailUtils.extractClearText(m, null);
    inbox.close(false);
    store.close();

}
