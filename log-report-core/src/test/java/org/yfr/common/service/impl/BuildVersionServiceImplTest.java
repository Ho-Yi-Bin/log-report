
package org.yfr.common.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.common.constant.ProgramState;
import org.yfr.common.service.BuildVersionService;
import org.yfr.entity.CodeLogDetailEntity;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.repository.CodeLogDetailRepository;
import org.yfr.repository.SqlLogDetailRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class BuildVersionServiceImplTest {

	@Resource
	BuildVersionService buildVersionService;

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

	@Resource
	SqlLogDetailRepository sqlLogDetailRepository;

	@Test
	public void test() {
		assertEquals(ProgramState.CODE_NO_NEW_VERSION, buildVersionService.updateCodeTagName("2014_1223_1506"));
		assertEquals(ProgramState.SQL_NO_NEW_VERSION, buildVersionService.updateSqlTagName("2014_1223_1506"));

		CodeLogDetailEntity codeLogDetailEntity = new CodeLogDetailEntity();
		for (int i = 1; i <= 10; i++) {
			codeLogDetailEntity.setRevisionNumber(new Integer(i));
			assertEquals(Boolean.TRUE, codeLogDetailRepository.insert(codeLogDetailEntity));
		}
		assertEquals(ProgramState.BUILD_ID_IS_NULL, buildVersionService.updateCodeTagName(null));
		assertEquals(ProgramState.UPDATE_CODE_TAG_NAME_SUCCESS, buildVersionService.updateCodeTagName("2014_1223_1506"));

		SqlLogDetailEntity sqlLogDetailEntity = new SqlLogDetailEntity();
		for (int i = 1; i <= 10; i++) {
			sqlLogDetailEntity.setRevisionNumber(new Integer(i));
			assertEquals(Boolean.TRUE, sqlLogDetailRepository.insert(sqlLogDetailEntity));
		}
		assertEquals(ProgramState.BUILD_ID_IS_NULL, buildVersionService.updateSqlTagName(null));
		assertEquals(ProgramState.UPDATE_SQL_TAG_NAME_SUCCESS, buildVersionService.updateSqlTagName("2014_1223_1506"));

		assertEquals(ProgramState.CODE_NO_NEW_VERSION, buildVersionService.updateCodeTagName("2014_1223_1506"));
		assertEquals(ProgramState.SQL_NO_NEW_VERSION, buildVersionService.updateSqlTagName("2014_1223_1506"));
	}

	@After
	public void clean() {
		assertEquals(Boolean.TRUE, codeLogDetailRepository.deleteAll());
		assertEquals(Boolean.TRUE, sqlLogDetailRepository.deleteAll());
	}

}
