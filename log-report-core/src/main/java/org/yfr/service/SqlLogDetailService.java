
package org.yfr.service;

import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.UnitTestService;
import org.yfr.entity.SqlLogDetailEntity;

/**
 * sql log detail service interface .
 * 
 * <p>setup time <b>Dec 17, 2014 2:31:33 PM .</b></p>
 *
 * @author Vincent Huang
 */
public interface SqlLogDetailService extends UnitTestService {

	/**
	 * verify the information (author and log)
	 * 
	 * @return ProgramState 
	 */
	ProgramState verify(String authorFilePath, String logFilePath);

	/**
	 * combine the entity .
	 * 
	 * @return SqlLogDetailEntity
	 */
	SqlLogDetailEntity combineEntity(String authorFilePath, String dateFilePath, String logFilePath, String changedFilePath, String revisionNumber);

	/**
	 * combine the update entity .
	 * 
	 * @return SqlLogDetailEntity
	 */
	SqlLogDetailEntity combineUpdateEntity(String logFilePath, String revisionNumber);

	/**
	 * @param entity - the entity <b>SqlLogDetailEntity</b> you want to notify
	 * 
	 * @return notify success or not
	 */
	Boolean commitNotify(SqlLogDetailEntity entity);

	/**
	 * insert entity .
	 * 
	 * @param entity - the entity <b>SqlLogDetailEntity</b> you want to insert
	 * 
	 * @return insert success or not
	 */
	Boolean insert(SqlLogDetailEntity entity);

	/**
	 * delete all entity .
	 * 
	 * @return delete success or not
	 */
	Boolean deleteAll();

	/**
	 * update entity .
	 * 
	 * @param entity - the entity <b>SqlLogDetailEntity</b> you want update
	 * 
	 * @return update success or not
	 */
	Boolean update(SqlLogDetailEntity entity);

}
