/*
 * @(#)SupplierRefundItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：邱荣发
 *  时　间：2010-6-22
 *  描　述：创建
 */

package com.infolion.xdss3.voucher.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

@Repository
public class VoucherJdbcDao extends BaseJdbcDao
{

	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	/**
	 * @param vendorTitleJdbcDao
	 *            the vendorTitleJdbcDao to set
	 */
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}

	@Autowired
	private PaymentItemJdbcDao paymentItemJdbcDao;

	/**
	 * @param paymentItemJdbcDao
	 *            the paymentItemJdbcDao to set
	 */
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao)
	{
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}

	/**
	 * 根据业务ID删除凭证对象（包括子对象）
	 * 
	 * @param bussinessid
	 */
	public void deleteVoucherByBusinessid(String bussinessid)
	{
		/**
		 * 先删除子对象
		 */
		String delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where BUSINESSID = '" + bussinessid + "')";
		this.getJdbcTemplate().execute(delSql);
		/**
		 * 删除主对象
		 */
		delSql = "delete from YVOUCHER where BUSINESSID = '" + bussinessid + "'";
		this.getJdbcTemplate().execute(delSql);

	}

	/**
	 * 根据ID删除凭证对象（包括子对象）
	 * 
	 * @param bussinessid
	 */
	public void deleteVoucherByVoucherid(String voucherid)
	{
		/**
		 * 先删除子对象
		 */
		String delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where VOUCHERID = '" + voucherid + "')";
		this.getJdbcTemplate().execute(delSql);
		/**
		 * 删除主对象
		 */
		delSql = "delete from YVOUCHER where VOUCHERID = '" + voucherid + "'";
		this.getJdbcTemplate().execute(delSql);

	}
	
	public Boolean isVoucherClassExist(String bussinessid, String voucherclass)
	{
		String strSql = "select count(1) count from YVOUCHER where bussinessid='" + bussinessid + "' and voucherclass='" + voucherclass + "'";
		int count = this.getJdbcTemplate().queryForInt(strSql);
		
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Voucher getVoucher(String businessid, String businesstype, String voucherclass)
	{
		Set<VoucherItem> voucherItemSet = new HashSet();
		String strSql = "select * from yvoucher where businessid = '"+businessid+"' and businesstype = '"+businesstype+"' and voucherclass = '"+voucherclass+"'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		Voucher voucher = new Voucher();
		if(null != rowList && rowList.size()!=0){
			ExBeanUtils.setBeanValueFromMap(voucher, rowList.get(0));
			
			strSql = "select a.*,b.*,a.gsber,b.voucherno from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.voucherid = '"+voucher.getVoucherid()+"'";
			rowList = this.getJdbcTemplate().queryForList(strSql);
			for (Map map : rowList)
			{
				VoucherItem voucherItem = new VoucherItem();
				ExBeanUtils.setBeanValueFromMap(voucherItem, map);
				Voucher _voucher = new Voucher();
				ExBeanUtils.setBeanValueFromMap(_voucher, map);
				voucherItem.setVoucher(_voucher);
				voucherItemSet.add(voucherItem);
			}
			
			voucher.setVoucherItem(voucherItemSet);
		}
		return voucher;
	}
	
	/**
	 * 凭证列表
	 * */
	public List<Voucher> getVoucherList(String businessid, String businesstype, String voucherclass)
	{
	    
	    List<Voucher> list = new ArrayList();
	    Set<VoucherItem> voucherItemSet = null;
	    String strSql = "select * from yvoucher where businessid = '"+businessid+"' and businesstype = '"+businesstype+"' and voucherclass = '"+voucherclass+"'";
	    
	    List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
        for (Map map : rowList) {
            Voucher voucher = new Voucher();
            ExBeanUtils.setBeanValueFromMap(voucher, map);

            strSql = "select * from yvoucheritem a,yvoucher b where a.voucherid = b.voucherid and a.voucherid = '"
                    + voucher.getVoucherid() + "'";
            rowList = this.getJdbcTemplate().queryForList(strSql);
            voucherItemSet = new HashSet();
            for (Map map2 : rowList) {
                VoucherItem voucherItem = new VoucherItem();
                ExBeanUtils.setBeanValueFromMap(voucherItem, map2);
                Voucher _voucher = new Voucher();
                ExBeanUtils.setBeanValueFromMap(_voucher, map2);
                voucherItem.setVoucher(_voucher);
                voucherItemSet.add(voucherItem);
            }

            voucher.setVoucherItem(voucherItemSet);
            list.add(voucher);
        }
	    return list;
	}
	
	/**
	 * 根据业务编号和类型删除指定信息
	 * 
	 * @param bussinessid
	 * @param strClass
	 */
	public void deleteVoucherByBusinessidAndClass(String bussinessid, String strClass)
	{
		/**
		 * 先删除没有凭证号的
		 */
		String delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where trim(VOUCHERNO) is null and BUSINESSID = '" + bussinessid + "') ";
		this.getJdbcTemplate().execute(delSql);

		delSql = "delete from YVOUCHER where trim(VOUCHERNO) is null and BUSINESSID = '" + bussinessid + "' ";
		this.getJdbcTemplate().execute(delSql);
		
		/**
		 * 删除有凭证号 被冲销的
		 */
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		String sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where trim(VOUCHERNO) is not null and BUSINESSID = '"
			+ bussinessid + "'" ;
	
		List<Map<String,String>> jdbcRetList = this.getJdbcTemplate().queryForList(sql);
					
		if(jdbcRetList != null)
		{
			for(int i = 0; i < jdbcRetList.size(); i++ )
			{
				CertificateNo certificateNo = new CertificateNo();
				certificateNo.setBelnr(jdbcRetList.get(i).get("voucherno"));
				certificateNo.setBukrs(jdbcRetList.get(i).get("bukrs"));
				certificateNo.setGjahr(jdbcRetList.get(i).get("fiyear"));
				certificateNoList.add(certificateNo);
			}
		}
		
		/**
		 * 有凭证编号
		 */
		if( certificateNoList != null && certificateNoList.size() > 0 )
		{
			/**
			 * 调用RFC判断是否已经人工重置清帐凭证
			 */
			ConnectManager manager = ConnectManager.manager;
			manager.getClient();
			JCO.Client client = null;
			JCO.Table inputTable = null;
			JCO.Table outputTable = null;
			try 
			{
				IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate("ZF_REVERSAL");
				
				if (ftemplate != null) 
				{
					JCO.Function function = ftemplate.getFunction();
					client = JCO.getClient(manager.poolName);
					inputTable = function.getTableParameterList().getTable(
					"T_BELNR");
					inputTable.firstRow();
					for (int i = 0; i < certificateNoList.size(); i++) 
					{
						inputTable.appendRow();
						inputTable.setValue(certificateNoList.get(i)
								.getBelnr(), "BELNR");
						inputTable.setValue(certificateNoList.get(i)
								.getBukrs(), "BUKRS");
						inputTable.setValue(certificateNoList.get(i)
								.getGjahr(), "GJAHR");
					}
					
					client.execute(function);	//执行RFC
					
					outputTable = function.getTableParameterList().getTable(
					"T_BELNR");
					
					List<Map<String, String>> outPutList = ExtractSapData
					.getList(outputTable);
				
					/**
					 * 判断是否全部重置或冲销
					 */
					if(outPutList != null && outPutList.size()>0 )
					{	
						for( int j = 0; j < outPutList.size(); j++ )
						{
							String strVouchNo = outPutList.get(j).get("BELNR");
							String strVoucherState = outPutList.get(j).get("BSTAT");
							String strOffVouchNo = outPutList.get(j).get("STBLG");
							String strOffYear = outPutList.get(j).get("STJAH");
							
							if (!StringUtils.isNullBlank(strOffVouchNo)){
								
								delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where trim(VOUCHERNO) ='"+strVouchNo+"' and BUSINESSID = '" + bussinessid + "') ";
								this.getJdbcTemplate().execute(delSql);

								delSql = "delete from YVOUCHER where trim(VOUCHERNO) ='"+strVouchNo+"' and BUSINESSID = '" + bussinessid + "' ";
								this.getJdbcTemplate().execute(delSql);
							}
						}
					}
				}
			}
			catch(Exception ex) 
			{
				
			}
		}
	}

	/**
	 * 根据凭证号、公司代码、会计年度取得业务ID
	 * 
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public String getBusinessId(String voucherNo, String companyCode, String fiYear)
	{
		String businessId = "";
		String strSql = "select BUSINESSID from YVOUCHER where VOUCHERNO='" + voucherNo + "' and COMPANYCODE='" + companyCode + "' and FIYEAR='" + fiYear + "'";
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
	public String getBusinessId(String voucherNo, String companyCode, String fiYear,String rowNumber)
	{
		//rowNumber 有时是001，有时会是1，而数据表存的是1，所以去0 ,去掉前导0
		String rowNumber2 = rowNumber.replaceAll("^0+(?!$)", "");
		
		String businessId = "";		
		String strSql = "select  v.businessid  from YVOUCHER v left join  YVOUCHERITEM vi on  v.voucherid = vi.voucherid where v.voucherno='" + voucherNo + "' and v.COMPANYCODE='" + companyCode + "' and v.FIYEAR='" + fiYear + "' and TRIM(TO_CHAR(TRIM(vi.ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('"+rowNumber2+"'),'000')) ";
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
	 * 根据供应商id获得供应商名称
	 * 
	 * @param customerId
	 * @param bukrs
	 * @return
	 */
	public String getSupplierDescByCustomerId(String supplierId, String burks)
	{
		String sql = "select NAME1 supplierName from YGETLIFNR where LIFNR = '" + supplierId + "' and BUKRS = '" + burks + "'";

		List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);
		String strRet = "";
		if (list != null && list.size() > 0)
		{
			strRet = list.get(0).get("supplierName");
		}
		return strRet;
	}

	/**
	 * 执行SQL，取得金额合计值。
	 * 
	 * @param sql
	 * @return
	 */
	private BigDecimal getSumAmount(String sql)
	{
		log.debug("执行SQL，取得金额合计值:" + sql);
		BigDecimal sumAmount = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		if (null == sumAmount)
			return new BigDecimal(0.00);
		else
			return sumAmount;
	}

	/**
	 * 是否已经进行凭证预览。
	 * 
	 * @param businessId
	 * @return
	 */
	public boolean isVoucherExist(String businessId)
	{
		String strSql = "select count(*) from YVOUCHER where BUSINESSID='" + businessId + "'";
		int i = this.getJdbcTemplate().queryForInt(strSql);
		if (i > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 是否已经生成凭证。
	 * 
	 * @param businessId
	 * @return
	 */
	public boolean isVoucherGen(String businessId)
	{
		String strSql = "select count(*) from YVOUCHER where BUSINESSID='" + businessId + "' and trim(VOUCHERNO) is null";
		int i = this.getJdbcTemplate().queryForInt(strSql);
		if (i > 0)
			return false;
		else
			return true;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-08-18
	 * 若凭证已经生成，则返回凭证ID
	 * @param businessId 
	 * @return 凭证ID
	 */
	public String getVoucherId(String businessId){
		String sql = "select VOUCHERID voucherid from YVOUCHER where BUSINESSID='" + businessId + "' and VOUCHERNO<>' '";
		List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list==null || list.isEmpty()){
			return null;
		}else{
			return list.get(0).get("voucherid");
		}
	}
	
	
	public List<Voucher> getVoucherByBusinessId(String businessId, String businessType){
		String sql = "select * from YVOUCHER where BUSINESSID='" + businessId + "' and BUSINESSTYPE='" + businessType + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		List<Voucher> voucherList = new ArrayList<Voucher>();
		for (Map map : rowList)
		{
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherList.add(voucher);
		}

		return voucherList;
	}
	
	public List<Voucher> getNewVoucherByBusinessId(String businessId){
		String sql = "select * from YVOUCHER where BUSINESSID='" + businessId + "' and trim(VOUCHERNO) is null";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		List<Voucher> voucherList = new ArrayList<Voucher>();
		for (Map map : rowList)
		{
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherList.add(voucher);
		}

		return voucherList;
	}
	
	public List<Voucher> getHasVoucherNoByBusinessId(String businessId){
	    String sql = "select * from YVOUCHER where BUSINESSID='" + businessId + "' and trim(VOUCHERNO) is not null";
	    
	    List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
	    List<Voucher> voucherList = new ArrayList<Voucher>();
	    for (Map map : rowList)
	    {
	        Voucher voucher = new Voucher();
	        ExBeanUtils.setBeanValueFromMap(voucher, map);
	        voucherList.add(voucher);
	    }
	    
	    return voucherList;
	}
	
	public Voucher getVoucherById(String voucherid){
		String sql = "select * from YVOUCHER where voucherid='" + voucherid + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Voucher voucher = new Voucher();
		ExBeanUtils.setBeanValueFromMap(voucher, rowList.get(0));

		return voucher;
	}
	/**
	 * 更新凭证的业务状态，为冲销，processstate =-1
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 */
	public void updateVoucherState(String voucherNo, String companyCode, String fiYear)
	{
		
		String strSql = "update  YVOUCHER set processstate='-1' where VOUCHERNO='" + voucherNo + "' and COMPANYCODE='" + companyCode + "' and FIYEAR='" + fiYear + "'";
		log.debug("根据凭证号、公司代码、会计年度更新凭证的业务状态为:" + strSql);
		this.getJdbcTemplate().execute(strSql);		
	}
	/**
	 * 取创建时间区间的凭证,不取清账凭证,去掉单清账，票清款
	 * @param importdate_to
	 * @param importdate_from
	 * @return
	 */
	public List<Voucher> getHasVoucherNoByImportdate(String importdate_to,String importdate_from){
		String sql = "select * from YVOUCHER where  importdate between '"+importdate_to+"' and '"+importdate_from+"'   and trim(voucherno) is not null and ( flag !='R' and flag !='P' ) " +
				" and  ( businessid not in ( select cs.customsclearid from ycustomsiclear cs ) and " +
				" businessid not in ( select sc.suppliersclearid from ysuppliersiclear sc) and " +
				"  businessid not in ( select bc.billclearid from ybillclear bc)  )  ";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		List<Voucher> voucherList = new ArrayList<Voucher>();
		for (Map map : rowList)
		{
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			
			Set<VoucherItem> voucherItems =new HashSet<VoucherItem>();
			String sql2 = "   select * from yvoucheritem vi where vi.voucherid='"+voucher.getVoucherid()+"'";
			List<Map> rowList2 = this.getJdbcTemplate().queryForList(sql2.toString());
			for (Map map2 : rowList2){
				VoucherItem voucherItem = new VoucherItem();
				ExBeanUtils.setBeanValueFromMap(voucherItem, map2);
				voucherItem.setVoucher(voucher);
				voucherItems.add(voucherItem);
			}
			voucher.setVoucherItem(voucherItems);
			voucherList.add(voucher);
		}
	    return voucherList;
	}
	
	/**
	 * 取创建时间区间的凭证,取票据的清账凭证
	 * @param importdate_to
	 * @param importdate_from
	 * @return
	 */
	public List<Voucher> getHasVoucherNoByImportdate2(String importdate_to,String importdate_from){
		String sql = "select * from YVOUCHER where  importdate between '"+importdate_to+"' and '"+importdate_from+"'   and trim(voucherno) is not null and ( flag ='R'  or flag ='P') " +
				" and ( businessid not in ( select cs.customsclearid from ycustomsiclear cs ) and " +
				" businessid not in ( select sc.suppliersclearid from ysuppliersiclear sc) and " +
				"  businessid not in ( select bc.billclearid from ybillclear bc)    ) ";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		List<Voucher> voucherList = new ArrayList<Voucher>();
		for (Map map : rowList)
		{
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			
			Set<VoucherItem> voucherItems =new HashSet<VoucherItem>();
			String sql2 = "   select * from yvoucheritem vi where vi.voucherid='"+voucher.getVoucherid()+"'";
			List<Map> rowList2 = this.getJdbcTemplate().queryForList(sql2.toString());
			for (Map map2 : rowList2){
				VoucherItem voucherItem = new VoucherItem();
				ExBeanUtils.setBeanValueFromMap(voucherItem, map2);
				voucherItem.setVoucher(voucher);
				voucherItems.add(voucherItem);
			}
			voucher.setVoucherItem(voucherItems);
			voucherList.add(voucher);
		}
	    return voucherList;
	}
	
	public void insertSapVoucherLog(String voucherid,String bussinessid,String bussinesstype,String oldvoucherNo,String newvoucherNo, String companyCode, String fiYear,String createtime,String flag,String agums,String logtext,String isgen)
	{
		
		String strSql = "insert into ysap_voucher_deal_log (MANDT , VOUCHERID ,BUSINESSID ,BUSINESSTYPE , OLDVOUCHERNO , NEWVOUCHERNO , " +
				"COMPANYCODE , FIYEAR ,  CREATETIME ,FLAG ,AGUMS , LOGTEXT,isgen) values('800','"+voucherid+"'," +
				"'"+bussinessid+"','"+bussinesstype+"','"+oldvoucherNo+"' ,'"+newvoucherNo+"','"+companyCode+"','"+fiYear+"','"+createtime+"'" +
				",'"+flag+"','"+agums+"','"+logtext+"' ,'"+isgen+"')";
		log.debug("新增到sap的凭证日志:" + strSql);
		this.getJdbcTemplate().execute(strSql);		
	}
	/**
	 * 判断在日志表中是否有生成过的凭证
	 * @param voucherid
	 * @return
	 */
	public boolean isgen(String voucherid ){
		String sql ="select vd.isgen from ysap_voucher_deal_log vd where vd.voucherid ='"+voucherid+"'";
		List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list==null || list.isEmpty()){
			return false;
		}else{
			String isgen =list.get(0).get("isgen");
			if("1".equals(isgen)){
				return true;
			}else{
				return false; 
			}			
		}
		
	}
	 
	
}
