package org.yfr.excel.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class SpecExcelVo extends ExcelVo {
	private String sType = "S";
	private String sSeqNo = "0";
	private String sDate = "0";
	private String sVersion = "";
	private String sModule = "";
	private String sWorkTp = "";
	private String sContext = "";
	private String sResponser = ""; 
	private String sHandler = "";
	private String sStatus = "";
	private String sEndDate = "0";
	private String sHandleWay = "";
	
	public String getType() {
		return sType;
	}
	public String getSeqNo() {
		return sSeqNo;
	}
	public void setSeqNo(String sSeqNo) {
		this.sSeqNo = sSeqNo;
	}
	public String getDate() {
		return sDate;
	}
	public void setDate(String sDate) {
		this.sDate = sDate;
	}
	public String getVersion() {
		return sVersion;
	}
	public void setVersion(String sVersion) {
		this.sVersion = sVersion;
	}
	public String getModule() {
		return sModule;
	}
	public void setModule(String sModule) {
		this.sModule = sModule;
	}
	public String getWorkTp() {
		return sWorkTp;
	}
	public void setWorkTp(String sWorkTp) {
		this.sWorkTp = sWorkTp;
	}
	public String getContext() {
		return sContext;
	}
	public void setContext(String sContext) {
		this.sContext = sContext;
	}
	public String getResponser() {
		return sResponser;
	}
	public void setResponser(String sResponser) {
		this.sResponser = sResponser;
	}
	public String getHandler() {
		return sHandler;
	}
	public void setHandler(String sHandler) {
		this.sHandler = sHandler;
	}
	public String getStatus() {
		return sStatus;
	}
	public void setStatus(String sStatus) {
		this.sStatus = sStatus;
	}
	public String getEndDate() {
		return sEndDate;
	}
	public void setEndDate(String sEndDate) {
		this.sEndDate = sEndDate;
	}
	public String getHandleWay() {
		return sHandleWay;
	}
	public void setHandleWay(String sHandleWay) {
		this.sHandleWay = sHandleWay;
	}
	
	public SpecExcelVo() {}
	
	public SpecExcelVo (Row row) {
		// 建立index 方便後面判斷塞入哪個欄位
		int index = 0;
		
		// 處理每個欄位格式，包含數字，文字，日期....等，全轉為String
		for (Cell cell : row) {
			String  sResult = null;
			// 判斷是否是數值型態
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				short  shFormat = cell.getCellStyle().getDataFormat();
			    /**
			     * 所有日期格式都可以透過 getDataFormat()來判斷
			     * yyyy-MM-dd	----------	14
			     * yyyy年m月d日	----------	31
			     * yyyy年m月		----------	57
			     * m月d日  		----------	58
			     * HH:mm		----------	20
			     * h時m分  		----------	32
			     * 
			     **/
				SimpleDateFormat sdf = null; 
			    if(shFormat == 14 || shFormat == 31 || shFormat == 57 || shFormat == 58){  
			        //日期  
			        sdf = new SimpleDateFormat("yyyy-MM-dd");
			        double value = cell.getNumericCellValue();  
				    Date date = DateUtil.getJavaDate(value);  
				    sResult = sdf.format(date);
			    }else if (shFormat == 20 || shFormat == 32) {  
			        //時間
			        sdf = new SimpleDateFormat("HH:mm");
			        double value = cell.getNumericCellValue();  
				    Date date = DateUtil.getJavaDate(value);  
				    sResult = sdf.format(date);
			    }
			    else {
			    	// 將excel中欄位的型態一律轉成String,避免出現型態異常
					cell.setCellType(Cell.CELL_TYPE_STRING);
					sResult = cell.getStringCellValue().trim();
			    }
			}  
			else {
				// 將excel中欄位的型態一律轉成String,避免出現型態異常
				cell.setCellType(Cell.CELL_TYPE_STRING);
				sResult = cell.getStringCellValue().trim();
			}
			
			// 藉由index 來決定此欄位該塞到哪個VO欄位
			switch (index) {
			case 0 :
				this.sSeqNo 	= sResult;
				break;
			case 1 :
				this.sDate 		= sResult;
				break;
			case 2:
				this.sVersion 	= sResult;
				break;
			case 3 :
				this.sModule 	= sResult;
				break;
			case 4 :
				this.sWorkTp 	= sResult;
				break;
			case 5 :
				this.sContext 	= sResult;
				break;
			case 6 :
				this.sResponser	= sResult;
				break;
			case 7 :
				this.sHandler	= sResult;
				break;
			case 8 :
				this.sStatus	= sResult;
				break;
			case 9 :
				this.sEndDate	= sResult;
				break;
			case 10 :
				this.sHandleWay = sResult;
				break;
			}
			index += 1;
		}
	}
	
	public SpecExcelVo (String sSeqNo, String sDate, String sVersion, 
					String sModule, String sWorkTp, String sContext, 
					String sResponser, String sHandler, String sStatus,
					String sEndDate, String sHandleWay) {
		this.sSeqNo 	= sSeqNo;
		this.sDate		= sDate;
		this.sVersion	= sVersion;
		this.sModule	= sModule;
		this.sWorkTp	= sWorkTp;
		this.sContext	= sContext;
		this.sResponser = sResponser;
		this.sHandler	= sHandler;
		this.sStatus 	= sStatus;
		this.sEndDate	= sEndDate;
		this.sHandleWay	= sHandleWay;
	}
	
	public SpecExcelVo copyOf() {
		SpecExcelVo vo = new SpecExcelVo();
		
		vo.setSeqNo(sSeqNo);
		vo.setDate(sDate);
		vo.setVersion(sVersion);
		vo.setModule(sModule);
		vo.setWorkTp(sWorkTp);
		vo.setContext(sContext);
		vo.setResponser(sResponser);
		vo.setHandler(sHandler);
		vo.setStatus(sStatus);
		vo.setEndDate(sEndDate);
		vo.setHandleWay(sHandleWay);
		return vo;
	}
	
	public void copyFields(SpecExcelVo v) {
		SpecExcelVo vo = (SpecExcelVo) v;

		sSeqNo = vo.getSeqNo();
		sDate = vo.getDate();
		sVersion = vo.getVersion();
		sModule = vo.getModule();
		sWorkTp = vo.getWorkTp();
		sContext = vo.getContext();
		sResponser = vo.getResponser();
		sHandler = vo.getHandler();
		sStatus = vo.getStatus();
		sEndDate = vo.getEndDate();
		sHandleWay = vo.getHandleWay();
	}

	public String getVoDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		sb.append(';');
		sb.append(sSeqNo);
		sb.append(';');
		sb.append(sDate);
		sb.append(';');
		sb.append(sVersion);
		sb.append(';');
		sb.append(sModule);
		sb.append(';');
		sb.append(sWorkTp);
		sb.append(';');
		sb.append(sContext);
		sb.append(';');
		sb.append(sResponser);
		sb.append(';');
		sb.append(sHandler);
		sb.append(';');
		sb.append(sStatus);
		sb.append(';');
		sb.append(sEndDate);
		sb.append(';');
		sb.append(sHandleWay);

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type=");
		sb.append(sType);
		sb.append(';');
		sb.append("SeqNo=");
		sb.append(sSeqNo);
		sb.append(';');
		sb.append("Date=");
		sb.append(sDate);
		sb.append(';');
		sb.append("Version=");
		sb.append(sVersion);
		sb.append(';');
		sb.append("Module=");
		sb.append(sModule);
		sb.append(';');
		sb.append("WorkTp=");
		sb.append(sWorkTp);
		sb.append(';');
		sb.append("Context=");
		sb.append(sContext);
		sb.append(';');
		sb.append("Responser=");
		sb.append(sResponser);
		sb.append(';');
		sb.append("Handler=");
		sb.append(sHandler);
		sb.append(';');
		sb.append("Status=");
		sb.append(sStatus);
		sb.append(';');
		sb.append("EndDate=");
		sb.append(sEndDate);
		sb.append(';');
		sb.append("HandleWay=");
		sb.append(sHandleWay);

		return sb.toString();
	}
	@Override
	public String getDefaultKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		sb.append("-");
		sb.append(sSeqNo);
		return sb.toString();
	}
}
