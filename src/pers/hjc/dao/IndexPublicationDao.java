package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexPublication;

public interface IndexPublicationDao
{
	List<IndexPublication> findAllPublication() throws Exception;

	void deletePublication(IndexPublication publication) throws Exception;

	Serializable addPublication(IndexPublication publication) throws Exception;

	void updatePublication(IndexPublication publication) throws Exception;

	IndexPublication findPublication(Long publicationID) throws Exception;
}
