
package org.yfr.service;

import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.UnitTestService;
import org.yfr.entity.CodeLogDetailEntity;

/**
 * code log detail service interface .
 * 
 * <p>setup time <b>Dec 4, 2014 2:53:21 PM .</b></p>
 *
 * @author Vincent Huang
 */
public interface CodeLogDetailService extends UnitTestService {

	/**
	 * verify the information (author and log)
	 * 
	 * @param authorFilePath - the author file path <b>(String)</b> to set
	 * @param logFilePath - the log file path <b>(String)</b> to set
	 * 
	 * @return program state
	 */
	ProgramState verify(String authorFilePath, String logFilePath);

	/**
	 * combine the entity .
	 * 
	 * @param authorFilePath - the author file path <b>(String)</b> to set
	 * @param dateFilePath - the date file path <b>(String)</b> to set
	 * @param logFilePath - the log file path <b>(String)</b> to set
	 * @param changedFilePath - the changed file path <b>(String)</b> to set
	 * @param revisionNumber - the revision number <b>(String)</b> to set
	 * 
	 * @return code log detail entity
	 */
	CodeLogDetailEntity combineEntity(String authorFilePath, String dateFilePath, String logFilePath, String changedFilePath, String revisionNumber);

	/**
	 * combine the update entity .
	 * 
	 * @param logFilePath - the log file path <b>(String)</b> to set
	 * @param revisionNumber - the revision number <b>(String)</b> to set
	 * 
	 * @return code log detail entity
	 */
	CodeLogDetailEntity combineUpdateEntity(String logFilePath, String revisionNumber);

	/**
	 * insert entity .
	 * 
	 * @param entity - the entity <b>CodeLogDetailEntity</b> you want insert
	 * 
	 * @return insert success or not
	 */
	Boolean insert(CodeLogDetailEntity entity);

	/**
	 * delete all entity .
	 * 
	 * @return delete success or not
	 */
	Boolean deleteAll();

	/**
	 * update entity .
	 * 
	 * @param entity - the entity <b>CodeLogDetailEntity</b> you want update
	 * 
	 * @return update success or not
	 */
	Boolean update(CodeLogDetailEntity entity);

}
