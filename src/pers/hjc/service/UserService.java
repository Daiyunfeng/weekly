package pers.hjc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.hjc.GlobelVariable;
import pers.hjc.dao.IndexUserDao;
import pers.hjc.dao.impl.RoleDaoImpl;
import pers.hjc.dao.impl.UserDaoImpl;
import pers.hjc.entity.LoginMessage;
import pers.hjc.model.IndexUser;
import pers.hjc.model.Role;
import pers.hjc.model.User;
import pers.hjc.util.MD5Util;

@Service
public class UserService
{
	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private RoleDaoImpl roleDao;

	@Autowired
	private IndexUserDao indexUserDao;

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public User login(LoginMessage loginMessage) throws Exception
	{
		Long userID = loginMessage.getUserID();
		User user = userDao.getById(userID);
		if (user != null)
		{
			if (user.getIsUse() == 1)
			{
				if (user.getRole().getDescription() != "管理员")
				{
					String password = loginMessage.getPassword();
					if (MD5Util.verify(password, user.getPassword()))
					{
						return user;
					}
					else
					{
						throw new Exception("用户名或密码错误");
					}
				}
				else
				{
					throw new Exception("用户不存在");
				}
			}
			else
			{
				throw new Exception("用户不存在");
			}
		}
		else
		{
			throw new Exception("用户不存在");
		}
	}

	@Transactional(readOnly = true)
	public User findUser(Long userID) throws Exception
	{
		User user = userDao.getById(userID);
		return user;
	}

	@Transactional(readOnly = true)
	public List<User> findUserByName(String realname, Boolean flag) throws Exception
	{
		List<User> users = userDao.findByRealname(realname, flag);
		return users;
	}

	/**
	 * 
	 * @param flag
	 *            是否找忽略isUse=0的 true不忽略
	 * @return user
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<User> findAllUser(Boolean flag) throws Exception
	{
		List<User> users = userDao.findAllUser(flag);
		return users;
	}

	/**
	 * 
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页几行
	 * @param flag
	 *            是否找忽略isUse=0的 true不忽略
	 * @return user集合
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<User> findUserByPage(Integer page, Integer rows, Boolean flag) throws Exception
	{
		List<User> users = userDao.findUserByPage(page, rows, flag);
		return users;
	}

	@Transactional(readOnly = false)
	public void registerUser(User user) throws Exception
	{
		User tmp = this.findUser(user.getID());
		if (tmp == null)
		{
			user.setPassword(MD5Util.generate(user.getPassword()));
			user.setRole(roleDao.getById(GlobelVariable.STUDENT_ROLE));
			userDao.save(user);
		}
		else
		{
			throw new Exception("此ID被使用");
		}
	}

	@Transactional(readOnly = false)
	public void updateUser(User user) throws Exception
	{
		userDao.update(user);
	}

	@Transactional(readOnly = true)
	public List<User> searchUser(Map<String, Object> params, Boolean flag) throws Exception
	{
		return userDao.searchUser(params, flag);
	}

	@Transactional(readOnly = true)
	public List<IndexUser> findAllIndexUser() throws Exception
	{
		return indexUserDao.findAllUser();
	}

	@Transactional(readOnly = true)
	public Role findRole(Long id) throws Exception
	{
		return roleDao.getById(id);
	}
	/*
	 * @Transactional(readOnly = true) public List<User>
	 * getAllUserInIndex(Boolean flag) throws Exception { return
	 * userDao.findAllUserInIndex(flag); }
	 */
}
