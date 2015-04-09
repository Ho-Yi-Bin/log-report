
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
import org.yfr.entity.CodeLogDetailEntity;
import org.yfr.repository.CodeLogDetailRepository;
import org.yfr.service.CodeLogDetailService;
import org.yfr.vo.CodeParam;

/**
 * code log detail service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 3:58:44 PM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("codeLogDetailService")
public class CodeLogDetailServiceImpl implements CodeLogDetailService {

	Log logger = LogFactory.getLog(this.getClass());

	Boolean isUnitTestMode = Boolean.FALSE;

	ProgramState programState;

	@Resource
	CodeParam codeParam;

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

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

			/* get author and log . */
			/* you can use nextLine() directly because the empty file will have two line . */
			String author = authorScanner.nextLine().trim();
			String log = logScanner.nextLine().trim();
			logger.info(author);
			logger.info(log);

			/* throw exception if author in ignore author list . */
			List<String> ignoreNameList = codeParam.getIgnoreAuthorList();
			if (ignoreNameList.contains(author)) {
				logger.info("ignore author");

				return ProgramState.IGNORE_AUTHOR;
			}

			/* throw exception if file is empty . */
			if (log == null || log.length() < 2) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (Log Empty) !");
				System.err.println("\n>>>>>" + codeParam.getLogTemplate1());
				System.err.println(">>>>>" + codeParam.getLogTemplate2());

				return ProgramState.LOG_FILE_IS_EMPTY;
			}

			/* throw exception if tag error . */
			if ((!(log.substring(1, 2).toUpperCase().equals("V"))) && (!(log.substring(1, 2).toUpperCase().equals("X")))) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (First Bracket Content Error) !");
				System.err.println("\n>>>>>" + codeParam.getLogTemplate1());
				System.err.println(">>>>>" + codeParam.getLogTemplate2());

				return ProgramState.TAG_ERROR;
			}

			/* throw exception if format v error . */
			String[] splitLogV = log.replace("[", "").split("]");
			if ((log.substring(1, 2).toUpperCase().equals("V")) && (splitLogV.length != 4)) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (V Format Error) !");
				System.err.println("\n>>>>>" + codeParam.getLogTemplate1());

				return ProgramState.V_FORMAT_ERROR;
			}

			/* throw exception if module name no in module name list . */
			List<String> moduleNameList = codeParam.getPermitModuleNameList();
			if ((log.substring(1, 2).toUpperCase().equals("V")) && (!(moduleNameList.contains(splitLogV[1].toUpperCase().trim())))) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (No This Module Name) !");
				System.err.println("\n>>>>>" + codeParam.getLogTemplate1());

				return ProgramState.MODULE_NAME_ERROR;
			}

			/* throw exception if format x error . */
			String[] splitLogX = log.replace("[", "").split("]");
			if ((log.substring(1, 2).toUpperCase().equals("X")) && (splitLogX.length != 2)) {
				System.err.println("\n>>>>> Error Message : ");
				System.err.println(">>>>> Wrong log format (X Format Error) !");
				System.err.println(">>>>>" + codeParam.getLogTemplate2());

				return ProgramState.X_FORMAT_ERROR;
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
	public CodeLogDetailEntity combineEntity(String authorFilePath, String dateFilePath, String logFilePath, String changedFilePath, String revisionNumber) {
		if (authorFilePath == null || dateFilePath == null || logFilePath == null || changedFilePath == null || revisionNumber == null) {
			return null;
		}

		CodeLogDetailEntity rtnEntity = new CodeLogDetailEntity();

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

			/* set author, date, log, changed, revision number, module name, generate flag . */
			rtnEntity.setAuthor(authorScanner.nextLine().trim());
			rtnEntity.setDate(dateScanner.nextLine().substring(0, 19));

			StringBuilder logBuilder = new StringBuilder();
			while (logScanner.hasNextLine()) {
				logBuilder.append(logScanner.nextLine().trim() + "\n");
			}
			rtnEntity.setLog(logBuilder.toString());

			if ((rtnEntity.getLog().trim().length() > 0) && (rtnEntity.getLog().substring(0, 2).toUpperCase().equals("[V"))) {
				rtnEntity.setGenerateFlag("YES");

				String[] eachLineLog = rtnEntity.getLog().split("\n");
				String[] eachTag = eachLineLog[0].replace("[", "").split("]");
				rtnEntity.setModuleName(eachTag[1].toUpperCase());
			} else {
				rtnEntity.setGenerateFlag("NO");
			}

			StringBuilder changedBuilder = new StringBuilder();
			while (changedScanner.hasNextLine()) {
				changedBuilder.append(changedScanner.nextLine().trim() + "\n");
			}
			rtnEntity.setChanged(changedBuilder.toString());

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
	public CodeLogDetailEntity combineUpdateEntity(String logFilePath, String revisionNumber) {
		if (logFilePath == null || revisionNumber == null) {
			return null;
		}

		CodeLogDetailEntity rtnEntity = new CodeLogDetailEntity();

		Scanner logScanner = null;
		try {
			/* load scanner . */
			if (isUnitTestMode.booleanValue()) {
				logScanner = new Scanner(new File(Thread.currentThread().getContextClassLoader().getResource(logFilePath.trim()).getFile()));
			} else {
				logScanner = new Scanner(new File(logFilePath.trim()));
			}

			/* set log, revision number, module name, generate flag . */
			StringBuilder logBuilder = new StringBuilder();
			while (logScanner.hasNextLine()) {
				logBuilder.append(logScanner.nextLine().trim());
			}
			rtnEntity.setLog(logBuilder.toString());

			if ((rtnEntity.getLog().trim().length() > 0) && (rtnEntity.getLog().substring(0, 2).toUpperCase().equals("[V"))) {
				rtnEntity.setGenerateFlag("YES");

				String[] eachLineLog = rtnEntity.getLog().split("\n");
				String[] eachTag = eachLineLog[0].replace("[", "").split("]");
				rtnEntity.setModuleName(eachTag[1].toUpperCase());
			} else {
				rtnEntity.setGenerateFlag("NO");
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
	public Boolean insert(CodeLogDetailEntity entity) {
		if (entity == null) {
			logger.error("insert entity null !");
			return Boolean.FALSE;
		}

		return codeLogDetailRepository.insert(entity);
	}

	@Override
	public Boolean deleteAll() {
		return codeLogDetailRepository.deleteAll();
	}

	@Override
	public Boolean update(CodeLogDetailEntity entity) {
		if (entity == null) {
			logger.error("update entity null !");
			return Boolean.FALSE;
		}

		return codeLogDetailRepository.update(entity);
	}

	@Override
	public void setIsUnitTestMode(Boolean isUnitTestMode) {
		this.isUnitTestMode = isUnitTestMode;
	}

}
