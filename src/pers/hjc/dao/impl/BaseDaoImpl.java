package pers.hjc.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import pers.hjc.dao.BaseDao;

/**
 * 基础数据库操作类
 * 
 * @author CXD
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDaoImpl<T> implements BaseDao<T>
{
	private Class<T> clazz;  
	
	@Autowired
	SessionFactory sessionFactory;

	public BaseDaoImpl() 
	{  
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();  
        clazz = (Class<T>) type.getActualTypeArguments()[0];  
    }  
	
	@Override
	public Serializable save(T o) throws Exception
	{
		return sessionFactory.getCurrentSession().save(o);
	}

	@Override
	public void delete(T o) throws Exception
	{
		sessionFactory.getCurrentSession().delete(o);
	}

	@Override
	public void update(T o) throws Exception
	{
		sessionFactory.getCurrentSession().update(o);

	}

	@Override
	public void merge(T o) throws Exception
	{
		sessionFactory.getCurrentSession().merge(o);

	}

	@Override
	public void saveOrUpdate(T o) throws Exception
	{
		sessionFactory.getCurrentSession().saveOrUpdate(o);

	}

	private Query setParam(Query q, Object[] param)
	{
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				if (param[i] instanceof List)
				{
					q.setParameterList("param" + i, (Collection<?>) param[i]);
				}
				else
				{
					q.setParameter("param" + i, param[i]);
				}
			}
		}
		return q;
	}

	private String toNamedParam(String sql, Object[] param)
	{
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				int index = sql.indexOf("?");
				String str1 = sql.substring(index + 1);
				String str2 = "";
				if (index > -1)
				{
					str2 = sql.substring(0, index + 1).replace("?", ":param" + i);
				}
				sql = str2 + str1;
			}
		}
		return sql;
	}

	@Override
	public List find(String hql, Object[] param) throws Exception
	{
		hql = toNamedParam(hql, param);
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return this.setParam(q, param).setCacheable(true).list();
	}

	@Override
	public List find(String hql, Integer page, Integer rows, Object[] param) throws Exception
	{
		if (page == null || page < 1)
		{
			page = 1;
		}
		if (rows == null || rows < 1)
		{
			rows = 15;
		}
		hql = toNamedParam(hql, param);
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return this.setParam(q, param).setFirstResult((page - 1) * rows).setMaxResults(rows).setCacheable(true).list();
	}

	@Override
	public T getById(Serializable id) throws Exception
	{
		return (T) sessionFactory.getCurrentSession().get(this.clazz, id);
	}

	@Override
	public Long count(String hql, Object[] param) throws Exception
	{
		hql = toNamedParam(hql, param);
		Query q = sessionFactory.getCurrentSession().createQuery("select count(1) " + hql);
		return (Long) this.setParam(q, param).uniqueResult();
	}

	@Override
	public Integer executeHql(String hql, Object[] param) throws Exception
	{
		hql = toNamedParam(hql, param);
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return this.setParam(q, param).executeUpdate();
	}

	@Override
	public Object executeHqlRetUnique(String hql, Object[] param) throws Exception
	{
		hql = toNamedParam(hql, param);
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return this.setParam(q, param).uniqueResult();
	}

	@Override
	public List findSQL(String sql, Object[] param) throws Exception
	{
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql);
		return this.setParam(q, param).list();
	}

	@Override
	public List findSQLToEntity(String sql, Class clazz, Object[] param) throws Exception
	{
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql, clazz);
		return this.setParam(q, param).list();
	}

	@Override
	public List findSQL(String sql, Integer page, Integer rows, Object[] param) throws Exception
	{
		if (page == null || page < 1)
		{
			page = 1;
		}
		if (rows == null || rows < 1)
		{
			rows = 15;
		}
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql);
		return this.setParam(q, param).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Integer executeSQL(String sql, Object[] param) throws Exception
	{
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql);
		return this.setParam(q, param).executeUpdate();
	}

	@Override
	public Object executeSQLRetUnique(String sql, Object[] param) throws Exception
	{
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql);
		return this.setParam(q, param).uniqueResult();
	}

	@Override
	public List findSQLToEntity(String sql, Class clazz, Integer page, Integer rows, Object[] param) throws Exception
	{
		if (page == null || page < 1)
		{
			page = 1;
		}
		if (rows == null || rows < 1)
		{
			rows = 15;
		}
		sql = toNamedParam(sql, param);
		NativeQuery q = sessionFactory.getCurrentSession().createNativeQuery(sql, clazz);
		return this.setParam(q, param).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public void clearSession() throws Exception
	{
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void executeBatch(final List<String> sqls) throws Exception
	{
		sessionFactory.getCurrentSession().doWork(new Work()
		{
			@Override
			public void execute(Connection conn) throws SQLException
			{
				Statement stmt = conn.createStatement();
				for (int i = 0; i < sqls.size(); i++)
				{
					stmt.addBatch(sqls.get(i));
					if ((i + 1) % 5000 == 0)
					{
						stmt.executeBatch();
						conn.commit();
					}
				}
				stmt.executeBatch();
				conn.commit();
			}
		});
	}

}
