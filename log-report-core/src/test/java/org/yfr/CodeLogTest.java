
package org.yfr;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.repository.CodeLogDetailRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class CodeLogTest {

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

	@Before
	public void up() {
		System.out.println("YA !YA !");
	}
	
	@Test
	public void test() {
		
		System.out.println(codeLogDetailRepository.query(1000, 1003));
	}

	@After
	public void clean() {
		System.out.println("finall clear !!");
	}

}
