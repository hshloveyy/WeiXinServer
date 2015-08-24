package com.infolion.sapss.ship.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.ship.dao.CheckCustomerSendCreditJdbcDao;
import com.infolion.sapss.ship.dao.ShipMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.xdss3.ceditValueControl.dao.CustomerCreditProjHibernateDao;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.masterData.dao.TcurrHibernateDao;
import com.infolion.xdss3.masterData.domain.Tcurr;


@Service
public class CheckCustomerSendCreditSerice {
	@Autowired
	CheckCustomerSendCreditJdbcDao checkCustomerSendCreditJdbcDao;
	@Autowired
	TcurrHibernateDao tcurrHibernateDao;
	@Autowired
	ProjectInfoHibernate projectInfoHibernate;
	@Autowired
	ShipMaterialHibernateDao shipMaterialHibernateDao;
	@Autowired
	ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	@Autowired
	CustomerCreditProjHibernateDao customerCreditProjHibernateDao;
	/**
	 * 放货额度计算方法
	 * @param customer  客户
	 * @param project_id 立项ID
	 * @param value 发货值
	 * @param fcurr 币别
	 * @return
	 */
	public boolean checkCustomerSendCredit(String customer_id,String project_id, float value){
		
		String rt=checkCustomerSendCreditJdbcDao.checkCustomerSendCredit(customer_id, project_id, value);
		if(Float.valueOf(rt)<0){
			return false;
		}else{
			return true;		
		}
	}
	
	/**
	 * 
	 * @param contractSalesId
	 * @param ship_id
	 * @return
	 */
	public boolean checkCustomerSendCredit(String contractSalesId,String ship_id){
		TContractSalesInfo contractSalesInfo = contractSalesInfoHibernateDao.get(contractSalesId);	
		String project_id = contractSalesInfo.getProjectId();
//		TProjectInfo tprojectInfo = projectInfoHibernate.get(project_id);
		Map<String ,BigDecimal> map=new HashMap<String,BigDecimal>();
		List li=shipMaterialHibernateDao.find("from TShipMaterial ts where ts.shipId=?",ship_id);
		for(int i=0;i<li.size();i++){
			TShipMaterial sm =  (TShipMaterial)li.get(i);
			BigDecimal saleTotal =  sm.getSaleTotal();			
			if(!map.isEmpty() && sm.getSaleCurrency()!=null){				
				BigDecimal saleTotal2=map.get(sm.getSaleCurrency());
				BigDecimal saleTotal3= saleTotal2.add(saleTotal);
				map.put(sm.getSaleCurrency(), saleTotal3);	
			}else{
				map.put(sm.getSaleCurrency(), saleTotal);		
			}			
		}
		
		
		
		float new_value = 0;
		for(String fcurr : map.keySet()){
			BigDecimal value = map.get(fcurr);
			float v2 = 0;
			if(value!=null)v2 = value.floatValue();
			//CNY 为人民币代号,如果是人民币就不用转换
			if(!"CNY".equals(fcurr)){
				new_value=new_value + convertTcurr(v2,fcurr);
			}else{
				new_value=new_value + v2;
			}	
		}
		
		String hql="from CustomerCreditProj c where c.projectid =?";
		List<CustomerCreditProj> customerCreditProjs = customerCreditProjHibernateDao.find(hql, project_id);
		String id ="";
		for(CustomerCreditProj customerCreditProj : customerCreditProjs){				
			id=customerCreditProj.getCustomerCreditConf().getCustomerid();	
			break;
		}		
		
		return checkCustomerSendCredit(id,project_id,new_value);
	}
	/**
	 * 查看发货信用额度
	 * @param project_no
	 * @param ship_id
	 * @param value
	 * @return
	 */
	public String viewCheckCustomerSendCredit(String project_no,String ship_id, float value){
		String hql="from TProjectInfo p where p.projectNo =?";
		List<TProjectInfo> projectInfos = projectInfoHibernate.find(hql,project_no);
		String project_id="";
		for(TProjectInfo projectInfo :projectInfos){			
			project_id = projectInfo.getProjectId();
		}		
//		TProjectInfo tprojectInfo = projectInfoHibernate.get(project_id);
		hql="from CustomerCreditProj c where c.projectid =?";
		List<CustomerCreditProj> customerCreditProjs = customerCreditProjHibernateDao.find(hql, project_id);
		String id ="";
		for(CustomerCreditProj customerCreditProj : customerCreditProjs){				
			id=customerCreditProj.getCustomerCreditConf().getCustomerid();	
			break;
		}
		String rt=checkCustomerSendCreditJdbcDao.checkCustomerSendCredit(id, project_id, value);
		if(Float.valueOf(rt)<0){
			return rt + "&" + "false";
		}else{
			return rt + "&" + "true";		
		}		
	}
	/**
	 * 转换汇率成人民币,取最新的汇率
	 * @param value 数值
	 * @param fcurr 原始币别
	 * @param gdatu 汇率时间
	 * @return
	 */
	public float convertTcurr(float value,String fcurr){
		
		//String hql="from Tcurr t where t.is_available='1' order by t.gdatu desc";
		StringBuffer hql = new StringBuffer();
		hql.append("from Tcurr t where t.fcurr='").append(fcurr).append("' order by t.gdatu desc");
		Session session = tcurrHibernateDao.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();  
		tr.begin();  
		Tcurr tcurr=(Tcurr) session.createQuery(hql.toString()).setMaxResults(1).uniqueResult();
		tr.commit();
//		List l=(List)tcurrHibernateDao.find(hql);
//		System.out.println(tcurr.getFcurr());
		float new_value=0;
		new_value = value * Float.parseFloat(tcurr.getUkurs().toString());
		return new_value;
	}
	/**
	 * 检查客户授信
	 * @param customer_id
	 * @param project_id
	 * @param value
	 * @return
	 */
	public String checkCustomerCredit(String customer_id,String project_id, float value){
		String rt=checkCustomerSendCreditJdbcDao.checkCustomerCredit(customer_id, project_id, value);
		
		if(Float.valueOf(rt)<0){
			return rt + "&" + "false";
		}else{
			return rt + "&" + "true";		
		}		
	}
	/**
	 * 检查供应商授信
	 * @param provider_id
	 * @param project_id
	 * @param value
	 * @return
	 */	
	public String checkProviderCredit(String provider_id,String project_id, float value){
		String rt=checkCustomerSendCreditJdbcDao.checkProviderCredit(provider_id, project_id, value);
		
		if(Float.valueOf(rt)<0){
			return rt + "&" + "false";
		}else{
			return rt + "&" + "true";		
		}		
	}
	
