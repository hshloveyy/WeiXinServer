/*
 * @(#)TShipInfo.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 12, 2009
 *  描　述：创建
 */

package com.infolion.sapss.ship.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_SHIP_INFO")
public class TShipInfo extends ProcessObject {

	private String shipId;
	private String exportApplyId;
	private String contractSalesId;
	private String contractPurchaseId;
	private String tradeType;
	private String projectNo;
	private String projectName;
	private String contractGroupNo;
	private String salesNo;
	private String sapOrderNo;
	private String shipNo;
	private String declarationsNo;
	private String warehouse;
	private String warehouseAdd;
	private String billState;
	private String shipOperator;
	private String shipNote;
	private String shipTime;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;	
	private String isProduct;
	
	private String contractPaperNo;//纸质合同号
	private String unitName;//销货单位
	private String sapReturnNo;//物料凭证号
	private String cmd;
	private String createTime;//CREATE_TIME 
	
	@ValidateRule(dataType = DataType.STRING, label = "预计收款日期",required = true)
	private String intendGatherTime;
	@ValidateRule(dataType = DataType.STRING, label = "预计开票日",required = true)
	private String makeInvoiceTime;
	
	//冲销新增字段
	private String oldShipId;//原出仓单ID
	private String oldShipNo;//原出仓单编码
	private String oldSapReturnNo;//原物料凭证号
	private String isHasInv;//是否已开票
	private String billApplyNo;//原开票凭证号或物料凭证号->开票申请单号
	private String billApplyId;
	private String isHome;//是否出口，只适用于进料加工
	//新增二次结算数据
	private String seConfigTime;//二次结算确认时间
	private String serialNo;//
	@Id
	@Column(name = "SHIP_ID", unique = true, length = 36)
	public String getShipId()
	{
		return shipId;
	}

	public void setShipId(String shipId)
	{
		this.shipId = shipId;
	}

	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId()
	{
		return exportApplyId;
	}

	public void setExportApplyId(String exportApplyId)
	{
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId()
	{
		return contractSalesId;
	}

	public void setContractSalesId(String contractSalesId)
	{
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "CONTRACT_PURCHASE_ID", length = 36)
	public String getContractPurchaseId()
	{
		return contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId)
	{
		this.contractPurchaseId = contractPurchaseId;
	}
	
	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType()
	{
		return tradeType;
	}

	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}

	@Column(name = "PROJECT_NO", length = 50)
	public String getProjectNo()
	{
		return projectNo;
	}

