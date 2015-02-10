
package org.yfr.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.service.SqlExpSvnInfoService;
import org.yfr.service.SqlLogDetailService;

/**
 * sql export SVN info service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:18:52 AM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("sqlExpSvnInfoService")
public class SqlExpSvnInfoServiceImpl implements SqlExpSvnInfoService {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	SqlLogDetailService sqlLogDetailService;

	@Override
	public Boolean expSvnInfo2Txt(String startRev, String endRev) {
		if (startRev == null || endRev == null) {
			logger.info("Start Revision Number or End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		try {
			Integer.parseInt(startRev.trim());
			Integer.parseInt(endRev.trim());
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage(), ex);

			return Boolean.FALSE;
		}

		try {
			logger.info("call output/sql-output.bat ... ");

			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("cmd /c output\\sql-output.bat " + startRev.trim() + " " + endRev.trim());

			BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}

			logger.info("Exited with error code : " + pr.waitFor());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	@Override
	public Boolean impTxt2DB(String startRev, String endRev) {
		if (startRev == null || endRev == null) {
			logger.info("Start Revision Number or End Revision Number Equals Null !");
			return Boolean.FALSE;
		}

		int startNum = 0;
		int endNum = 0;
		try {
			startNum = Integer.parseInt(startRev.trim());
			endNum = Integer.parseInt(endRev.trim());
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage(), ex);

			return Boolean.FALSE;
		}

		for (int i = startNum; i <= endNum; ++i) {
			SqlLogDetailEntity entityForInsert = sqlLogDetailService.combineEntity("./output/outputFiles/sql_author_" + i + ".txt", "./output/outputFiles/sql_date_" + i + ".txt", "./output/outputFiles/sql_log_" + i
			        + ".txt", "./output/outputFiles/sql_changed_" + i + ".txt", i + "");
			Boolean insertResult = sqlLogDetailService.insert(entityForInsert);

			if (insertResult == Boolean.FALSE) {
				logger.info("insert " + entityForInsert + " fail !");

				return Boolean.FALSE;
			}

			logger.info("insert " + entityForInsert + " success !");
		}

		return Boolean.TRUE;
	}

}
