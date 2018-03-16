package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.model.IndexImage;

@Repository
public class ImageDaoImpl implements pers.hjc.dao.ImageDao
{
	@Autowired
	BaseDaoImpl<IndexImage> baseDao;

	@Override
	public List<IndexImage> findAllImage() throws Exception
	{
		/*Object[] params = new Object[1];
		params[0] = "orders";*/
		//hql order by 不能用占位符??
		return baseDao.find("FROM IndexImage ORDER BY orders",null);
	}

	@Override
	public void deleteImage(IndexImage image) throws Exception
	{
		baseDao.delete(image);
	}

	@Override
	public Serializable addImage(IndexImage image) throws Exception
	{
		Serializable imageID = baseDao.save(image);
		return imageID;
	}

	@Override
	public void updateImage(IndexImage image) throws Exception
	{
		baseDao.update(image);
	}

	public IndexImage findImage(Long imageID) throws Exception
	{
		return baseDao.get(IndexImage.class, imageID);
	}

}
