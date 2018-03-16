package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexResearch;

public interface IndexResearchDao
{
	List<IndexResearch> findAllResearch() throws Exception;

	void deleteResearch(IndexResearch research) throws Exception;

	Serializable addResearch(IndexResearch research) throws Exception;

	void updateResearch(IndexResearch research) throws Exception;

	IndexResearch findResearch(Long researchID) throws Exception;
}
