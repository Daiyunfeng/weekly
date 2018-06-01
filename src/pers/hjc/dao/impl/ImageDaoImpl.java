package pers.hjc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.hjc.model.IndexImage;

@Repository
public class ImageDaoImpl extends BaseDaoImpl<IndexImage> implements pers.hjc.dao.ImageDao
{
	@SuppressWarnings("unchecked")
	@Override
	public List<IndexImage> findAllImage() throws Exception
	{
		/*Object[] params = new Object[1];
		params[0] = "orders";*/
		//hql order by 不能用占位符??
		return find("FROM IndexImage ORDER BY orders",null);
	}
}
