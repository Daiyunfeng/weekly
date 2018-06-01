package pers.hjc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexResearchDao;
import pers.hjc.model.IndexResearch;

@Repository
public class IndexResearchDaoImpl extends BaseDaoImpl<IndexResearch> implements IndexResearchDao
{
	@Override
	public List<IndexResearch> findAllResearch() throws Exception
	{
		return find("FROM IndexResearch WHERE isUse = 1 ORDER BY id desc", null);
	}

}
