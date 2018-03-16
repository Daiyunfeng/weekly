package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.UserDao;
import pers.hjc.model.User;

@Repository
public class UserDaoImpl implements UserDao
{
	@Autowired
	BaseDaoImpl<User> baseDao;

	@Override
	public User findByUserID(Long userID) throws Exception
	{
		User user = null;
		user = baseDao.get(User.class, userID);
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUser(Boolean flag) throws Exception
	{
		Object[] param = new Object[1];
		param[0] = "realname";
		if (flag)
		{
			return baseDao.find("FROM User ORDER BY ?", param);
		}
		else
		{
			return baseDao.find("FROM User WHERE isUse = 1 ORDER BY ?", param);
		}
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<User> findAllUserInIndex(Boolean flag) throws
	 * Exception { Object[] param = new Object[1]; param[0] = 1; if (flag) {
	 * return baseDao.find("FROM User WHERE inIndex = ?", param); } else {
	 * return baseDao.find("FROM User WHERE isUse = 1 AND inIndex = ?", param);
	 * } }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByPage(Integer page, Integer rows, Boolean flag) throws Exception
	{
		Object[] param = new Object[1];
		param[0] = "realname";
		if (flag)
		{
			return baseDao.find("FROM User ORDER BY ?", page, rows, param);
		}
		else
		{
			return baseDao.find("FROM User WHERE isUse = 1 ORDER BY ?", page, rows, param);
		}
	}

	@Override
	public Boolean addUser(User user) throws Exception
	{
		Serializable userID = baseDao.save(user);
		if (userID == null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateUser(User user) throws Exception
	{
		baseDao.update(user);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByRealname(String realname, Boolean flag) throws Exception
	{
		if (flag == true)
		{
			String hql = "FROM User WHERE realname = ? AND isUse = ? ORDER BY ?";
			Object[] param = new Object[3];
			param[0] = realname;
			param[1] = 1;
			param[2] = "realname";
			List<User> users = baseDao.find(hql, param);
			return users;
		}
		else
		{
			String hql = "FROM User WHERE realname = ? ORDER BY ?";
			Object[] param = new Object[2];
			param[0] = realname;
			param[1] = "realname";
			List<User> users = baseDao.find(hql, param);
			return users;
		}
	}

	@Override
	public List<User> searchUser(Map<String, Object> params, Boolean flag) throws Exception
	{
		Object[] param;
		if (params.size() == 0)
		{
			if (flag)
			{
				return baseDao.find("FROM User", null);
			}
			else
			{
				return baseDao.find("FROM User WHERE isUse = 1", null);
			}
		}
		StringBuilder hql = new StringBuilder("FROM User WHERE ");
		param = new Object[params.size()];

		int count = 0;
		if (params.get("ID") != null)
		{
			hql.append("ID = ? ");
			param[count++] = params.get("ID");
		}
		if (params.get("realname") != null)
		{
			if (count != 0)
			{
				hql.append("AND realname = ? ");
			}
			else
			{
				hql.append("realname = ? ");
			}
			param[count++] = params.get("realname");
		}
		if (flag)
		{
			return baseDao.find(hql.toString(), param);
		}
		else
		{
			if (count != 0)
			{
				hql.append("AND isUse = 1");
			}
			else
			{
				hql.append("isUse = 1");
			}
			return baseDao.find(hql.toString(), param);
		}
	}
}
