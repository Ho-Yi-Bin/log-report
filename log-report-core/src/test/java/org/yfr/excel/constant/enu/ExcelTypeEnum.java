/**
 * 
 */
package org.yfr.excel.constant.enu;

/**
 * Excel來源分類
 * @author Ben
 *
 */
public enum ExcelTypeEnum {
	/**
	 * Excel Source Type : 分析 Excel 來源所屬為Spec
	 */
	SPEC 	('S'),
	
	/**
	 * Excel Source Type : 分析 Excel 來源所屬為Bug
	 */
	BUG	  	('B'),
	
	/**
	 * Excel Source Type : 分析 Excel 來源所屬為不明
	 */
	OTHER	('O');
	
	private char cExcelSourceType;
	
	/**
	 * Excel 來源別定義
	 * @param iExcelSourceType
	 */
	private ExcelTypeEnum(char cExcelSourceType) {
		this.cExcelSourceType = cExcelSourceType;
	}
	
	/**
	 * 取得Excel 來源別
	 * @return
	 */
	public char getValue() { 
		return this.cExcelSourceType;
	}
	
	/**
	 * 輸入ExcelSourceType後判斷是否又被定義，並回傳對應代號
	 * @param iExcelSourceTyoe
	 * @return
	 */
	public static ExcelTypeEnum conver(char cExcelSourceTyoe) {
		for (ExcelTypeEnum e : ExcelTypeEnum.values()) {
			if (e.getValue() == cExcelSourceTyoe) {
				return e;
			}
		}
		return null;
	}
}
