
package org.yfr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.SendMailService;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.repository.SqlLogDetailRepository;
import org.yfr.service.SqlLogDetailService;
import org.yfr.vo.SqlParam;

/**
 * sql log detail service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 3:58:58 PM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("sqlLogDetailService")
public class SqlLogDetailServiceImpl implements SqlLogDetailService {

	Log logger = LogFactory.getLog(this.getClass());

	Boolean isUnitTestMode = Boolean.FALSE;

	ProgramState programState;

	@Resource
	SqlParam sqlParam;

	@Resource
	SqlLogDetailRepository sqlLogDetailRepository;

	@Resource
	SendMailService sendMailService;

	@Override
	public ProgramState verify(String authorFilePath, String logFilePath) {
		ProgramState rtnState = ProgramState.FORMAT_CORRECT;

		Scanner authorScanner = null, logScanner = null;
		try {
			/* load scanner . */
			if (isUnitTestMode.booleanValue()) {
				URL authorFileUrl = Thread.currentThread().getContextClassLoader().getResource(authorFilePath.trim());
				URL logFileUrl = Thread.currentThread().getContextClassLoader().getResource(logFilePath.trim());

				if (authorFileUrl == null || logFileUrl == null) {
					System.err.println("\n>>>>> Error Message : ");
					System.err.println(">>>>> author.txt or log.txt not found !");
					System.err.println(">>>>> System wrong! Please Connect SE to solve this problem .");

					return ProgramState.FILE_NOT_FOUND;
				}

				authorScanner = new Scanner(new File(authorFileUrl.getFile()));
				logScanner = new Scanner(new File(logFileUrl.getFile()));
			} else {
				authorScanner = new Scanner(new File(authorFilePath.trim()));
				logScanner = new Scanner(new File(logFilePath.trim()));
			}

			/* get author and log line 1 . */
			String author = authorScanner.nextLine().trim();
			String logLine1 = logScanner.nextLine().trim();
			logger.info(author);
			logger.info(logLine1);

			/* throw exception if file is empty . */
			if (logLine1 == null || logLine1.length() == 0) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (Log Empty) !");
				System.err.println("\n>>>>>" + sqlParam.getLogTemplateLine1());
				System.err.println(">>>>>" + sqlParam.getLogTemplateLine2());

				return ProgramState.LOG_FILE_IS_EMPTY;
			}

			/* throw exception if log line 1 match svn admin mode . */
			if (logLine1.toUpperCase().equals(ProgramState.SVN_ADMIN.toString())) {
				return ProgramState.SVN_ADMIN;
			}

			if (!logScanner.hasNextLine()) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (Format Error) !");
				System.err.println("\n>>>>>" + sqlParam.getLogTemplateLine1());
				System.err.println(">>>>>" + sqlParam.getLogTemplateLine2());

				return ProgramState.V_FORMAT_ERROR;
			}

			/* log line 2 . */
			String logLine2 = logScanner.nextLine().trim();
			logger.info(logLine2);

			/* throw exception if format v error . */
			String[] splitLog = logLine1.replace("[", "").split("]");
			if (splitLog.length != 3 || logLine2 == null || logLine2.length() == 0) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (Format Error) !");
				System.err.println("\n>>>>>" + sqlParam.getLogTemplateLine1());
				System.err.println(">>>>>" + sqlParam.getLogTemplateLine2());

				return ProgramState.V_FORMAT_ERROR;
			}

			/* throw exception if module name no in module name list . */
			List<String> moduleNameList = sqlParam.getPermitModuleNameList();
			if (!moduleNameList.contains(splitLog[0].toUpperCase().trim())) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (No This Module Name) !");
				System.err.println("\n>>>>>" + sqlParam.getLogTemplateLine1());
				System.err.println(">>>>>" + sqlParam.getLogTemplateLine2());

				return ProgramState.MODULE_NAME_ERROR;
			}

			/* throw exception if db user no in db user list . */
			List<String> dbUserList = sqlParam.getPermitDbUserList();
			if (!dbUserList.contains(splitLog[1].toLowerCase().trim())) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (No This DB User) !");
				System.err.println("\n>>>>>" + sqlParam.getLogTemplateLine1());
				System.err.println(">>>>>" + sqlParam.getLogTemplateLine2());

				return ProgramState.DB_USER_ERROR;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			System.err.println("\n>>>>> Error Message : ");
			System.err.println(">>>>> Something thing wrong! Please Connect SE to solve this problem .");

			return ProgramState.VERIFY_FAIL;
		} finally {
			if (authorScanner != null) {
				authorScanner.close();
			}

			if (logScanner != null) {
				logScanner.close();
			}
		}

		return rtnState;
	}

	@Override
	public SqlLogDetailEntity combineEntity(String authorFilePath, String dateFilePath, String logFilePath, String changedFilePath, String revisionNumber) {
		if (authorFilePath == null || dateFilePath == null || logFilePath == null || changedFilePath == null || revisionNumber == null) {
			return null;
		}

		SqlLogDetailEntity rtnEntity = new SqlLogDetailEntity();

		Scanner authorScanner = null, dateScanner = null, logScanner = null, changedScanner = null;
		try {
			/* load scanner . */
			if (isUnitTestMode.booleanValue()) {
				authorScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(authorFilePath.trim()).getFile()));
				dateScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(dateFilePath.trim()).getFile()), "ms950");
				logScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(logFilePath.trim()).getFile()));
				changedScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(changedFilePath.trim()).getFile()));
			} else {
				authorScanner = new Scanner(new File(authorFilePath.trim()));
				dateScanner = new Scanner(new FileInputStream(new File(dateFilePath.trim())), "ms950");
				logScanner = new Scanner(new File(logFilePath.trim()));
				changedScanner = new Scanner(new File(changedFilePath.trim()));
			}

			/* set author, date, log, changed, revision number, module name, DB user, table name, tag name . */
			rtnEntity.setAuthor(authorScanner.nextLine().trim());
			rtnEntity.setDate(dateScanner.nextLine().substring(0, 19));

			StringBuilder logBuilder = new StringBuilder();
			while (logScanner.hasNextLine()) {
				logBuilder.append(logScanner.nextLine().trim() + "\n");
			}
			rtnEntity.setLog(logBuilder.toString());

			String[] eachLineLog = rtnEntity.getLog().split("\n");
			String[] eachTag = eachLineLog[0].replace("[", "").split("]");
			if (eachTag.length == 3) {
				rtnEntity.setModuleName(eachTag[0].toUpperCase());
				rtnEntity.setDbUser(eachTag[1].toUpperCase());
				rtnEntity.setTableName(eachTag[2].toUpperCase());
			}

			if (rtnEntity.getLog().contains("svn_admin")) {
				rtnEntity.setTagName("IGNORE");
			}

			StringBuilder changedBuilder = new StringBuilder();
			while (changedScanner.hasNextLine()) {
				changedBuilder.append(changedScanner.nextLine().trim() + "\n");
			}
			rtnEntity.setChanged(changedBuilder.toString());

			if (!rtnEntity.getChanged().contains("prepareToUpdate")) {
				rtnEntity.setTagName("IGNORE");
			}

			rtnEntity.setRevisionNumber(new Integer(revisionNumber.trim()));

			logger.debug(rtnEntity.toString());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			return null;
		} finally {
			if (authorScanner != null) {
				authorScanner.close();
			}

			if (dateScanner != null) {
				dateScanner.close();
			}

			if (logScanner != null) {
				logScanner.close();
			}

			if (changedScanner != null) {
				changedScanner.close();
			}
		}

		return rtnEntity;
	}

	@Override
	public SqlLogDetailEntity combineUpdateEntity(String logFilePath, String revisionNumber) {
		if (logFilePath == null || revisionNumber == null) {
			return null;
		}

		SqlLogDetailEntity rtnEntity = new SqlLogDetailEntity();

		Scanner logScanner = null;
		try {
			/* load scanner . */
			if (isUnitTestMode.booleanValue()) {
				logScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(logFilePath.trim()).getFile()));
			} else {
				logScanner = new Scanner(new File(logFilePath.trim()));
			}

			/* set log, revision number, module name, DB user, table name . */
			StringBuilder logBuilder = new StringBuilder();
			while (logScanner.hasNextLine()) {
				logBuilder.append(logScanner.nextLine().trim());
			}
			rtnEntity.setLog(logBuilder.toString());

			String[] eachLineLog = rtnEntity.getLog().split("\n");
			String[] eachTag = eachLineLog[0].replace("[", "").split("]");
			if (eachTag.length == 3) {
				rtnEntity.setModuleName(eachTag[0].toUpperCase());
				rtnEntity.setDbUser(eachTag[1].toUpperCase());
				rtnEntity.setTableName(eachTag[2].toUpperCase());
			}

			if (rtnEntity.getLog().contains("svn_admin")) {
				rtnEntity.setTagName("IGNORE");
			}

			rtnEntity.setRevisionNumber(new Integer(revisionNumber.trim()));

			logger.debug(rtnEntity.toString());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			return null;
		} finally {
			if (logScanner != null) {
				logScanner.close();
			}
		}

		return rtnEntity;
	}

	@Override
	public Boolean commitNotify(SqlLogDetailEntity entity) {
		if (entity == null) {
			return Boolean.FALSE;
		}

		if (entity.getLog().contains("svn_admin")) {
			return Boolean.TRUE;
		}

		if (!entity.getChanged().contains("prepareToUpdate")) {
			return Boolean.TRUE;
		}

		StringBuilder subjectBuilder = new StringBuilder();
		subjectBuilder.append("[" + entity.getModuleName() + "]");
		subjectBuilder.append("[" + entity.getDbUser() + "]");
		subjectBuilder.append("[" + entity.getTableName() + "]");

		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("Dear All：<br>");
		contentBuilder.append("svn/gt-D-sql/ 有更動, 以下為詳細資料：<br><br>");
		contentBuilder.append("AUTHOR：<br>");
		contentBuilder.append(entity.getAuthor() + "<br><br>");
		contentBuilder.append("DATE：<br>");
		contentBuilder.append(entity.getDate() + "<br><br>");
		contentBuilder.append("LOG：<br>");
		contentBuilder.append(entity.getLog().replace("\n", "<br>") + "<br>");
		contentBuilder.append("CHANGED：<br>");
		contentBuilder.append(entity.getChanged().replace("\n", "<br>") + "<br>");
		contentBuilder.append("Regards,<br>");
		contentBuilder.append("syscom.v4.svn");

		if (isUnitTestMode.booleanValue()) {
			sendMailService.setIsUnitTestMode(Boolean.TRUE);
		}
		sendMailService.setSubject(subjectBuilder.toString());
		sendMailService.setMailContent(contentBuilder.toString());
		sendMailService.setMailAddressList(sqlParam.getCommitNotifyMailAddressList());
		sendMailService.setCcMailAddressList(sqlParam.getCommitNotifyCcAddressList());
		sendMailService.setAttachFileNameList(null);
		Boolean sendResult = sendMailService.sendMailWithAttachFiles();

		return sendResult;
	}

	@Override
	public Boolean insert(SqlLogDetailEntity entity) {
		if (entity == null) {
			logger.error("insert entity null !");
			return Boolean.FALSE;
		}

		return sqlLogDetailRepository.insert(entity);
	}

	@Override
	public Boolean deleteAll() {
		return sqlLogDetailRepository.deleteAll();
	}

	@Override
	public Boolean update(SqlLogDetailEntity entity) {
		if (entity == null) {
			logger.error("update entity null !");
			return Boolean.FALSE;
		}

		return sqlLogDetailRepository.update(entity);
	}

	@Override
	public void setIsUnitTestMode(Boolean isUnitTestMode) {
		this.isUnitTestMode = isUnitTestMode;
	}

}
