
package org.yfr.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.yfr.entity.SqlLogDetailEntity;
import org.yfr.repository.SqlLogDetailRepository;

/**
 * sql log detail repository implement .
 * 
 * <p>setup time <b>Dec 29, 2014 11:51:01 AM .</b></p>
 *
 * @author Vincent Huang
 */
@Repository("sqlLogDetailRepository")
public class SqlLogDetailRepositoryImpl implements SqlLogDetailRepository {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	JdbcTemplate jdbcTemplate;

	@Override
	public Boolean insert(final SqlLogDetailEntity entity) {
		Boolean insertResult = Boolean.FALSE;

		if (entity == null || entity.getRevisionNumber() == null) {
			logger.info("Entity Null or Revision Number Null !");
			return insertResult;
		}

		try {
			jdbcTemplate.update("INSERT INTO SQL_LOG_DETAIL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pps) throws SQLException {
					pps.setString(1, entity.getAuthor());
					pps.setString(2, entity.getDate());
					pps.setString(3, entity.getLog());
					pps.setString(4, entity.getChanged());
					pps.setInt(5, entity.getRevisionNumber().intValue());
					pps.setString(6, entity.getModuleName());
					pps.setString(7, entity.getDbUser());
					pps.setString(8, entity.getTableName());
					pps.setString(9, entity.getTagName());
				}
			});

			insertResult = Boolean.TRUE;
			logger.info("Insert Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return insertResult;
	}

