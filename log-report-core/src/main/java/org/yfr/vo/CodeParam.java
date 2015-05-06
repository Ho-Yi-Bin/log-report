
package org.yfr.vo;

import java.util.List;

/**
 * code parameter value object . 
 * 
 * <p>setup time <b>Nov 25, 2014 9:03:14 AM .</b></p>
 *
 * @author Vincent Huang
 */
public class CodeParam {

	/** ignore author list . */
	private List<String> ignoreAuthorList;

	/** permit module name list . */
	private List<String> permitModuleNameList;

	/** log template 1 . */
	private String logTemplate1;

	/** log template 2 . */
	private String logTemplate2;

	/**
	 * constructor of CodeParam .
	 *
	 * @param ignoreAuthorList - the ignore author list <b>List&lt;String&gt;</b> to set
	 * @param permitModuleNameList - the permit module name list <b>List&lt;String&gt;</b> to set
	 * @param logTemplate1 - the log template 1 <b>String</b> to set 
	 * @param logTemplate2 - the log template 2 <b>String</b> to set
	 */
	public CodeParam(List<String> ignoreAuthorList, List<String> permitModuleNameList, String logTemplate1, String logTemplate2) {
		this.ignoreAuthorList = ignoreAuthorList;
		this.permitModuleNameList = permitModuleNameList;
		this.logTemplate1 = logTemplate1;
		this.logTemplate2 = logTemplate2;
	}

	/**
	 * getter of ignoreAuthorList .
	 *
	 * @return ignoreAuthorList
	 */
	public List<String> getIgnoreAuthorList() {
		return ignoreAuthorList;
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
	 * getter of logTemplate1 .
	 *
	 * @return logTemplate1
	 */
	public String getLogTemplate1() {
		return logTemplate1;
	}

	/**
	 * getter of logTemplate2 .
	 *
	 * @return logTemplate2
	 */
	public String getLogTemplate2() {
		return logTemplate2;
	}

}