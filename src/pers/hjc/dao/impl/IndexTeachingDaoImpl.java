package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexTeachingDao;
import pers.hjc.model.IndexTeaching;
import pers.hjc.model.IndexUser;

@Repository
public class IndexTeachingDaoImpl implements IndexTeachingDao
{
	@Autowired
	BaseDaoImpl<IndexTeaching> baseDao;

	@Override
	public List<IndexTeaching> findAllTeaching() throws Exception
	{
		return baseDao.find("FROM IndexTeaching WHERE isUse = 1 ORDER BY id desc",null);
	}

	@Override
	public void deleteTeaching(IndexTeaching teaching) throws Exception
	{
		baseDao.delete(teaching);
	}


	@Override
	public IndexTeaching findTeaching(Long teachingID) throws Exception
	{
		return baseDao.get(IndexTeaching.class, teachingID);
	}

	@Override
	public Serializable addTeaching(IndexTeaching teaching) throws Exception
	{
		Serializable teachingID = baseDao.save(teaching);
		return teachingID;
	}

	@Override
	public void updateTeaching(IndexTeaching teaching) throws Exception
	{
		baseDao.update(teaching);
	}

}
