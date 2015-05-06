
package org.yfr.common.service;

import java.util.List;
import java.util.Properties;

/**
 * send mail service interface which extends unit test business object interface .
 * 
 * <p>setup time <b>Dec 2, 2014 6:10:38 PM .</b></p>
 *
 * @author Vincent Huang
 */
public interface SendMailService extends UnitTestService {

	/**
	 * send mail with attach files .
	 * 
	 * @return send mail success or not
	 */
	Boolean sendMailWithAttachFiles();

	/**
	 * setter of mailProp .
	 *
	 * @param mailProp - the mailProp <b>Properties</b> to set
	 */
	void setMailProp(Properties mailProp);

	/**
	 * setter of accountName .
	 *
	 * @param accountName - the accountName <b>String</b> to set
	 */
	void setAccountName(String accountName);

	/**
	 * setter of password .
	 *
	 * @param password - the password <b>String</b> to set
	 */
	void setPassword(String password);

	/**
	 * setter of fromAddress .
	 *
	 * @param fromAddress - the fromAddress <b>String</b> to set
	 */
	void setFromAddress(String fromAddress);

	/**
	 * setter of subject .
	 *
	 * @param subject - the subject <b>String</b> to set
	 */
	void setSubject(String subject);

	/**
	 * setter of mailContent .
	 *
	 * @param mailContent - the mailContent <b>String</b> to set
	 */
	void setMailContent(String mailContent);

	/**
	 * setter of mailAddressList .
	 *
	 * @param mailAddressList - the mailAddressList <b>List&lt;String&gt;</b> to set
	 */
	void setMailAddressList(List<String> mailAddressList);

	/**
	 * setter of ccMailAddressList .
	 *
	 * @param ccMailAddressList - the ccMailAddressList <b>List&lt;String&gt;</b> to set
	 */
	void setCcMailAddressList(List<String> ccMailAddressList);

	/**
	 * setter of attachFileNameList .
	 *
	 * @param attachFileNameList - the attachFileNameList <b>List&lt;String&gt;</b> to set
	 */
	void setAttachFileNameList(List<String> attachFileNameList);

}
