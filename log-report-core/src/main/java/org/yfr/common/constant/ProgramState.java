
package org.yfr.common.constant;

/**
 * program state for unit test .
 * 
 * <p>setup time <b>Dec 29, 2014 11:35:29 AM .</b></p>
 *
 * @author Vincent Huang
 */
public enum ProgramState {

	/* ----------------------------------------------------------- *
	 * using at code log detail service and sql log detail service *
	 * ----------------------------------------------------------- */
	/** format correct . */
	FORMAT_CORRECT,

	/** ignore author . */
	IGNORE_AUTHOR,
	
	/** file not found . */
	FILE_NOT_FOUND,
	
	/** author file is empty . */
	AUTHOR_FILE_IS_EMPTY,
	
	/** log file is empty . */
	LOG_FILE_IS_EMPTY,
	
	/** tag error . */
	TAG_ERROR,
	
	/** module name error . */
	MODULE_NAME_ERROR,
	
	/** db user error . */
	DB_USER_ERROR,
	
	/** table name is empty . */
	TABLE_NAME_IS_EMPTY,
	
	/** svn admin . */
	SVN_ADMIN,
	
	/** v format error . */
	V_FORMAT_ERROR,
	
	/** x format error . */
	X_FORMAT_ERROR,
	
	/** verify fail . */
	VERIFY_FAIL,

	/* ------------------------------ *
	 * using at build version service *
	 * ------------------------------ */
	/** build id is null . */
	BUILD_ID_IS_NULL,
	
	/** code no new version . */
	CODE_NO_NEW_VERSION,
	
	/** sql no new version . */
	SQL_NO_NEW_VERSION,
	
	/** update code tag name success . */
	UPDATE_CODE_TAG_NAME_SUCCESS,
	
	/** update sql tag name success. */
	UPDATE_SQL_TAG_NAME_SUCCESS,
	
	/** update code tag name fail . */
	UPDATE_CODE_TAG_NAME_FAIL,
	
	/** update sql tag name fail . */
	UPDATE_SQL_TAG_NAME_FAIL;

}