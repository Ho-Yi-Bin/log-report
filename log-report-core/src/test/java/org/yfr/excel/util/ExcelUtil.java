package org.yfr.excel.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yfr.excel.constant.ExcelContant;
import org.yfr.excel.constant.enu.ExcelTypeEnum;
import org.yfr.excel.manager.ExcelConfigManager;
import org.yfr.excel.vo.BugExcelVo;
import org.yfr.excel.vo.BugSVNVo;
import org.yfr.excel.vo.ExcelVo;
import org.yfr.excel.vo.OtherSVNVo;
import org.yfr.excel.vo.SVNVo;
import org.yfr.excel.vo.SpecExcelVo;
import org.yfr.excel.vo.SpecSVNVo;

/**
 * Excel工具
 * @author Ben
 *
 */
public class ExcelUtil {
	private static Logger mLogger = Logger.getLogger(ExcelContant.SERVER_LOGGER);
	
	private static ExcelUtil instance = new ExcelUtil();
	
	private ExcelUtil () {
		
	}
	
	public static ExcelUtil getInstance () {
		return instance;
	}
	
	/**
	 * 處理Input，分析是哪種Excel來源來做分類分析轉成所屬類型VO：SpecVO、BugVO、OtherVO,已決定後續可以處理的方式跟來源檔
	 * @param sInputDatas
	 * @return
	 */
	public HashMap<String, SVNVo> getSvnInfomationVosMap(String[] sInputDatas){
		HashMap<String, SVNVo> mapSVNInfo = new HashMap<String, SVNVo>();
		for (String sData : sInputDatas) {
			// SNV 讀取內容屬於轉為VO，方便處理
			SVNVo vo = null;
			
			// 取代掉" [ " ,以方便判斷
			String sReplaceData = sData.replace(ExcelContant.OPEN_BRACKET, ExcelContant.NOTHING);
			// 已" ] "來做分割
			String [] sHandleData = sReplaceData.split(ExcelContant.CLOSE_BRACKET);
			
			// 開始分類SVN的內容該處理成哪種類型的VO
			ExcelTypeEnum func = ExcelTypeEnum.conver(sHandleData[0].toUpperCase().charAt(0));
			switch(func){
			case BUG:
				if (sHandleData.length == 2) {
					vo = new BugSVNVo(sHandleData[0].toUpperCase(), sHandleData[1], ExcelContant.NOTHING, null);
				}
				else {
					vo = new BugSVNVo(sHandleData[0].toUpperCase(), sHandleData[1], sHandleData[2], null);
				}
				break;
			case OTHER:
				vo = new OtherSVNVo(sHandleData[0].toUpperCase(), sHandleData[1]);
				break;
			case SPEC:
				if (sHandleData.length == 2) {
					vo = new SpecSVNVo(sHandleData[0].toUpperCase(), sHandleData[1], ExcelContant.NOTHING, sData);
				}
				else {
					vo = new SpecSVNVo(sHandleData[0].toUpperCase(), sHandleData[1], sHandleData[2], sData);
				}
				break;
			default:
				break;
			}
			mapSVNInfo.put(vo.getDefaultKey(), vo);
		}
		
		return mapSVNInfo;
	}
	
	/**
	 * 讀取 Excel Source Data，並進行轉成vo作業
	 * @param sSourceFilePath
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, ExcelVo> readExcelBecomeVO(char cSourceType, String sSourceFilePath) throws IOException {
		// 用來存取vo的map，方便用來取得指定的vo
		HashMap<String, ExcelVo> mapExcelData = new HashMap<String, ExcelVo>();
		// 由config讀取路徑
		String fileType = sSourceFilePath.substring(sSourceFilePath.lastIndexOf(".") + 1, sSourceFilePath.length());
		// 用stream方式傳遞資料
		InputStream stream = new FileInputStream(sSourceFilePath);
		// 建立workbook工具，準備放入Excel作業
		Workbook wb = null;
		try {
			// 判斷excel格式是不是2007
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				mLogger.warn("您輸入的excel格式不正確!!");
			}
			
			// 讀取哪個sheet
			Sheet sheet = wb.getSheetAt(ExcelConfigManager.getInstance().getSheet()-1);
			
			if (cSourceType != 0 && !"".equals(cSourceType)) {
				// 自訂放入map中的key,當作是取得指定vo的條件
				for (Row row : sheet) {
					if (row != null) {
						// 將 row 的 data放入直接切換成vo作業
						ExcelVo vo = null;
						if (cSourceType == ExcelTypeEnum.BUG.getValue()) {
							vo = new BugExcelVo(row);
							// 將處理好的data轉成vo並放入map中以方便後面可以取值
							mapExcelData.put(vo.getDefaultKey(), vo);
						}
						else if (cSourceType == ExcelTypeEnum.SPEC.getValue()) {
							vo = new SpecExcelVo(row);
							// 將處理好的data轉成vo並放入map中以方便後面可以取值
							mapExcelData.put(vo.getDefaultKey(), vo);
						}
						else {
							// TODO
							mLogger.info("KeyNo=" + vo.getDefaultKey() + ";" + vo.toString());
						}
						
						mLogger.debug("KeyNo=" + vo.getDefaultKey() + ";" + vo.toString());
					}	
					else {
						mLogger.warn(fileType + "無任何資料!!");
					}
				}
			}
			else {
				throw new IOException("未輸入Excel所屬類型，請確認讀取Excel是屬於 <Bug> 或 <Spec>!!");
			}
		} 
		finally {
			wb.close();
		}
		
		return mapExcelData;
	}

	
	/**
	 * 產生處理過後新的 Excel File 到指定位置
	 * @param sCreateOutFilePath
	 * @return
	 * @throws Exception
	 */
	public boolean write(String sCreateOutFilePath) throws Exception {
		String fileType = sCreateOutFilePath.substring(sCreateOutFilePath.lastIndexOf(".") + 1, sCreateOutFilePath.length());
		System.out.println(fileType);
		// 建立工作物件
		Workbook wb = null;
		// 建立串流
		OutputStream stream  = null;
		try {
			
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook();
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook();
			} else {
				System.out.println("您的文件檔案格式不正確!!");
				return false;
			}
			// 建立sheet
			Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
			// 循環寫入"行"的資料
			for (int i = 0; i < 5; i++) {
				Row row = (Row) sheet1.createRow(i);
				// 循環寫入"列"的資料
				for (int j = 0; j < 8; j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue("測試" + j);
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
			}
			
			stream = new FileOutputStream(sCreateOutFilePath);
			// 寫入data
			wb.write(stream);
			
		}
		finally {
			// 關閉stream
			stream.close();
			// 關閉wb
			wb.close();
		}
		
		return true;
	}
	
//	public void main(String[] args) {
//		try {
//			ExcelUtil.getInstance().write("D:" + File.separator + "out.xlsx");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			ExcelUtil.getInstance().read("D:" + File.separator + "out.xlsx");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
