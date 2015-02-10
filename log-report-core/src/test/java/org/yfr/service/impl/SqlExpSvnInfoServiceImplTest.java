
package org.yfr.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.service.SqlExpSvnInfoService;
import org.yfr.service.SqlLogDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class SqlExpSvnInfoServiceImplTest {

	@Resource
	SqlExpSvnInfoService sqlExpSvnInfoService;

	@Resource
	SqlLogDetailService sqlLogDetailService;

	@Test
	public void test() {
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.expSvnInfo2Txt(null, "60"));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.expSvnInfo2Txt("51", null));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.expSvnInfo2Txt("abc", "60"));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.expSvnInfo2Txt("51", "abc"));

		assertEquals(Boolean.TRUE, sqlExpSvnInfoService.expSvnInfo2Txt("51", "60"));

		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.impTxt2DB(null, "60"));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.impTxt2DB("51", null));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.impTxt2DB("abc", "60"));
		assertEquals(Boolean.FALSE, sqlExpSvnInfoService.impTxt2DB("51", "abc"));

		assertEquals(Boolean.TRUE, sqlExpSvnInfoService.impTxt2DB("51", "60"));
	}

	@After
	public void afterAllTest() {
		assertEquals(Boolean.TRUE, sqlLogDetailService.deleteAll());
	}

}
