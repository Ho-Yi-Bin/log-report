
package org.yfr.common.service.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.common.service.SendMailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class SendMailServiceImplTest {

	@Resource
	SendMailService sendMailService;

	@Test
	public void testSendMailWithAttachFiles() {
		sendMailService.setIsUnitTestMode(Boolean.TRUE);
		Boolean sendMailResult = sendMailService.sendMailWithAttachFiles();

		assertEquals(Boolean.TRUE, sendMailResult);
	}

}
