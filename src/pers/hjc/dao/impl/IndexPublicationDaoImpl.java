package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexPublicationDao;
import pers.hjc.model.IndexPublication;

@Repository
public class IndexPublicationDaoImpl implements IndexPublicationDao
{
	@Autowired
	BaseDaoImpl<IndexPublication> baseDao;

	@Override
	public List<IndexPublication> findAllPublication() throws Exception
	{
		return baseDao.find("FROM IndexPublication WHERE isUse = 1 ORDER BY id desc",null);
	}

	@Override
	public void deletePublication(IndexPublication publication) throws Exception
	{
		baseDao.delete(publication);
	}


	@Override
	public IndexPublication findPublication(Long publicationID) throws Exception
	{
		return baseDao.get(IndexPublication.class, publicationID);
	}

	@Override
	public Serializable addPublication(IndexPublication publication) throws Exception
	{
		Serializable publicationID = baseDao.save(publication);
		return publicationID;
	}

	@Override
	public void updatePublication(IndexPublication publication) throws Exception
	{
		baseDao.update(publication);
	}

}