	@Override
	public Boolean deleteAll() {
		Boolean deleteResult = Boolean.FALSE;

		try {
			jdbcTemplate.update("DELETE FROM SQL_LOG_DETAIL");

			deleteResult = Boolean.TRUE;
			logger.info("Delete All Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return deleteResult;
	}

	@Override
	public Boolean update(final SqlLogDetailEntity entity) {
		Boolean updateResult = Boolean.FALSE;

		if (entity == null || entity.getRevisionNumber() == null) {
			logger.info("Entity Null or Revision Number Null !");
			return updateResult;
		}

		try {
			jdbcTemplate.update("UPDATE SQL_LOG_DETAIL SET LOG = ?, MODULE_NAME = ?, DB_USER = ?, TABLE_NAME = ? WHERE REVISION_NUMBER = ?", new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pps) throws SQLException {
					pps.setString(1, entity.getLog());
					pps.setString(2, entity.getModuleName());
					pps.setString(3, entity.getDbUser());
					pps.setString(4, entity.getTableName());
					pps.setInt(5, entity.getRevisionNumber().intValue());
				}
			});

			updateResult = Boolean.TRUE;
			logger.info("Update Success!");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return updateResult;
	}

	@Override
	public Boolean updateTagName(final String buildId, final Integer startRevisionNum, final Integer endRevisionNum) {
		Boolean updateResult = Boolean.FALSE;

		if (buildId == null || startRevisionNum == null || endRevisionNum == null) {
			logger.info("Build Id Null or Start Revision Number Null or End Revision Number Null !");
			return updateResult;
		}

		try {
			jdbcTemplate.update("UPDATE SQL_LOG_DETAIL SET TAG_NAME = ? WHERE REVISION_NUMBER >= ? AND REVISION_NUMBER <= ?", new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pps) throws SQLException {
					pps.setString(1, buildId);
					pps.setInt(2, startRevisionNum);
					pps.setInt(3, endRevisionNum);
				}
			});

			updateResult = Boolean.TRUE;
			logger.info("Update Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return updateResult;
	}

	@Override
	public List<SqlLogDetailEntity> query(Integer startRevisionNum, Integer endRevisionNum) {
		List<SqlLogDetailEntity> rtnList = new ArrayList<SqlLogDetailEntity>();

		if (startRevisionNum == null || endRevisionNum == null) {
			logger.info("Start Revision Number Null or End Revision Number Null !");
			return rtnList;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM SQL_LOG_DETAIL WHERE REVISION_NUMBER >= ? AND REVISION_NUMBER <= ?");

		try {
			List<Map<String, Object>> queryResultList = jdbcTemplate.queryForList(sql.toString(), startRevisionNum, endRevisionNum);

			for (int i = 0; i < queryResultList.size(); i++) {
				SqlLogDetailEntity entity = new SqlLogDetailEntity();
				Map<String, Object> fieldMap = queryResultList.get(i);

				for (String key : fieldMap.keySet()) {
					if (key.equals("AUTHOR")) {
						entity.setAuthor((String) fieldMap.get(key));
					} else if (key.equals("DATE")) {
						entity.setDate((String) fieldMap.get(key));
					} else if (key.equals("LOG")) {
						entity.setLog((String) fieldMap.get(key));
					} else if (key.equals("CHANGED")) {
						entity.setChanged((String) fieldMap.get(key));
					} else if (key.equals("REVISION_NUMBER")) {
						entity.setRevisionNumber((Integer) fieldMap.get(key));
					} else if (key.equals("MODULE_NAME")) {
						entity.setModuleName((String) fieldMap.get(key));
					} else if (key.equals("DB_USER")) {
						entity.setDbUser((String) fieldMap.get(key));
					} else if (key.equals("TABLE_NAME")) {
						entity.setTableName((String) fieldMap.get(key));
					} else if (key.equals("TAG_NAME")) {
						entity.setTagName((String) fieldMap.get(key));
					}
				}

				rtnList.add(entity);
			}

			logger.info("Query Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return rtnList;
	}

	@Override
	public List<SqlLogDetailEntity> queryTagNameIsNull(Integer startRevisionNum, Integer endRevisionNum) {
		List<SqlLogDetailEntity> rtnList = new ArrayList<SqlLogDetailEntity>();

		if (startRevisionNum == null || endRevisionNum == null) {
			logger.info("Start Revision Number Null or End Revision Number Null !");
			return rtnList;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM SQL_LOG_DETAIL WHERE REVISION_NUMBER >= ? AND REVISION_NUMBER <= ? AND TAG_NAME ISNULL");

		try {
			List<Map<String, Object>> queryResultList = jdbcTemplate.queryForList(sql.toString(), startRevisionNum, endRevisionNum);

			for (int i = 0; i < queryResultList.size(); i++) {
				SqlLogDetailEntity entity = new SqlLogDetailEntity();
				Map<String, Object> fieldMap = queryResultList.get(i);

				for (String key : fieldMap.keySet()) {
					if (key.equals("AUTHOR")) {
						entity.setAuthor((String) fieldMap.get(key));
					} else if (key.equals("DATE")) {
						entity.setDate((String) fieldMap.get(key));
					} else if (key.equals("LOG")) {
						entity.setLog((String) fieldMap.get(key));
					} else if (key.equals("CHANGED")) {
						entity.setChanged((String) fieldMap.get(key));
					} else if (key.equals("REVISION_NUMBER")) {
						entity.setRevisionNumber((Integer) fieldMap.get(key));
					} else if (key.equals("MODULE_NAME")) {
						entity.setModuleName((String) fieldMap.get(key));
					} else if (key.equals("DB_USER")) {
						entity.setDbUser((String) fieldMap.get(key));
					} else if (key.equals("TABLE_NAME")) {
						entity.setTableName((String) fieldMap.get(key));
					} else if (key.equals("TAG_NAME")) {
						entity.setTagName((String) fieldMap.get(key));
					}
				}

				rtnList.add(entity);
			}

			logger.info("Query Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return rtnList;
	}

	@Override
	public Integer queryMinNoTagRev() {
		Integer minNoTagRev = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MIN(REVISION_NUMBER) FROM SQL_LOG_DETAIL WHERE TAG_NAME ISNULL");

		try {
			minNoTagRev = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

			logger.info("Query Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return minNoTagRev;
	}

	@Override
	public Integer queryYoungestRev() {
		Integer youngestRev = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(REVISION_NUMBER) FROM SQL_LOG_DETAIL");

		try {
			youngestRev = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

			logger.info("Query Success !");
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return youngestRev;
	}

}
