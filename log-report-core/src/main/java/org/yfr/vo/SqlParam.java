
package org.yfr.vo;

import java.util.List;

/**
 * sql parameter value object . 
 * 
 * <p>setup time <b>Dec 17, 2014 2:42:38 PM .</b></p>
 *
 * @author Vincent Huang
 */
public class SqlParam {

	/** permit module name list . */
	private List<String> permitModuleNameList;

	/** permit DB user . */
	private List<String> permitDbUserList;

	/** log template line 1 . */
	private String logTemplateLine1;

	/** log template line 2 . */
	private String logTemplateLine2;

	/** commit notify mail content . */
	private String commitNotifyMailContent;

	/** commit notify mail address list . */
	private List<String> commitNotifyMailAddressList;

	/** commit notify cc address list . */
	private List<String> commitNotifyCcAddressList;

	/**
	 * constructor of SqlParam .
	 *
	 * @param permitModuleNameList - the permit module name list <b>List&lt;String&gt;</b> to set
	 * @param permitDbUserList - the permit DB user list <b>List&lt;String&gt;</b> to set
	 * @param logTemplateLine1 - the log template line 1 <b>String</b> to set
	 * @param logTemplateLine2 - the log template line 2 <b>String</b> to set
	 * @param commitNotifyMailContent - the commit notify mail content <b>String</b> to set
	 * @param commitNotifyMailAddressList - the commit notify mail address list <b>List&lt;String&gt;</b> to set
	 * @param commitNotifyCcAddressList - the commit notify cc address list <b>List&lt;String&gt;</b> to set
	 */
	public SqlParam(List<String> permitModuleNameList, List<String> permitDbUserList, String logTemplateLine1, String logTemplateLine2, String commitNotifyMailContent,
	        List<String> commitNotifyMailAddressList, List<String> commitNotifyCcAddressList) {
		this.permitModuleNameList = permitModuleNameList;
		this.permitDbUserList = permitDbUserList;
		this.logTemplateLine1 = logTemplateLine1;
		this.logTemplateLine2 = logTemplateLine2;
		this.commitNotifyMailContent = commitNotifyMailContent;
		this.commitNotifyMailAddressList = commitNotifyMailAddressList;
		this.commitNotifyCcAddressList = commitNotifyCcAddressList;
	}

	/**
	 * getter of permitModuleNameList .
	 *
	 * @return permitModuleNameList
	 */
	public List<String> getPermitModuleNameList() {
		return permitModuleNameList;
	}

	/**
	 * getter of permitDbUserList .
	 *
	 * @return permitDbUserList
	 */
	public List<String> getPermitDbUserList() {
		return permitDbUserList;
	}

	/**
	 * getter of logTemplateLine1 .
	 *
	 * @return logTemplateLine1
	 */
	public String getLogTemplateLine1() {
		return logTemplateLine1;
	}

	/**
	 * getter of logTemplateLine2 .
	 *
	 * @return logTemplateLine2
	 */
	public String getLogTemplateLine2() {
		return logTemplateLine2;
	}

	/**
	 * getter of commitNotifyMailContent .
	 *
	 * @return commitNotifyMailContent
	 */
	public String getCommitNotifyMailContent() {
		return commitNotifyMailContent;
	}

	/**
	 * getter of commitNotifyMailAddressList .
	 *
	 * @return commitNotifyMailAddressList
	 */
	public List<String> getCommitNotifyMailAddressList() {
		return commitNotifyMailAddressList;
	}

	/**
	 * getter of commitNotifyCcAddressList .
	 *
	 * @return commitNotifyCcAddressList
	 */
	public List<String> getCommitNotifyCcAddressList() {
		return commitNotifyCcAddressList;
	}

}
