
package org.yfr.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.service.SqlGenLogReportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class SqlGenLogReportServiceImplTest {

	@Resource
	SqlGenLogReportService sqlGenLogReportService;

	@Test
	public void testGenReport() {
		sqlGenLogReportService.setIsUnitTestMode(Boolean.TRUE);
		assertEquals(Boolean.FALSE, sqlGenLogReportService.genSimpleReport(null, "1", "10"));
		assertEquals(Boolean.FALSE, sqlGenLogReportService.genSimpleReport("Manual", null, "10"));
		assertEquals(Boolean.FALSE, sqlGenLogReportService.genSimpleReport("Manual", "1", null));
		assertEquals(Boolean.FALSE, sqlGenLogReportService.genSimpleReport("Manual", "1", "abc"));
		assertEquals(Boolean.FALSE, sqlGenLogReportService.genSimpleReport("Manual", "abc", "10"));
		assertEquals(Boolean.TRUE, sqlGenLogReportService.genSimpleReport("Manual", "1", "10"));
	}

}
