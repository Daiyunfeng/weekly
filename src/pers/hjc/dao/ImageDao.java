package pers.hjc.dao;

import java.util.List;

import pers.hjc.model.IndexImage;

public interface ImageDao extends BaseDao<IndexImage>
{
	List<IndexImage> findAllImage() throws Exception;
}
