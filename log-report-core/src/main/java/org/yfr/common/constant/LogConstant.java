
package org.yfr.common.constant;

/**
 * log constant .
 * 
 * <p>setup time <b>Apr 9, 2015 11:37:50 AM .</b></p>
 *
 * @author Vincent Huang
 */
public enum LogConstant {

	CODE_PRE_COMMIT_BAT_CALL("code pre-commit.bat calls"),

	CODE_POST_COMMIT_BAT_CALL("code post-commit.bat calls"),

	CODE_POST_REVPROP_CHANGE_BAT_CALL("code post-revprop-change.bat calls"),

	CODE_GEN_REPORT_BAT_CALL("code gen-report.bat calls"),

	CODE_FULL_EXP_BAT_CALL("code full-exp.bat calls"),

	SQL_PRE_COMMIT_BAT_CALL("sql pre-commit.bat calls"),

	SQL_POST_COMMIT_BAT_CALL("sql post-commit.bat calls"),

	SQL_POST_REVPROP_CHANGE_BAT_CALL("sql post-revprop-change.bat calls"),

	SQL_GEN_REPORT_BAT_CALL("sql gen-report.bat calls"),

	SQL_FULL_EXP_BAT_CALL("sql full-exp.bat calls"),

	BUILD_MODE_CALL("build mode calls");

	private String message;

	private LogConstant(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}
