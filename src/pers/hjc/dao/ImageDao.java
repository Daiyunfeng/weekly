package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexImage;

public interface ImageDao
{
	List<IndexImage> findAllImage() throws Exception;

	void deleteImage(IndexImage image) throws Exception;

	Serializable addImage(IndexImage image) throws Exception;

	void updateImage(IndexImage image) throws Exception;

	IndexImage findImage(Long imageID) throws Exception;
}
