package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexUser;

public interface IndexUserDao extends BaseDao<IndexUser>
{
	List<IndexUser> findAllUser() throws Exception;
}
