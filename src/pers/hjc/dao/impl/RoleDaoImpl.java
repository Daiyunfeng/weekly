package pers.hjc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.RoleDao;
import pers.hjc.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao
{

	@Autowired
	BaseDaoImpl<Role> baseDao;

	@Override
	public Role findRole(Long id) throws Exception
	{
		Role role = baseDao.get(Role.class, id);
		return role;
	}

}