	public void setProjectNo(String projectNo)
	{
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_GROUP_NO", length = 30)
	public String getContractGroupNo()
	{
		return contractGroupNo;
	}

	public void setContractGroupNo(String contractGroupNo)
	{
		this.contractGroupNo = contractGroupNo;
	}

	@Column(name = "SALES_NO", length = 50)
	public String getSalesNo()
	{
		return salesNo;
	}

	public void setSalesNo(String salesNo)
	{
		this.salesNo = salesNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 50)
	public String getSapOrderNo()
	{
		return sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo)
	{
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "SHIP_NO", length = 50)
	public String getShipNo()
	{
		return shipNo;
	}

	public void setShipNo(String shipNo)
	{
		this.shipNo = shipNo;
	}

	@Column(name = "DECLARATIONS_NO", length = 50)
	public String getDeclarationsNo()
	{
		return declarationsNo;
	}

	public void setDeclarationsNo(String declarationsNo)
	{
		this.declarationsNo = declarationsNo;
	}

	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse()
	{
		return warehouse;
	}

	public void setWarehouse(String warehouse)
	{
		this.warehouse = warehouse;
	}

	@Column(name = "WAREHOUSE_ADD", length = 50)
	public String getWarehouseAdd()
	{
		return warehouseAdd;
	}

	public void setWarehouseAdd(String warehouseAdd)
	{
		this.warehouseAdd = warehouseAdd;
	}

	@Column(name = "BILL_STATE", length = 1)
	public String getBillState()
	{
		return billState;
	}

	public void setBillState(String billState)
	{
		this.billState = billState;
	}

	@Column(name = "SHIP_OPERATOR", length = 30)
	public String getShipOperator()
	{
		return shipOperator;
	}

	public void setShipOperator(String shipOperator)
	{
		this.shipOperator = shipOperator;
	}

	@Column(name = "SHIP_NOTE", length = 200)
	public String getShipNote()
	{
		return shipNote;
	}

	public void setShipNote(String shipNote)
	{
		this.shipNote = shipNote;
	}

	@Column(name = "SHIP_TIME", length = 20)
	public String getShipTime()
	{
		return shipTime;
	}

	public void setShipTime(String shipTime)
	{
		this.shipTime = shipTime;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState()
	{
		return examineState;
	}

	public void setExamineState(String examineState)
	{
		this.examineState = examineState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime()
	{
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime)
	{
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable()
	{
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime()
	{
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	@Column(name = "IS_PRODUCT", length = 1)	
	public String getIsProduct()
	{
		return isProduct;
	}

	public void setIsProduct(String isProduct)
	{
		this.isProduct = isProduct;
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="111";
	}
	
	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="出仓单申请";
	}
	
	@Override
	public void setWorkflowProcessName() {
		//this.workflowProcessName="consignment_import_home_v1";		
	}
	
	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="shipController.spr?action=shipExamine";	
	}		
	@Column(name = "CONTRACT_PAPER_NO", length = 50)
	public String getContractPaperNo() {
		return contractPaperNo;
	}

	public void setContractPaperNo(String contractPaperNo) {
		this.contractPaperNo = contractPaperNo;
	}
	@Column(name = "UNIT_NAME", length = 50)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name = "SAP_RETURN_NO", length = 50)
	public String getSapReturnNo() {
		return sapReturnNo;
	}

	public void setSapReturnNo(String sapReturnNo) {
		this.sapReturnNo = sapReturnNo;
	}
	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "INTEND_GATHER_TIME", length=20)
	public String getIntendGatherTime()
	{
		return intendGatherTime;
	}

	public void setIntendGatherTime(String intendGatherTime)
	{
		this.intendGatherTime = intendGatherTime;
	}
	
	@Column(name = "MAKE_INVOICE_TIME", length=20)
	public String getMakeInvoiceTime()
	{
		return makeInvoiceTime;
	}

	public void setMakeInvoiceTime(String makeInvoiceTime)
	{
		this.makeInvoiceTime = makeInvoiceTime;
	}
	@Column(name = "old_Ship_Id", length = 36)
	public String getOldShipId() {
		return oldShipId;
	}

	public void setOldShipId(String oldShipId) {
		this.oldShipId = oldShipId;
	}
	@Column(name = "old_Ship_NO", length = 200)
	public String getOldShipNo() {
		return oldShipNo;
	}

	public void setOldShipNo(String oldShipNo) {
		this.oldShipNo = oldShipNo;
	}

	@Column(name = "OLD_SAP_RETURN_NO", length = 50)
	public String getOldSapReturnNo() {
		return oldSapReturnNo;
	}

	public void setOldSapReturnNo(String oldSapReturnNo) {
		this.oldSapReturnNo = oldSapReturnNo;
	}
	@Column(name = "IS_HAS_INV", length = 50)
	public String getIsHasInv() {
		return isHasInv;
	}

	public void setIsHasInv(String isHasInv) {
		this.isHasInv = isHasInv;
	}
	@Column(name = "bill_apply_no", length = 50)
	public String getBillApplyNo() {
		return billApplyNo;
	}

	public void setBillApplyNo(String billApplyNo) {
		this.billApplyNo = billApplyNo;
	}
	@Column(name = "bill_apply_id", length = 50)
	public String getBillApplyId() {
		return billApplyId;
	}

	public void setBillApplyId(String billApplyId) {
		this.billApplyId = billApplyId;
	}
	@Column(name = "is_Home", length = 4)
	public String getIsHome() {
		return isHome;
	}

	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}
	@Column(name = "seConfigTime", length = 20)
	public String getSeConfigTime() {
		return seConfigTime;
	}

	public void setSeConfigTime(String seConfigTime) {
		this.seConfigTime = seConfigTime;
	}
	@Column(name = "serialNo", length = 20)
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
