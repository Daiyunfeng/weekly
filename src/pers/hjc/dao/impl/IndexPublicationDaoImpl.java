package pers.hjc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexPublicationDao;
import pers.hjc.model.IndexPublication;

@Repository
public class IndexPublicationDaoImpl extends BaseDaoImpl<IndexPublication> implements IndexPublicationDao
{

	@Override
	public List<IndexPublication> findAllPublication() throws Exception
	{
		return find("FROM IndexPublication WHERE isUse = 1 ORDER BY id desc",null);
	}
}