	/**
	 * 检查立项是否授信，如授信则返回余额。
	 * 1、根据立项号查询立项ID
	 * 2、根据立项ID先从客户授信、供应商授信查询
	 * 3、取得客户ID、供应商ID+立项ID再查询得余额
	 * <br>
	 * @param id 客户ID，或供应商ID,目前没有用，从客户授信找。
	 * @param project_no 项目no
	 * @param value 人民币金额
	 * @return 为 value&true 或 value&false ,或false&false的字符串,value为返回的数值
	 */
	public String checkCredit(String id,String project_no, float value){
		String hql="from TProjectInfo p where p.projectNo =?";
		List<TProjectInfo> projectInfos = projectInfoHibernate.find(hql,project_no);
		String project_id="";
		for(TProjectInfo projectInfo :projectInfos){			
			project_id = projectInfo.getProjectId();
		}		
		hql="from CustomerCreditProj c where c.projectid =?";
		List<CustomerCreditProj> customerCreditProjs = customerCreditProjHibernateDao.find(hql, project_id);
		String creditType="";
		String _id="";
		for(CustomerCreditProj customerCreditProj : customerCreditProjs){
			creditType=customerCreditProj.getCustomerCreditConf().getCredittype();		
			_id=customerCreditProj.getCustomerCreditConf().getCustomerid();
		}
		//2代垫，3放货+代垫
		if("2".equals(creditType) || "3".equals(creditType)){
			return checkCustomerCredit(_id,project_id,value);
		}
		hql="from ProviderCreditProj c where c.projectid =?";
		List<ProviderCreditProj> providerCreditProjs = (List<ProviderCreditProj>)customerCreditProjHibernateDao.find(hql, project_id);
		String rs="";
		_id="";
		for(ProviderCreditProj providerCreditProj : providerCreditProjs){			
			String providerid=providerCreditProj.getProviderCreditConf().getProviderid();			
			String credittype =providerCreditProj.getProviderCreditConf().getCredittype();
			// a 用于非货款计算,并且是自营的不用计算授信
			if("a".equals(id) && "2".equals(credittype))return "true&true";
			return checkProviderCredit(providerid,project_id,value);
			// a 用于非货款计算
//			if((id!=null && id.equals(providerid)) || "a".equals(id)){		
//				rs=providerid;
//				if ( "a".equals(id)) {
//				    id = providerid;
//				}
//			}
		}
		//
//		if(!"".equals(rs)){
//			return checkProviderCredit(id,project_id,value);
//		}		
		return "false&false";
	}
	/**
	 * 检查客户
	 * @param project_no
	 * @return
	 */	
	 public boolean checkCustomerCredit(String project_no){
		 String hql="from TProjectInfo p where p.projectNo =?";
			List<TProjectInfo> projectInfos = projectInfoHibernate.find(hql,project_no);
			String project_id="";
			for(TProjectInfo projectInfo :projectInfos){			
				project_id = projectInfo.getProjectId();
			}		
			hql="from CustomerCreditProj c where c.projectid =?";
			List<CustomerCreditProj> customerCreditProjs = customerCreditProjHibernateDao.find(hql, project_id);
			String creditType="";
			for(CustomerCreditProj customerCreditProj : customerCreditProjs){
				creditType=customerCreditProj.getCustomerCreditConf().getCredittype();			
			}
			//1，3，表示 1放货　3放货+代垫
			if("1".equals(creditType) || "3".equals(creditType)){
				return true;
			}else{
				return false;
			}
	 }	 

	 /**
		 * 取得供应商授信类型
		 * @param provider_id
		 * @param project_id
		 * @param currentdate
		 * @return 返回-1代表没有授信，2为自营，1为非自营
		 */
		public String getCreditTypeByProvider(String provider_id,String project_id){
			Date dt = new Date();  
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
			 String currentdate=sdf.format(dt);   
			return this.checkCustomerSendCreditJdbcDao.getCreditTypeByProvider(provider_id,project_id,currentdate);
		}
		
}
