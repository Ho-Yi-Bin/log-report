
package org.yfr.common.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yfr.common.service.SendMailService;

/**
 * send mail service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:36:10 AM .</b></p>
 *
 * @author Vincent Huang
 */
public class SendMailServiceImpl implements SendMailService {

	Log logger = LogFactory.getLog(this.getClass());

	Boolean isUnitTestMode = Boolean.FALSE;

	Properties mailProp;
	String accountName, password, fromAddress, subject, mailContent;
	List<String> mailAddressList, ccMailAddressList, attachFileNameList;

	@Override
	public Boolean sendMailWithAttachFiles() {
		Boolean sendMailResult = Boolean.FALSE;

		Session session = Session.getInstance(mailProp, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(accountName, password);
			}
		});

		try {
			/* create mail message, set sender address, sent date, subject, receiver address, cc address . */
			Message mail = new MimeMessage(session);
			mail.setFrom(new InternetAddress(fromAddress));
			mail.setSentDate(new Date());
			mail.setSubject(subject);

			InternetAddress[] toAddress = new InternetAddress[mailAddressList.size()];
			for (int i = 0; i < mailAddressList.size(); i++) {
				toAddress[i] = new InternetAddress(mailAddressList.get(i));
			}
			mail.setRecipients(Message.RecipientType.TO, toAddress);

			InternetAddress[] ccAddress = new InternetAddress[ccMailAddressList.size()];
			for (int i = 0; i < ccMailAddressList.size(); i++) {
				ccAddress[i] = new InternetAddress(ccMailAddressList.get(i));
			}
			mail.setRecipients(Message.RecipientType.CC, ccAddress);

			/* create mail multipart, set mail content, attach file . */
			Multipart multipart = new MimeMultipart();

			MimeBodyPart mailContentBody = new MimeBodyPart();
			mailContentBody.setContent(mailContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(mailContentBody);

			if (attachFileNameList != null && attachFileNameList.size() > 0) {
				MimeBodyPart[] attachFileBody = new MimeBodyPart[attachFileNameList.size()];
				for (int i = 0; i < attachFileNameList.size(); i++) {
					attachFileBody[i] = new MimeBodyPart();
					FileDataSource fds;
					if (isUnitTestMode) {
						fds = new FileDataSource(new File(Thread.currentThread().getContextClassLoader().getResource(attachFileNameList.get(i)).getFile()));
					} else {
						fds = new FileDataSource(new File(attachFileNameList.get(i)));
					}
					attachFileBody[i].setDataHandler(new DataHandler(fds));
					attachFileBody[i].setFileName(fds.getName());
					multipart.addBodyPart(attachFileBody[i]);
				}
			}

			mail.setContent(multipart);

			/* send mail . */
			if (!isUnitTestMode) {
				Transport.send(mail);
			}

			sendMailResult = Boolean.TRUE;
			logger.info("Send Mail Success !");
		} catch (MessagingException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return sendMailResult;
	}

	@Override
	public void setIsUnitTestMode(Boolean isUnitTestMode) {
		this.isUnitTestMode = isUnitTestMode;
	}

	@Override
	public void setMailProp(Properties mailProp) {
		this.mailProp = mailProp;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Override
	public void setMailAddressList(List<String> mailAddressList) {
		this.mailAddressList = mailAddressList;
	}

	@Override
	public void setCcMailAddressList(List<String> ccMailAddressList) {
		this.ccMailAddressList = ccMailAddressList;
	}

	@Override
	public void setAttachFileNameList(List<String> attachFileNameList) {
		this.attachFileNameList = attachFileNameList;
	}

}
