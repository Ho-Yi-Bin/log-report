
package org.yfr.repository;

import java.util.List;

import org.yfr.entity.CodeLogDetailEntity;

/**
 * code log detail repository interface .
 * 
 * <p>setup time <b>Nov 25, 2014 9:08:48 AM .</b></p>
 *
 * @author Vincent Huang
 */
public interface CodeLogDetailRepository {

	/**
	 * insert entity .
	 * 
	 * @param codeLogDetailEntity - the entity <b>CodeLogDetailEntity</b> to insert 
	 * 
	 * @return insert success or not 
	 */
	Boolean insert(CodeLogDetailEntity codeLogDetailEntity);

	/**
	 * delete all entity .
	 * 
	 * @return delete success or not
	 */
	Boolean deleteAll();

	/**
	 * update entity .
	 * 
	 * @param codeLogDetailEntity - the entity <b>CodeLogDetailEntity</b> to update
	 * 
	 * @return update success or not 
	 */
	Boolean update(CodeLogDetailEntity codeLogDetailEntity);

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
	 * @return List of CodeLogDetailEntity 
	 */
	List<CodeLogDetailEntity> query(Integer startRevisionNum, Integer endRevisionNum);

	/**
	 * query entities where revision number >= startRevisionNum and revision number <= endRevisionNum and tag name is null .
	 * 
	 * @param startRevisionNum - the start revision number lower bound <b>Integer</b> to set
	 * @param endRevisionNum - the end revision number upper bound <b>Integer</b> to set
	 * 
	 * @return List of CodeLogDetailEntity 
	 */
	List<CodeLogDetailEntity> queryTagNameIsNull(Integer startRevisionNum, Integer endRevisionNum);

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