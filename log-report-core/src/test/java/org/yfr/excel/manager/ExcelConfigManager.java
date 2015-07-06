package org.yfr.excel.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yfr.excel.constant.ExcelContant;
import org.yfr.excel.constant.enu.ExcelTypeEnum;


public class ExcelConfigManager {
	
	private Logger mLogger = Logger.getLogger(ExcelContant.SERVER_LOGGER);
	
	private static ExcelConfigManager mConfigManager = new ExcelConfigManager();
	
	private String sNodeRoot = "ROOT", sFileName = "new", sExcelName = "UpdateList", sFiletype = ".xlsx";
	
	private int iSheet = 0;
	
	private ArrayList<String> lNodeName = new ArrayList<String>();
	
	private ArrayList<String> lColumnName = new ArrayList<String>();
	
	private HashMap<Character, String>  mFileNameMap = new HashMap<Character, String>();
	
	private String paString = "config/config.xml";
	
	private static StringBuffer stringBuffer = new StringBuffer();
	
	
	private ExcelConfigManager(){
	
	}
	
	public static ExcelConfigManager getInstance(){
		return mConfigManager;
	}
	
	
	/**
	 * ConfigManager , 取裡從xml讀取來的訊息
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception{

		File fileXml = new File(paString);
		try {	
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			 
			Document doc = docBuilder.parse(fileXml);
			
			doc.getDocumentElement().normalize();
			
			if(doc.getDocumentElement().getNodeName().trim() == null){
				throw new Exception("Config.xml內容錯誤，查無節點<serivce>!!");
			}
			else{
				
				//sNodeRoot = doc.getDocumentElement().getNodeName();
				parseConfingManager(doc);
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			mLogger.warn("找不到檔案路徑 < " + fileXml + " >!!");
			mLogger.warn(e.getMessage(),e);
		}
		catch (Exception e) {
			e.printStackTrace();
			mLogger.warn(fileXml + " 格式錯誤，請檢查內容是否正確!!");
			mLogger.warn(e.getMessage(),e);
		}
	}
	
	private void parseConfingManager(Document doc){
		
		try {
			
			NodeList nListHeadList = doc.getElementsByTagName("head");
			
			if(null == nListHeadList || nListHeadList.getLength() == 0){
				mLogger.warn("Config.xml內容錯誤，無節點<head>!!");
			}
			
			for(int i = 0; i < nListHeadList.getLength(); i++){
				Node nNodehead = nListHeadList.item(i);
				
				NodeList nNodeheadChd = nNodehead.getChildNodes();
				
				if(nNodeheadChd.getLength() == 0){
					mLogger.warn("<head></head>查無資訊!!");
				}
				else {
					NodeList nLstNodeFileName = doc.getElementsByTagName("FileName");
					NodeList nLstNodeCreateExcel = doc.getElementsByTagName("CreateExcel"); 
					NodeList nLstNodeColumn = doc.getElementsByTagName("Column");
					NodeList nLstNodeSheet = doc.getElementsByTagName("Sheet");
					
					if(null == nLstNodeFileName || nLstNodeFileName.getLength() == 0){
						sFileName = sFileName + sFiletype;
						mLogger.warn("ExcelConfig.xml內容錯誤，無節點<FileName>!!");
						mLogger.info("預設檔名啟用 : < " + sFileName + " >!!");
					}
					if (null == nLstNodeCreateExcel || nLstNodeCreateExcel.getLength() == 0) {
						sExcelName = sExcelName + sFiletype;
						mLogger.warn("ExcelConfig.xml內容錯誤，無節點<CreateExcel>!!");
						mLogger.info("預設檔名啟用 : < " + sExcelName + " >!!");
					}
					if(null == nLstNodeColumn || nLstNodeColumn.getLength() == 0){
						mLogger.warn("ExcelConfig.xml內容錯誤，無節點<Column>!!");
					}
					if(null == nLstNodeSheet || nLstNodeSheet.getLength() == 0){
						mLogger.warn("ExcelConfig.xml內容錯誤，無節點<Sheet>!!");
					}
					
					//<FileNmae>
					for (int j = 0; j < nLstNodeFileName.getLength(); j++) {
						Node nNodeFileName = nLstNodeFileName.item(j);
						
						if(nNodeFileName.getNodeType() == Element.ELEMENT_NODE){
							Element eNodeFileNameElement = (Element) nNodeFileName;
							
							if(eNodeFileNameElement.getChildNodes().item(0) != null){
								String sFileNameString = eNodeFileNameElement.getChildNodes().item(0).getTextContent().trim();
								if(sFileNameString == null || sFileNameString.isEmpty()){
									sFileName = sFileName + sFiletype;
									mLogger.warn("<FileName></FileName>查無資料!!");
									mLogger.info("預設檔名啟用 : < " + sFileName + " >!!");
								}
								else {
									String[] sFileNames = sFileNameString.split(ExcelContant.SPLIT_SIGN);
									for (String sName : sFileNames) {
										// 判斷要塞給Map當key的值
										if (sName.indexOf(ExcelContant.TITLE_BUG) > 0) {
											mFileNameMap.put(ExcelTypeEnum.BUG.getValue(), sName);
										}
										else if (sName.indexOf(ExcelContant.TITLE_SPEC) > 0) {
											mFileNameMap.put(ExcelTypeEnum.SPEC.getValue(), sName);
										}
										else {
											mFileNameMap.put(ExcelTypeEnum.OTHER.getValue(), sName);
										}
									}
								}
							}
							else {
								sFileName = sFileName + sFiletype;
								mLogger.warn("<FileName></FileName>查無資料!!");
								mLogger.info("預設檔名啟用 : < " + sFileName + " >!!");
							}
						}
					}
					
					//<CreateExcel>
					for (int j = 0; j < nLstNodeCreateExcel.getLength(); j++) {
						Node nNodeCreateExcelName = nLstNodeCreateExcel.item(j);
						
						if(nNodeCreateExcelName.getNodeType() == Element.ELEMENT_NODE){
							Element eNodeCreateExcelElement = (Element) nNodeCreateExcelName;
							
							if(eNodeCreateExcelElement.getChildNodes().item(0) != null){
								String sCreateExcel = eNodeCreateExcelElement.getChildNodes().item(0).getTextContent().trim();
								if(sExcelName == null || sExcelName.isEmpty()){
									sExcelName = sExcelName + sFiletype;
									mLogger.warn("<CreateExcel></CreateExcel>查無資料!!");
									mLogger.info("預設檔名啟用 : < " + sExcelName + " >!!");
								}
								else {
									if(sCreateExcel.endsWith(sFiletype)){
										sExcelName = sCreateExcel;
									}
									else {
										sExcelName = sCreateExcel + sFiletype;
									}
								}
							}
							else {
								sExcelName = sExcelName + sFiletype;
								mLogger.warn("<CreateExcel></CreateExcel>查無資料!!");
								mLogger.info("預設檔名啟用 : < " + sExcelName + " >!!");
							}
						}
					}
					
					//<Column>
					for (int j = 0; j < nLstNodeColumn.getLength(); j++) {
						Node nNodeColumn = nLstNodeColumn.item(j);
						
						if(nNodeColumn.getNodeType() == Element.ELEMENT_NODE){
							Element eNodeColumnElement = (Element) nNodeColumn;
							
							if(eNodeColumnElement.getChildNodes().item(0) != null){
								String sColumn = eNodeColumnElement.getChildNodes().item(0).getTextContent().trim();
								if(sColumn == null || sColumn.isEmpty()){
									mLogger.warn("<Column></Column>查無資料!!");
								}
								else{
									lColumnName.add(sColumn);
								}
							}
							else{
								mLogger.warn("<Column></Column>查無資料!!");
							}
						}
					}
					
					//<Sheet>
					for (int j = 0; j < nLstNodeSheet.getLength(); j++) {
						Node nNodeSheet = nLstNodeSheet.item(j);
						
						if(nNodeSheet.getNodeType() == Element.ELEMENT_NODE){
							Element eNodeSheetElement = (Element) nNodeSheet;
							
							if(eNodeSheetElement.getChildNodes().item(0) != null){
								String sSheet= eNodeSheetElement.getChildNodes().item(0).getTextContent().trim();
								if(sSheet == null || sSheet.isEmpty()){
									mLogger.warn("<Sheet></Sheet>查無資料!!");
								}
								else{
									iSheet = Integer.valueOf(sSheet);
								}
							}
							else{
								mLogger.warn("<Sheet></Sheet>查無資料!!");
							}
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mLogger.warn("檔案讀取錯誤 < " + doc.getLocalName() + " > 無效!!");
			mLogger.warn(e.getMessage(),e);
		}
	}

	public String getsNodeRoot() {
		return sNodeRoot;
	}
	
	public int getSheet(){
		return iSheet;
	}

	public ArrayList<String> getlNodeName() {
		return lNodeName;
	}
	
	public ArrayList<String> getlColumn(){
		return lColumnName;
	}

	public HashMap<Character, String> getFileNameMap() {
		return mFileNameMap;
	}
	
	public void setConfigPath(String pathString){
		this.paString = pathString;
	}
	
	public String getConfigPath(){
		return paString;
	}
	
	public String getsExcelName() {
		return sExcelName;
	}

	public String getFileName(){
		return sFileName;
	}
	
	public StringBuffer getStirngBuffer(){
		return stringBuffer;
	}
}
