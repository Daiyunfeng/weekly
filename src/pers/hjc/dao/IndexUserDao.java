package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexUser;

public interface IndexUserDao
{
	List<IndexUser> findAllUser() throws Exception;

	void deleteUser(IndexUser user) throws Exception;

	Serializable addUser(IndexUser user) throws Exception;

	void updateUser(IndexUser user) throws Exception;

	IndexUser findUser(Long userID) throws Exception;
}
