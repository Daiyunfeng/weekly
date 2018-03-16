package pers.hjc;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * 自动创建的表依然是latin字符集的 修改为UTF8
 * 
 * @author Administrator
 *
 */
public class CustomMysqlDialect extends MySQL5Dialect
{	
	@Override
	public String getTableTypeString()
	{
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}
