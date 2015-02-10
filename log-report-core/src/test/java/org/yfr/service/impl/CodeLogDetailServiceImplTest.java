
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
import org.yfr.service.CodeLogDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class CodeLogDetailServiceImplTest {

	@Resource
	CodeLogDetailService codeLogDetailService;

	/* the test about format V correct . */
	@Test
	public void testFormatVCorrect() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FORMAT_CORRECT, codeLogDetailService.verify("author_Simple.txt", "log_code_V_Correct.txt"));
	}

	@Test
	public void testFormatVCorrect2() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FORMAT_CORRECT, codeLogDetailService.verify("author_Simple.txt", "log_code_V_Correct2.txt"));
	}

	@Test
	public void testFormatXCorrect() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FORMAT_CORRECT, codeLogDetailService.verify("author_Simple.txt", "log_code_X_Correct.txt"));
	}

	@Test
	public void testFormatXCorrect2() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FORMAT_CORRECT, codeLogDetailService.verify("author_Simple.txt", "log_code_X_Correct2.txt"));
	}

	/* the test about ignore author . */
	@Test
	public void testIgnoreAuthor() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.IGNORE_AUTHOR, codeLogDetailService.verify("author_Ignore.txt", "log_Empty.txt"));
	}

	/* the test about file not found . */
	@Test
	public void testAuthorFileNotFound() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FILE_NOT_FOUND, codeLogDetailService.verify("autho.txt", "log_code_V_Correct.txt"));
	}

	@Test
	public void testLogFileNotFound() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.FILE_NOT_FOUND, codeLogDetailService.verify("author_Simple.txt", "lo.txt"));
	}

	@Test
	public void testLogFileEmpty() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.LOG_FILE_IS_EMPTY, codeLogDetailService.verify("author_Simple.txt", "log_Empty.txt"));
	}

	/* the test about tag error . */
	@Test
	public void testLogTagName() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.TAG_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_TagError.txt"));
	}

	/* the test about module name error . */
	@Test
	public void testLogVNoModuleName() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.MODULE_NAME_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_V_NoModuleName.txt"));
	}

	@Test
	public void testLogVNoModuleName2() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.MODULE_NAME_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_V_NoModuleName2.txt"));
	}

	/* the test about about format v error . */
	@Test
	public void testLogVFormatError() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.V_FORMAT_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_V_WrongFormat.txt"));
	}

	@Test
	public void testLogVFormatError2() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.V_FORMAT_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_V_WrongFormat2.txt"));
	}

	/* the test about about format x error . */
	@Test
	public void testLogXFormatError() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.X_FORMAT_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_X_WrongFormat.txt"));
	}

	@Test
	public void testLogXFormatError2() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertEquals(ProgramState.X_FORMAT_ERROR, codeLogDetailService.verify("author_Simple.txt", "log_code_X_WrongFormat2.txt"));
	}

	@Test
	public void testCombineEntity() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertNull(codeLogDetailService.combineEntity(null, "date.txt", "log_code_V_Correct.txt", "changed_code.txt", "51"));
		assertNull(codeLogDetailService.combineEntity("author_Simple.txt", null, "log_code_V_Correct.txt", "changed_code.txt", "51"));
		assertNull(codeLogDetailService.combineEntity("author_Simple.txt", "date.txt", null, "changed_code.txt", "51"));
		assertNull(codeLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_code_V_Correct.txt", null, "51"));
		assertNull(codeLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_code_V_Correct.txt", "changed_code.txt", null));
		assertNotNull(codeLogDetailService.combineEntity("author_Simple.txt", "date.txt", "log_code_V_Correct.txt", "changed_code.txt", "51"));
	}

	@Test
	public void testCombineUpdateEntity() {
		codeLogDetailService.setIsUnitTestMode(Boolean.TRUE);

		assertNull(codeLogDetailService.combineUpdateEntity(null, "51"));
		assertNull(codeLogDetailService.combineUpdateEntity("log_code_V_Correct.txt", null));
		assertNotNull(codeLogDetailService.combineUpdateEntity("log_code_V_Correct.txt", "51"));
	}

}
