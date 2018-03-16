package pers.hjc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil
{
	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	private static String publicPath;
	private static String systemVersion;
	private static PropUtil instance = null;

	private PropUtil()
	{
	}

	public static PropUtil getInstance() throws IOException
	{
		if (instance == null)
		{
			init();
			instance = new PropUtil();
		}
		return instance;
	}

	static void init() throws IOException
	{
		String propfilename = PathUtil.getInstance().getWebRootPath() + "WEB-INF/config/jdbc.properties";
		InputStream in = new FileInputStream(propfilename);
		Properties p = new Properties();
		p.load(in);
		in.close();
		if (p.containsKey("jdbc.driverClass"))
		{
			driverClass = p.getProperty("jdbc.driverClass");
		}
		if (p.containsKey("jdbc.url"))
		{
			url = p.getProperty("jdbc.url");
		}
		if (p.containsKey("jdbc.username"))
		{
			username = p.getProperty("jdbc.username");
		}
		if (p.containsKey("jdbc.password"))
		{
			password = p.getProperty("jdbc.password");
		}
		if (p.containsKey("public.path"))
		{
			publicPath = p.getProperty("public.path");
		}
		if (p.containsKey("system.version"))
		{
			systemVersion = p.getProperty("system.version");
		}
	}

	public static String getByKey(String key)
	{
		try
		{
			String propfilename = PathUtil.getInstance().getWebRootPath() + "WEB-INF/config/jdbc.properties";
			InputStream in = new FileInputStream(propfilename);
			Properties p = new Properties();
			p.load(in);
			in.close();
			if (p.containsKey(key))
			{
				return p.getProperty(key);
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static String getVersion()
	{
		try
		{
			String version = PropUtil.getByKey("jdbc.username").substring(0, 2).toUpperCase() + "t"
					+ PropUtil.getByKey("jdbc.username").substring(2).toUpperCase();
			return version;
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public String getPublicPath()
	{
		return publicPath;
	}

	public String getDriverClass()
	{
		return driverClass;
	}

	public String getUrl()
	{
		return url;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public static String getSystemVersion()
	{
		return systemVersion;
	}
}
