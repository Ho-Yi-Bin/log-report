
package org.yfr.service;

import org.yfr.common.service.UnitTestService;

/**
 * code generate log report service interface .
 * 
 * <p>setup time <b>Dec 29, 2014 11:19:26 AM .</b></p>
 *
 * @author Vincent Huang
 */
public interface CodeGenLogReportService extends UnitTestService {

	/**
	 * generate simple report .
	 * 
	 * @param sheetName - the sheet name <b>String</b> to set
	 * @param startRev - the start revision number <b>String</b> to set
	 * @param endRev - the end revision number <b>String</b> to set
	 * 
	 * @return generate success or not
	 */
	Boolean genSimpleReport(String sheetName, String startRev, String endRev);

	/**
	 * generate build report .
	 * 
	 * @param buildId - the build id <b>String</b> to set
	 * 
	 * @return generate success or not
	 */
	Boolean genBuildReport(String buildId);

}
