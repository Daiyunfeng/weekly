package pers.hjc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pers.hjc.annotation.FormToken;

@Controller
public class PageController
{
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@RequestMapping({ "/element" })
	public String elementListPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "element";
	}
	
	@RequestMapping("/errors/error")
	public String errorPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "/errors、error";
	}

	@RequestMapping("/errors/invalidRequest")
	public String invalidRequestPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "/errors/invalidRequest";
	}

	@RequestMapping({ "/tourist" })
	public String touristPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "tourist/tourist";
	}
	
	@RequestMapping({ "/tourist/nav" })
	public String touristNavPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "tourist/nav";
	}
	
	@RequestMapping({ "/" })
	public String indexPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "index";
	}

	@RequestMapping({ "/index" })
	public String indexPage2(HttpServletRequest request, HttpServletResponse response)
	{
		return "index";
	}

	@RequestMapping({ "/nav" })
	public String navPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "nav";
	}

	@RequestMapping({ "/include/home" })
	public String homePage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/home";
	}
	
	@RequestMapping({ "/include/people" })
	public String peoplePage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/people";
	}

	@RequestMapping({ "/include/photos" })
	public String photosPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/photos";
	}

	@RequestMapping({ "/include/publications" })
	public String publicationsPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/publications";
	}

	@RequestMapping({ "/include/teaching" })
	public String teachingPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/teaching";
	}

	@RequestMapping({ "/include/research" })
	public String researchPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "include/research";
	}

	@RequestMapping({ "/admin/nav" })
	public String navAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/nav";
	}

	@RequestMapping({ "/list" })
	public String listPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "list";
	}

	@RequestMapping({ "/list/{id}" })
	public String listIDPage(@PathVariable String id, RedirectAttributes attr, HttpServletRequest request,
			HttpServletResponse response)
	{
		attr.addFlashAttribute("ID", id);
		return "redirect:/list";
	}

	@RequestMapping({ "/userList" })
	public String userListPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "userList";
	}

	@RequestMapping({ "/addArticle" })
	@FormToken(produce = true)
	public String addArticlePage(HttpServletRequest request, HttpServletResponse response)
	{
		return "addArticle";
	}

	@RequestMapping({ "/admin" })
	public String indexAdminPage0(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/index";
	}

	@RequestMapping({ "/admin/" })
	public String indexAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/index";
	}

	@RequestMapping({ "/admin/index" })
	public String indexAdminPage2(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/index";
	}

	@RequestMapping({ "/admin/list" })
	public String listAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/list";
	}

	@RequestMapping({ "/admin/list/{id}" })
	public String listIDAdminPage(@PathVariable String id, RedirectAttributes attr, HttpServletRequest request,
			HttpServletResponse response)
	{
		attr.addFlashAttribute("ID", id);
		return "redirect:/admin/list";
	}

	@RequestMapping({ "/admin/editPublication" })
	public String editPublicationAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/editPublication";
	}
	
	@RequestMapping({ "/admin/editResearch" })
	public String editResearchAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/editResearch";
	}
	
	@RequestMapping({ "/admin/editTeaching" })
	public String editTeachingAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/editTeaching";
	}

	@RequestMapping({ "/admin/editPhotos" })
	public String editPhotosAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/editPhotos";
	}

	@RequestMapping({ "/admin/editPeople" })
	public String editPeopleAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/editPeople";
	}

	@RequestMapping({ "/admin/userList" })
	public String userListAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/userList";
	}

	@RequestMapping({ "/admin/addArticle" })
	@FormToken(produce = true)
	public String addArticleAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/addArticle";
	}
}
