package pers.hjc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexTeachingDao;
import pers.hjc.model.IndexTeaching;

@Repository
public class IndexTeachingDaoImpl extends BaseDaoImpl<IndexTeaching> implements IndexTeachingDao
{
	@Override
	public List<IndexTeaching> findAllTeaching() throws Exception
	{
		return find("FROM IndexTeaching WHERE isUse = 1 ORDER BY id desc",null);
	}
}
