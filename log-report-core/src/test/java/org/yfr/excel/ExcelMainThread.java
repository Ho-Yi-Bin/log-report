package org.yfr.excel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.yfr.excel.constant.ExcelContant;
import org.yfr.excel.manager.ExcelConfigManager;
import org.yfr.excel.manager.ExcelPaserManaer;
import org.yfr.excel.util.ExcelUtil;
import org.yfr.excel.vo.ExcelVo;
import org.yfr.excel.vo.SVNVo;

public class ExcelMainThread {
	private static Logger mLogger = Logger.getLogger(ExcelContant.SERVER_LOGGER);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 取得excel處理條件的原始檔
		String[] sInputDatas = {"[B][1]", "[b][2]"};
		
		// SVNVO Data
		HashMap<String, SVNVo> mapSVNInfo = null;
		
		// ExcelVO Data
		HashMap<String, ExcelVo> mapExcelInfo = null;
 		
		// Get ExcelConfig information
		ExcelConfigManager.getInstance().setConfigPath("config/ExcelInfo.xml");
		
		// init ExcelConfig
		try {
			ExcelConfigManager.getInstance().init();
		} catch (Exception e) {
			mLogger.warn(e.getStackTrace());
		}
		
		// do Excel Handle
		try {
			// 取得SVN Data for VO style
			mapSVNInfo = ExcelUtil.getInstance().getSvnInfomationVosMap(sInputDatas);
			
			// 取得Source Excel Data 轉為 ExcelVO
			Map<Character, String> mapSourceFilePath = ExcelConfigManager.getInstance().getFileNameMap();
			Iterator<Entry<Character, String>> iter = mapSourceFilePath.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry<Character, String> entry = (Map.Entry<Character, String>) iter.next(); 
			    char cKeyWithExcelType 		= entry.getKey(); 
			    String sValueWithExcelPath 	= (String) entry.getValue();
			    mapExcelInfo = ExcelUtil.getInstance().readExcelBecomeVO(cKeyWithExcelType, sValueWithExcelPath);
			} 
			
			// 由獲得的條件資料來決定Excel的處理作業,即 SVNVO & ExcelVO 做比對作業
			ExcelPaserManaer.getInstance().doExcelHandle(mapSVNInfo, mapExcelInfo);
			
		} catch (Exception e) {
			mLogger.warn(e.getMessage(),e);
		}
		
	}

}
