
package org.yfr.common.enu;

/**
 * action type for main class .
 * 
 * <p>setup time <b>Dec 29, 2014 11:35:12 AM .</b></p>
 *
 * @author Vincent Huang
 */
public enum ActionType {

	/** code action . */
	CODE,

	/** sql action . */
	SQL,

	/** pre-commit.bat calls . */
	PRE,

	/** post-commit.bat calls . */
	POST,

	/** post-revprop-change.bat calls . */
	UPDATE,

	/** post-commit.bat calls . */
	FULL_EXP,

	/** post-commit.bat calls . */
	GEN_REPORT,

	/** build mode . */
	BUILD;

}