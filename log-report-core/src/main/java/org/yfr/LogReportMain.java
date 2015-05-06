
package org.yfr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yfr.common.constant.ActionType;
import org.yfr.common.constant.LogConstant;
import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.BuildVersionService;
import org.yfr.common.service.SendMailService;
import org.yfr.entity.CodeLogDetailEntity;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.manager.ApplicationContextManager;
import org.yfr.service.CodeExpSvnInfoService;
import org.yfr.service.CodeGenLogReportService;
import org.yfr.service.CodeLogDetailService;
import org.yfr.service.SqlExpSvnInfoService;
import org.yfr.service.SqlGenLogReportService;
import org.yfr.service.SqlLogDetailService;
import org.yfr.vo.BuildParam;

/**
 * main class of log-report-core .
 * 
 * <p>setup time <b>Dec 29, 2014 11:52:08 AM .</b></p>
 *
 * @author Vincent Huang
 */
public class LogReportMain {

	/** logger . */
	static Log logger = LogFactory.getLog(LogReportMain.class);

	/**
	 * main function of log-report .
	 * 
	 * @param args - the args <b>String[]</b> to set .<br>
	 * <ol>
	 *   <li>code pre-commit.bat
	 * 	   <ul>
	 * 	     <li>args[0] = ActionType.CODE</li>
	 * 	     <li>args[1] = ActionType.PRE</li>
	 *    	 <li>args[2] = path of author.txt</li>
	 *    	 <li>args[3] = path of log.txt</li>
	 *     </ul>
	 *   </li>
	 *   <li>code post-commit.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.CODE</li>	
	 *       <li>args[1] = ActionType.POST</li>
	 *       <li>args[2] = path of author.txt</li>
	 *       <li>args[3] = path of date.txt</li>
	 *       <li>args[4] = path of log.txt</li>
	 *       <li>args[5] = path of changed.txt</li>
	 *       <li>args[6] = revision number</li>
	 *     </ul>
	 *   </li>  
	 *   <li>code post-revprop-change.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.CODE</li>
	 *       <li>args[1] = ActionType.UPDATE</li>
	 *       <li>args[2] = path of modify log.txt</li>
	 *       <li>args[3] = revision number</li>
	 *     </ul>
	 *   </li> 
	 *   <li>code gen-report.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.CODE</li>
	 *       <li>args[1] = ActionType.GEN_REPORT</li>
	 *       <li>args[2] = sheet name</li>
	 *       <li>args[3] = start revision number</li>
	 *       <li>args[4] = end revision number</li>
	 *     </ul>
	 *   </li>
	 *   <li>code full-exp.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.CODE</li> 
	 *       <li>args[1] = ActionType.FULL_EXP</li>
	 *       <li>args[2] = start revision number</li>
	 *       <li>args[3] = end revision number</li>
	 *       <li>args[4] = skip exp or not</li>
	 *     </ul>
	 *   </li>
	 *   <li>sql pre-commit.bat
	 * 	   <ul>
	 * 	     <li>args[0] = ActionType.SQL</li>
	 * 	     <li>args[1] = ActionType.PRE</li>
	 *    	 <li>args[2] = path of author.txt</li>
	 *    	 <li>args[3] = path of log.txt</li>
	 *     </ul>
	 *   </li>
	 *   <li>sql post-commit.bat  
	 *     <ul>
	 *     	 <li>args[0] = ActionType.SQL</li>	
	 *       <li>args[1] = ActionType.POST</li>
	 *       <li>args[2] = path of author.txt</li>
	 *       <li>args[3] = path of date.txt</li>
	 *       <li>args[4] = path of log.txt</li>
	 *       <li>args[5] = path of changed.txt</li>
	 *       <li>args[6] = revision number</li>
	 *     </ul>
	 *   </li>
	 *   <li>sql post-revprop-change.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.SQL</li>
	 *       <li>args[1] = ActionType.UPDATE</li>
	 *       <li>args[2] = path of modify log.txt</li>
	 *       <li>args[3] = revision number</li>
	 *     </ul>
	 *   </li>
	 *   <li>sql gen-report.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.SQL</li>
	 *       <li>args[1] = ActionType.GEN_REPORT</li>
	 *       <li>args[2] = sheet name</li>
	 *       <li>args[3] = start revision number</li>
	 *       <li>args[4] = end revision number</li>
	 *     </ul>
	 *   </li>
	 *   <li>sql full-exp.bat
	 *     <ul>
	 *     	 <li>args[0] = ActionType.SQL</li> 
	 *       <li>args[1] = ActionType.FULL_EXP</li>
	 *       <li>args[2] = start revision number</li>
	 *       <li>args[3] = end revision number</li>
	 *       <li>args[4] = skip exp or not</li>
	 *     </ul>
	 *   </li>
	 *   <li>build mode
	 *     <ul> 
	 *       <li>args[0] = ActionType.BUILD</li>
	 *       <li>args[1] = build id (yyyy-MM-dd_HH-mm-ss)</li>
	 *       <li>args[2] = has sql or not</li>
	 *     </ul>
	 *   </li>
	 * </ol>
	 */
	public static void main(String[] args) {
		/* get all needed bean from application context . */
		CodeLogDetailService codeLogDetailService = ApplicationContextManager.INSTANCE.getBean("codeLogDetailService", CodeLogDetailService.class);
		CodeExpSvnInfoService codeExpSvnInfoService = ApplicationContextManager.INSTANCE.getBean("codeExpSvnInfoService", CodeExpSvnInfoService.class);
		CodeGenLogReportService codeGenLogReportService = ApplicationContextManager.INSTANCE.getBean("codeGenLogReportService", CodeGenLogReportService.class);

		SqlLogDetailService sqlLogDetailService = ApplicationContextManager.INSTANCE.getBean("sqlLogDetailService", SqlLogDetailService.class);
		SqlExpSvnInfoService sqlExpSvnInfoService = ApplicationContextManager.INSTANCE.getBean("sqlExpSvnInfoService", SqlExpSvnInfoService.class);
		SqlGenLogReportService sqlGenLogReportService = ApplicationContextManager.INSTANCE.getBean("sqlGenLogReportService", SqlGenLogReportService.class);

		BuildVersionService buildVesrionService = ApplicationContextManager.INSTANCE.getBean("buildVersionService", BuildVersionService.class);
		SendMailService sendMailService = ApplicationContextManager.INSTANCE.getBean("sendMailService", SendMailService.class);

		BuildParam buildParam = ApplicationContextManager.INSTANCE.getBean("buildParam", BuildParam.class);

		for (int i = 0; i < args.length; i++) {
			logger.debug("args[" + i + "] = " + args[i]);
		}

		/* >>>>> code program action . <<<<< */
		if (args[0].trim().equals(ActionType.CODE.toString())) {
			if (args[1].trim().equals(ActionType.PRE.toString())) {
				logger.info(LogConstant.CODE_PRE_COMMIT_BAT_CALL.toString());

				ProgramState programState = codeLogDetailService.verify(args[2], args[3]);
				if (programState.equals(ProgramState.FORMAT_CORRECT) == false) {
					if (programState.equals(ProgramState.IGNORE_AUTHOR) == false) {
						System.exit(1);
					}
				}
			} else if (args[1].trim().equals(ActionType.POST.toString())) {
				logger.info(LogConstant.CODE_POST_COMMIT_BAT_CALL.toString());

				CodeLogDetailEntity entityForInsert = codeLogDetailService.combineEntity(args[2], args[3], args[4], args[5], args[6]);
				codeLogDetailService.insert(entityForInsert);
			} else if (args[1].trim().equals(ActionType.UPDATE.toString())) {
				logger.info(LogConstant.CODE_POST_REVPROP_CHANGE_BAT_CALL.toString());

				CodeLogDetailEntity entityForUpdate = codeLogDetailService.combineUpdateEntity(args[2], args[3]);
				codeLogDetailService.update(entityForUpdate);
			} else if (args[1].trim().equals(ActionType.GEN_REPORT.toString())) {
				logger.info(LogConstant.CODE_GEN_REPORT_BAT_CALL.toString());

				codeGenLogReportService.genSimpleReport(args[2], args[3], args[4]);
			} else if (args[1].trim().equals(ActionType.FULL_EXP.toString())) {
				logger.info(LogConstant.CODE_FULL_EXP_BAT_CALL.toString());

				if (args[4].trim().equals("Y")) {
					codeExpSvnInfoService.impTxt2DB(args[2].trim(), args[3].trim());
				} else if (args[4].trim().equals("N")) {
					Boolean expResult = codeExpSvnInfoService.expSvnInfo2Txt(args[2].trim(), args[3].trim());
					if (expResult.booleanValue()) {
						codeExpSvnInfoService.impTxt2DB(args[2].trim(), args[3].trim());
					}
				}
			}
		}

		/* >>>>> sql program action . <<<<< */
		if (args[0].trim().equals(ActionType.SQL.toString())) {
			if (args[1].trim().equals(ActionType.PRE.toString())) {
				logger.info(LogConstant.SQL_PRE_COMMIT_BAT_CALL.toString());

				ProgramState programState = sqlLogDetailService.verify(args[2], args[3]);
				if (programState.equals(ProgramState.FORMAT_CORRECT) == false) {
					if (programState.equals(ProgramState.SVN_ADMIN) == false) {
						System.exit(1);
					}
				}
			} else if (args[1].trim().equals(ActionType.POST.toString())) {
				logger.info(LogConstant.SQL_POST_COMMIT_BAT_CALL.toString());

				SqlLogDetailEntity entityForInsert = sqlLogDetailService.combineEntity(args[2], args[3], args[4], args[5], args[6]);
				Boolean insertResult = sqlLogDetailService.insert(entityForInsert);
				if (insertResult == Boolean.TRUE) {
					sqlLogDetailService.commitNotify(entityForInsert);
				}
			} else if (args[1].trim().equals(ActionType.UPDATE.toString())) {
				logger.info(LogConstant.SQL_POST_REVPROP_CHANGE_BAT_CALL.toString());

				SqlLogDetailEntity entityForUpdate = sqlLogDetailService.combineUpdateEntity(args[2], args[3]);
				sqlLogDetailService.update(entityForUpdate);
			} else if (args[1].trim().equals(ActionType.GEN_REPORT.toString())) {
				logger.info(LogConstant.SQL_GEN_REPORT_BAT_CALL.toString());

				sqlGenLogReportService.genSimpleReport(args[2], args[3], args[4]);
			} else if (args[1].trim().equals(ActionType.FULL_EXP.toString())) {
				logger.info(LogConstant.SQL_FULL_EXP_BAT_CALL.toString());

				if (args[4].trim().equals("Y")) {
					sqlExpSvnInfoService.impTxt2DB(args[2].trim(), args[3].trim());
				} else if (args[4].trim().equals("N")) {
					Boolean expResult = sqlExpSvnInfoService.expSvnInfo2Txt(args[2].trim(), args[3].trim());
					if (expResult.booleanValue()) {
						sqlExpSvnInfoService.impTxt2DB(args[2].trim(), args[3].trim());
					}
				}
			}
		}

		/* >>>>> build mode (call by manual or jenkins) . <<<<< */
		if (args[0].trim().equals(ActionType.BUILD.toString())) {
			logger.info(LogConstant.BUILD_MODE_CALL.toString());

			String buildId = null;
			try {
				Date parse = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").parse(args[1].trim());
				buildId = new SimpleDateFormat("yyyyMMdd_HHmm").format(parse);
			} catch (ParseException ex) {
				logger.fatal(ex.getMessage(), ex);
				System.exit(1);
			}

			List<String> attachFileList = new LinkedList<String>();
			if (args[2].trim().equals("Y")) {
				if (codeGenLogReportService.genBuildReport(buildId).equals(Boolean.TRUE)) {
					attachFileList.add("./output/report/Code_" + buildId + ".xls");
				}
				if (sqlGenLogReportService.genBuildReport(buildId).equals(Boolean.TRUE)) {
					attachFileList.add("./output/report/Sql_" + buildId + ".xls");
				}

				ProgramState updateCodeTagNameResult = buildVesrionService.updateCodeTagName(buildId);
				ProgramState updateSqlTagNameResult = buildVesrionService.updateSqlTagName(buildId);
				logger.debug(updateCodeTagNameResult.toString());
				logger.debug(updateSqlTagNameResult.toString());
			} else if (args[2].trim().equals("N")) {
				if (codeGenLogReportService.genBuildReport(buildId).equals(Boolean.TRUE)) {
					attachFileList.add("./output/report/Code_" + buildId + ".xls");
				}

				ProgramState updateCodeTagNameResult = buildVesrionService.updateCodeTagName(buildId);
				logger.debug(updateCodeTagNameResult.toString());
			}

			sendMailService.setSubject(buildParam.getSubject() + buildId);
			sendMailService.setMailContent(buildParam.getMailContent());
			sendMailService.setMailAddressList(buildParam.getMailAddressList());
			sendMailService.setCcMailAddressList(buildParam.getCcAddressList());
			sendMailService.setAttachFileNameList(attachFileList);
			sendMailService.sendMailWithAttachFiles();
		}
	}

}