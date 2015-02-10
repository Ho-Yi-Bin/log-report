
package org.yfr.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.repository.SqlLogDetailRepository;
import org.yfr.service.SqlGenLogReportService;

/**
 * sql generate log report service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:19:08 AM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("sqlGenLogReportService")
public class SqlGenLogReportServiceImpl implements SqlGenLogReportService {

	Log logger = LogFactory.getLog(this.getClass());

	Boolean isUnitTestMode = Boolean.FALSE;

	@Resource
	SqlLogDetailRepository sqlLogDetailRepository;

	@Override
	public Boolean genSimpleReport(String sheetName, String startRev, String endRev) {
		if (sheetName == null || startRev == null || endRev == null) {
			logger.info("Sheet Name or Start Revision Number or End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		int startNum;
		int endNum;
		try {
			startNum = Integer.parseInt(startRev);
			endNum = Integer.parseInt(endRev);
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage(), ex);

			return Boolean.FALSE;
		}

		List<SqlLogDetailEntity> queryResult = sqlLogDetailRepository.query(startNum, endNum);

		return genReport(sheetName, queryResult);
	}

	@Override
	public Boolean genBuildReport(String buildId) {
		if (buildId == null) {
			logger.info("Build Id Equals Null");
			return Boolean.FALSE;
		}

		Integer queryMinNoTagRev = sqlLogDetailRepository.queryMinNoTagRev();
		Integer queryYoungestRev = sqlLogDetailRepository.queryYoungestRev();

		if (queryMinNoTagRev == null || queryYoungestRev == null) {
			logger.info("Query Start Revision Number or Query End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		List<SqlLogDetailEntity> queryResult = sqlLogDetailRepository.queryTagNameIsNull(queryMinNoTagRev, queryYoungestRev);

		return genReport(buildId, queryResult);
	}

	@Override
	public void setIsUnitTestMode(Boolean isUnitTestMode) {
		this.isUnitTestMode = isUnitTestMode;
	}

	private Boolean genReport(String buildId, List<SqlLogDetailEntity> queryResult) {
		Boolean generateResult = Boolean.FALSE;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(buildId);

		/* create title style . */
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setFontName("MV Boli");
		titleFont.setItalic(true);
		titleFont.setColor(HSSFColor.BLUE.index);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);

		/* create author style . */
		Font authorFont = wb.createFont();
		authorFont.setFontHeightInPoints((short) 12);
		authorFont.setFontName("Times New Roman");
		authorFont.setColor(HSSFColor.BLUE.index);

		CellStyle authorStyle = wb.createCellStyle();
		authorStyle.setFont(authorFont);

		/* create date style . */
		Font dateFont = wb.createFont();
		dateFont.setFontHeightInPoints((short) 12);
		dateFont.setFontName("Times New Roman");
		dateFont.setColor(HSSFColor.LIGHT_ORANGE.index);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setFont(dateFont);

		/* create module name style . */
		Font moduleNameFont = wb.createFont();
		moduleNameFont.setFontHeightInPoints((short) 12);
		moduleNameFont.setFontName("Times New Roman");
		moduleNameFont.setColor(HSSFColor.BLUE_GREY.index);

		CellStyle moduleNameStyle = wb.createCellStyle();
		moduleNameStyle.setFont(moduleNameFont);

		/* create DB user style . */
		Font dbUserFont = wb.createFont();
		dbUserFont.setFontHeightInPoints((short) 12);
		dbUserFont.setFontName("Times New Roman");
		dbUserFont.setColor(HSSFColor.BROWN.index);

		CellStyle dbUserStyle = wb.createCellStyle();
		dbUserStyle.setFont(dbUserFont);

		/* create table name style . */
		Font tableNameFont = wb.createFont();
		tableNameFont.setFontHeightInPoints((short) 12);
		tableNameFont.setFontName("Times New Roman");
		tableNameFont.setColor(HSSFColor.RED.index);

		CellStyle tableNameStyle = wb.createCellStyle();
		tableNameStyle.setFont(tableNameFont);

		/* create log style . */
		Font logFont = wb.createFont();
		logFont.setFontHeightInPoints((short) 12);
		logFont.setFontName("Times New Roman");
		logFont.setColor(HSSFColor.DARK_YELLOW.index);

		CellStyle logStyle = wb.createCellStyle();
		logStyle.setFont(logFont);

		/* create changed style . */
		Font changedFont = wb.createFont();
		changedFont.setFontHeightInPoints((short) 12);
		changedFont.setFontName("Times New Roman");
		changedFont.setColor(HSSFColor.RED.index);

		CellStyle changedStyle = wb.createCellStyle();
		changedStyle.setFont(changedFont);

		/* create revision number style . */
		Font revisionNumberFont = wb.createFont();
		revisionNumberFont.setFontHeightInPoints((short) 12);
		revisionNumberFont.setFontName("Times New Roman");
		revisionNumberFont.setColor(HSSFColor.RED.index);

		CellStyle revisionNumberStyle = wb.createCellStyle();
		revisionNumberStyle.setFont(revisionNumberFont);

		/* Create row 0 and put some cells in it . */
		Row row = sheet.createRow(0);

		Cell cell0 = row.createCell(0);
		cell0.setCellValue("AUTHOR");
		cell0.setCellStyle(titleStyle);

		Cell cell1 = row.createCell(1);
		cell1.setCellValue("DATE");
		cell1.setCellStyle(titleStyle);

		Cell cell2 = row.createCell(2);
		cell2.setCellValue("MODULE_NAME");
		cell2.setCellStyle(titleStyle);

		Cell cell3 = row.createCell(3);
		cell3.setCellValue("DB_USER");
		cell3.setCellStyle(titleStyle);

		Cell cell4 = row.createCell(4);
		cell4.setCellValue("TABLE_NAME");
		cell4.setCellStyle(titleStyle);

		Cell cell5 = row.createCell(5);
		cell5.setCellValue("LOG");
		cell5.setCellStyle(titleStyle);

		Cell cell6 = row.createCell(6);
		cell6.setCellValue("CHANGED");
		cell6.setCellStyle(titleStyle);

		Cell cell7 = row.createCell(7);
		cell7.setCellValue("REVISION_NUMBER");
		cell7.setCellStyle(titleStyle);

		for (int i = 0; i < queryResult.size(); i++) {
			SqlLogDetailEntity entity = queryResult.get(i);

			/* Create row i and put some cells in it . */
			Row tmpRow = sheet.createRow(i + 1);

			Cell tmpCell0 = tmpRow.createCell(0);
			tmpCell0.setCellValue(entity.getAuthor());
			tmpCell0.setCellStyle(authorStyle);

			Cell tmpCell1 = tmpRow.createCell(1);
			tmpCell1.setCellValue(entity.getDate());
			tmpCell1.setCellStyle(dateStyle);

			Cell tmpCell2 = tmpRow.createCell(2);
			tmpCell2.setCellValue(entity.getModuleName());
			tmpCell2.setCellStyle(moduleNameStyle);

			Cell tmpCell3 = tmpRow.createCell(3);
			tmpCell3.setCellValue(entity.getDbUser());
			tmpCell3.setCellStyle(dbUserStyle);

			Cell tmpCell4 = tmpRow.createCell(4);
			tmpCell4.setCellValue(entity.getTableName());
			tmpCell4.setCellStyle(tableNameStyle);

			Cell tmpCell5 = tmpRow.createCell(5);
			tmpCell5.setCellValue(entity.getLog());
			tmpCell5.setCellStyle(logStyle);

			Cell tmpCell6 = tmpRow.createCell(6);
			try {
				tmpCell6.setCellValue(entity.getChanged());
				tmpCell6.setCellStyle(changedStyle);
			} catch (IllegalArgumentException e) {
				tmpCell6.setCellValue("too many characters . ");
			}

			Cell tmpCell7 = tmpRow.createCell(7);
			tmpCell7.setCellValue(entity.getRevisionNumber());
			tmpCell7.setCellStyle(revisionNumberStyle);
		}

		/* set column auto size . */
		for (int j = 0; j < 5; j++) {
			sheet.autoSizeColumn(j);
		}

		/* Write the output to a file . */
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("./output/report/Sql_" + buildId + ".xls");
			if (!isUnitTestMode) {
				wb.write(fileOut);
			}

			generateResult = Boolean.TRUE;
			logger.info("Generate Success !");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return generateResult;
	}

}
