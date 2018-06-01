package pers.hjc.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import pers.hjc.GlobelVariable;
import pers.hjc.annotation.FormToken;
import pers.hjc.annotation.SameUrlData;
import pers.hjc.entity.CutAndRotateAdmin;
import pers.hjc.model.Article;
import pers.hjc.model.ArticleContent;
import pers.hjc.model.IndexImage;
import pers.hjc.model.IndexPublication;
import pers.hjc.model.IndexResearch;
import pers.hjc.model.IndexTeaching;
import pers.hjc.model.IndexUser;
import pers.hjc.model.Role;
import pers.hjc.model.User;
import pers.hjc.service.AdminService;
import pers.hjc.service.ArticleService;
import pers.hjc.service.UserService;
import pers.hjc.util.ConfigUtil;
import pers.hjc.util.ErrorUtil;
import pers.hjc.util.ImageUtil;
import pers.hjc.util.MD5Util;
import pers.hjc.util.XSSUtil;

/**
 * 管理员操作 修改删除任意人的周报 修改权限 重置密码 对首页信息进行 增删改操作 包括 首页显示的顺序 上下 置顶 置底移动
 * 需要注意使用XSSUtil.cleanXSS()方法将某些特殊字符过滤了 代码中修改后需要reload将信息加载到application中
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ServletContext servletContext;

	/*
	 * @InitBinder private void initBinder(WebDataBinder binder) {
	 * binder.registerCustomEditor(String.class, new StringEscapeEditor(true,
	 * false)); }
	 */

	@RequestMapping("/updateArticleAjax")
	@ResponseBody
	@FormToken(remove = true)
	@SameUrlData
	public Object updateArticle(Article article, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (article.getTitle() == null || article.getTitle().trim().equals(""))
			{
				throw new Exception("标题不能为空");
			}
			if (article.getArticleContent() == null || article.getArticleContent().getContent() == null
					|| article.getArticleContent().getContent().trim().equals(""))
			{
				throw new Exception("内容不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			Article newArticle = articleService.findArticle(article.getID());
			if (newArticle == null)
			{
				throw new Exception("文章不存在");
			}

			newArticle.setTitle(XSSUtil.cleanXSS(article.getTitle()));
			ArticleContent content = newArticle.getArticleContent();
			content.setContent(article.getArticleContent().getContent());
			newArticle.setArticleContent(content);
			articleService.updateArticle(newArticle);
			map.put("flag", 1);
			map.put("msg", article.getID());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/deleteArticleByIDAjax")
	@ResponseBody
	public Object deleteArticleByID(Long articleID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (articleID == null)
			{
				throw new Exception("异常操作");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			Article article = articleService.findArticle(articleID);
			if (article == null)
			{
				throw new Exception("文章不存在");
			}

			articleService.deleteArticle(article);
			logger.warn("Delete Ariticle " + articleID + " by " + ID);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/resetPasswordAjax")
	@ResponseBody
	public Object resetPassword(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			String ID = request.getSession().getAttribute(GlobelVariable.SESSION_USER_ID).toString();
			if (userID == null)
			{
				throw new Exception("异常操作");
			}
			User user = userService.findUser(userID);
			if (user == null)
			{
				throw new Exception("此ID不存在");
			}
			if (user.getIsUse() == 0)
			{
				throw new Exception("此ID被禁用");
			}
			user.setPassword(MD5Util
					.generate(userID.toString().substring(userID.toString().length() - 6, userID.toString().length())));
			userService.updateUser(user);
			map.put("flag", 1);
			logger.warn("Reset password " + userID + " by " + ID);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/changeRoleAjax")
	@ResponseBody
	public Object changeRole(Long userID, Long roleID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			String ID = request.getSession().getAttribute(GlobelVariable.SESSION_USER_ID).toString();
			if (userID == null || roleID == null)
			{
				throw new Exception("异常操作");
			}
			User user = userService.findUser(userID);
			Role role = userService.findRole(roleID);
			if (user == null)
			{
				throw new Exception("此用户不存在");
			}
			if (role == null)
			{
				throw new Exception("此权限不存在");
			}
			if (user.getIsUse() == 0)
			{
				throw new Exception("此ID被禁用");
			}
			user.setRole(role);
			userService.updateUser(user);
			map.put("flag", 1);
			logger.warn("Change role " + userID + " by " + ID);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	/// 首页展示图片

	@RequestMapping("/deleteIndexImageAjax")
	@ResponseBody
	public Object deleteIndexImage(Long imageID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (imageID == null)
			{
				throw new Exception("删除图片ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexImage deleteImage = adminService.getImageByID(imageID);
			int orders = deleteImage.getOrders();
			adminService.deleteImage(imageID);

			List<IndexImage> images = adminService.getAllImage();
			for (IndexImage image : images)
			{
				if (image.getOrders() > orders)
				{
					image.setOrders(image.getOrders() - 1);
					adminService.updateImage(image);
				}
			}
			loadImage();
			logger.warn("Delete image " + imageID + " by " + ID);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/addImageAjax")
	@ResponseBody
	public Object addImage(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			List<IndexImage> images = adminService.getAllImage();
			IndexImage image = new IndexImage();
			image.setOrders(images.size());
			Long imageID = adminService.addImage(image);
			if (imageID != null)
			{
				loadImage();
				logger.warn("Add image " + imageID + " by " + ID);
				image = adminService.getImageByID(imageID);
				map.put("flag", 1);
				map.put("res", image);
			}
			else
			{
				throw new Exception("添加失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/topImageAjax")
	@ResponseBody
	public Object topImage(Long imageID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (imageID == null)
			{
				throw new Exception("修改图片ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexImage image = adminService.getImageByID(imageID);
			if (image == null)
			{
				throw new Exception("修改图片为空");
			}
			int orders = image.getOrders();
			if (orders == 0)
			{
				map.put("flag", 1);
				return map;
			}

			List<IndexImage> images = adminService.getAllImage();
			for (IndexImage img : images)
			{
				if (img.getOrders() < orders)
				{
					img.setOrders(img.getOrders() + 1);
					adminService.updateImage(img);
				}
			}
			image.setOrders(0);
			adminService.updateImage(image);
			loadImage();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/upImageAjax")
	@ResponseBody
	public Object upImage(Long imageID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (imageID == null)
			{
				throw new Exception("修改图片ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexImage image = adminService.getImageByID(imageID);
			if (image == null)
			{
				throw new Exception("修改图片为空");
			}
			int orders = image.getOrders();
			if (orders == 0)
			{
				map.put("flag", 1);
				return map;
			}

			List<IndexImage> images = adminService.getAllImage();
			for (IndexImage img : images)
			{
				if (img.getOrders() == orders - 1)
				{
					img.setOrders(orders);
					adminService.updateImage(img);
					break;
				}
			}
			image.setOrders(orders - 1);
			adminService.updateImage(image);
			loadImage();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/downImageAjax")
	@ResponseBody
	public Object downImage(Long imageID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (imageID == null)
			{
				throw new Exception("修改图片ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexImage image = adminService.getImageByID(imageID);
			if (image == null)
			{
				throw new Exception("修改图片为空");
			}
			int orders = image.getOrders();
			List<IndexImage> images = adminService.getAllImage();
			if (orders == images.size() - 1)
			{
				map.put("flag", 1);
				return map;
			}

			for (IndexImage img : images)
			{
				if (img.getOrders() == orders + 1)
				{
					img.setOrders(orders);
					adminService.updateImage(img);
					break;
				}
			}
			image.setOrders(orders + 1);
			adminService.updateImage(image);
			loadImage();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/bottomImageAjax")
	@ResponseBody
	public Object bottomImage(Long imageID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (imageID == null)
			{
				throw new Exception("修改图片ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexImage image = adminService.getImageByID(imageID);
			if (image == null)
			{
				throw new Exception("修改图片为空");
			}
			int orders = image.getOrders();
			List<IndexImage> images = adminService.getAllImage();
			if (orders == images.size() - 1)
			{
				map.put("flag", 1);
				return map;
			}

			for (IndexImage img : images)
			{
				if (img.getOrders() > orders)
				{
					img.setOrders(img.getOrders() - 1);
					adminService.updateImage(img);
				}
			}
			image.setOrders(images.size() - 1);
			adminService.updateImage(image);
			loadImage();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping(value = "/updateImageInfoAjax")
	@ResponseBody
	public Object updateImageInfo(@Valid IndexImage image, BindingResult result, HttpServletRequest request,
			HttpServletResponse response)
	{

		Map<String, Object> map = new HashMap<>();
		try
		{
			if (result.hasErrors())
			{
				ErrorUtil.doError(map, result);
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			Long imageID = image.getID();
			IndexImage saveImage = adminService.getImageByID(imageID);
			if (saveImage == null)
			{
				throw new Exception("图片不存在");
			}
			saveImage.setTitle(XSSUtil.cleanXSS(image.getTitle()));
			saveImage.setDescription(image.getDescription());
			adminService.updateImage(saveImage);
			loadImage();
			logger.warn("Update image " + imageID + " by " + ID);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping(value = "/updateImageAjax")
	@ResponseBody
	public Object updateImage(@RequestParam(value = "avatar_data") String params,
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
			// String str =
			// "{\"ID\":1,\"x\":0,\"y\":0,\"height\":82.41250000000001,\"width\":109.88333333333333,\"rotate\":90}";
			ObjectMapper mapper = new ObjectMapper();
			CutAndRotateAdmin param = (CutAndRotateAdmin) mapper.readValue(params, CutAndRotateAdmin.class);
			Long imageID = param.getiD();
			IndexImage saveImage = adminService.getImageByID(imageID);
			if (saveImage == null)
			{
				throw new Exception("图片不存在");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			String resourcePath = ConfigUtil.get("savepath") + "indeximage/";
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
						String path = "/upload/indeximage/" + uuid + "_s." + prefix;
						saveImage.setImage(path);
						adminService.updateImage(saveImage);
						loadImage();
						logger.warn("Update image " + imageID + " by " + ID);
						map.put("flag", 1);
						map.put("res", path);
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

	/// 首页展示用户

	@RequestMapping("/topUserAjax")
	@ResponseBody
	public Object topUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("修改用户ID为空");
			}

			IndexUser indexUser = adminService.getIndexUserByID(userID);
			if (indexUser == null)
			{
				throw new Exception("修改数据为空");
			}
			int orders = indexUser.getOrders();
			if (orders == 0)
			{
				map.put("flag", 1);
				return map;
			}

			List<IndexUser> users = userService.findAllIndexUser();
			for (IndexUser user : users)
			{
				if (user.getOrders() < orders)
				{
					user.setOrders(user.getOrders() + 1);
					adminService.updateIndexUser(user);
				}
			}
			indexUser.setOrders(0);
			adminService.updateIndexUser(indexUser);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/upUserAjax")
	@ResponseBody
	public Object upUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("修改用户ID为空");
			}

			IndexUser indexUser = adminService.getIndexUserByID(userID);
			if (indexUser == null)
			{
				throw new Exception("修改数据为空");
			}
			int orders = indexUser.getOrders();
			if (orders == 0)
			{
				map.put("flag", 1);
				return map;
			}

			List<IndexUser> users = userService.findAllIndexUser();
			for (IndexUser user : users)
			{
				if (user.getOrders() == orders - 1)
				{
					user.setOrders(orders);
					adminService.updateIndexUser(user);
					break;
				}
			}
			indexUser.setOrders(orders - 1);
			adminService.updateIndexUser(indexUser);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/downUserAjax")
	@ResponseBody
	public Object downUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("修改用户ID为空");
			}

			IndexUser indexUser = adminService.getIndexUserByID(userID);
			if (indexUser == null)
			{
				throw new Exception("修改数据为空");
			}
			int orders = indexUser.getOrders();
			List<IndexUser> users = userService.findAllIndexUser();
			if (orders == users.size() - 1)
			{
				map.put("flag", 1);
				return map;
			}

			for (IndexUser user : users)
			{
				if (user.getOrders() == orders + 1)
				{
					user.setOrders(orders);
					adminService.updateIndexUser(user);
					break;
				}
			}
			indexUser.setOrders(orders + 1);
			adminService.updateIndexUser(indexUser);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/bottomUserAjax")
	@ResponseBody
	public Object bottomUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("修改用户ID为空");
			}

			IndexUser indexUser = adminService.getIndexUserByID(userID);
			if (indexUser == null)
			{
				throw new Exception("修改数据为空");
			}
			int orders = indexUser.getOrders();
			List<IndexUser> users = userService.findAllIndexUser();
			if (orders == users.size() - 1)
			{
				map.put("flag", 1);
				return map;
			}

			for (IndexUser user : users)
			{
				if (user.getOrders() > orders)
				{
					user.setOrders(user.getOrders() - 1);
					adminService.updateIndexUser(user);
				}
			}
			indexUser.setOrders(users.size() - 1);
			adminService.updateIndexUser(indexUser);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/addIndexUserAjax")
	@ResponseBody
	public Object addIndexUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("添加用户ID为空");
			}
			User user = userService.findUser(userID);
			if (user == null)
			{
				throw new Exception("添加用户为空");
			}
			IndexUser indexUser = new IndexUser();
			indexUser.setUser(user);
			indexUser.setOrders(userService.findAllIndexUser().size());
			adminService.addUser(indexUser);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/deleteIndexUserAjax")
	@ResponseBody
	public Object deleteIndexUser(Long userID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (userID == null)
			{
				throw new Exception("删除首页用户ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			IndexUser deleteuser = adminService.getIndexUserByID(userID);
			int orders = deleteuser.getOrders();
			adminService.deleteIndexUser(userID);

			List<IndexUser> users = userService.findAllIndexUser();
			for (IndexUser user : users)
			{
				if (user.getOrders() > orders)
				{
					user.setOrders(user.getOrders() - 1);
					adminService.updateIndexUser(user);
				}
			}
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	/// 首页教学经历

	@RequestMapping("/addTeachingAjax")
	@ResponseBody
	public Object addTeaching(IndexTeaching teaching, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (teaching == null)
			{
				throw new Exception("添加教学记录为空");
			}
			if (teaching.getContent() == null || teaching.getContent().trim().equals(""))
			{
				throw new Exception("教学记录内容不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			teaching.setContent(XSSUtil.cleanXSS(teaching.getContent()));
			Long teachingID = adminService.addIndexTeaching(teaching);
			if (teachingID != null)
			{
				logger.warn("Add teaching " + teachingID + " by " + ID);
				loadTeaching();
				map.put("flag", 1);
			}
			else
			{
				throw new Exception("添加失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/updateTeachingAjax")
	@ResponseBody
	public Object updateTeaching(IndexTeaching teaching, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (teaching == null)
			{
				throw new Exception("更新教学记录为空");
			}
			if (teaching.getContent() == null || teaching.getContent().trim().equals(""))
			{
				throw new Exception("教学记录内容不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			Long teachingID = teaching.getID();
			IndexTeaching newTeaching = adminService.getTeachingByID(teachingID);
			newTeaching.setContent(XSSUtil.cleanXSS(teaching.getContent()));
			adminService.updateIndexTeaching(newTeaching);
			logger.warn("Update teaching " + teachingID + " by " + ID);
			loadTeaching();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/deleteTeachingAjax")
	@ResponseBody
	public Object deleteTeaching(Long teachingID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (teachingID == null)
			{
				throw new Exception("删除教学记录ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			adminService.deleteIndexTeaching(teachingID);
			logger.warn("Delete teaching " + teachingID + " by " + ID);
			loadTeaching();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	/// 首页发表

	@RequestMapping("/addPublicationAjax")
	@ResponseBody
	public Object addPublication(IndexPublication publication, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (publication == null)
			{
				throw new Exception("添加论文记录为空");
			}
			if (publication.getContent() == null || publication.getContent().trim().equals(""))
			{
				throw new Exception("详情不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			publication.setContent(XSSUtil.cleanXSS(publication.getContent()));
			Long publicationID = adminService.addIndexPublication(publication);
			if (publicationID != null)
			{
				logger.warn("Add publication " + publicationID + " by " + ID);
				loadPublication();
				map.put("flag", 1);
			}
			else
			{
				throw new Exception("添加失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/updatePublicationAjax")
	@ResponseBody
	public Object updatePublication(IndexPublication publication, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (publication == null)
			{
				throw new Exception("更新论文记录为空");
			}
			if (publication.getContent() == null || publication.getContent().trim().equals(""))
			{
				throw new Exception("详情不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			Long publicationID = publication.getID();
			IndexPublication newPublication = adminService.getPublicationByID(publicationID);
			newPublication.setContent(XSSUtil.cleanXSS(publication.getContent()));
			adminService.updateIndexPublication(newPublication);
			logger.warn("Update publication " + publicationID + " by " + ID);
			loadPublication();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/deletePublicationAjax")
	@ResponseBody
	public Object deletePublication(Long publicationID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (publicationID == null)
			{
				throw new Exception("删除论文ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			adminService.deleteIndexPublication(publicationID);
			logger.warn("Delete publication " + publicationID + " by " + ID);
			loadPublication();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/reloadAjax")
	@ResponseBody
	public Object reload(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			loadImage();
			loadTeaching();
			loadPublication();
			loadResearch();
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	/// 首页研究方向

	@RequestMapping("/addResearchAjax")
	@ResponseBody
	public Object addResearch(IndexResearch research, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (research == null)
			{
				throw new Exception("添加论文记录为空");
			}
			if (research.getContent() == null || research.getContent().trim().equals(""))
			{
				throw new Exception("详情不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			research.setContent(XSSUtil.cleanXSS(research.getContent()));
			Long researchID = adminService.addIndexResearch(research);
			if (researchID != null)
			{
				logger.warn("Add research " + researchID + " by " + ID);
				loadResearch();
				map.put("flag", 1);
			}
			else
			{
				throw new Exception("添加失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/updateResearchAjax")
	@ResponseBody
	public Object updateResearch(IndexResearch research, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (research == null)
			{
				throw new Exception("更新论文记录为空");
			}
			if (research.getContent() == null || research.getContent().trim().equals(""))
			{
				throw new Exception("详情不能为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			Long researchID = research.getID();
			IndexResearch newResearch = adminService.getResearchByID(researchID);
			newResearch.setContent(XSSUtil.cleanXSS(research.getContent()));
			adminService.updateIndexResearch(newResearch);
			logger.warn("Update research " + researchID + " by " + ID);
			loadResearch();
			map.put("flag", 1);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping("/deleteResearchAjax")
	@ResponseBody
	public Object deleteResearch(Long researchID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (researchID == null)
			{
				throw new Exception("删除论文ID为空");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());

			adminService.deleteIndexResearch(researchID);
			logger.warn("Delete research " + researchID + " by " + ID);
			loadResearch();
			map.put("flag", 1);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	/*
	 * 首也各种信息加载至application中 不同步应该也没问题
	 */
	private synchronized void loadImage() throws Exception
	{
		List<IndexImage> images = adminService.getAllImage();
		servletContext.setAttribute(GlobelVariable.APPLICATION_IMAGE_PATH, images);
		servletContext.setAttribute(GlobelVariable.APPLICATION_IMAGE_SIZE, images.size());
	}

	private synchronized void loadTeaching() throws Exception
	{
		List<IndexTeaching> teachings = adminService.getAllTeaching();
		servletContext.setAttribute(GlobelVariable.APPLICATION_TEACH_PATH, teachings);
		servletContext.setAttribute(GlobelVariable.APPLICATION_TEACH_SIZE, teachings.size());
	}

	private synchronized void loadPublication() throws Exception
	{
		List<IndexPublication> publications = adminService.getAllPublication();
		servletContext.setAttribute(GlobelVariable.APPLICATION_PUBLICATION_PATH, publications);
		servletContext.setAttribute(GlobelVariable.APPLICATION_PUBLICATION_SIZE, publications.size());
	}

	private synchronized void loadResearch() throws Exception
	{
		List<IndexResearch> researchs = adminService.getAllResearch();
		servletContext.setAttribute(GlobelVariable.APPLICATION_RESEARCH_PATH, researchs);
		servletContext.setAttribute(GlobelVariable.APPLICATION_RESEARCH_SIZE, researchs.size());
	}
}
