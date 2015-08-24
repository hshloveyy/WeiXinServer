package com.infolion.sapss.receipts.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;
import com.infolion.sapss.common.WorkflowUtils;

/**
 * TReceiptInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RECEIPT_INFO")
public class TReceiptInfo extends ProcessObject {

	// Fields

	private String receiptId; // 进仓ID
	private String tradeType; // 贸易方式
	private String contractPurchaseId; // 采购合同ID
	private String projectNo; // 立项号
	private String projectName; // 立项名称
	private String contractGroupNo; // 合同组编号
	private String contractNo; // 采购合同号
	private String sapOrderNo; // SAP订单号
	private String receiptNo; // 进仓单号
	private String warehouse; // 仓库
	private String warehouseAdd; // 仓库地址
	private String billState; // 单据状态
	private String receiptOperator; // 进仓执行人
	private String receiptNote; // 进仓执行意见
	private String operateTime; // 进仓执行时间
	private String deptId; // 部门编号
	private String examineState; // 审批状态
	private String applyTime; // 申报时间
	private String approvedTime; // 审批通过时间
	private String isAvailable; // 是否有效
	private String creatorTime; // 创建时间
	private String creator; // 创建人
	/** ****** */
	private String contractPaperNo;// 纸质合同号
	private String unitName;// 销货单位
	private String sapReturnNo;// 物料凭证号
	@ValidateRule(dataType = DataType.STRING, label = "实际入库时间",required = true)
	private String receiptTime;//进仓时间
	private String declarationsNo;//报关单号
	private String memo;//备注
	private String postDate;//过帐日期
	@ValidateRule(dataType = DataType.STRING, label = "应发货日",required = true)
	private String sendGoodsDate;
	@ValidateRule(dataType = DataType.STRING, label = "预计付款日",required = true)
	private String payAbleDate;
	@ValidateRule(dataType = DataType.STRING, label = "预计收票日",required = true)
	private String receiveBillDate;
	
	//冲销新增字段
	private String oldReceiptId;//原进仓单ID
	private String oldReceiptNo;//原进仓单编码
	private String oldSapReturnNo;//原物料凭证号
	//新增二次结算数据
	private String seConfigTime;//二次结算确认时间
	private String serialNo;//序号

	private String isReturn;//退货标识
	// Constructors
	/** default constructor */
	public TReceiptInfo() {
	}

	/** minimal constructor */
	public TReceiptInfo(String receiptId) {
		this.receiptId = receiptId;
	}

	/** full constructor */
	public TReceiptInfo(String receiptId, String tradeType,
			String contractPurchaseId, String creditNo, String projectNo,
			String projectName, String contractGroupNo, String contractNo,
			String sapOrderNo, String receiptNo, String warehouse,
			String warehouseAdd, String billState, String receiptOperator,
			String receiptNote, String operateTime, String deptId,
			String examineState, String applyTime, String approvedTime,
			String isAvailable, String creatorTime, String creator) {

		this.receiptId = receiptId;
		this.tradeType = tradeType;
		this.contractPurchaseId = contractPurchaseId;
		this.projectNo = projectNo;
		this.projectName = projectName;
		this.contractGroupNo = contractGroupNo;
		this.contractNo = contractNo;
		this.sapOrderNo = sapOrderNo;
		this.receiptNo = receiptNo;
		this.warehouse = warehouse;
		this.warehouseAdd = warehouseAdd;
		this.billState = billState;
		this.receiptOperator = receiptOperator;
		this.receiptNote = receiptNote;
		this.operateTime = operateTime;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	@Id
	@Column(name = "RECEIPT_ID", unique = true, nullable = false, length = 36)
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "CONTRACT_PURCHASE_ID", length = 36)
	public String getContractPurchaseId() {
		return contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}

	@Column(name = "PROJECT_NO", length = 50)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_GROUP_NO", length = 30)
	public String getContractGroupNo() {
		return contractGroupNo;
	}

	public void setContractGroupNo(String contractGroupNo) {
		this.contractGroupNo = contractGroupNo;
	}

	@Column(name = "CONTRACT_NO", length = 50)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 50)
	public String getSapOrderNo() {
		return sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "RECEIPT_NO", length = 50)
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "WAREHOUSE_ADD", length = 100)
	public String getWarehouseAdd() {
		return warehouseAdd;
	}

	public void setWarehouseAdd(String warehouseAdd) {
		this.warehouseAdd = warehouseAdd;
	}

	@Column(name = "BILL_STATE", length = 1)
	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	@Column(name = "RECEIPT_OPERATOR", length = 30)
	public String getReceiptOperator() {
		return receiptOperator;
	}

	public void setReceiptOperator(String receiptOperator) {
		this.receiptOperator = receiptOperator;
	}

	@Column(name = "RECEIPT_NOTE", length = 200)
	public String getReceiptNote() {
		return receiptNote;
	}

	public void setReceiptNote(String receiptNote) {
		this.receiptNote = receiptNote;
	}

	@Column(name = "OPERATE_TIME", length = 20)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1007";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "入仓申请";

	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = processName;
		 this.workflowProcessName = WorkflowUtils.chooseWorkflowName("receipt_v1");
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "receiptsController.spr?action=receiptsExamine";

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
	@Column(name = "RECEIPT_TIME", length = 20)
	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
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
	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "POST_DATE", length = 20)
	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	
	@Column(name = "PAYABLEDATE", length = 20)
	public String getPayAbleDate()
	{
		return payAbleDate;
	}

	public void setPayAbleDate(String payAbleDate)
	{
		this.payAbleDate = payAbleDate;
	}
	
	@Column(name = "RECEIVEBILLDATE", length = 20)
	public String getReceiveBillDate()
	{
		return receiveBillDate;
	}

	public void setReceiveBillDate(String receiveBillDate)
	{
		this.receiveBillDate = receiveBillDate;
	}
	
	@Column(name = "SENDGOODSDATE", length = 20)
	public String getSendGoodsDate()
	{
		return sendGoodsDate;
	}

	public void setSendGoodsDate(String sendGoodsDate)
	{
		this.sendGoodsDate = sendGoodsDate;
	}

	@Column(name = "old_Receipt_Id", length = 36)
	public String getOldReceiptId() {
		return oldReceiptId;
	}

	public void setOldReceiptId(String oldReceiptId) {
		this.oldReceiptId = oldReceiptId;
	}
	@Column(name = "old_Receipt_NO", length = 200)
	public String getOldReceiptNo() {
		return oldReceiptNo;
	}

	public void setOldReceiptNo(String oldReceiptNo) {
		this.oldReceiptNo = oldReceiptNo;
	}

	@Column(name = "OLD_SAP_RETURN_NO", length = 50)
	public String getOldSapReturnNo() {
		return oldSapReturnNo;
	}

	public void setOldSapReturnNo(String oldSapReturnNo) {
		this.oldSapReturnNo = oldSapReturnNo;
	}
	@Column(name = "seConfigTime", length = 36)
	public String getSeConfigTime() {
		return seConfigTime;
	}

	public void setSeConfigTime(String seConfigTime) {
		this.seConfigTime = seConfigTime;
	}
	@Column(name = "serialNo", length = 36)
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name = "isReturn", length = 2)
	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
}