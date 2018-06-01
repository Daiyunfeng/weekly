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
	public void deleteImage(Long imageID) throws Exception
	{
		IndexImage image = imageDao.getById(imageID);
		if (image == null)
		{
			throw new Exception("图片不存在");
		}
		imageDao.delete(image);
	}

	@Transactional(readOnly = true)
	public List<IndexImage> getAllImage() throws Exception
	{
		return imageDao.findAllImage();
	}

	@Transactional(readOnly = true)
	public IndexImage getImageByID(Long ID) throws Exception
	{
		return imageDao.getById(ID);
	}

	@Transactional(readOnly = false)
	public void updateImage(IndexImage image) throws Exception
	{
		imageDao.update(image);
	}

	@Transactional(readOnly = false)
	public Long addImage(IndexImage image) throws Exception
	{
		Serializable imageID = imageDao.save(image);
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
		return indexUserDao.getById(ID);
	}

	@Transactional(readOnly = false)
	public void updateIndexUser(IndexUser user) throws Exception
	{
		indexUserDao.update(user);
	}
	
	@Transactional(readOnly = false)
	public void addUser(IndexUser user) throws Exception
	{
		Serializable userID = indexUserDao.save(user);
		if (userID == null)
		{
			throw new Exception("添加首页用户失败");
		}
	}
	
	@Transactional(readOnly = false)
	public void deleteIndexUser(Long userID) throws Exception
	{
		IndexUser user = indexUserDao.getById(userID);
		if (user == null)
		{
			throw new Exception("首页用户不存在");
		}
		indexUserDao.delete(user);
	}
	
	@Transactional(readOnly = true)
	public List<IndexTeaching> getAllTeaching() throws Exception
	{
		return indexTeachingDao.findAllTeaching();
	}

	@Transactional(readOnly = true)
	public IndexTeaching getTeachingByID(Long ID) throws Exception
	{
		return indexTeachingDao.getById(ID);
	}

	@Transactional(readOnly = false)
	public void updateIndexTeaching(IndexTeaching teaching) throws Exception
	{
		indexTeachingDao.update(teaching);
	}
	
	@Transactional(readOnly = false)
	public Long addIndexTeaching(IndexTeaching teaching) throws Exception
	{
		Serializable teachingID = indexTeachingDao.save(teaching);
		if (teachingID == null)
		{
			throw new Exception("添加教学记录失败");
		}
		return Long.parseLong(teachingID.toString());
	}
	
	@Transactional(readOnly = false)
	public void deleteIndexTeaching(Long teachingID) throws Exception
	{
		IndexTeaching teaching = indexTeachingDao.getById(teachingID);
		if (teaching == null)
		{
			throw new Exception("此条教学记录不存在");
		}
		indexTeachingDao.delete(teaching);
	}
	
	@Transactional(readOnly = true)
	public List<IndexPublication> getAllPublication() throws Exception
	{
		return indexPublicationDao.findAllPublication();
	}

	@Transactional(readOnly = true)
	public IndexPublication getPublicationByID(Long ID) throws Exception
	{
		return indexPublicationDao.getById(ID);
	}

	@Transactional(readOnly = false)
	public void updateIndexPublication(IndexPublication publication) throws Exception
	{
		indexPublicationDao.update(publication);
	}
	
	@Transactional(readOnly = false)
	public Long addIndexPublication(IndexPublication publication) throws Exception
	{
		Serializable publicationID = indexPublicationDao.save(publication);
		if (publicationID == null)
		{
			throw new Exception("添加发表论文失败");
		}
		return Long.parseLong(publicationID.toString());
	}
	
	@Transactional(readOnly = false)
	public void deleteIndexPublication(Long publicationID) throws Exception
	{
		IndexPublication publication = indexPublicationDao.getById(publicationID);
		if (publication == null)
		{
			throw new Exception("此条发表论文不存在");
		}
		indexPublicationDao.delete(publication);
	}
	
	@Transactional(readOnly = true)
	public List<IndexResearch> getAllResearch() throws Exception
	{
		return indexResearchDao.findAllResearch();
	}

	@Transactional(readOnly = true)
	public IndexResearch getResearchByID(Long ID) throws Exception
	{
		return indexResearchDao.getById(ID);
	}

	@Transactional(readOnly = false)
	public void updateIndexResearch(IndexResearch research) throws Exception
	{
		indexResearchDao.update(research);
	}
	
	@Transactional(readOnly = false)
	public Long addIndexResearch(IndexResearch research) throws Exception
	{
		Serializable researchID = indexResearchDao.save(research);
		if (researchID == null)
		{
			throw new Exception("添加研究方向失败");
		}
		return Long.parseLong(researchID.toString());
	}
	
	@Transactional(readOnly = false)
	public void deleteIndexResearch(Long researchID) throws Exception
	{
		IndexResearch research = indexResearchDao.getById(researchID);
		if (research == null)
		{
			throw new Exception("此条研究方向不存在");
		}
		indexResearchDao.delete(research);
	}
}
