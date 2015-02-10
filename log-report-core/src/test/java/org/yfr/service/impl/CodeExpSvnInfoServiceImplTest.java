
package org.yfr.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.service.CodeExpSvnInfoService;
import org.yfr.service.CodeLogDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class CodeExpSvnInfoServiceImplTest {

	@Resource
	CodeExpSvnInfoService codeExpSvnInfoService;

	@Resource
	CodeLogDetailService codeLogDetailService;

	@Test
	public void test() {
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.expSvnInfo2Txt(null, "10"));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.expSvnInfo2Txt("1", null));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.expSvnInfo2Txt("abc", "10"));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.expSvnInfo2Txt("1", "abc"));

		assertEquals(Boolean.TRUE, codeExpSvnInfoService.expSvnInfo2Txt("1", "10"));

		assertEquals(Boolean.FALSE, codeExpSvnInfoService.impTxt2DB(null, "10"));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.impTxt2DB("1", null));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.impTxt2DB("abc", "10"));
		assertEquals(Boolean.FALSE, codeExpSvnInfoService.impTxt2DB("1", "abc"));

		assertEquals(Boolean.TRUE, codeExpSvnInfoService.impTxt2DB("1", "10"));
	}

	@After
	public void clean() {
		assertEquals(Boolean.TRUE, codeLogDetailService.deleteAll());
	}

}
