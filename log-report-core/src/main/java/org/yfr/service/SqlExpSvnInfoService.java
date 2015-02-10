
package org.yfr.service;

/**
 * sql export SVN info service interface .
 * 
 * <p>setup time <b>Dec 17, 2014 6:38:09 PM .</b></p>
 *
 * @author Vincent Huang
 */
public interface SqlExpSvnInfoService {

	/**
	 * export SVN info to txt .
	 * 
	 * @param startRev - the start revision number <b>String</b> to set
	 * @param endRev - the end revision number <b>String</b> to set
	 * 
	 * @return export success or not
	 */
	Boolean expSvnInfo2Txt(String startRev, String endRev);

	/**
	 * import txt to DB .
	 * 
	 * @param startRev - the start revision number <b>String</b> to set
	 * @param endRev - the end revision number <b>String</b> to set
	 * 
	 * @return import success or not 
	 */
	Boolean impTxt2DB(String startRev, String endRev);

}