package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexResearchDao;
import pers.hjc.model.IndexResearch;

@Repository
public class IndexResearchDaoImpl implements IndexResearchDao
{
	@Autowired
	BaseDaoImpl<IndexResearch> baseDao;

	@Override
	public List<IndexResearch> findAllResearch() throws Exception
	{
		return baseDao.find("FROM IndexResearch WHERE isUse = 1 ORDER BY id desc",null);
	}

	@Override
	public void deleteResearch(IndexResearch research) throws Exception
	{
		baseDao.delete(research);
	}


	@Override
	public IndexResearch findResearch(Long researchID) throws Exception
	{
		return baseDao.get(IndexResearch.class, researchID);
	}

	@Override
	public Serializable addResearch(IndexResearch research) throws Exception
	{
		Serializable researchID = baseDao.save(research);
		return researchID;
	}

	@Override
	public void updateResearch(IndexResearch research) throws Exception
	{
		baseDao.update(research);
	}

}
