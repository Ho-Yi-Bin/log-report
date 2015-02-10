
package org.yfr.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.service.CodeGenLogReportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class CodeGenLogReportServiceImplTest {

	@Resource
	CodeGenLogReportService codeGenLogReportService;

	@Test
	public void testGenSimpleReport() {
		codeGenLogReportService.setIsUnitTestMode(Boolean.TRUE);
		assertEquals(Boolean.FALSE, codeGenLogReportService.genSimpleReport(null, "1", "10"));
		assertEquals(Boolean.FALSE, codeGenLogReportService.genSimpleReport("Manual", null, "10"));
		assertEquals(Boolean.FALSE, codeGenLogReportService.genSimpleReport("Manual", "1", null));
		assertEquals(Boolean.FALSE, codeGenLogReportService.genSimpleReport("Manual", "1", "abc"));
		assertEquals(Boolean.FALSE, codeGenLogReportService.genSimpleReport("Manual", "abc", "10"));
		assertEquals(Boolean.TRUE, codeGenLogReportService.genSimpleReport("Manual", "1", "100"));
	}

	@Test
	@Ignore
	public void testGenBuildReport() {
		codeGenLogReportService.setIsUnitTestMode(Boolean.TRUE);
		assertEquals(Boolean.TRUE, codeGenLogReportService.genBuildReport("20150106_1156"));
	}

}
