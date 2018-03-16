package pers.hjc.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.hjc.dao.IndexPublicationDao;
import pers.hjc.dao.IndexResearchDao;
import pers.hjc.dao.IndexTeachingDao;
import pers.hjc.dao.IndexUserDao;
import pers.hjc.dao.UserDao;
import pers.hjc.dao.impl.ImageDaoImpl;
import pers.hjc.model.IndexImage;
import pers.hjc.model.IndexPublication;
import pers.hjc.model.IndexResearch;
import pers.hjc.model.IndexTeaching;
import pers.hjc.model.IndexUser;

@Service
public class AdminService
{
	@Autowired
	private ImageDaoImpl imageDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private IndexUserDao indexUserDao;
	
	@Autowired
	private IndexTeachingDao indexTeachingDao;

	@Autowired
	private IndexPublicationDao indexPublicationDao;
	
	@Autowired
	private IndexResearchDao indexResearchDao;

	@Transactional(readOnly = false)
	public Boolean deleteImage(Long imageID) throws Exception
	{
		IndexImage image = imageDao.findImage(imageID);
		if (image == null)
		{
			throw new Exception("图片不存在");
		}
		imageDao.deleteImage(image);
		return true;
	}

	@Transactional(readOnly = true)
	public List<IndexImage> getAllImage() throws Exception
	{
		return imageDao.findAllImage();
	}

	@Transactional(readOnly = true)
	public IndexImage getImageByID(Long ID) throws Exception
	{
		return imageDao.findImage(ID);
	}

	@Transactional(readOnly = false)
	public Boolean updateImage(IndexImage image) throws Exception
	{
		imageDao.updateImage(image);
		return true;
	}

	@Transactional(readOnly = false)
	public Long addImage(IndexImage image) throws Exception
	{
		Serializable imageID = imageDao.addImage(image);
		if (imageID == null)
		{
			throw new Exception("添加首页图片失败" + image.getID() + "    " + image.getImage() + "    " + image.getTitle() + "    "
					+ image.getDescription());
		}
		return Long.parseLong(imageID.toString());
	}

	@Transactional(readOnly = true)
	public IndexUser getIndexUserByID(Long ID) throws Exception
	{
		return indexUserDao.findUser(ID);
	}

	@Transactional(readOnly = false)
	public Boolean updateIndexUser(IndexUser user) throws Exception
	{
		indexUserDao.updateUser(user);
		return true;
	}
	
	@Transactional(readOnly = false)
	public void addUser(IndexUser user) throws Exception
	{
		Serializable userID = indexUserDao.addUser(user);
		if (userID == null)
		{
			throw new Exception("添加首页用户失败");
		}
	}
	
	@Transactional(readOnly = false)
	public Boolean deleteIndexUser(Long userID) throws Exception
	{
		IndexUser user = indexUserDao.findUser(userID);
		if (user == null)
		{
			throw new Exception("首页用户不存在");
		}
		indexUserDao.deleteUser(user);
		return true;
	}
	
	@Transactional(readOnly = true)
	public List<IndexTeaching> getAllTeaching() throws Exception
	{
		return indexTeachingDao.findAllTeaching();
	}

	@Transactional(readOnly = true)
	public IndexTeaching getTeachingByID(Long ID) throws Exception
	{
		return indexTeachingDao.findTeaching(ID);
	}

	@Transactional(readOnly = false)
	public Boolean updateIndexTeaching(IndexTeaching teaching) throws Exception
	{
		indexTeachingDao.updateTeaching(teaching);
		return true;
	}
	
	@Transactional(readOnly = false)
	public Long addIndexTeaching(IndexTeaching teaching) throws Exception
	{
		Serializable teachingID = indexTeachingDao.addTeaching(teaching);
		if (teachingID == null)
		{
			throw new Exception("添加教学记录失败");
		}
		return Long.parseLong(teachingID.toString());
	}
	
	@Transactional(readOnly = false)
	public Boolean deleteIndexTeaching(Long teachingID) throws Exception
	{
		IndexTeaching teaching = indexTeachingDao.findTeaching(teachingID);
		if (teaching == null)
		{
			throw new Exception("此条教学记录不存在");
		}
		teaching.setIsUse(0);
		indexTeachingDao.updateTeaching(teaching);
		return true;
	}
	
	@Transactional(readOnly = true)
	public List<IndexPublication> getAllPublication() throws Exception
	{
		return indexPublicationDao.findAllPublication();
	}

	@Transactional(readOnly = true)
	public IndexPublication getPublicationByID(Long ID) throws Exception
	{
		return indexPublicationDao.findPublication(ID);
	}

	@Transactional(readOnly = false)
	public Boolean updateIndexPublication(IndexPublication publication) throws Exception
	{
		indexPublicationDao.updatePublication(publication);
		return true;
	}
	
	@Transactional(readOnly = false)
	public Long addIndexPublication(IndexPublication publication) throws Exception
	{
		Serializable publicationID = indexPublicationDao.addPublication(publication);
		if (publicationID == null)
		{
			throw new Exception("添加发表论文失败");
		}
		return Long.parseLong(publicationID.toString());
	}
	
	@Transactional(readOnly = false)
	public Boolean deleteIndexPublication(Long publicationID) throws Exception
	{
		IndexPublication publication = indexPublicationDao.findPublication(publicationID);
		if (publication == null)
		{
			throw new Exception("此条发表论文不存在");
		}
		publication.setIsUse(0);
		indexPublicationDao.updatePublication(publication);
		return true;
	}
	
	@Transactional(readOnly = true)
	public List<IndexResearch> getAllResearch() throws Exception
	{
		return indexResearchDao.findAllResearch();
	}

	@Transactional(readOnly = true)
	public IndexResearch getResearchByID(Long ID) throws Exception
	{
		return indexResearchDao.findResearch(ID);
	}

	@Transactional(readOnly = false)
	public Boolean updateIndexResearch(IndexResearch research) throws Exception
	{
		indexResearchDao.updateResearch(research);
		return true;
	}
	
	@Transactional(readOnly = false)
	public Long addIndexResearch(IndexResearch research) throws Exception
	{
		Serializable researchID = indexResearchDao.addResearch(research);
		if (researchID == null)
		{
			throw new Exception("添加研究方向失败");
		}
		return Long.parseLong(researchID.toString());
	}
	
	@Transactional(readOnly = false)
	public Boolean deleteIndexResearch(Long researchID) throws Exception
	{
		IndexResearch research = indexResearchDao.findResearch(researchID);
		if (research == null)
		{
			throw new Exception("此条研究方向不存在");
		}
		research.setIsUse(0);
		indexResearchDao.updateResearch(research);
		return true;
	}
}
