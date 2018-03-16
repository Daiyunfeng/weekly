package pers.hjc.dao;

import pers.hjc.model.Role;

public interface RoleDao
{
	Role findRole(Long id) throws Exception;
}
