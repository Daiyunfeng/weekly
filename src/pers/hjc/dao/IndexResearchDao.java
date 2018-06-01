package pers.hjc.dao;

import java.util.List;

import pers.hjc.model.IndexResearch;

public interface IndexResearchDao extends BaseDao<IndexResearch>
{
	List<IndexResearch> findAllResearch() throws Exception;
}
