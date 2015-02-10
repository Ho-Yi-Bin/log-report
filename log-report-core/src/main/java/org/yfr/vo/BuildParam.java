
package org.yfr.vo;

import java.util.List;

/**
 * build parameter value object .
 * 
 * <p>setup time <b>Dec 22, 2014 10:32:15 AM .</b></p>
 *
 * @author Vincent Huang
 */
public class BuildParam {

	/** mail subject . */
	String subject;

	/** mail content . */
	String mailContent;

	/** mail address list . */
	List<String> mailAddressList;

	/** cc address list . */
	List<String> ccAddressList;

	/**
	 * constructor of BuildParam .
	 *
	 * @param subject - subject <b>String</b> to set 
	 * @param mailContent - mail content <b>String</b> to set
	 * @param mailAddressList - mail address list <b>List</b> to set
	 * @param ccAddressList - cc address list <b>List</b> to set
	 */
	public BuildParam(String subject, String mailContent, List<String> mailAddressList, List<String> ccAddressList) {
		this.subject = subject;
		this.mailContent = mailContent;
		this.mailAddressList = mailAddressList;
		this.ccAddressList = ccAddressList;
	}

	/**
	 * getter of subject .
	 *
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * getter of mailContent .
	 *
	 * @return mailContent
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * getter of mailAddressList .
	 *
	 * @return mailAddressList
	 */
	public List<String> getMailAddressList() {
		return mailAddressList;
	}

	/**
	 * getter of ccAddressList .
	 *
	 * @return ccAddressList
	 */
	public List<String> getCcAddressList() {
		return ccAddressList;
	}

}
