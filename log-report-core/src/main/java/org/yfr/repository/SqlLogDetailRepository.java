
package org.yfr.repository;

import java.util.List;

import org.yfr.entity.SqlLogDetailEntity;

/**
 * sql log detail repository interface .
 * 
 * <p>setup time <b>Dec 3, 2014 6:16:55 PM .</b></p>
 *
 * @author Vincent Huang
 */
public interface SqlLogDetailRepository {

	/**
	 * insert entity .
	 * 
	 * @param sqlLogDetailEntity - the entity <b>SqlLogDetailEntity</b> to insert 
	 * 
	 * @return insert success or not 
	 */
	Boolean insert(SqlLogDetailEntity sqlLogDetailEntity);

	/**
	 * delete all entity .
	 * 
	 * @return delete success or not
	 */
	Boolean deleteAll();

	/**
	 * update entity .
	 * 
	 * @param sqlLogDetailEntity - the entity <b>SqlLogDetailEntity</b> to update
	 * 
	 * @return update success or not 
	 */
	Boolean update(SqlLogDetailEntity sqlLogDetailEntity);

	/**
	 * update TAG_NAME to buildId where revision number >= startRevisionNum and <= endRevisionNum .
	 * 
	 * @param buildId - the build id <b>String</b> to set .
	 * @param startRevisionNum - the start revision number lower bound <b>Integer</b> to set
	 * @param endRevisionNum - the end revision number upper bound <b>Integer</b> to set
	 * 
	 * @return update success or not 
	 */
	Boolean updateTagName(String buildId, Integer startRevisionNum, Integer endRevisionNum);

	/**
	 * query entities where revision number >= startRevisionNum and revision number <= endRevisionNum .
	 * 
	 * @param startRevisionNum - the start revision number lower bound <b>Integer</b> to set
	 * @param endRevisionNum - the end revision number upper bound <b>Integer</b> to set
	 * 
	 * @return List of SqlLogDetailEntity 
	 */
	List<SqlLogDetailEntity> query(Integer startRevisionNum, Integer endRevisionNum);

	/**
	 * query entities where revision number >= startRevisionNum and revision number <= endRevisionNum and tag name is null .
	 * 
	 * @param startRevisionNum - the start revision number lower bound <b>Integer</b> to set
	 * @param endRevisionNum - the end revision number upper bound <b>Integer</b> to set
	 * 
	 * @return List of SqlLogDetailEntity 
	 */
	List<SqlLogDetailEntity> queryTagNameIsNull(Integer startRevisionNum, Integer endRevisionNum);

	/**
	 * query min revision number which tag name is null . 
	 * 
	 * @return revsion number 
	 */
	Integer queryMinNoTagRev();

	/**
	 * query youngest revision number .
	 * 
	 * @return revsion number
	 */
	Integer queryYoungestRev();

}
