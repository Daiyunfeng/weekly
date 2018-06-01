package pers.hjc.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基础数据库操作类
 * 
 * @author CXD
 * 
 */
@SuppressWarnings("rawtypes")
public interface BaseDao<T>
{

	/**
	 * 将瞬态（自由态）的实体加入session缓存，变成持久化对象
	 * 
	 * @param o
	 * @return 保存以后的ID
	 */
	public Serializable save(T o) throws Exception;

	/**
	 * 将持久化的对象变成游离态（托管状态）
	 * 
	 * @param o
	 */
	public void delete(T o) throws Exception;

	/**
	 * 更新一个对象，以下下情况使用 1、数据库记录已存在，更改的属性有变化。
	 * 
	 * 
	 * @param o
	 */
	public void update(T o) throws Exception;

	/**
	 * 将我们提供的对象转变为托管状态的对象，对象不会纳入session的管理，再次查询该对象还是会从数据库中取 更新一个对象，以下下情况使用
	 * 1、数据库记录已存在，更改的属性无变化。
	 * 
	 * @param o
	 */
	public void merge(T o) throws Exception;

	/**
	 * 将我们提供的对象变成一个持久化对象，对象会纳入session的管理，对象的状态会跟数据库同步， 再次查询该对象会直接从session中取
	 * 
	 * @param o
	 */
	public void saveOrUpdate(T o) throws Exception;

	/**
	 * 查询
	 * 
	 * @param hql
	 * @param param
	 * @return List<T>或者List<Object[]>
	 */
	public List find(String hql, Object[] param) throws Exception;

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 *            参数数组
	 * @param page
	 *            查询第几页
	 * @param rows
	 *            每页显示几条记录
	 * @return List<T>或者List<Object[]>
	 */
	public List find(String hql, Integer page, Integer rows, Object[] param) throws Exception;

	/**
	 * 获得一个单个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            对象ID
	 * @return Object
	 */
	public T getById(Serializable id) throws Exception;
	
	/**
	 * select count(1) from 类
	 * 
	 * @param hql
	 * @param param
	 *            参数数组
	 * @return Long 记录数
	 */
	public Long count(String hql, Object[] param) throws Exception;

	/**
	 * 执行HQL语句insert，update，delete等
	 * 
	 * @param hql
	 * @param param
	 *            参数数组
	 * @return Integer 响应数目
	 */
	public Integer executeHql(String hql, Object[] param) throws Exception;

	/**
	 * 执行HQL语句,select
	 * 
	 * @param hql
	 * @param param
	 *            参数数组
	 * @return Object 返回唯一值
	 */
	public Object executeHqlRetUnique(String hql, Object[] param) throws Exception;

	/**
	 * 执行SQL语句,select
	 * 
	 * @param sql
	 * @param param
	 *            参数数组
	 * @return List<Object[]>
	 */
	public List findSQL(String sql, Object[] param) throws Exception;

	/**
	 * 
	 * @param sql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List findSQL(String sql, Integer page, Integer rows, Object[] param) throws Exception;

	/**
	 * 执行SQL语句,select,并把结果集封装到自定义的类
	 * 
	 * @param sql
	 * @param clazz
	 * @param param
	 *            Object数组
	 * @return
	 */
	public List findSQLToEntity(String sql, Class clazz, Object[] param) throws Exception;

	/**
	 * 执行SQL语句,select,并把结果集封装到自定义的类
	 * 
	 * @param sql
	 * @param clazz
	 *            自定义类
	 * @param page
	 *            页码
	 * @param rows
	 *            每页数量
	 * @param param
	 *            Object数组
	 * @return
	 */
	public List findSQLToEntity(String sql, Class clazz, Integer page, Integer rows, Object[] param) throws Exception;

	/**
	 * 执行SQL语句,insert，update，delete等
	 * 
	 * @param sql
	 * @param param
	 *            参数数组
	 * @return Integer 响应数目
	 */
	public Integer executeSQL(String sql, Object[] param) throws Exception;

	/**
	 * 执行SQL语句,select
	 * 
	 * @param sql
	 * @param param
	 *            参数数组
	 * @return Object 返回唯一值
	 */
	public Object executeSQLRetUnique(String sql, Object[] param) throws Exception;

	/**
	 * 清除缓存
	 */
	public void clearSession() throws Exception;

	/**
	 * 执行批处理语句
	 * 
	 * @param sqls
	 *            参数数组
	 * @return Integer 响应数目
	 */
	public void executeBatch(final List<String> sqls) throws Exception;

}
