package pers.hjc.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import pers.hjc.GlobelVariable;
import pers.hjc.annotation.FormToken;
import pers.hjc.annotation.SameUrlData;
import pers.hjc.entity.CutAndRotate;
import pers.hjc.entity.IndexUserMessage;
import pers.hjc.entity.LoginMessage;
import pers.hjc.entity.SimpleUserMessage;
import pers.hjc.entity.UserMessage;
import pers.hjc.model.IndexUser;
import pers.hjc.model.User;
import pers.hjc.service.UserService;
import pers.hjc.util.ConfigUtil;
import pers.hjc.util.CookieUtils;
import pers.hjc.util.ErrorUtil;
import pers.hjc.util.ImageUtil;
import pers.hjc.util.MD5Util;

/**
 * 用户操作 注册 登陆 修改 realname禁止输入'('')''<''>'' ' 如果能解决js 修改input的value时转义问题
 * 也可用XSSUtil.cleanXSS()
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class UserController
{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping({ "/login" })
	@FormToken(produce = true)
	public String loginPage()
	{
		return "login";
	}

	/**
	 * 个人中心
	 * 
	 * @return
	 */
	@RequestMapping({ "/center" })
	@FormToken(produce = true)
	public String centerPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			request.setAttribute("ID", ID);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "center";
	}

	@RequestMapping({ "/logout" })
	public String lougout(HttpServletRequest request, HttpServletResponse response)
	{
		CookieUtils.cleanCookie(response);
		request.getSession().removeAttribute(GlobelVariable.SESSION_USER_ID);
		return "redirect:/tourist";
	}

	@RequestMapping("/user/{id}")
	public String userPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			User user = userService.findUser(Long.parseLong(id));
			if (user != null && user.getIsUse() != 0)
			{
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("用户不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "user";
	}

	@RequestMapping({ "/admin/center" })
	@FormToken(produce = true)
	public String centerAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			request.setAttribute("ID", ID);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "admin/center";
	}

	@RequestMapping("/admin/user/{id}")
	public String userAdminPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			User user = userService.findUser(Long.parseLong(id));
			if (user != null && user.getIsUse() != 0)
			{
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("用户不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "admin/user";
	}

	@RequestMapping({ "/loginAjax" })
	@ResponseBody
	@FormToken(remove = true)
	@SameUrlData
	public Object loginAjax(@Valid LoginMessage loginMessage, BindingResult result, HttpServletRequest request,
			HttpServletResponse response)
	{

		Map<String, Object> map = new HashMap<>();
		try
		{
			if (result.hasErrors())
			{
				ErrorUtil.doError(map, result);
			}
			else
			{
				String captcha = loginMessage.getCaptcha();
				HttpSession session = request.getSession();
				if (session.getAttribute("captchaToken").toString().toLowerCase().equals(captcha.toLowerCase()))
				{
					User user = userService.login(loginMessage);
					session.setAttribute(GlobelVariable.SESSION_USER_ID, user.getID());
					map.put("flag", 1);
					map.put("role", user.getRole().getID());
					CookieUtils.saveCookie(user.getID().toString(), response);
				}
				else
				{
					// System.out.println(session.getAttribute("captchaToken").toString().toLowerCase()
					// + " "
					// + captcha.toLowerCase());
					throw new Exception("验证码错误");
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findAllUserAjax" })
	@ResponseBody
	public Object findAllUser(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			List<User> users = userService.findAllUser(false);
			List<SimpleUserMessage> result = new ArrayList<>();
			for (User user : users)
			{
				if (user.getIsUse() == 1)
				{
					result.add(new SimpleUserMessage(user));
				}
			}
			map.put("flag", 1);
			map.put("res", result);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findUserByPageAjax" })
	@ResponseBody
	public Object findUserByPage(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (page == null || rows == null)
			{
				throw new Exception("分页信息错误");
			}
			if (page <= 0 || rows <= 0)
			{
				throw new Exception("分页信息错误");
			}
			List<User> users = userService.findUserByPage(page, rows, false);
			if (users.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<SimpleUserMessage> result = new ArrayList<>();
			for (User user : users)
			{
				result.add(new SimpleUserMessage(user));
			}
			map.put("flag", 1);
			map.put("res", result);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findUserByNameAjax" })
	@ResponseBody
	public Object findUserByName(String realname, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (realname == null)
			{
				throw new Exception("信息错误");
			}
			List<User> users = userService.findUserByName(realname, false);
			if (users.size() == 0)
			{
				throw new Exception("此用户不存在");
			}
			List<SimpleUserMessage> result = new ArrayList<>();
			for (User user : users)
			{
				result.add(new SimpleUserMessage(user));
			}
			map.put("flag", 1);
			map.put("res", result);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/searchUserAjax" })
	@ResponseBody
	public Object searchUser(Long userID, String realname, Integer page, Integer rows, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (page == null || rows == null)
			{
				throw new Exception("分页信息错误");
			}
			if (page <= 0 || rows <= 0)
			{
				throw new Exception("分页信息错误");
			}
			Map<String, Object> params = new HashMap<>();
			if (userID != null)
			{
				params.put("ID", userID);
			}
			if (realname != null && !realname.trim().equals(""))
			{
				params.put("realname", realname);
			}
			List<User> users = userService.searchUser(params, false);
			List<UserMessage> result = new ArrayList<>();
			for (int i = (page - 1) * rows; i < page * rows && i < users.size(); i++)
			{
				result.add(new UserMessage(users.get(i)));
			}
			map.put("flag", 1);
			map.put("res", result);
			map.put("total", users.size());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findUserByIdAjax" })
	@ResponseBody
	public Object findUserByID(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("ID信息错误");
			}
			User user = userService.findUser(userID);
			if (user == null)
			{
				throw new Exception("此ID不存在");
			}
			UserMessage result = new UserMessage(user);
			if (result.getIsUse() == 0)
			{
				throw new Exception("此用户已被管理员禁止");
			}
			map.put("flag", 1);
			map.put("res", result);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/registerUserAjax" })
	@ResponseBody
	@FormToken(remove = true)
	@SameUrlData
	public Object registerUser(User user, /* String captcha, */HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			HttpSession session = request.getSession();
			/*
			 * if
			 * (!session.getAttribute("captchaToken").toString().toLowerCase().
			 * equals(captcha.toLowerCase())) { throw new Exception("验证码错误"); }
			 */
			// 验证数据
			validateUser(user);
			user.setRealname(user.getRealname());
			userService.registerUser(user);
			map.put("flag", 1);
			session.setAttribute(GlobelVariable.SESSION_USER_ID, user.getID());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/updateUserAjax" })
	@ResponseBody
	@FormToken(remove = true)
	public Object updateUser(User user, String newPassword, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			if (user.getID().longValue() == ID.longValue())
			{
				validateUser(user);
				User newUser = userService.findUser(ID);
				if (!MD5Util.verify(user.getPassword(), newUser.getPassword()))
				{
					throw new Exception("密码错误");
				}
				newUser.setRealname(user.getRealname());
				newUser.setPhone(user.getPhone());
				if (newPassword != null && !newPassword.trim().equals(""))
				{
					if (newPassword.length() < 6 || newPassword.length() > 20)
					{
						throw new Exception("新密码必须是6-20个字符的数字或字母组合");
					}
					newUser.setPassword(MD5Util.generate(newPassword));
				}
				userService.updateUser(newUser);
				map.put("flag", 1);
			}
			else
			{
				throw new Exception("非法操作");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping(value = "/updateUserHeadAjax")
	@ResponseBody
	public Object updateUserHead(@RequestParam(value = "avatar_data") String params,
			@RequestParam(value = "avatar_file") MultipartFile imageFile, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (params == null)
			{
				throw new Exception("裁剪参数错误");
			}
			ObjectMapper mapper = new ObjectMapper();
			CutAndRotate param = (CutAndRotate) mapper.readValue(params, CutAndRotate.class);
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			String resourcePath = ConfigUtil.get("savepath") + "head/";
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			if (imageFile != null)
			{
				BufferedImage image = ImageIO.read(imageFile.getInputStream());
				if (image != null)
				{ // 如果image=null 表示上传的不是图片格式
					int x = (int) param.getX();
					int y = (int) param.getY();
					int width = (int) param.getWidth();
					int height = (int) param.getHeight();
					int rotate = param.getRotate();
					if (width <= 0 || height <= 0)
					{
						throw new Exception("裁剪长宽必须大于0");
					}
					if (x >= image.getWidth() || y >= image.getHeight())
					{
						throw new Exception("裁剪参数出错");
					}
					// 先把用户上传到原图保存到服务器上
					String prefix = imageFile.getOriginalFilename()
							.substring(imageFile.getOriginalFilename().lastIndexOf(".") + 1);
					String original = uuid + "." + prefix;
					File file = new File(resourcePath, original);
					imageFile.transferTo(file);
					if (file.exists())
					{
						String src = resourcePath + original;
						String target = resourcePath + uuid + "_s." + prefix;
						boolean flag = ImageUtil.cutAndRotateImage(src, target, x, y, width, height, rotate);
						if (!flag)
						{
							throw new Exception("服务器裁剪图片失败");
						}
						String head = "/upload/head/" + uuid + "_s." + prefix;
						User user = userService.findUser(ID);
						user.setHead(head);
						userService.updateUser(user);
						map.put("flag", 1);
						map.put("res", head);
					}
				}
				else
				{
					throw new Exception("上传文件不是图片");
				}
			}
			else
			{
				throw new Exception("文件上传失败");
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findIndexUserAjax" })
	@ResponseBody
	public Object findIndexUser(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			HttpSession session = request.getSession();
			Object userID = session.getAttribute(GlobelVariable.SESSION_USER_ID);
			String role = "";
			if (userID != null)
			{
				Long ID = Long.parseLong(userID.toString());
				if (userService.findUser(ID) != null)
				{
					role = userService.findUser(ID).getRole().getDescription();
				}
			}
			List<IndexUser> users = userService.findAllIndexUser();
			List<IndexUserMessage> result = new ArrayList<>();
			for (IndexUser user : users)
			{
				result.add(new IndexUserMessage(user));
			}
			map.put("flag", 1);
			map.put("res", result);
			map.put("role", role);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	private void validateUser(User user) throws Exception
	{
		Long ID = user.getID();
		if (ID == null || ID < 1000000L || ID > 9999999999999L)
		{
			throw new Exception("学号位数错误");
		}
		String password = user.getPassword();
		if (password == null || password.length() < 6 || password.length() > 20)
		{
			throw new Exception("密码是6-20个字符的数字或字母组合");
		}
		String realname = user.getRealname();
		if (realname == null)
		{
			throw new Exception("真实姓名不能为空");
		}
		for (int i = 0; i < realname.length(); i++)
		{
			char c = realname.charAt(i);
			if (c == ' ' || c == '<' || c == '>' || c == '(' || c == ')')
			{
				throw new Exception("真实姓名中包含非法字符");
			}
		}
		Long phone = user.getPhone();
		if (phone == null)
		{
			throw new Exception("手机号不能为空");
		}
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone.toString());
		if (!m.matches())
		{
			throw new Exception("手机号错误");
		}
	}
}
