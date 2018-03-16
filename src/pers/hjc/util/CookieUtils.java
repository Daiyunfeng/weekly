package pers.hjc.util;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.Base64;

public class CookieUtils
{

	// cookie最大生存时间
	private static final int cookieMageAge = 7 * 24 * 60 * 60;// 7天

	// cookieName
	public static final String cookieDomainnName = "ddd.weekly";

	// 加密cookie时网站自定码
	public static final String webKey = "ddddingdandan";

	private static final String path = "/weekly";

	// 保持cookie
	public static void saveCookie(String userId, HttpServletResponse response) throws NoSuchAlgorithmException
	{
		// cookie有效时间
		long validTime = System.currentTimeMillis() + cookieMageAge * 1000;

		// md5加密id+validTiem+webkey
		String md5cookieStr = MD5Util.MD5(userId + validTime + webKey);

		// 合成保存的完整cookie串:id+validtime+mdcookieStr
		String cookieValue = userId + ":" + validTime + ":" + md5cookieStr;

		// base64合成保存cookie保持在本地
		String saveCookie = new String(Base64.byteArrayToBase64(cookieValue.getBytes()));

		Cookie userCookie = new Cookie(cookieDomainnName, saveCookie);

		// cookie的生存时间
		userCookie.setMaxAge(cookieMageAge);
		userCookie.setPath(path);

		// 写到客户端
		response.addCookie(userCookie);
	}

	// s删除cookie
	public static void cleanCookie(HttpServletResponse response)
	{
		Cookie cookie = new Cookie(cookieDomainnName, null);
		cookie.setMaxAge(0);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
}