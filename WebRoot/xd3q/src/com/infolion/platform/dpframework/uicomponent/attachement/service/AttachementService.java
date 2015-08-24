/**
 * @(#) AttachementService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：May 14, 2009 1:04:05 PM
 *    描　述：创建
 */

package com.infolion.platform.dpframework.uicomponent.attachement.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.component.fileProcess.domain.TAttachementLog;
import com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.Constants;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementHibernateDao;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 业务附件服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class AttachementService extends BaseService
{
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private AttachementJdbcDao attachementJdbcDao;

	public void setAttachementJdbcDao(AttachementJdbcDao attachementJdbcDao)
	{
		this.attachementJdbcDao = attachementJdbcDao;
	}

	@Autowired
	private AttachementHibernateDao attachementHibernateDao;

	public void setAttachementHibernateDao(AttachementHibernateDao attachementHibernateDao)
	{
		this.attachementHibernateDao = attachementHibernateDao;
	}

	/**
	 * 保持附件信息，同时根据文件存放规则，上传附件文件
	 * 
	 * @param attachementBusiness
	 */
	public void saveAndUploadAttachement(Attachement attachement)
	{
		this.attachementHibernateDao.save(attachement);
	}

	/**
	 * @param attachement
	 * @return
	 */
	public File download(Attachement attachement)
	{
		return null;
	}

	/**
	 * 删除附件
	 * 
	 * @param attachementId
	 * @return
	 */
	public boolean deleteAttachement(String attachementIdList,String userId)
	{
		boolean isPass = false;
		Integer n = 0;

		attachementIdList = attachementIdList.substring(0, attachementIdList.length() - 1);
		n = this.attachementJdbcDao.delete(attachementIdList,userId);

		log.debug("删除的附件ID列表:" + attachementIdList);

		if (n > 0)
		{
			isPass = true;
		}

		return isPass;
	}

	/**
	 * 根据业务ID，删除业务附件
	 * 
	 * @param businessId
	 */
	public void deleteByBusinessId(String businessId)
	{
		this.attachementJdbcDao.deleteByBusinessId(businessId);
	}

	/**
	 * 新增一笔业务附件。
	 * 
	 * @param filepath
	 * @param description
	 * @param boName
	 * @param boProperty
	 *            TODO
	 * @param boId
	 * @param multipartFile
	 * 
	 * @return
	 */
	public boolean addAttachement(Attachement attachement, String filepath, String description, String boName, String boProperty, String boId, MultipartFile multipartFile)
	{
		boolean isPass = false;

		// 建立时间
		String createTime = "";
		// 建立者
		String creator = "";
		// 最后修改者
		String lastModifyer = "";
		// 最后修改时间
		String lastModifyTime = "";
		String attachementId = "";
		String client = UserContextHolder.getLocalUserContext().getUserContext().getClient();
		String fileName = "";
		byte[] fileStore;

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		creator = userContext.getUser().getUserId();
		createTime = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		lastModifyer = creator;
		lastModifyTime = createTime;

		try
		{
			attachementId = CodeGenerator.getUUID();

			fileStore = multipartFile.getBytes();
			fileName = multipartFile.getOriginalFilename();
			// long fileSize = multipartFile.getSize();
			// Object oo = (Object) fileSize;

			log.debug("上传文件:" + fileName);
			// System.out.println("fileStore:" + fileStore.toString());

			attachement.setBusinessId(boId);
			attachement.setBoName(boName);
			attachement.setBoProperty(boProperty);
			attachement.setAttachementId(attachementId);
			attachement.setCreateTime(createTime);
			attachement.setCreator(creator);
			attachement.setDescription(StringUtils.isBlank(description) ? null : description);
			attachement.setFileName(fileName);
			// attachement.setFileStore(fileStore);

			attachement.setClient(client);
			attachement.setLastModifyer(lastModifyer);
			attachement.setLastModifyTime(lastModifyTime);

			// LJX 20100811 Modify 不使用Hibernate进行保存，改用JDBC操作BLOB
			// this.attachementHibernateDao.save(attachement);
			this.attachementJdbcDao.updateAttachement(attachement, fileStore);
			isPass = true;

		}
		catch (IOException e)
		{
			isPass = false;
			e.printStackTrace();
		}

		return isPass;
	}

	public boolean addAttachement(Attachement attachement, String filepath, String filename, String description, String boName, String boProperty, String boId, MultipartFile multipartFile,String userId)
	{
		boolean isPass = false;

		// 建立时间
		String createTime = "";
		// 最后修改时间
		String lastModifyTime = "";
		String attachementId = "";
		//String client = UserContextHolder.getLocalUserContext().getUserContext().getClient();

		createTime = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		 
		lastModifyTime = createTime;

		attachementId = CodeGenerator.getUUID();

		// long fileSize = multipartFile.getSize();
		// Object oo = (Object) fileSize;

		log.debug("上传文件:" + filename);
		// System.out.println("fileStore:" + fileStore.toString());

		attachement.setBusinessId(boId);
		attachement.setBoName(boName);
		attachement.setBoProperty(boProperty);
		attachement.setAttachementId(attachementId);
		attachement.setCreateTime(createTime);
		attachement.setCreator(userId);
		attachement.setDescription(StringUtils.isBlank(description) ? null : description);
		attachement.setFileName(filename);
		// attachement.setFileStore(fileStore);

		attachement.setClient("800");
		attachement.setLastModifyer(userId);
		attachement.setLastModifyTime(lastModifyTime);

		// LJX 20100811 Modify 不使用Hibernate进行保存，改用JDBC操作BLOB
		// this.attachementHibernateDao.save(attachement);
		this.attachementJdbcDao.updateAttachement(attachement);
		isPass = true;


		return isPass;
	}
	
	
	public boolean addAttachementMoidfy(Attachement attachement, String filepath, String filename, String description, String boName, String boProperty, String boId, MultipartFile multipartFile,String userId,String modifyAddTime)
	{
		boolean isPass = false;


		String lastModifyTime = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		 
		String attachementId = CodeGenerator.getUUID();

		// long fileSize = multipartFile.getSize();
		// Object oo = (Object) fileSize;

		log.debug("上传文件:" + filename);
		// System.out.println("fileStore:" + fileStore.toString());

		attachement.setBusinessId(boId);
		attachement.setBoName(boName);
		attachement.setBoProperty(boProperty);
		attachement.setAttachementId(attachementId);
		attachement.setCreateTime(modifyAddTime);
		attachement.setCreator(userId);
		attachement.setDescription(StringUtils.isBlank(description) ? null : description);
		attachement.setFileName(filename);

		attachement.setClient("800");
		attachement.setLastModifyer(userId);
		attachement.setLastModifyTime(lastModifyTime);
		this.attachementJdbcDao.updateAttachement(attachement);
		
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		TAttachementLog log = new TAttachementLog();
		log.setAttachementBusinessId(boId);
		log.setAttachementCmd(attachement.getDescription());
		log.setAttachementId(attachementId);
		log.setAttachementLogId(CodeGenerator.getUUID());
		log.setCmd("三期");
		log.setCreator(userId);
		log.setCreatorTime(lastModifyTime);
		log.setOperateTime(lastModifyTime);
		log.setOperater(userId);
		log.setMack("1");
		log.setNewAttachementCmd(attachement.getDescription());
		log.setNewOldName(filename);
		log.setNewReadName(filename);
		abs.attachementLogRecord(log);
		
		isPass = true;
		return isPass;
	}
	
	
	public boolean addAttachementReplace(Attachement at, String filename, String description,String userId)
	{
		boolean isPass = false;
		
		// 建立时间
		String currencyTime = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		TAttachementLog log = new TAttachementLog();
		log.setAttachementBusinessId(at.getBusinessId());
		log.setAttachementCmd(at.getDescription());
		log.setAttachementId(at.getAttachementId());
		log.setAttachementLogId(CodeGenerator.getUUID());
		log.setCmd("三期");
		log.setCreator(at.getCreator());
		log.setCreatorTime(at.getCreateTime());
		log.setMack("2");
		log.setNewAttachementCmd(description);
		log.setNewOldName(filename);
		log.setNewReadName(filename);
		log.setReadName(at.getFileName());
		log.setOldName(at.getFileName());
		log.setOperater(userId);
		log.setOperateTime(currencyTime);
		abs.attachementLogRecord(log);
		
		at.setFileName(filename);
		at.setDescription(description);
		at.setLastModifyTime(currencyTime);
		at.setCreator(userId);
		at.setLastModifyer(userId);		
		this.attachementHibernateDao.update(at);
		
		isPass = true;


		return isPass;
	}
	
	
	public boolean deleteAttachementModify(String attachementIdList,String userId)
	{

		attachementIdList = attachementIdList.substring(0, attachementIdList.length() - 1);
		String[] ids = attachementIdList.split(",");
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		String currencyTime = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		for(String id : ids){
			Attachement at = attachementHibernateDao.get(id);
			
			TAttachementLog log = new TAttachementLog();
			log.setAttachementBusinessId(at.getBusinessId());
			log.setAttachementCmd(at.getDescription());
			log.setAttachementId(at.getAttachementId());
			log.setAttachementLogId(CodeGenerator.getUUID());
			log.setCmd("三期");
			log.setCreator(at.getCreator());
			log.setCreatorTime(at.getCreateTime());
			log.setMack("3");
			log.setOperater(userId);
			log.setOperateTime(currencyTime);
			log.setOldName(at.getFileName());
			log.setReadName(at.getFileName());
			abs.attachementLogRecord(log);
			
			attachementHibernateDao.remove(at);
		}

		log.debug("删除的附件ID列表:" + attachementIdList);
		return true;
	}
	
	/**
	 * 上传业务附件
	 * 
	 * @param filepath
	 * @param description
	 * @param boName
	 * @param boProperty
	 *            TODO
	 * @param boId
	 * @param multipartFile
	 * @param attachementBusinessId
	 * 
	 * @return
	 */
	public JSONObject uploadAttachement(String filepath, String description, String boName, String boProperty, String boId, MultipartFile multipartFile)
	{
		JSONObject jo = new JSONObject();
		// String strResponseWrite ="";
		boolean isPass = false; // 上传是否成功

		Attachement attachement = new Attachement();
		isPass = this.addAttachement(attachement, filepath, description, boName, boProperty, boId, multipartFile);
		String fileName = multipartFile.getOriginalFilename();
		String msg = "";
		if (isPass)
		{
			// 文件&gt;&1&lt;上传成功！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadSuccess, fileName);
			jo.put("success", "true");
			jo.put("attachement", JSONArray.fromObject(attachement));
			jo.put("msg", msg);
		}
		else
		{
			// 文件&gt;&1&lt;上传失敗！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadFailure, fileName);
			jo.put("success", "false");
			jo.put("msg", msg);
		}

		return jo;
	}

	public JSONObject uploadAttachement(String filepath, String filename, String description, String boName, String boProperty, String boId, MultipartFile multipartFile,String userId)
	{
		JSONObject jo = new JSONObject();
		// String strResponseWrite ="";
		boolean isPass = false; // 上传是否成功

		Attachement attachement = new Attachement();
		isPass = this.addAttachement(attachement, filepath, filename, description, boName, boProperty, boId, multipartFile,userId);
		String msg = "";
		filename = filename.replace("\\", "\\\\");
		if (isPass)
		{
			// 文件&gt;&1&lt;上传成功！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadSuccess, filename);
			jo.put("success", "true");
			jo.put("attachement", JSONArray.fromObject(attachement));
			jo.put("msg", msg);
		}
		else
		{
			// 文件&gt;&1&lt;上传失敗！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadFailure, filename);
			jo.put("success", "false");
			jo.put("msg", msg);
		}

		return jo;
	}
	
	public JSONObject uploadAttachementModify(String filepath, String filename, String description, String boName, String boProperty, String boId, MultipartFile multipartFile,String userId,String modifyAddTime)
	{
		JSONObject jo = new JSONObject();
		// String strResponseWrite ="";
		boolean isPass = false; // 上传是否成功

		Attachement attachement = new Attachement();
		isPass = this.addAttachementMoidfy(attachement, filepath, filename, description, boName, boProperty, boId, multipartFile,userId,modifyAddTime);
		String msg = "";
		filename = filename.replace("\\", "\\\\");
		if (isPass)
		{
			// 文件&gt;&1&lt;上传成功！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadSuccess, filename);
			jo.put("success", "true");
			jo.put("attachement", JSONArray.fromObject(attachement));
			jo.put("msg", msg);
		}
		else
		{
			// 文件&gt;&1&lt;上传失敗！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadFailure, filename);
			jo.put("success", "false");
			jo.put("msg", msg);
		}

		return jo;
	}
	
	
	public JSONObject uploadAttachementReplace(Attachement at,String filename,String description, String userId)
	{
		JSONObject jo = new JSONObject();
		// String strResponseWrite ="";
		boolean isPass = false; // 上传是否成功

		isPass = this.addAttachementReplace(at, filename, description, userId);
		String msg = "";
		filename = filename.replace("\\", "\\\\");
		if (isPass)
		{
			// 文件&gt;&1&lt;上传成功！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadSuccess, filename);
			jo.put("success", "true");
			jo.put("attachement", JSONArray.fromObject(at));
			jo.put("msg", msg);
		}
		else
		{
			// 文件&gt;&1&lt;上传失敗！
			msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadFailure, filename);
			jo.put("success", "false");
			jo.put("msg", msg);
		}

		return jo;
	}
	
	/**
	 * 下载业务附件
	 * 
	 * @param attachementId
	 */
	public Attachement download(String attachementId)
	{
		Attachement attachement;
		attachement = this.attachementHibernateDao.get(attachementId);

		return attachement;
	}

	/**
	 * 获取附件
	 * 
	 * @param attachementId
	 */
	public Attachement getAttachement(String businessId)
	{
		String hql = "from Attachement where businessId = ?";
		List<Attachement> list = attachementHibernateDao.getHibernateTemplate().find(hql, new Object[] { businessId });
		if (list.size() > 0)
		{
			return (Attachement) list.get(0);
		}
		else
		{
			return null;
		}

	}
	
	/**
	 * 获取附件
	 * 
	 * @param attachementId
	 */
	public Attachement getAttachement(String businessId,String boname)
	{
		String hql = "from Attachement where businessId = ? and boname = ? ";
		List<Attachement> list = attachementHibernateDao.getHibernateTemplate().find(hql, new Object[] { businessId ,boname});
		if (list.size() > 0)
		{
			return (Attachement) list.get(0);
		}
		else
		{
			return null;
		}

	}
	
	public Attachement getAttachementById(String id){		
		return attachementHibernateDao.get(id);
	}
	
}
