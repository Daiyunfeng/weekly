package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

import pers.hjc.model.IndexTeaching;
import pers.hjc.model.IndexUser;

public interface IndexTeachingDao extends BaseDao<IndexTeaching>
{
	List<IndexTeaching> findAllTeaching() throws Exception;
}
