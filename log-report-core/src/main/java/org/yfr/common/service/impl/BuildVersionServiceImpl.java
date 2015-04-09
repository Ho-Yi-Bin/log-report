
package org.yfr.common.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.BuildVersionService;
import org.yfr.repository.CodeLogDetailRepository;
import org.yfr.repository.SqlLogDetailRepository;

/**
 * build version service implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:36:26 AM .</b></p>
 *
 * @author Vincent Huang
 */
@Service("buildVersionService")
public class BuildVersionServiceImpl implements BuildVersionService {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

	@Resource
	SqlLogDetailRepository sqlLogDetailRepository;

	@Override
	public ProgramState updateCodeTagName(String buildId) {
		Integer codeMinNoTagRev = codeLogDetailRepository.queryMinNoTagRev();
		Integer codeYoungestRev = codeLogDetailRepository.queryYoungestRev();

		logger.debug("Code Minimum No Tag Revision Number : " + codeMinNoTagRev);
		logger.debug("Code Youngest Revision Number : " + codeYoungestRev);

		if (buildId == null) {
			return ProgramState.BUILD_ID_IS_NULL;
		}

		if (codeMinNoTagRev == null || codeYoungestRev == null) {
			return ProgramState.CODE_NO_NEW_VERSION;
		}

		if (codeLogDetailRepository.updateTagName(buildId, codeMinNoTagRev, codeYoungestRev).equals(Boolean.FALSE)) {
			return ProgramState.UPDATE_CODE_TAG_NAME_FAIL;
		}

		return ProgramState.UPDATE_CODE_TAG_NAME_SUCCESS;
	}

	@Override
	public ProgramState updateSqlTagName(String buildId) {
		Integer sqlMinNoTagRev = sqlLogDetailRepository.queryMinNoTagRev();
		Integer sqlYoungestRev = sqlLogDetailRepository.queryYoungestRev();

		logger.debug("Sql Minimum No Tag Revision Number : " + sqlMinNoTagRev);
		logger.debug("Sql Youngest Revision Number : " + sqlYoungestRev);

		if (buildId == null) {
			return ProgramState.BUILD_ID_IS_NULL;
		}

		if (sqlMinNoTagRev == null || sqlYoungestRev == null) {
			return ProgramState.SQL_NO_NEW_VERSION;
		}

		if (sqlLogDetailRepository.updateTagName(buildId, sqlMinNoTagRev, sqlYoungestRev).equals(Boolean.FALSE)) {
			return ProgramState.UPDATE_SQL_TAG_NAME_FAIL;
		}

		return ProgramState.UPDATE_SQL_TAG_NAME_SUCCESS;
	}

}
