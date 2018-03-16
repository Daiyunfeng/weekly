package pers.hjc.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pers.hjc.GlobelVariable;
import pers.hjc.annotation.FormToken;
import pers.hjc.annotation.SameUrlData;
import pers.hjc.model.User;
import pers.hjc.service.UserService;
import pers.hjc.util.CookieUtils;
import pers.hjc.util.MD5Util;
import pers.hjc.util.RequestUtil;
import pers.hjc.util.TokenUtil;

/**
 * 拦截全部
 * 
 * @author Administrator
 */
public class AuthInterceptor implements HandlerInterceptor
{
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	private UserService userService;

	public final static String TOKEN_NAME = "token";

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception
	{
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception
	{
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception
	{
		String requestPath = RequestUtil.getRequestPath(request);
		request.setAttribute("ctx", request.getContextPath());
		if (!requestPath.startsWith("/res"))
		{
			Boolean flag = token(request, response, object);
			if (flag == false)
			{
				return flag;
			}
			flag = repeat(request, response, object);
			if (flag == false)
			{
				return flag;
			}
		}

		List<String> notLogin = new ArrayList<String>();
		notLogin.add("/login");
		notLogin.add("/logout");
		notLogin.add("/register");
		notLogin.add("/res");
		notLogin.add("/pcrimg");
		notLogin.add("/erroes");
		notLogin.add("/tourist");
		notLogin.add("/include");
		notLogin.add("/WEB-INF/jsp");
		notLogin.add("/findIndexUserAjax");

		// System.out.println(requestPath);
		for (String str : notLogin)
		{
			if (requestPath.startsWith(str))
			{
				return true;
			}
		}
		// System.out.println("需要登陆"+requestPath);
		HttpSession session = request.getSession();
		if (session.getAttribute(GlobelVariable.SESSION_USER_ID) != null)
		{
			User user = userService.findUser((Long) session.getAttribute(GlobelVariable.SESSION_USER_ID));
			if (user == null)
			{
				session.setAttribute(GlobelVariable.SESSION_USER_ID, null);
				forward("异常操作", "/tourist", request, response);
				return false;
			}
			if (user.getIsUse() == 0)
			{
				session.setAttribute(GlobelVariable.SESSION_USER_ID, null);
				forward("您已被禁用", "/tourist", request, response);
				return false;
			}
			if (requestPath.startsWith("/admin"))
			{
				if (user.getRole().getID() == GlobelVariable.ADMIN_ROLE)
				{
					return true;
				}
				forward("您没有权限", "/errors/error", request, response);
				return false;
			}
			return true;
		}
		else
		{
			String cookieValue = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null)
			{
				for (Cookie cookie : cookies)
				{
					if (CookieUtils.cookieDomainnName.equals(cookie.getName()))
					{
						// 找到用户cookie
						cookieValue = cookie.getValue();
						break;
					}
				}
				// 如果cookie值为空，登陆页面
				if (cookieValue == null)
				{
					forward("", "/tourist", request, response);
					return false;
				}
				// cookie值不为空，对cookie进行base64解码
				String cookieValueNoBase64 = new String(Base64.base64ToByteArray(cookieValue));
				// 对cookie进行分离
				String cookieSpilt[] = cookieValueNoBase64.split(":");
				// 非法访问网站
				if (cookieSpilt.length != 3)
				{
					forward("非法访问本网站,请重新登陆", "/tourist", request, response);
					return false;
				}
				// 验证cookie有效期
				Long viladTime = new Long(cookieSpilt[1]);
				if (System.currentTimeMillis() > viladTime)
				{
					// System.out.println(System.currentTimeMillis());
					// System.out.println(viladTime);
					// 超过有效期，删除cookie，然后重新登陆
					CookieUtils.cleanCookie(response);
					forward("登陆超时,请重新登陆", "/tourist", request, response);
					return false;
				}
				// 验证数据库中有这个用户，并合成cookie的加密串与客户端的cookie加密串对比
				String userID = cookieSpilt[0];
				User user = null;
				try
				{
					user = userService.findUser(Long.parseLong(userID));

				}
				catch (Exception e)
				{
					logger.error(e.getMessage(), e);
				}
				if (user != null)
				{
					// 查找到user,合成cookie型加密串
					String userCookieStr = MD5Util.MD5(user.getID().toString() + viladTime + CookieUtils.webKey);
					if (userCookieStr.equals(cookieSpilt[2]))
					{
						if (user.getIsUse() == 0)
						{
							forward("您已被禁用", "/tourist", request, response);
							return false;
						}
						session.setAttribute(GlobelVariable.SESSION_USER_ID, user.getID());
						CookieUtils.saveCookie(user.getID().toString(), response);
						return true;
					}
					forward("状态异常，请重新登陆", "/tourist", request, response);
					return false;
				}
			}
			// cookie不存在,跳转到游客页面
			forward("请先登录", "/tourist", request, response);
			return false;
		}
	}

	private void forward(String msg, Object flag, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if (msg != null && msg.equals("json"))
		{
			PrintWriter writer = response.getWriter();
			writer.print(flag);
			writer.flush();
			writer.close();
		}
		else
		{
			request.setAttribute("msg", msg);
			request.getRequestDispatcher(flag.toString()).forward(request, response);
		}

	}

	private boolean token(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException, ServletException
	{
		if (handler instanceof HandlerMethod)
		{
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			FormToken formToken = method.getAnnotation(FormToken.class);
			if (null != formToken)
			{
				if ((formToken.produce() && formToken.remove()) || (!formToken.produce() && !formToken.remove()))
				{
					throw new RuntimeException("请不要在同一个方法上同时注解：@FormToken(remove = true/false, produce = true/false)");
				}
				else if (formToken.produce())
				{
					request.getSession().setAttribute(TOKEN_NAME, TokenUtil.makeToken());
				}
				else if (formToken.remove())
				{
					String serverToken = (String) request.getSession().getAttribute(TOKEN_NAME);
					String clientToken = request.getParameter(TOKEN_NAME);
					// request.getSession().removeAttribute(TOKEN_NAME); //
					// remove 只验证
					// token
					System.out.println(serverToken + "    " + clientToken);
					if (serverToken == null || clientToken == null || !serverToken.equals(clientToken))
					{
						if (null != method.getAnnotation(ResponseBody.class))
						{ // JSON
							response.setContentType(MediaType.APPLICATION_JSON_VALUE);
							PrintWriter out = response.getWriter();
							Map map = new HashMap();
							map.put("flag", 0);
							map.put("msg", "无效请求，请刷新页面后重试");
							ObjectMapper mapper = new ObjectMapper();
							out.print(mapper.writeValueAsString(map));
							out.flush();
							out.close();
						}
						else
						{ // 普通页面
							request.getRequestDispatcher("/errors/invalidRequest").forward(request, response);
						}
						return false;
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private Boolean repeat(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
	{
		if (handler instanceof HandlerMethod)
		{
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			SameUrlData annotation = method.getAnnotation(SameUrlData.class);
			if (annotation != null)
			{
				if (repeatDataValidator(request))// 如果重复相同数据
				{
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					PrintWriter out = response.getWriter();
					Map map = new HashMap();
					map.put("flag", 0);
					map.put("msg", "重复提交");
					ObjectMapper mapper = new ObjectMapper();
					out.print(mapper.writeValueAsString(map));
					out.flush();
					out.close();
					return false;
				}
				else
				{
					return true;
				}
			}
			return true;
		}
		return true;
	}

	private Boolean repeatDataValidator(HttpServletRequest httpServletRequest) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String params = mapper.writeValueAsString(httpServletRequest.getParameterMap());
		String url = httpServletRequest.getRequestURI();
		Map<String, String> map = new HashMap<String, String>();
		map.put(url, params);
		String nowUrlParams = map.toString();//

		Object preUrlParams = httpServletRequest.getSession().getAttribute("repeatData");
		if (preUrlParams == null)// 如果上一个数据为null,表示还没有访问页面
		{
			httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
			return false;
		}
		else// 否则，已经访问过页面
		{
			if (preUrlParams.toString().equals(nowUrlParams))// 如果上次url+数据和本次url+数据相同，则表示重复添加数据
			{
				return true;
			}
			else// 如果上次 url+数据 和本次url加数据不同，则不是重复提交
			{
				httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
				return false;
			}

		}
	}

}
