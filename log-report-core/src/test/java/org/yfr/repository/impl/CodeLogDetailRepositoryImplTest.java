
package org.yfr.repository.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.entity.CodeLogDetailEntity;
import org.yfr.repository.CodeLogDetailRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class CodeLogDetailRepositoryImplTest {

	@Resource
	CodeLogDetailRepository codeLogDetailRepository;

	@Test
	public void test() {
		assertEquals(Boolean.FALSE, codeLogDetailRepository.insert(null));

		assertEquals(null, codeLogDetailRepository.queryYoungestRev());

		CodeLogDetailEntity entity = new CodeLogDetailEntity();
		assertEquals(Boolean.FALSE, codeLogDetailRepository.insert(entity));

		for (int i = 1; i <= 10; i++) {
			entity.setRevisionNumber(new Integer(i));
			assertEquals(Boolean.TRUE, codeLogDetailRepository.insert(entity));
		}

		assertEquals(Boolean.FALSE, codeLogDetailRepository.update(null));

		entity.setLog(new String("[x][Test Change Log]"));
		assertEquals(Boolean.TRUE, codeLogDetailRepository.update(entity));

		assertEquals(Boolean.FALSE, codeLogDetailRepository.updateTagName(null, null, null));
		assertEquals(Boolean.FALSE, codeLogDetailRepository.updateTagName(null, new Integer(1), new Integer(1)));
		assertEquals(Boolean.FALSE, codeLogDetailRepository.updateTagName(new String("2014_1223_1506"), null, new Integer(1)));
		assertEquals(Boolean.FALSE, codeLogDetailRepository.updateTagName(new String("2014_1223_1506"), new Integer(1), null));

		assertEquals(Boolean.TRUE, codeLogDetailRepository.updateTagName(new String("2014_1223_1506"), new Integer(1), new Integer(5)));
		assertEquals(Boolean.TRUE, codeLogDetailRepository.updateTagName(new String("IGNORE"), new Integer(7), new Integer(7)));

		assertEquals(new Integer(6), codeLogDetailRepository.queryMinNoTagRev());
		assertEquals(new Integer(10), codeLogDetailRepository.queryYoungestRev());

		assertEquals(0, codeLogDetailRepository.query(null, null).size());
		assertEquals(0, codeLogDetailRepository.query(new Integer(1), null).size());
		assertEquals(0, codeLogDetailRepository.query(null, new Integer(1)).size());
		assertEquals(3, codeLogDetailRepository.query(new Integer(1), new Integer(3)).size());

		assertEquals(0, codeLogDetailRepository.queryTagNameIsNull(null, null).size());
		assertEquals(0, codeLogDetailRepository.queryTagNameIsNull(new Integer(1), null).size());
		assertEquals(0, codeLogDetailRepository.queryTagNameIsNull(null, new Integer(1)).size());
		assertEquals(4, codeLogDetailRepository.queryTagNameIsNull(new Integer(6), new Integer(10)).size());

		assertEquals(Boolean.TRUE, codeLogDetailRepository.updateTagName(new String("2014_1224_1609"), new Integer(6), new Integer(10)));
		assertEquals(null, codeLogDetailRepository.queryMinNoTagRev());
	}

	@After
	public void clean() {
		assertEquals(Boolean.TRUE, codeLogDetailRepository.deleteAll());
	}

}
