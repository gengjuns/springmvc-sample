package com.saas.core.util;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

//import org.mortbay.util.Log;

/**
 * Outflow class to send mail Via gmail SMTP
 * 
 * @author jimin
 * 
 */
public class EmailUtils {
	
    // used for email services
    public static final String EMAIL_SMTP_HOST = "mail.smtp.host";
    public static final String EMAIL_SMTP_PORT = "mail.smtp.port";
    public static final String EMAIL_SMTP_SENDER_EMAIL = "mail.smtp.sender_email";
    public static final String EMAIL_SMTP_SENDER_NAME = "mail.smtp.sender_name";
    public static final String EMAIL_PROPERTIES = "mail.smtp.host";
    public static final String EMAIL_TEMPLATE_HELPER_CLASS = "mail.template.helper.class";
    public static final String EMAIL_HTML_CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String EMAIL_CHARSET_UTF8 = "UTF-8";
    public static final String EMAIL_SMTP_AUTH = "mail.smtp.auth";

    public static final String DEFAULT_EMAIL_SMTP_HOST = "localhost";
    public static final int DEFAULT_EMAIL_SMTP_PORT = 25;
    public static final String DEFAULT_EMAIL_SMTP_SENDER_EMAIL = "email@localhost";
    public static final String DEFAULT_EMAIL_SMTP_SENDER_NAME = "Email Process";

    public static final String EMAIL_SCHEDULER_HELPER_CLASS = "mail.scheduler.helper.class";
    public static final String EMAIL_ARCHIVER_CLASS = "mail.archiver.class";
    public static final String SEND_EMAIL_AS_ASYNC = "SendMailAsAsync";
    public static final String NUM_OF_EMAILS_PER_BATCH = "NumOfEmailsPerBatch";
    public static final String BATCH_EMAILS_INTERVAL_TIME_IN_MS = "BatchEmailsIntervalTimeInMs";

    public static final String EMAIL_AUDIT = "mail.audit";
    public static final String EMAIL_AUDIT_TRUE = "true";
    public static final String EMAIL_AUDIT_FALSE = "false";

    public static final String PROP_EMAIL_SMTP_AUTH_REQUIRED = "mail.smtp.auth.required";
    public static final String PROP_EMAIL_SMTP_AUTH_USER = "mail.smtp.auth.user";
    public static final String PROP_EMAIL_SMTP_AUTH_PASSWORD = "mail.smtp.auth.password";
    
	private String smtpHostName; // gmail-smtp.l.google.com
	private String smtpAuthUser;
	private String smtpAuthPwd;
	private String smtpFrom;
	private int smtpPort;

	private Session session;

	/**
	 * Constructor from SMTP host name and authenticator user id and password
	 * 
	 * @param SMTPHost
	 * @param authUser
	 * @param authPwd
	 */
	public EmailUtils(String smtpHostName, int smtpPort, String smtpAuthUser,
			String smtpAuthPwd) {
		this.smtpHostName = smtpHostName;
		this.smtpAuthUser = smtpAuthUser;
		this.smtpAuthPwd = smtpAuthPwd;
		this.smtpPort = smtpPort;
		this.smtpFrom = null;
		setSession();
	}
	
	/**
	 * Constructor from SMTP host name and authenticator user id and password
	 * 
	 * @param SMTPHost
	 * @param authUser
	 * @param authPwd
	 */
	public EmailUtils(String smtpHostName, int smtpPort, String smtpAuthUser,String smtpAuthPwd,String smtpFrom) {
	    this.smtpHostName = smtpHostName;
	    this.smtpAuthUser = smtpAuthUser;
	    this.smtpAuthPwd = smtpAuthPwd;
	    this.smtpPort = smtpPort;
	    this.smtpFrom = smtpFrom;
	    
	    setSession();
	}

	/**
	 * set SMTP session for the outflow gateway
	 */
	public void setSession() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.port", smtpPort);

		// for bounce email
		if(smtpFrom != null){
		    props.put("mail.smtp.from", smtpFrom);
		}
		Authenticator auth = new SMTPAuthenticator();
		session = Session.getInstance(props, auth);
		session.setDebug(false);
	}

	public boolean postMailwithAttachment(String Header, String Headertxt,
			String recipients[], String[] ReplyTo, String CC[], String BCC[],
			String subject, String message, String from, File[] attachmentFile)
			throws MessagingException {

		// create a message
		Message msg = new MimeMessage(session);
		// set the from and to address
		try {
			// Set Header
			msg.setHeader(Header, Headertxt);

			// Set Recipients
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			InternetAddress[] addressReplyTo = new InternetAddress[ReplyTo.length];
			for (int i = 0; i < ReplyTo.length; i++) {
				addressReplyTo[i] = new InternetAddress(ReplyTo[i]);
			}
			msg.setReplyTo(addressReplyTo);

			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			if (CC != null) {
				InternetAddress[] addressCC = new InternetAddress[CC.length];
				for (int i = 0; i < CC.length; i++) {
					addressCC[i] = new InternetAddress(CC[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, addressCC);
			}

			if (BCC != null) {
				InternetAddress[] addressBCC = new InternetAddress[BCC.length];
				for (int i = 0; i < BCC.length; i++) {
					addressBCC[i] = new InternetAddress(BCC[i]);
				}
				msg.setRecipients(Message.RecipientType.BCC, addressBCC);
			}

			// Setting the Subject
			msg.setSubject(subject);

			Multipart mp;
			String cs = MimeUtility
					.mimeCharset(EMAIL_CHARSET_UTF8);
			mp = new MimeMultipart();
			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.setText(message, cs);
			mbp2.setContent(message, "text/html");
			mp.addBodyPart(mbp2);
			// Add the Multipart to the message
			msg.setContent(mp);
			/*if (attachmentFile.length == 0) {
				mp = new MimeMultipart();
				MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setText(message, cs);
				mp.addBodyPart(mbp2);
				// Add the Multipart to the message
				msg.setContent(mp);
			} else {
				// Create the Multipart to add the message body
				mp = new MimeMultipart("alternative");
				// Add the mail body to the message object
				MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setText(message, cs);
				mp.addBodyPart(mbp2);
				// Create the Multipart and add the message body Multipart and
				// list of
				// attachments to the message object
				Multipart mp2 = new MimeMultipart("mixed");
				MimeBodyPart wrap = new MimeBodyPart();
				wrap.setContent(mp); // add message body Multipart
				mp2.addBodyPart(wrap);
				// create the additional message part for attachments
				MimeBodyPart mbp1;
				FileDataSource fds;
				for (int i = 0; i < attachmentFile.length; i++) {
					mbp1 = new MimeBodyPart();
					// Attach the file to the message
					fds = new FileDataSource(attachmentFile[i]);
					mbp1.setDataHandler(new DataHandler(fds));
					mbp1.setFileName(attachmentFile[i].getName());
					mp2.addBodyPart(mbp1);
				}
				// Add the Multipart to the message
				msg.setContent(mp2);
			}*/

			// Send
			Transport.send(msg);
			return true;
		} catch (AddressException e) {
			return false;
		}
	}


	/**
	 * SMTP authenticator class
	 * 
	 * @author jimin
	 * 
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = smtpAuthUser;
			String password = smtpAuthPwd;
			return new PasswordAuthentication(username, password);
		}
	}
}
