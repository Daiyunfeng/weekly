package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexTeaching;
import pers.hjc.model.IndexUser;

public interface IndexTeachingDao
{
	List<IndexTeaching> findAllTeaching() throws Exception;

	void deleteTeaching(IndexTeaching teaching) throws Exception;

	Serializable addTeaching(IndexTeaching teaching) throws Exception;

	void updateTeaching(IndexTeaching teaching) throws Exception;

	IndexTeaching findTeaching(Long teachingID) throws Exception;
}
