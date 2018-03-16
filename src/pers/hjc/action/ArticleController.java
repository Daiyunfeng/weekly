package pers.hjc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.hjc.GlobelVariable;
import pers.hjc.annotation.FormToken;
import pers.hjc.annotation.SameUrlData;
import pers.hjc.entity.ArticleMessage;
import pers.hjc.entity.SimpleArticleMessage;
import pers.hjc.model.Article;
import pers.hjc.model.ArticleContent;
import pers.hjc.model.User;
import pers.hjc.service.ArticleService;
import pers.hjc.service.UserService;
import pers.hjc.util.DateUtil;
import pers.hjc.util.ErrorUtil;
import pers.hjc.util.XSSUtil;
/**
 * 对周报的操作
 * 包含查看修改页面的控制
 * 对周报的增删改查
 * 查可以选择多种查询方式
 * 查找全部 分页查找 serch包含条件搜索
 * @author Administrator
 *
 */
@Controller
public class ArticleController
{
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article/{id}")
	@FormToken(produce = true)
	public String articlePage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Article article = articleService.findArticle(Long.parseLong(id));
			if (article != null && article.getIsUse() != 0)
			{
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("文章不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "article";
	}

	@RequestMapping("/update/{id}")
	@FormToken(produce = true)
	public String updatePage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Article article = articleService.findArticle(Long.parseLong(id));
			if (article != null && article.getIsUse() != 0)
			{
				HttpSession session = request.getSession();
				Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
				Long userID = article.getUser().getID();
				if (userID.longValue() != ID.longValue())
				{
					throw new Exception("异常操作");
				}
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("文章不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "update";
	}

	@RequestMapping("/admin/article/{id}")
	@FormToken(produce = true)
	public String articleAdminPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Article article = articleService.findArticle(Long.parseLong(id));
			if (article != null && article.getIsUse() != 0)
			{
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("文章不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "admin/article";
	}

	@RequestMapping("/admin/update/{id}")
	@FormToken(produce = true)
	public String updateAdminPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Article article = articleService.findArticle(Long.parseLong(id));
			if (article != null && article.getIsUse() != 0)
			{
				HttpSession session = request.getSession();
				// 全部允许
				request.setAttribute("ID", id);
			}
			else
			{
				throw new Exception("文章不存在");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("msg", e.getMessage());
			return "/errors/error";
		}
		return "admin/update";
	}

	/**
	 * 添加文章 手动添加user
	 * 
	 * @param article
	 *            前端 需要传入 title articleContent.content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addNewArticleAjax")
	@ResponseBody
	@FormToken(remove = true)
	@SameUrlData
	public Object addNewArticle(Article article, HttpServletRequest request, HttpServletResponse response)
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
			User user = userService.findUser(ID);
			article.setUser(user);
			article.setTitle(XSSUtil.cleanXSS(article.getTitle()));
			Boolean flag = articleService.addArticle(article);
			if (flag)
			{
				map.put("flag", 1);
				map.put("msg", article.getID());
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
			Long userID = newArticle.getUser().getID();
			if (userID.longValue() != ID.longValue())
			{
				throw new Exception("异常操作");
			}

			newArticle.setTitle(XSSUtil.cleanXSS(article.getTitle()));
			ArticleContent content = newArticle.getArticleContent();
			content.setContent(article.getArticleContent().getContent());
			newArticle.setArticleContent(content);
			Boolean flag = articleService.updateArticle(newArticle);
			if (flag)
			{
				map.put("flag", 1);
				map.put("msg", article.getID());
			}
			else
			{
				throw new Exception("更新失败");
			}
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
			Long userID = article.getUser().getID();
			if (userID.longValue() != ID.longValue())
			{
				throw new Exception("异常操作");
			}

			article.setIsUse(0);
			Boolean flag = articleService.updateArticle(article);
			if (flag)
			{
				logger.warn("Delete Ariticle " + articleID + " by " + ID);
				map.put("flag", 1);
			}
			else
			{
				throw new Exception("删除失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findArticleByIDAjax" })
	@ResponseBody
	public Object findAllArticle(Long ID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			Article articles = articleService.findArticle(ID);
			ArticleMessage result = new ArticleMessage(articles);
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findAllArticleAjax" })
	@ResponseBody
	public Object findAllArticle(String orderBy, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			List<Article> articles = articleService.findAllArticle(false, orderBy);
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findArticleByPageAjax" })
	@ResponseBody
	public Object findArticleByPage(String orderBy, Integer page, Integer rows, HttpServletRequest request,
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
			String userID = request.getSession().getAttribute(GlobelVariable.SESSION_USER_ID).toString();
			List<Article> articles = articleService.findArticleByPage(page, rows, false, orderBy);
			if (articles.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			map.put("flag", 1);
			map.put("total", articleService.count(false));
			map.put("res", result);
			map.put("userID", userID);
			// System.out.println(map.get("total"));
			return map;
		}
		catch (Exception e)
		{
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findArticleByTitleAjax" })
	@ResponseBody
	public Object findArticleByName(String orderBy, Integer page, Integer rows, String title,
			HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (title == null)
			{
				throw new Exception("信息错误");
			}
			if (page == null || rows == null)
			{
				throw new Exception("分页信息错误");
			}
			if (page <= 0 || rows <= 0)
			{
				throw new Exception("分页信息错误");
			}
			List<Article> articles = articleService.findArticleByTitle(page, rows, title, false, orderBy);
			if (articles.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findArticleByUserAjax" })
	@ResponseBody
	public Object findArticleByUser(Long userID, HttpServletRequest request, HttpServletResponse response)
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
			if (user.getIsUse() == 0)
			{
				throw new Exception("此用户已被管理员禁止");
			}
			Set<Article> articles = user.getArticles();
			if (articles.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			result.sort(SimpleArticleMessage.sortByUpdateTime);
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findAllArticleBetweenAjax" })
	@ResponseBody
	public Object findAllArticleBetween(String beginTime, String endTime, String orderBy, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (beginTime == null || endTime == null)
			{
				throw new Exception("日期信息错误");
			}
			List<Article> articles = articleService.findAllArticleBetween(DateUtil.format(beginTime),
					DateUtil.format(endTime), false, orderBy);
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findArticleByPageBetweenAjax" })
	@ResponseBody
	public Object findArticleByPageBetween(String beginTime, String endTime, String orderBy, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response)
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
			if (beginTime == null || endTime == null)
			{
				throw new Exception("日期信息错误");
			}
			List<Article> articles = articleService.findArticleByPageBetween(DateUtil.format(beginTime),
					DateUtil.format(endTime), page, rows, false, orderBy);
			if (articles.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (Article article : articles)
			{
				if (article.getIsUse() == 1)
				{
					result.add(new SimpleArticleMessage(article));
				}
			}
			map.put("flag", 1);
			map.put("res", result);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/searchArticleAjax" })
	@ResponseBody
	public Object searchArticle(Long ID, String author, String title, String beginTime, String endTime, Integer page,
			Integer rows, HttpServletRequest request, HttpServletResponse response)
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
			if (ID != null)
			{
				params.put("user.ID", ID);
			}
			if (author != null && !author.trim().equals(""))
			{
				params.put("user.realname", author);
			}
			if (title != null && !title.trim().equals(""))
			{
				params.put("title", title);
			}
			if (beginTime != null && endTime != null && !beginTime.trim().equals("") && !endTime.trim().equals(""))
			{
				params.put("beginTime", beginTime);
				params.put("endTime", endTime);
			}
			String userID = request.getSession().getAttribute(GlobelVariable.SESSION_USER_ID).toString();
			List<Article> articles = articleService.searchArticle(params, false);
			List<SimpleArticleMessage> result = new ArrayList<>();
			for (int i = (page - 1) * rows; i < page * rows && i < articles.size(); i++)
			{
				result.add(new SimpleArticleMessage(articles.get(i)));
			}
			map.put("flag", 1);
			map.put("total", articles.size());
			map.put("res", result);
			map.put("userID", userID);
			return map;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}
}
