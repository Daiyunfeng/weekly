package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.IndexUserDao;
import pers.hjc.model.IndexUser;

@Repository
public class IndexUserDaoImpl implements IndexUserDao
{
	@Autowired
	BaseDaoImpl<IndexUser> baseDao;

	@Override
	public List<IndexUser> findAllUser() throws Exception
	{
		return baseDao.find("FROM IndexUser ORDER BY orders",null);
	}

	@Override
	public void deleteUser(IndexUser user) throws Exception
	{
		baseDao.delete(user);
	}

	@Override
	public Serializable addUser(IndexUser user) throws Exception
	{
		Serializable userID = baseDao.save(user);
		return userID;
	}

	@Override
	public void updateUser(IndexUser user) throws Exception
	{
		baseDao.update(user);
	}

	@Override
	public IndexUser findUser(Long userID) throws Exception
	{
		return baseDao.get(IndexUser.class, userID);
	}

}
