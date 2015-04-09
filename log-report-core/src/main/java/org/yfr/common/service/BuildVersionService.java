
package org.yfr.common.service;

import org.yfr.common.constant.ProgramState;

/**
 * build version service interface .
 * 
 * <p>setup time <b>Dec 29, 2014 11:35:48 AM .</b></p>
 *
 * @author Vincent Huang
 */
public interface BuildVersionService {

	/**
	 * update the code tag with buildId .
	 * 
	 * @param buildId - the build id <b>String</b> to set
	 * 
	 * @return program state
	 */
	ProgramState updateCodeTagName(String buildId);

	/**
	 * update the sql tag with buildId .
	 * 
	 * @param buildId - the build id <b>String</b> to set
	 * 
	 * @return program state
	 */
	ProgramState updateSqlTagName(String buildId);

}
