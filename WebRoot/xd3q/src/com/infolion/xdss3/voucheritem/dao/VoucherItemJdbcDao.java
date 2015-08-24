/*
 * @(#)SupplierRefundItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：邱荣发
 *  时　间：2010-6-22
 *  描　述：创建
 */

package com.infolion.xdss3.voucheritem.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

@Repository
public class VoucherItemJdbcDao extends BaseJdbcDao
{
	public List<VoucherItem> getVoucherItemList(String businessid)
	{
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and trim(a.businessitemid) is not null";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	   public List<VoucherItem> getVoucherItemList2(String businessid)
	    {
	        List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
	        String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and glflag = 'W'";
	        log.debug("根据业务ID取得凭证明细:" + strSql);
	        
	        List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
	        for (Map map : rowList)
	        {
	            VoucherItem voucherItem = new VoucherItem();
	            ExBeanUtils.setBeanValueFromMap(voucherItem, map);
	            Voucher voucher = new Voucher();
	            ExBeanUtils.setBeanValueFromMap(voucher, map);
	            voucherItem.setVoucher(voucher);
	            voucherItemList.add(voucherItem);
	        }

	        return voucherItemList;
	    }
	   
	public List<VoucherItem> getVoucherItemListById(String voucherid)
	{
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.voucherid = '"+voucherid+"' and trim(a.businessitemid) is not null";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	public List<VoucherItem> getVoucherItemById(String voucherid)
	{
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.voucherid = '"+voucherid+"'";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	/**
	 * @param businessid
	 * @param businesstype  业务类型
	 * @return
	 */
	public List<VoucherItem> getVoucherItemList(String businessid, String businesstype)
	{
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "select a.*,b.* from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and b.businesstype = '"+businesstype+"'";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			
			//changed by chenfei at 2011-1-10
//			if(voucherItem.getVoucherno() == null || "".equals(voucherItem.getVoucherno().trim())){
//				String sql = "select b.voucherno from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and b.businesstype = '"+businesstype+"'";
//				List<String> lst = this.getJdbcTemplate().queryForList(sql);
//				if(lst != null && !lst.isEmpty())
//					voucherItem.setVoucherno(lst.get(0));
//			}
			
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	public List<VoucherItem> getVoucherItemList2(String businessid, String businesstype, String voucherclass)
	{
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and b.businesstype = '"+businesstype+"' and b.voucherclass = '"+voucherclass+"'";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	public Set<VoucherItem> getVoucherItemSet(String businessid, String businesstype, String voucherclass)
	{
		Set<VoucherItem> voucherItemSet = new HashSet();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid+"' and b.businesstype = '"+businesstype+"' and b.voucherclass = '"+voucherclass+"'";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemSet.add(voucherItem);
		}

		return voucherItemSet;
	}
	
	/**
	 * 根据合同取得凭证明细
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public List getVoucherItemListByContract(String contractnos)
	{
		String strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.businessitemid in (select collectitemid from ycollectitem where contract_no in ("+contractnos+"))";
		log.debug("根据合同取得凭证明细:" + strSql);
		
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	/**
	 * 根据合同取得凭证明细
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public List getPayVoucherItemListByContract(String contractnos)
	{
		String strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.businessitemid in (select paymentitemid from ypaymentitem where contract_no in ("+contractnos+"))";
		log.debug("根据合同取得凭证明细:" + strSql);
		
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	public List getRefundVoucherItemListByContract(String contractnos)
	{
		String strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.businessitemid in (select refundmentitemid from yrefundmentitem where contract_no in ("+contractnos+"))";
		log.debug("根据合同取得凭证明细:" + strSql);
		
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	/**
	 * 根据立项取得凭证明细
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public List getVoucherItemListByProject(String projectnos)
	{
		String strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.businessitemid in (select collectitemid from ycollectitem where project_no in ("+projectnos+"))";
		log.debug("根据立项取得凭证明细:" + strSql);
			
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	/**
	 * 根据客户取得凭证明细
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public List getVoucherItemListByCustomer(String dept_id, String customer)
	{
		String strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.businessitemid in (select a.collectitemid from ycollectitem a,ycollect b where a.collectid = b.collectid and b.dept_id = '"+dept_id+"' and b.customer = '"+customer+"')";
		log.debug("根据客户取得凭证明细:" + strSql);
			
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}

		return voucherItemList;
	}
	
	public boolean isVoucherItemExist(VoucherItem voucherItem)
	{
		String strSql = "";
		if(!StringUtils.isNullBlank(voucherItem.getCostcenter())||!StringUtils.isNullBlank(voucherItem.getProfitcenter()))return false;
		if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
			if(StringUtils.isNullBlank(voucherItem.getCheckcode())){
				return false;
			}else{
				strSql = "select count(*) from YVOUCHERITEM where CHECKCODE='" + voucherItem.getCheckcode() + "'" +
				" and SUBJECT='" + voucherItem.getSubject() + "'" +
				" and AMOUNT='" + voucherItem.getAmount() + "'" +
				" and CASHFLOWITEM='" + voucherItem.getCashflowitem() + "'" +
				" and VOUCHERID in (select VOUCHERID from YVOUCHER where BUSINESSID ='" + voucherItem.getVoucher().getBusinessid() + "')" +
				" and VOUCHERID <> '" + voucherItem.getVoucher().getVoucherid() + "'" +
				" and VOUCHERITEMID <> '" + voucherItem.getVoucheritemid() + "'" +
				" and (trim(costcenter) is null and trim(profitcenter) is null)";
			}
		}else{
			strSql = "select count(*) from YVOUCHERITEM where VOUCHERNO='" + voucherItem.getVoucherno() + "'" +
			" and VOUCHERID in (select VOUCHERID from YVOUCHER where BUSINESSID ='" + voucherItem.getVoucher().getBusinessid() + "')" +
			" and VOUCHERITEMID <> '" + voucherItem.getVoucheritemid() + "'";
		}

		int i = this.getJdbcTemplate().queryForInt(strSql);
		if (i > 0)
			return true;
		else
			return false;
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建日期：2011-05-30 
     * 判断贴现/议付凭证是否已经存在
     * 每当要判断本次凭证是否重复生成并删除时，只对凭证抬头“参考码”为空（TRIM(REFERENCECODE) IS NULL）的凭证进行比较与删除。
     */
	public boolean isDisNegVoucherItemExist(VoucherItem voucherItem)
	{
		String strSql = "";
		if(!StringUtils.isNullBlank(voucherItem.getCostcenter())||!StringUtils.isNullBlank(voucherItem.getProfitcenter()))return false;
		if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
			if(StringUtils.isNullBlank(voucherItem.getCheckcode())){
				return false;
			}else{
				strSql = "select count(*) from YVOUCHERITEM where CHECKCODE='" + voucherItem.getCheckcode() + "'" +
				" and SUBJECT='" + voucherItem.getSubject() + "'" +
				" and AMOUNT='" + voucherItem.getAmount() + "'" +
				" and CASHFLOWITEM='" + voucherItem.getCashflowitem() + "'" +
				" and VOUCHERID in (select VOUCHERID from YVOUCHER where TRIM(REFERENCECODE) IS NULL AND BUSINESSID ='" + voucherItem.getVoucher().getBusinessid() + "')" +
				" and VOUCHERID <> '" + voucherItem.getVoucher().getVoucherid() + "'" +
				" and VOUCHERITEMID <> '" + voucherItem.getVoucheritemid() + "'" +
				" and (trim(costcenter) is null and trim(profitcenter) is null)";
			}
		}else{
			strSql = "select count(*) from YVOUCHERITEM where VOUCHERNO='" + voucherItem.getVoucherno() + "'" +
			" and VOUCHERID in (select VOUCHERID from YVOUCHER where TRIM(REFERENCECODE) IS NULL AND BUSINESSID ='" + voucherItem.getVoucher().getBusinessid() + "')" +
			" and VOUCHERITEMID <> '" + voucherItem.getVoucheritemid() + "'";
		}

		int i = this.getJdbcTemplate().queryForInt(strSql);
		if (i > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 判断在别一凭证中是否存在相同记录
	 * @param voucherItem
	 * @return
	 */
	public boolean isVoucherItemExistInVoucherGroup(VoucherItem voucherItem, String compVoucherId)
	{
	    String strSql = "";
        strSql = "select count(*) from YVOUCHERITEM where CHECKCODE='" + voucherItem.getCheckcode() + "'" +
                        " and SUBJECT='" + voucherItem.getSubject() + "'" +
                        " and AMOUNT='" + voucherItem.getAmount() + "'" +
                        " and CASHFLOWITEM='" + voucherItem.getCashflowitem() + "'" +
//                        " and TRIM(TO_CHAR(TRIM(ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('"+voucherItem.getRownumber()+"'),'000')) " +
                        " and checkcode='" + voucherItem.getCheckcode() + "'" +
                        " and voucherid = '" + compVoucherId + "'";
	    int i = this.getJdbcTemplate().queryForInt(strSql);
	    if (i > 0)
	        return true;
	    else
	        return false;
	}
	
	public VoucherItem getVoucherItem(String businessItemid,String voucherclass)
	{
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and a.businessitemid = '"+businessItemid + "' and b.voucherclass in ('"+voucherclass2+"')";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);			
			return voucherItem;
		}
		return null;
	}
	/**
	 * 通过 科目号，合同号或立项号得到voucheritem
	 * @param subject
	 * @param taxCode
	 * @return
	 */
	public List<VoucherItem> getVoucherItems(String subject ,String taxCode){
		List<VoucherItem> voucherItems =new ArrayList<VoucherItem>();
		String strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and a.subject = '"+subject + "' and a.taxcode = '"+taxCode+"' and trim(b.voucherno) is not null"; 
		//判断是否是立项，立项为6为数
		if(null != taxCode && taxCode.length()==6){
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and a.subject = '"+subject + "' and a.taxcode like ('"+taxCode+"%') and trim(b.voucherno) is not null";
		}
		////判断是否是客户层，taxCode 为不为空的字符串
		if("".equals(taxCode.trim())){
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and a.subject = '"+subject + "' and trim(a.taxcode) is not null  and trim(b.voucherno) is not null";
		}
		
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);			
			voucherItems.add(voucherItem);
		}
		return voucherItems;
	}
	/**
	 * 取得之前的所有的清帐凭证的行项目
	 * @param businessitemid
	 * @return
	 */
	public List<VoucherItem> getVoucherItems(String businessid){
		List<VoucherItem> voucherItems =new ArrayList<VoucherItem>();
		String strSql = "select b.*,a.* from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '"+businessid + "' and trim(a.subject) is null";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);			
			voucherItems.add(voucherItem);
		}
		return voucherItems;
	}
	
