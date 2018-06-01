package pers.hjc.interceptors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pers.hjc.GlobelVariable;
import pers.hjc.model.IndexImage;
import pers.hjc.model.IndexPublication;
import pers.hjc.model.IndexResearch;
import pers.hjc.model.IndexTeaching;
import pers.hjc.util.PathUtil;
import pers.hjc.util.PropUtil;
import pers.hjc.util.SqlFileExecutor;

public class ServletStartInit extends HttpServlet
{
	private static final Logger logger = LoggerFactory.getLogger(ServletStartInit.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException
	{
		System.out.println("我启动了，开始初始化数据库");
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		Integer flagcount = 0;
		try
		{
			Class.forName(PropUtil.getInstance().getDriverClass());
			conn = DriverManager.getConnection(PropUtil.getInstance().getUrl(), PropUtil.getInstance().getUsername(),
					PropUtil.getInstance().getPassword());
			stat = conn.createStatement();
			rs = stat.executeQuery("select count(*) from role");
			ServletContext application = this.getServletContext();
			while (rs.next())
			{
				flagcount = rs.getInt(1);
			}
			if (flagcount == 0)
			{
				SqlFileExecutor executor = new SqlFileExecutor();
				executor.execute(PathUtil.getInstance().getWebRootPath() + "WEB-INF/sql/db-init.sql");
			}
			else
			{
				System.out.println("数据库已经初始化，无需初始化");
			}

			rs = stat.executeQuery("select * from index_image order by orders");
			List<IndexImage> paths = new ArrayList<>();
			while (rs.next())
			{
				paths.add(new IndexImage(rs.getLong("id"), rs.getString("image"), rs.getString("title"),
						rs.getString("description"), rs.getInt("orders")));
			}
			application.setAttribute(GlobelVariable.APPLICATION_IMAGE_PATH, paths);
			application.setAttribute(GlobelVariable.APPLICATION_IMAGE_SIZE, paths.size());

//			rs = stat.executeQuery("select * from index_user order by orders");
//			List<Long> usersID = new ArrayList<>();
//			while (rs.next())
//			{
//				usersID.add(rs.getLong("user_id"));
//			}
//
//			application.setAttribute(GlobelVariable.APPLICATION_USER_PATH,usersID);
//			application.setAttribute(GlobelVariable.APPLICATION_USER_SIZE,usersID.size());
						
			rs = stat.executeQuery("select * from index_teaching WHERE is_use = 1 ORDER BY id desc");
			List<IndexTeaching> teachings = new ArrayList<>();
			while (rs.next())
			{
				teachings.add(new IndexTeaching(rs.getLong("id"), rs.getString("content")));
			}
			application.setAttribute(GlobelVariable.APPLICATION_TEACH_PATH, teachings);
			application.setAttribute(GlobelVariable.APPLICATION_TEACH_SIZE, teachings.size());
			
			rs = stat.executeQuery("select * from index_publication WHERE is_use = 1 ORDER BY id desc");
			List<IndexPublication> publications = new ArrayList<>();
			while (rs.next())
			{
				publications.add(new IndexPublication(rs.getLong("id"), rs.getString("content")));
			}
			application.setAttribute(GlobelVariable.APPLICATION_PUBLICATION_PATH, publications);
			application.setAttribute(GlobelVariable.APPLICATION_PUBLICATION_SIZE, publications.size());
			
			rs = stat.executeQuery("select * from index_research WHERE is_use = 1 ORDER BY id desc");
			List<IndexResearch> researchs = new ArrayList<>();
			while (rs.next())
			{
				researchs.add(new IndexResearch(rs.getLong("id"), rs.getString("content")));
			}
			application.setAttribute(GlobelVariable.APPLICATION_RESEARCH_PATH, publications);
			application.setAttribute(GlobelVariable.APPLICATION_RESEARCH_SIZE, publications.size());
			
			System.out.println("application初始化结束");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (stat != null)
					stat.close();
				if (rs != null)
					rs.close();
			}
			catch (SQLException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		System.out.println("数据库初始化完成");
	}

}
