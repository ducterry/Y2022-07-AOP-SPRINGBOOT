package com.ndangducbn.aop.dao.impl;

import com.ndangducbn.aop.dao.SysLogDao;
import com.ndangducbn.aop.model.SysLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SysLogDaoImp implements SysLogDao {

	private final JdbcTemplate jdbcTemplate;

    public SysLogDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
	public void saveSysLog(SysLog syslog) {
		StringBuffer sql = new StringBuffer("insert into sys_log ");
		sql.append("(id,username,operation,time,method,params,ip,create_time) ");
		sql.append("values(:id,:username,:operation,:time,:method,");
		sql.append(":params,:ip,:createTime)");

		NamedParameterJdbcTemplate nameParams = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        nameParams.update(sql.toString(), new BeanPropertySqlParameterSource(syslog));
	}

}
