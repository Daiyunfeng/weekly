package pers.hjc.dao;

import java.util.List;
import java.util.Map;

import pers.hjc.model.User;

public interface UserDao
{
	User findByUserID(Long userID) throws Exception;

	List<User> findByRealname(String realname, Boolean flag) throws Exception;

	/**
	 * 
	 * @param flag
	 *            是否找忽略isUse=0的 true不忽略
	 * @return 所有user集合
	 * @throws Exception
	 */
	List<User> findAllUser(Boolean flag) throws Exception;

	// List<User> findAllUserInIndex(Boolean flag) throws Exception;

	/**
	 * 
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页几行
	 * @param flag
	 *            是否找忽略isUse=0的 true不忽略
	 * @return 对应user集合
	 * @throws Exception
	 */
	List<User> findUserByPage(Integer page, Integer rows, Boolean flag) throws Exception;

	List<User> searchUser(Map<String, Object> params, Boolean flag) throws Exception;

	Boolean addUser(User user) throws Exception;

	Boolean updateUser(User user) throws Exception;
}
