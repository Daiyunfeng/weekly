package pers.hjc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.hjc.GlobelVariable;
import pers.hjc.annotation.FormToken;
import pers.hjc.annotation.SameUrlData;
import pers.hjc.entity.CommentMessage;
import pers.hjc.model.Comment;
import pers.hjc.model.User;
import pers.hjc.service.ArticleService;
import pers.hjc.service.CommentService;
import pers.hjc.service.UserService;
import pers.hjc.util.ErrorUtil;
import pers.hjc.util.XSSUtil;

@Controller
public class CommentController
{
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private CommentService commentService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@RequestMapping("/addNewCommentAjax")
	@ResponseBody
	@FormToken(remove = true)
	@SameUrlData
	public Object addNewArticle(Comment comment, Long articleID, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			if (comment.getContent() == null || comment.getContent().trim().equals(""))
			{
				throw new Exception("内容不能为空");
			}

			if (comment.getContent().length() > 200)
			{
				throw new Exception("内容过长");
			}
			HttpSession session = request.getSession();
			Long ID = Long.parseLong(session.getAttribute(GlobelVariable.SESSION_USER_ID).toString());
			User user = userService.findUser(ID);
			comment.setUser(user);
			comment.setContent(XSSUtil.cleanXSS(comment.getContent()));
			comment.setArticle(articleService.findArticle(articleID));
			commentService.addComment(comment);
			map.put("flag", 1);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			ErrorUtil.doError(map, e);
		}
		return map;
	}

	@RequestMapping({ "/findAllCommentAjax" })
	@ResponseBody
	public Object findAllComment(Long articleID, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<>();
		try
		{
			List<Comment> comments = commentService.findAllComment(articleID, false);
			List<CommentMessage> result = new ArrayList<>();
			for (Comment comment : comments)
			{
				if (comment.getIsUse() == 1)
				{
					result.add(new CommentMessage(comment));
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

	@RequestMapping({ "/findCommentByPageAjax" })
	@ResponseBody
	public Object findCommentByPage(Long articleID, Integer page, Integer rows, HttpServletRequest request,
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
			List<Comment> comments = commentService.findCommentByPage(articleID, page, rows, false);
			if (comments.size() == 0)
			{
				throw new Exception("结果为空");
			}
			List<CommentMessage> result = new ArrayList<>();
			for (Comment comment : comments)
			{
				if (comment.getIsUse() == 1)
				{
					result.add(new CommentMessage(comment));
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
}
