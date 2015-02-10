
package org.yfr.common.enu;

/**
 * program state for unit test .
 * 
 * <p>setup time <b>Dec 29, 2014 11:35:29 AM .</b></p>
 *
 * @author Vincent Huang
 */
public enum ProgramState {

	/* using at code log detail service and sql log detail service . */
	FORMAT_CORRECT,
	IGNORE_AUTHOR,
	FILE_NOT_FOUND,
	AUTHOR_FILE_IS_EMPTY,
	LOG_FILE_IS_EMPTY,
	TAG_ERROR,
	MODULE_NAME_ERROR,
	DB_USER_ERROR,
	TABLE_NAME_IS_EMPTY,
	SVN_ADMIN,
	V_FORMAT_ERROR,
	X_FORMAT_ERROR,
	VERIFY_FAIL,

	/* using at build version service . */
	BUILD_ID_EQUALS_NULL,
	CODE_NO_NEW_VERSION,
	SQL_NO_NEW_VERSION,
	UPDATE_CODE_TAG_NAME_SUCCESS,
	UPDATE_SQL_TAG_NAME_SUCCESS,
	UPDATE_CODE_TAG_NAME_FAIL,
	UPDATE_SQL_TAG_NAME_FAIL;

}