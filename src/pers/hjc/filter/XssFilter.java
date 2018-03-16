package pers.hjc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器 使用XssHttpServletRequestWrapper 替换特殊字符
 * 不使用
 * @author Administrator
 *
 */
public class XssFilter implements Filter
{
	FilterConfig filterConfig = null;

	@Override
	public void destroy()
	{
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		this.filterConfig = filterConfig;
	}

}