/*
 * @(#)UnnamerCollectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月08日 10点19分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.unnameCollect.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.unnameCollect.domain.UnnamerCollect;
import com.infolion.xdss3.unnameCollect.service.UnnamerCollectService;
import com.infolion.xdss3.unnameCollect.dao.UnnamerCollectHibernateDao;
import com.infolion.xdss3.unnameCollect.dao.UnnamerCollectJdbcDao;
import com.infolion.xdss3.unnameCollectGen.service.UnnamerCollectServiceGen;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 未明户收款(UnnamerCollect)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class UnnamerCollectService extends UnnamerCollectServiceGen {
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;

	/**
	 * @param sysDeptJdbcDao
	 *            the sysDeptJdbcDao to set
	 */
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}

	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}
	
	@Autowired
	private VoucherHibernateDao voucherHibernateDao;

	/**
	 * @param voucherHibernateDao
	 *            the voucherHibernateDao to set
	 */
	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao) {
		this.voucherHibernateDao = voucherHibernateDao;
	}

	@Autowired
	private UnnamerCollectJdbcDao unnamerCollectJdbcDao;

	public VoucherHibernateDao getVoucherHibernateDao() {
		return voucherHibernateDao;
	}

	public UnnamerCollectJdbcDao getUnnamerCollectJdbcDao() {
		return unnamerCollectJdbcDao;
	}

	public VoucherJdbcDao getVoucherJdbcDao() {
		return voucherJdbcDao;
	}

	/**
	 * @param unnamerCollectJdbcDao
	 *            the unnamerCollectJdbcDao to set
	 */
	public void setUnnamerCollectJdbcDao(
			UnnamerCollectJdbcDao unnamerCollectJdbcDao) {
		this.unnamerCollectJdbcDao = unnamerCollectJdbcDao;
	}

	@Autowired
	private VoucherJdbcDao voucherJdbcDao;
	
	/**
	 * @param voucherJdbcDao the voucherJdbcDao to set
	 */
	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao) {
		this.voucherJdbcDao = voucherJdbcDao;
	}
	
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID) {
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}

	/**
	 * 保存凭证预览信息
	 * 
	 * @param unnamerCollect
	 * @return
	 */
	public String saveVoucher(UnnamerCollect unnamerCollect) {
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String bukrs = getCompanyIDByDeptID(unnamerCollect.getDeptid());
		/**
		 * @创建作者：邱杰烜
		 * @创建时间：2010-08-18
		 * 若凭证NO已经生成，则返回凭证ID；否则将该未生成NO的凭证删除
		 */
		/************************************************************************/
		String voucherId = this.voucherJdbcDao.getVoucherId(unnamerCollect.getUnnamercollectid());
		if(!StringUtils.isNullBlank(voucherId)){
			return voucherId;
		}else{
			// 保存前先删除 
			this.voucherJdbcDao.deleteVoucherByBusinessid(unnamerCollect.getUnnamercollectid());
		}
		/************************************************************************/
		
		Voucher voucher = new Voucher();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();

		/**
		 * 凭证抬头
		 */
		voucher.setBusinessid(unnamerCollect.getUnnamercollectid());
		voucher.setBusinesstype("07"); // 07： 未明户收款
		voucher.setCheckdate(unnamerCollect.getAccountdate().replace("-", "")); // 过账日期

		voucher.setCompanycode("2100"); // 公司代码

		voucher.setCurrency(unnamerCollect.getCurrency()); // 币别
		voucher.setExchangerate(unnamerCollect.getCollectrate()); // 退款汇率
		voucher.setFiperiod(unnamerCollect.getAccountdate().substring(5, 7));// 会计期间
		voucher.setFiyear(unnamerCollect.getAccountdate().substring(0, 4)); // 会计年度

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date())); // 输入日期

		//输入者
		voucher.setImporter(userContext.getUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getUser().getUserName());
		voucher
				.setVoucherdate(unnamerCollect.getVoucherdate()
						.replace("-", "")); // 凭证日期
		voucher.setVouchertext(unnamerCollect.getText()); // 凭证抬头文本
		voucher.setVouchertype("KR");
		
		/**
		 * 凭证行项目
		 */
		VoucherItem voucherItem1 = new VoucherItem();
		voucherItem1.setRownumber("1");
		voucherItem1.setCheckcode("40");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(unnamerCollect.getCollectbanksbj(),bukrs);
		voucherItem1.setSubject(unnamerCollect.getCollectbanksbj());
		voucherItem1.setSubjectdescribe(subjectdesc);
		//统驭项目
		voucherItem1.setControlaccount(unnamerCollect.getCollectbanksbj());
		//统驭科目说明
		voucherItem1.setCaremark(subjectdesc);
		voucherItem1.setCurrency(unnamerCollect.getCurrency());
		voucherItem1.setAmount(unnamerCollect.getApplyamount());
		voucherItem1.setAmount2(unnamerCollect.getApplyamount2());
		voucherItem1.setDepid("2199");
		voucherItem1.setText(unnamerCollect.getText());
		voucherItem1.setCashflowitem(unnamerCollect.getCashflowitem());
		voucherItem1.setDebitcredit("S");
		voucherItem1.setVoucher(voucher);
		voucherItemList.add(voucherItem1);

		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setRownumber("2");
		voucherItem2.setCheckcode("31");

		String vendor = ""; // 测试系统用30000005
		/**
		 * 人民币
		 */
		if (unnamerCollect.getCurrency().equals("CNY")) {
			vendor = "0040000244"; // 实际生成系统用：0040000244
		}
		/**
		 * 外币
		 */
		else {
			vendor = "0050000208"; // 实际生成系统：0050000208
		}
		voucherItem2.setSubject(vendor); // 科目（供应商）
		voucherItem2.setSubjectdescribe(this.unnamerCollectJdbcDao.getVendorDescById(vendor));// 科目名称（供应商名称）
		String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(vendor,bukrs);
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem2,subject);
		//统驭项目
		voucherItem2.setControlaccount(subject);
		//统驭科目说明
		voucherItem2.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		voucherItem2.setCurrency(unnamerCollect.getCurrency());
		voucherItem2.setAmount(unnamerCollect.getApplyamount());
		voucherItem2.setAmount2(unnamerCollect.getApplyamount2());
		voucherItem2.setDepid("2199");
		voucherItem2.setText(unnamerCollect.getText());
		voucherItem2.setDebitcredit("H");

		voucherItem2.setVoucher(voucher);
		voucherItemList.add(voucherItem2);

		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		return voucher.getVoucherid();
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param unnamerCollect
	 */
	public void _saveOrUpdate(UnnamerCollect unnamerCollect,
			BusinessObject businessObject) {
		
		/**
		 * 添加代码开始：获取银行ID，银行科目
		 */
		Map<String,String> retMap = this.unnamerCollectJdbcDao.getBankIdAndSubject(unnamerCollect.getCollectbankname(),
				unnamerCollect.getCollcetbankacc());
		
		if(retMap != null)
		{
			unnamerCollect.setCollectbankid(retMap.get("bankid"));
			unnamerCollect.setCollectbanksbj(retMap.get("bankSubject"));
		}
		/**
		 * 添加代码结束
		 */
		
		
		if (StringUtils.isNullBlank(unnamerCollect.getUnnamercollectno())) {
			String unnamercollectno = NumberService.getNextObjectNumber(
					"UnnamerCollectNo", unnamerCollect);
			unnamerCollect.setUnnamercollectno(unnamercollectno);
		}
		if (StringUtils.isNullBlank(unnamerCollect.getUnnamercollectid())) {
			_save(unnamerCollect);
		} else {
			_update(unnamerCollect, businessObject);
		}
	}

}