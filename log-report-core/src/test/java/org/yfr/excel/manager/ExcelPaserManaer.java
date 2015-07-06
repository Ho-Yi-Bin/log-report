package org.yfr.excel.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.yfr.excel.constant.ExcelContant;
import org.yfr.excel.vo.BugExcelVo;
import org.yfr.excel.vo.BugSVNVo;
import org.yfr.excel.vo.ExcelVo;
import org.yfr.excel.vo.OtherSVNVo;
import org.yfr.excel.vo.SVNVo;
import org.yfr.excel.vo.SpecExcelVo;

/**
 * @author Ben
 *
 */
public class ExcelPaserManaer {

	private static Logger mLogger = Logger.getLogger(ExcelContant.SERVER_LOGGER);
	
	private static ExcelPaserManaer instance = new ExcelPaserManaer();
	
	private ExcelPaserManaer () {
		
	}
	
	public static ExcelPaserManaer getInstance () {
		return instance;
	}
	
	/**
	 * 處理所有SVN 與 Excel比對作業 : 
	 * 1. 找出 SVN 與 Excel mapping data
	 * 2. 分別處理 Bug , Spec , Other 分類
	 * 
	 * @param mapSVNInfo
	 * @param mapExcelInfo
	 * @throws Exception
	 */
	public void doExcelHandle(HashMap<String, SVNVo> mapSVNInfo, HashMap<String, ExcelVo> mapExcelInfo) throws Exception {
		// 將mapping的資料放入一個list中記錄
		List<ExcelVo> lExcelEqualSVNInfos = new ArrayList<ExcelVo>();
		
		// 1. 用loop找出相同的key 並將vo 放入分類好的list中
		for (String sKey : mapSVNInfo.keySet()) {
			if (mapExcelInfo.containsKey(sKey)) {
				
				ExcelVo excelTmpVo 	= mapExcelInfo.get(sKey);
				SVNVo 	svnTmpVo	= mapSVNInfo.get(sKey);
				
				if (excelTmpVo instanceof BugExcelVo) {
					BugExcelVo 	excelvo 	= (BugExcelVo)	excelTmpVo;
					BugSVNVo	svnvo		= (BugSVNVo)	svnTmpVo;
					
					excelvo.setStatus(ExcelContant.OK);
					excelvo.setHandler(svnvo.getHandler());
					
					lExcelEqualSVNInfos.add(excelvo);
				} 
				else if (excelTmpVo instanceof SpecExcelVo) {
					SpecExcelVo excelvo 	= (SpecExcelVo)	excelTmpVo;
					lExcelEqualSVNInfos.add(excelvo);
				}
			}
			else {
				SVNVo svnTmpVo	= mapSVNInfo.get(sKey);
				OtherSVNVo svnvo		= (OtherSVNVo) svnTmpVo;
			}
		}
		
		
	}
	
	public void doSpecExcelHandle(String[] sRuleData, String sSpecSourceFile, String sSpecCreateFile) throws Exception {
		
	}
	
}
