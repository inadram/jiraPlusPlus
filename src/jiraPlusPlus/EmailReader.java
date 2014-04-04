package jiraPlusPlus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;

public class EmailReader implements IImageLocation {

    private final String emailAddress;
    private final String emailPassword;
    private final String serverUrl;
    private final String tmpFileLocation;
    private final Properties props;
    private final Store store;
    private Folder inbox;

    public EmailReader(String emailAddress, String emailPassword, String serverUrl, String tmpFileLocation) throws Exception {
        this.emailAddress = emailAddress;
        this.emailPassword = emailPassword;
        this.serverUrl = serverUrl;
        this.tmpFileLocation = tmpFileLocation;
        this.props = new Properties();
        this.props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(this.props, null);
        this.store = session.getStore();
        this.connectToStore();
        this.connectToInbox();
    }

    private void connectToStore() throws MessagingException {
        this.store.connect(this.serverUrl, this.emailAddress, this.emailPassword);
        System.out.println("Connection to store established");
    }

    private void connectToInbox() throws MessagingException {
        this.inbox = store.getFolder("INBOX");
        this.inbox.open(Folder.READ_WRITE);
        System.out.println("Connecting to inbox");
    }

    @Override
    public File getOldestUnprocessedImage() throws Exception {
        if (!inbox.isOpen()) {
            System.out.println("Inbox is not open");
            this.connectToInbox();
        }

        Message[] messageList = inbox.getMessages(1, inbox.getMessageCount());
        File file = null;
        for (int i = 1; i <= messageList.length; ++i) {
            Message message = inbox.getMessage(i);
            if (message.isExpunged()) {
                continue;
            }
            InputStream is = getAttachmentForMessage(message);
            message.setFlag(Flags.Flag.DELETED, true);
            if (is != null) {
                file = writeStreamToFile(is);
                return file;
            }
            this.inbox.expunge();
        }

        return file;
    }

    private InputStream getAttachmentForMessage(Message message) throws Exception {
        System.out.println("Message subject: " + message.getSubject());
        Multipart multipart = (Multipart) message.getContent();

        for (int j = 0; j < multipart.getCount(); ++j) {
            BodyPart bodyPart = multipart.getBodyPart(j);

            String disposition = bodyPart.getDisposition();
            String fileName = bodyPart.getFileName();

            boolean inlineOrAttachment = Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition);
            if (inlineOrAttachment && fileName.length() > 0) {
                return bodyPart.getInputStream();
            }
        }
        return null;
    }

    private File writeStreamToFile(InputStream is) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("YYMMDD-HHmmss-SSS");
        String uniquenessNaming = format.format(new Date());

        File file = new File(this.tmpFileLocation + "/" + uniquenessNaming + ".jpg");
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
            fos.write(buf, 0, bytesRead);
        }
        fos.flush();
        fos.close();
        return file;
    }
}
