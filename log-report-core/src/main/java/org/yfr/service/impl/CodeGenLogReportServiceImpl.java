
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
import org.yfr.entity.CodeLogDetailEntity;
import org.yfr.repository.CodeLogDetailRepository;
import org.yfr.service.CodeGenLogReportService;

/**
 * code generate log report service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:18:35 AM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("codeGenLogReportService")
public class CodeGenLogReportServiceImpl implements CodeGenLogReportService {

	Log logger = LogFactory.getLog(this.getClass());

	Boolean isUnitTestMode = Boolean.FALSE;

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

	@Override
	public Boolean genSimpleReport(String sheetName, String startRev, String endRev) {
		if (sheetName == null || startRev == null || endRev == null) {
			logger.info("Sheet Name or Start Revision Number or End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		int startNum, endNum;
		try {
			startNum = Integer.parseInt(startRev);
			endNum = Integer.parseInt(endRev);
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage(), ex);

			return Boolean.FALSE;
		}

		List<CodeLogDetailEntity> queryResult = codeLogDetailRepository.query(startNum, endNum);

		return genSimpleReportFiles(sheetName, queryResult);
	}

	@Override
	public Boolean genBuildReport(String buildId) {
		if (buildId == null) {
			logger.info("Build Id Equals Null");
			return Boolean.FALSE;
		}

		Integer queryMinNoTagRev = codeLogDetailRepository.queryMinNoTagRev();
		Integer queryYoungestRev = codeLogDetailRepository.queryYoungestRev();

		if (queryMinNoTagRev == null || queryYoungestRev == null) {
			logger.info("Query Start Revision Number or Query End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		List<CodeLogDetailEntity> queryResult = codeLogDetailRepository.queryTagNameIsNull(queryMinNoTagRev, queryYoungestRev);

		return genBuildReportFiles(buildId, queryResult);
	}

	@Override
	public void setIsUnitTestMode(Boolean isUnitTestMode) {
		this.isUnitTestMode = isUnitTestMode;
	}

	private Boolean genSimpleReportFiles(String buildId, List<CodeLogDetailEntity> queryResult) {
		Boolean generateResult = Boolean.FALSE;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(buildId);

		/* create title style . */
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 13);
		titleFont.setFontName("MV Boli");
		titleFont.setItalic(true);
		titleFont.setColor(HSSFColor.BLUE.index);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);

		/* create author style . */
		Font authorFont = wb.createFont();
		authorFont.setFontHeightInPoints((short) 12);
		authorFont.setFontName("Times New Roman");
		authorFont.setColor(HSSFColor.BLACK.index);

		CellStyle authorStyle = wb.createCellStyle();
		authorStyle.setFont(authorFont);

		/* create date style . */
		Font dateFont = wb.createFont();
		dateFont.setFontHeightInPoints((short) 12);
		dateFont.setFontName("Times New Roman");
		dateFont.setColor(HSSFColor.LIGHT_ORANGE.index);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setFont(dateFont);

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
		cell2.setCellValue("LOG");
		cell2.setCellStyle(titleStyle);

		Cell cell3 = row.createCell(3);
		cell3.setCellValue("CHANGED");
		cell3.setCellStyle(titleStyle);

		Cell cell4 = row.createCell(4);
		cell4.setCellValue("REVISION_NUMBER");
		cell4.setCellStyle(titleStyle);

		for (int i = 0; i < queryResult.size(); i++) {
			CodeLogDetailEntity entity = queryResult.get(i);

			/* Create row i and put some cells in it . */
			Row tmpRow = sheet.createRow(i + 1);

			Cell tmpCell0 = tmpRow.createCell(0);
			tmpCell0.setCellValue(entity.getAuthor());
			tmpCell0.setCellStyle(authorStyle);

			Cell tmpCell1 = tmpRow.createCell(1);
			tmpCell1.setCellValue(entity.getDate());
			tmpCell1.setCellStyle(dateStyle);

			Cell tmpCell2 = tmpRow.createCell(2);
			tmpCell2.setCellValue(entity.getLog());
			tmpCell2.setCellStyle(logStyle);

			Cell tmpCell3 = tmpRow.createCell(3);
			try {
				tmpCell3.setCellValue(entity.getChanged());
				tmpCell3.setCellStyle(changedStyle);
			} catch (IllegalArgumentException e) {
				tmpCell3.setCellValue("too many characters . ");
			}

			Cell tmpCell4 = tmpRow.createCell(4);
			tmpCell4.setCellValue(entity.getRevisionNumber());
			tmpCell4.setCellStyle(revisionNumberStyle);
		}

		/* set column auto size . */
		for (int j = 0; j < 5; j++) {
			sheet.autoSizeColumn(j);
		}

		/* Write the output to a file . */
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("./output/report/Code_" + buildId + ".xls");
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

	private Boolean genBuildReportFiles(String buildId, List<CodeLogDetailEntity> queryResult) {
		Boolean generateResult = Boolean.FALSE;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet_V = wb.createSheet(buildId + "_V");
		Sheet sheet_X = wb.createSheet(buildId + "_X");

		/* create title style . */
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 12);
		titleFont.setFontName("MV Boli");
		titleFont.setItalic(true);
		titleFont.setColor(HSSFColor.BLUE.index);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);

		/* create author style . */
		Font authorFont = wb.createFont();
		authorFont.setFontHeightInPoints((short) 12);
		authorFont.setFontName("Times New Roman");
		authorFont.setColor(HSSFColor.BLACK.index);

		CellStyle authorStyle = wb.createCellStyle();
		authorStyle.setFont(authorFont);

		/* create date style . */
		Font dateFont = wb.createFont();
		dateFont.setFontHeightInPoints((short) 12);
		dateFont.setFontName("Times New Roman");
		dateFont.setColor(HSSFColor.LIGHT_ORANGE.index);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setFont(dateFont);

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

		/* create module style . */
		Font moduleNameFont = wb.createFont();
		moduleNameFont.setFontHeightInPoints((short) 12);
		moduleNameFont.setFontName("Times New Roman");
		moduleNameFont.setColor(HSSFColor.BROWN.index);

		CellStyle moduleNameStyle = wb.createCellStyle();
		moduleNameStyle.setFont(moduleNameFont);

		/* Create row 0 and put some cells in sheet_V . */
		Row row_V = sheet_V.createRow(0);

		Cell cell0_V = row_V.createCell(0);
		cell0_V.setCellValue("MODULE_NAME");
		cell0_V.setCellStyle(titleStyle);

		Cell cell1_V = row_V.createCell(1);
		cell1_V.setCellValue("AUTHOR");
		cell1_V.setCellStyle(titleStyle);

		Cell cell2_V = row_V.createCell(2);
		cell2_V.setCellValue("DATE");
		cell2_V.setCellStyle(titleStyle);

		Cell cell3_V = row_V.createCell(3);
		cell3_V.setCellValue("SPEC_NAME_OR_BUG_ID");
		cell3_V.setCellStyle(titleStyle);

		Cell cell4_V = row_V.createCell(4);
		cell4_V.setCellValue("LOG");
		cell4_V.setCellStyle(titleStyle);

		Cell cell5_V = row_V.createCell(5);
		cell5_V.setCellValue("REVISION_NUMBER");
		cell5_V.setCellStyle(titleStyle);

		/* Create row 0 and put some cells in sheet_X . */
		Row row_X = sheet_X.createRow(0);

		Cell cell0_X = row_X.createCell(0);
		cell0_X.setCellValue("AUTHOR");
		cell0_X.setCellStyle(titleStyle);

		Cell cell1_X = row_X.createCell(1);
		cell1_X.setCellValue("DATE");
		cell1_X.setCellStyle(titleStyle);

		Cell cell2_X = row_X.createCell(2);
		cell2_X.setCellValue("LOG");
		cell2_X.setCellStyle(titleStyle);

		Cell cell3_X = row_X.createCell(3);
		cell3_X.setCellValue("CHANGED");
		cell3_X.setCellStyle(titleStyle);

		Cell cell4_X = row_X.createCell(4);
		cell4_X.setCellValue("REVISION_NUMBER");
		cell4_X.setCellStyle(titleStyle);

		int idx_V = 1, idx_X = 1;
		for (int i = 0; i < queryResult.size(); i++) {
			CodeLogDetailEntity entity = queryResult.get(i);

			Row tmpRow = null;
			if (entity.getGenerateFlag().equals("YES")) {
				tmpRow = sheet_V.createRow(idx_V++);

				Cell tmpCell0 = tmpRow.createCell(0);
				tmpCell0.setCellValue(entity.getModuleName());
				tmpCell0.setCellStyle(moduleNameStyle);

				Cell tmpCell1 = tmpRow.createCell(1);
				tmpCell1.setCellValue(entity.getAuthor());
				tmpCell1.setCellStyle(authorStyle);

				Cell tmpCell2 = tmpRow.createCell(2);
				tmpCell2.setCellValue(entity.getDate());
				tmpCell2.setCellStyle(dateStyle);

				String[] splitLog = entity.getLog().replace("[", "").split("]");
				Cell tmpCell3 = tmpRow.createCell(3);
				tmpCell3.setCellValue(splitLog[2]);
				tmpCell3.setCellStyle(logStyle);

				Cell tmpCell4 = tmpRow.createCell(4);
				tmpCell4.setCellValue(splitLog[3]);
				tmpCell4.setCellStyle(logStyle);

				Cell tmpCell5 = tmpRow.createCell(5);
				tmpCell5.setCellValue(entity.getRevisionNumber());
				tmpCell5.setCellStyle(revisionNumberStyle);
			} else if (entity.getGenerateFlag().equals("NO")) {
				tmpRow = sheet_X.createRow(idx_X++);

				Cell tmpCell0 = tmpRow.createCell(0);
				tmpCell0.setCellValue(entity.getAuthor());
				tmpCell0.setCellStyle(authorStyle);

				Cell tmpCell1 = tmpRow.createCell(1);
				tmpCell1.setCellValue(entity.getDate());
				tmpCell1.setCellStyle(dateStyle);

				Cell tmpCell2 = tmpRow.createCell(2);
				tmpCell2.setCellValue(entity.getLog());
				tmpCell2.setCellStyle(logStyle);

				Cell tmpCell3 = tmpRow.createCell(3);
				try {
					tmpCell3.setCellValue(entity.getChanged());
					tmpCell3.setCellStyle(changedStyle);
				} catch (IllegalArgumentException e) {
					tmpCell3.setCellValue("too many characters . ");
				}

				Cell tmpCell4 = tmpRow.createCell(4);
				tmpCell4.setCellValue(entity.getRevisionNumber());
				tmpCell4.setCellStyle(revisionNumberStyle);
			}
		}

		/* set column auto size . */
		for (int j = 0; j < sheet_V.getRow(0).getLastCellNum(); j++) {
			sheet_V.autoSizeColumn(j);
		}
		for (int j = 0; j < sheet_X.getRow(0).getLastCellNum(); j++) {
			sheet_X.autoSizeColumn(j);
		}

		/* Write the output to a file . */
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("./output/report/Code_" + buildId + ".xls");
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
