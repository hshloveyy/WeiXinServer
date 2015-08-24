package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.CostCenter;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class CostCenterJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除成本中心数据
	 */
	public void delete()
	{
		String sql = "delete from YCOSTCENTER";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入成本中心
	 * @param hkont
	 */
	public void insert(CostCenter costCenter)
	{
		String uId = CodeGenerator.getUUID();
		costCenter.setCostcenterid(uId);
		
		String sql = "insert into YCOSTCENTER(MANDT,COSTCENTERID,KOKRS,BUKRS,KOSTL,DATBI,DATAB,GSBER,FUNC_AREA,PRCTR,KTEXT) values( '800', '"
			 + uId 
			 + "', '" + costCenter.getKokrs() 
			 + "', '" +	costCenter.getBukrs() 
			 + "', '" + costCenter.getKostl() 
			 + "', '" + costCenter.getDatbi() 
			 + "', '" + costCenter.getDatab() 
			 + "', '" + costCenter.getGsber() 
			 + "', '" + costCenter.getFunc_area()
			 + "', '" + costCenter.getPrctr()
			 + "', '"+ costCenter.getKtext() + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
