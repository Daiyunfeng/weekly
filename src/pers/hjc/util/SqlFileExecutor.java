package pers.hjc.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 读取 SQL 脚本并执行
 * 
 * @author Unmi
 */
public class SqlFileExecutor
{

	/**
	 * 读取 SQL 文件，获取 SQL 语句
	 * 
	 * @param sqlFile
	 *            SQL 脚本文件
	 * @return List<sql> 返回所有 SQL 语句的 List
	 * @throws Exception
	 */
	private List<String> loadSql(String sqlFile) throws Exception
	{
		List<String> sqlList = new ArrayList<String>();
		List<String> txtList = new ArrayList<String>();

		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFile), "UTF8"));
			String line = null;
			while ((line = br.readLine()) != null)
			{
				txtList.add(line);
			}
			for (int i = 0; i < txtList.size() - 1; i++)
			{
				if (txtList.get(i).startsWith("INSERT"))
				{
					if (txtList.get(i + 1).startsWith("VALUES"))
					{
						sqlList.add(txtList.get(i) + " " + txtList.get(i + 1));
					}
					else if (txtList.get(i + 1).startsWith("INSERT"))
					{
						sqlList.add(txtList.get(i));
					}
				}
			}
			if (txtList.get(txtList.size() - 1).startsWith("INSERT"))
			{
				sqlList.add(txtList.get(txtList.size() - 1));
			}
			br.close();
			return sqlList;
		}
		catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 传入连接来执行 SQL 脚本文件，这样可与其外的数据库操作同处一个事物中
	 * 
	 * @param conn
	 *            传入数据库连接
	 * @param sqlFile
	 *            SQL 脚本文件
	 * @throws Exception
	 */
	public void execute(Connection conn, String sqlFile) throws Exception
	{
		Statement stmt = null;
		List<String> sqlList = loadSql(sqlFile);
		stmt = conn.createStatement();
		for (String sql : sqlList)
		{
			stmt.addBatch(sql);
		}
		int[] rows = stmt.executeBatch();
		System.out.println("Row count:" + Arrays.toString(rows));
	}

	/**
	 * 自建连接，独立事物中执行 SQL 文件
	 * 
	 * @param sqlFile
	 *            SQL 脚本文件
	 * @throws Exception
	 */
	public void execute(String sqlFile) throws Exception
	{
		String driver = PropUtil.getInstance().getDriverClass();
		String url = PropUtil.getInstance().getUrl();
		String username = PropUtil.getInstance().getUsername();
		String password = PropUtil.getInstance().getPassword();
		Class.forName(driver);

		Connection conn = null;
		conn = DriverManager.getConnection(url, username, password);
		Statement stmt = null;
		List<String> sqlList = loadSql(sqlFile);
		for (String sql : sqlList)
		{
			System.out.println(sql);
		}
		try
		{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (String sql : sqlList)
			{
				stmt.addBatch(sql.replace(";", ""));
			}
			int[] rows = stmt.executeBatch();
			System.out.println("Row count:" + Arrays.toString(rows));
			conn.commit();
		}
		catch (Exception ex)
		{
			conn.rollback();
			throw ex;
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}

		}
	}

	public void execute(List<String> sqlList) throws Exception
	{
		String driver = PropUtil.getInstance().getDriverClass();
		String url = PropUtil.getInstance().getUrl();
		String username = PropUtil.getInstance().getUsername();
		String password = PropUtil.getInstance().getPassword();

		Class.forName(driver);
		Connection conn = null;
		conn = DriverManager.getConnection(url, username, password);
		Statement stmt = null;
		try
		{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++)
			{
				stmt.addBatch(sqlList.get(i));
				if ((i + 1) % 1000 == 0)
				{
					stmt.executeBatch();
					conn.commit();
				}
			}
			stmt.executeBatch();
			conn.commit();
		}
		catch (Exception ex)
		{
			conn.rollback();
			throw ex;
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		List<String> sqlList = new SqlFileExecutor().loadSql("d:\\init.sql");
		System.out.println("size:" + sqlList.size());
		for (String sql : sqlList)
		{
			System.out.println(sql);
		}
	}
}