package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.BaseDao;
import pers.hjc.dao.IndexUserDao;
import pers.hjc.model.IndexUser;

@Repository
public class IndexUserDaoImpl extends BaseDaoImpl<IndexUser> implements IndexUserDao
{

	@Override
	public List<IndexUser> findAllUser() throws Exception
	{
		return find("FROM IndexUser ORDER BY orders",null);
	}

}
