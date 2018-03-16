package pers.hjc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 访问异常
 * 
 * @author Administrator
 *
 */
public class ErrorUtil
{
	public static Object doError(Map<String, Object> map, Exception e)
	{
		String error = e.getMessage();
		if (map == null)
		{
			map = new HashMap<>();
		}
		map.put("flag", 0);
		if (error != null)
		{
			map.put("msg", error);
		}
		else
		{
			map.put("msg", "系统错误");
		}
		return map;

	}

	public static Object doError(Map<String, Object> map, BindingResult e)
	{
		List<ObjectError> errors = e.getAllErrors();
		if (map == null)
		{
			map = new HashMap<>();
		}
		map.put("flag", 0);
		StringBuilder error = new StringBuilder();
		// System.out.println(errors.size());
		for (int i = 0; i < errors.size(); i++)
		{
			error.append(errors.get(i).getDefaultMessage() + "\n");
		}
		if (!error.equals(""))
		{
			map.put("msg", error.toString());
		}
		else
		{
			map.put("msg", "系统错误");
		}
		return map;

	}
}
