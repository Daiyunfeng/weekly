package pers.hjc.dao;

import java.util.List;

import pers.hjc.model.IndexPublication;

public interface IndexPublicationDao extends BaseDao<IndexPublication>
{
	List<IndexPublication> findAllPublication() throws Exception;
}
