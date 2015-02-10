
package org.yfr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.common.enu.ProgramState;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.service.SqlLogDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class SqlLogDetailServiceImplTest {

	@Resource
	SqlLogDetailService sqlLogDetailService;

	/* the test about format correct . */
	@Test
	public void testFormatCorrect() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FORMAT_CORRECT, sqlLogDetailService.verify("author_Simple.txt", "log_sql_Correct.txt"));
	}

	/* the test about file not found . */
	@Test
	public void testAuthorFileNotFound() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FILE_NOT_FOUND, sqlLogDetailService.verify("autho.txt", "log_sql_Correct.txt"));
	}

	@Test
	public void testLogFileNotFound() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FILE_NOT_FOUND, sqlLogDetailService.verify("author_Simple.txt", "lo.txt"));
	}

	/* the test about empty file . */
	@Test
	public void testLogFileEmpty() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.LOG_FILE_IS_EMPTY, sqlLogDetailService.verify("author_Simple.txt", "log_Empty.txt"));
	}

	/* the test about module name error . */
	@Test
	public void testLogNoModuleName() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.MODULE_NAME_ERROR, sqlLogDetailService.verify("author_Simple.txt", "log_sql_NoModuleName.txt"));
	}

	/* the test about DB user error . */
	@Test
	public void testLogNoDbUser() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.DB_USER_ERROR, sqlLogDetailService.verify("author_Simple.txt", "log_sql_NoDbUser.txt"));
	}

	/* the test about about format error . */
	@Test
	public void testLogFormatError() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.V_FORMAT_ERROR, sqlLogDetailService.verify("author_Simple.txt", "log_sql_WrongFormat.txt"));
	}

	@Test
	public void testCombineEntity() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertNull(sqlLogDetailService.combineEntity(null, "date.txt", "log_sql_Correct.txt", "changed_sql.txt", "51"));
		assertNull(sqlLogDetailService.combineEntity("author_Simple.txt", null, "log_sql_Correct.txt", "changed_sql.txt", "51"));
		assertNull(sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", null, "changed_sql.txt", "51"));
		assertNull(sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Correct.txt", null, "51"));
		assertNull(sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Correct.txt", "changed_sql.txt", null));
		assertNotNull(sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Correct.txt", "changed_sql.txt", "51"));
	}

	@Test
	public void testCombineUpdateEntity() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertNull(sqlLogDetailService.combineUpdateEntity(null, "51"));
		assertNull(sqlLogDetailService.combineUpdateEntity("log_sql_Correct.txt", null));
		assertNotNull(sqlLogDetailService.combineUpdateEntity("log_sql_Correct.txt", "51"));
	}

	@Test
	public void testCommitNotify1() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		SqlLogDetailEntity combineEntity = sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Correct.txt", "changed_sql_IgnoreNotify.txt", "51");
		assertNotNull(combineEntity);
		assertEquals(Boolean.TRUE, sqlLogDetailService.commitNotify(combineEntity));
	}

	@Test
	public void testCommitNotify2() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		SqlLogDetailEntity combineEntity = sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Correct.txt", "changed_sql_notify.txt", "51");
		assertNotNull(combineEntity);
		assertEquals(Boolean.TRUE, sqlLogDetailService.commitNotify(combineEntity));
	}

	@Test
	public void testCommitNotify3() {
		sqlLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		SqlLogDetailEntity combineEntity = sqlLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_sql_Ignore.txt", "changed_sql.txt", "51");
		assertNotNull(combineEntity);
		assertEquals(Boolean.TRUE, sqlLogDetailService.commitNotify(combineEntity));
	}

}