	/**
	 * 取得BUSINESSITEMID不为空的凭证明细
	 */
    public List<Map<String, String>> getRelateVoucherItems(String businessid){
        List<VoucherItem> voucherItems = new ArrayList<VoucherItem>();
        String strSql = "select b.BUSINESSID, b.BUSINESSTYPE, a.BUSINESSITEMID from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.businessid = '" + businessid
                        + "' and trim(a.businessitemid) is not null";
        log.debug("根据业务ID取得凭证明细:" + strSql);

        return this.getJdbcTemplate().queryForList(strSql);
    }
	
	/**
	 * 根据凭证号、公司代码、会计年度取得业务ID,rowNumber行项目
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public String getBusinessId(String voucherNo, String companyCode, String fiYear,String rowNumber)
	{
		//rowNumber 有时是001，有时会是1，而数据表存的是1，所以去0 ,去掉前导0
		String rowNumber2 = rowNumber.replaceAll("^0+(?!$)", "");
		
		String businessId = "";		
		String strSql = "select  vi.businessitemid  from YVOUCHER v left join  YVOUCHERITEM vi on  v.voucherid = vi.voucherid where v.voucherno='" + voucherNo + "' and v.COMPANYCODE='" + companyCode + "' and v.FIYEAR='" + fiYear + "' and TRIM(TO_CHAR(TRIM(vi.ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('"+rowNumber2+"'),'000')) ";
		log.debug("根据凭证号、公司代码、会计年度取得业务ID:" + strSql);
		if (StringUtils.isNullBlank(voucherNo))
		{
			businessId = "";
		}
		else
		{
			List<String> list = getJdbcTemplate().queryForList(strSql, String.class);
			if (null != list && list.size() == 1)
				businessId = list.get(0);
		}

		return businessId;
	}
	
	/**
	 * 根据凭证号、公司代码、会计年度取得业务ID,rowNumber行项目
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public VoucherItem getVoucherItem(String voucherNo, String companyCode, String fiYear,String rowNumber)
	{
		
		//rowNumber 有时是001，有时会是1，而数据表存的是1，所以去0 ,去掉前导0
		String rowNumber2 = rowNumber.replaceAll("^0+(?!$)", "");
		
		String businessId = "";		
		String strSql = "select  v.*,vi.*  from YVOUCHER v ,  YVOUCHERITEM vi where  v.voucherid = vi.voucherid and v.voucherno='" + voucherNo + "' and v.COMPANYCODE='" + companyCode + "' and v.FIYEAR='" + fiYear + "' and TRIM(TO_CHAR(TRIM(vi.ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('"+rowNumber2+"'),'000'))  ";
		log.debug("根据凭证号、公司代码、会计年度取得业务ID:" + strSql);
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);			
			return voucherItem;
		}
		return null;
		
	}
	
}
