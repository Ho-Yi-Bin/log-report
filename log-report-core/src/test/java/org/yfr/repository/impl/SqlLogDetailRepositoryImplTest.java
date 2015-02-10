
package org.yfr.repository.impl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.repository.SqlLogDetailRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:conf/applicationContext.xml"})
public class SqlLogDetailRepositoryImplTest {

	@Resource
	SqlLogDetailRepository sqlLogDetailRepository;

	@Test
	public void test() {
		assertEquals(Boolean.FALSE, sqlLogDetailRepository.insert(null));

		assertEquals(null, sqlLogDetailRepository.queryYoungestRev());

		SqlLogDetailEntity entity = new SqlLogDetailEntity();
		assertEquals(Boolean.FALSE, sqlLogDetailRepository.insert(entity));

		for (int i = 1; i <= 10; i++) {
			entity.setRevisionNumber(new Integer(i));
			assertEquals(Boolean.TRUE, sqlLogDetailRepository.insert(entity));
		}

		assertEquals(Boolean.FALSE, sqlLogDetailRepository.update(null));

		entity.setLog(new String("[OMS][gtd][Test Test]\nTest Note"));
		assertEquals(Boolean.TRUE, sqlLogDetailRepository.update(entity));

		assertEquals(Boolean.FALSE, sqlLogDetailRepository.updateTagName(null, null, null));
		assertEquals(Boolean.FALSE, sqlLogDetailRepository.updateTagName(null, new Integer(1), new Integer(1)));
		assertEquals(Boolean.FALSE, sqlLogDetailRepository.updateTagName(new String("2014_1223_1506"), null, new Integer(1)));
		assertEquals(Boolean.FALSE, sqlLogDetailRepository.updateTagName(new String("2014_1223_1506"), new Integer(1), null));

		assertEquals(Boolean.TRUE, sqlLogDetailRepository.updateTagName(new String("2014_1223_1506"), new Integer(1), new Integer(5)));
		assertEquals(Boolean.TRUE, sqlLogDetailRepository.updateTagName(new String("IGNORE"), new Integer(7), new Integer(7)));

		assertEquals(new Integer(6), sqlLogDetailRepository.queryMinNoTagRev());
		assertEquals(new Integer(10), sqlLogDetailRepository.queryYoungestRev());

		assertEquals(0, sqlLogDetailRepository.query(null, null).size());
		assertEquals(0, sqlLogDetailRepository.query(new Integer(1), null).size());
		assertEquals(0, sqlLogDetailRepository.query(null, new Integer(1)).size());
		assertEquals(3, sqlLogDetailRepository.query(new Integer(1), new Integer(3)).size());

		assertEquals(0, sqlLogDetailRepository.queryTagNameIsNull(null, null).size());
		assertEquals(0, sqlLogDetailRepository.queryTagNameIsNull(new Integer(1), null).size());
		assertEquals(0, sqlLogDetailRepository.queryTagNameIsNull(null, new Integer(1)).size());
		assertEquals(4, sqlLogDetailRepository.queryTagNameIsNull(new Integer(6), new Integer(10)).size());

		assertEquals(Boolean.TRUE, sqlLogDetailRepository.updateTagName(new String("2014_1224_1609"), new Integer(6), new Integer(10)));
		assertEquals(null, sqlLogDetailRepository.queryMinNoTagRev());
	}

	@After
	public void clean() {
		assertEquals(Boolean.TRUE, sqlLogDetailRepository.deleteAll());
	}

}
