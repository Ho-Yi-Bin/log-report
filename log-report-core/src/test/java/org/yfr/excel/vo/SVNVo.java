package org.yfr.excel.vo;

public abstract class SVNVo {

	/**
	 * 一個global VO 的通用架構，讓繼承的 VO 都時做這個取Key的方法，以方便未來再使用 VO 時可以取得此VO的Key
	 * @return
	 */
	public abstract String getDefaultKey();
}
