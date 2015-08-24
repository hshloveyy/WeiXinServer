<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月16日 11点33分53秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象人员(Personnel)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/personnelAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/personnelAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_orgPersonnel" boName="OrgPersonnel" needCheckBox="true" editable="false" defaultCondition=" YORGPERSONNEL.PERSONNELID='${personnel.personnelid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.personnelname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Personnel.personnelname" name="Personnel.personnelname" value="${personnel.personnelname}" <fisc:authentication sourceName="Personnel.personnelname" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.title}：</td>
		<td   width="40%" >
			<div id="div_title_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="title" dictionaryName="YDTITLE" divId="div_title_dict" isNeedAuth="false"  value="${personnel.title}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.sex}：</td>
		<td  width="30%" >
			<div id="div_sex_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="sex" dictionaryName="YDSEX" divId="div_sex_dict" isNeedAuth="false"  value="${personnel.sex}"  ></fisc:dictionary>
		</td>
		<td align="right"  width="15%" >${vt.property.marriage}：</td>
		<td   width="40%" >
			<div id="div_marriage_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="marriage" dictionaryName="YDMARRIAGE" divId="div_marriage_dict" isNeedAuth="false"  value="${personnel.marriage}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.citizenship}：</td>
		<td  width="30%" >
			<div id="div_citizenship_sh"></div>
			<fisc:searchHelp divId="div_citizenship_sh" boName="Personnel" boProperty="citizenship" value="${personnel.citizenship}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" >${vt.property.identitycard}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Personnel.identitycard" name="Personnel.identitycard" value="${personnel.identitycard}" <fisc:authentication sourceName="Personnel.identitycard" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.bank}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Personnel.bank" name="Personnel.bank" value="${personnel.bank}" <fisc:authentication sourceName="Personnel.bank" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.account}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Personnel.account" name="Personnel.account" value="${personnel.account}" <fisc:authentication sourceName="Personnel.account" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.birthday}：</td>
		<td  width="30%" >
			<input type="text" id="Personnel.birthday" name="Personnel.birthday" value="">
				<fisc:calendar applyTo="Personnel.birthday"  divId="" fieldName="" defaultValue="${personnel.birthday}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" >${vt.property.level_}：</td>
		<td   width="40%" >
			<div id="div_level__dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="level_" dictionaryName="YDLEVEL" divId="div_level__dict" isNeedAuth="false"  value="${personnel.level_}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.department}：</td>
		<td  width="30%" >
			<div id="div_department_sh"></div>
			<fisc:searchHelp divId="div_department_sh" boName="Personnel" boProperty="department" value="${personnel.department}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" >${vt.property.isleader}：</td>
		<td   width="40%" >
			<div id="div_isleader_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="isleader" dictionaryName="YDYESORNO" divId="div_isleader_dict" isNeedAuth="false"  value="${personnel.isleader}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.personnelno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Personnel.personnelno" name="Personnel.personnelno" value="${personnel.personnelno}" <fisc:authentication sourceName="Personnel.personnelno" taskId=""/>  readonly="readonly">
		</td>
		<td align="right"  width="15%" >${vt.property.personnelexno}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Personnel.personnelexno" name="Personnel.personnelexno" value="${personnel.personnelexno}" <fisc:authentication sourceName="Personnel.personnelexno" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.userid}：</td>
		<td  width="30%" >
			<div id="div_userid_sh"></div>
			<fisc:searchHelp divId="div_userid_sh" boName="Personnel" boProperty="userid" value="${personnel.userid}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" >${vt.property.status}：</td>
		<td   width="40%" >
			<div id="div_status_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="status" dictionaryName="YDPERSONNELSTATUS" divId="div_status_dict" isNeedAuth="false"  value="${personnel.status}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.leavedate}：</td>
		<td  width="30%" >
			<input type="text" id="Personnel.leavedate" name="Personnel.leavedate" value="">
				<fisc:calendar applyTo="Personnel.leavedate"  divId="" fieldName="" defaultValue="${personnel.leavedate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" >${vt.property.postid}：</td>
		<td   width="40%" >
			<div id="div_postid_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="postid" dictionaryName="YPOST" divId="div_postid_dict" isNeedAuth="false"  value="${personnel.postid}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.positionid}：</td>
		<td  width="30%" >
			<div id="div_positionid_dict"></div>
			<fisc:dictionary boName="Personnel" boProperty="positionid" dictionaryName="YPOSITION" divId="div_positionid_dict" isNeedAuth="false"  value="${personnel.positionid}"  ></fisc:dictionary>
		</td>
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td   width="40%" >
			<fisc:user boProperty="creator" boName="Personnel" userId="${personnel.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.createtime}：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Personnel.createtime" name="Personnel.createtime" value="${personnel.createtime}"  readonly="readonly" <fisc:authentication sourceName="Personnel.createtime" taskId=""/>>
		</td>
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td   width="40%" >
			<fisc:user boProperty="lastmodifyer" boName="Personnel" userId="${personnel.lastmodifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Personnel.lastmodifytime" name="Personnel.lastmodifytime" value="${personnel.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="Personnel.lastmodifytime" taskId=""/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Personnel.personnelid" name="Personnel.personnelid" value="${personnel.personnelid}">
	<input type="hidden" id="Personnel.client" name="Personnel.client" value="${personnel.client}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="$(calActivityId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_address" class="x-hide-display">
<form id="addressForm" name="addressForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td align="right" width="15%"><font color="red">★</font>${addressVt.property.country}：</td>
<td width="30%" >
<div id="div_Addresscountry_sh"></div>
<fisc:searchHelp divId="div_Addresscountry_sh" boName="Address" boProperty="country" value="${personnel.address.country}"></fisc:searchHelp>
</td>
<td align="right" width="15%">${addressVt.property.mobilephone}：</td>
<td width="40%" >
<input type="text" class="inputText" id="Address.mobilephone" name="Address.mobilephone" value="${personnel.address.mobilephone}" <fisc:authentication sourceName="Address.mobilephone" taskId=""/>>
</td>
</tr>
<tr>
<td align="right" width="15%"><font color="red">★</font>${addressVt.property.area}：</td>
<td width="30%" >
<div id="div_Addressarea_sh"></div>
<fisc:searchHelp divId="div_Addressarea_sh" boName="Address" boProperty="area" value="${personnel.address.area}"></fisc:searchHelp>
</td>
<td align="right" width="15%">${addressVt.property.fax}：</td>
<td width="40%" >
<input type="text" class="inputText" id="Address.fax" name="Address.fax" value="${personnel.address.fax}" <fisc:authentication sourceName="Address.fax" taskId=""/>>
</td>
</tr>
<tr>
<td align="right" width="15%"><font color="red">★</font>${addressVt.property.city}：</td>
<td width="30%" >
<input type="text" class="inputText" id="Address.city" name="Address.city" value="${personnel.address.city}" <fisc:authentication sourceName="Address.city" taskId=""/>>
</td>
<td align="right" width="15%">${addressVt.property.website}：</td>
<td width="40%" >
<input type="text" class="inputText" id="Address.website" name="Address.website" value="${personnel.address.website}" <fisc:authentication sourceName="Address.website" taskId=""/>>
</td>
</tr>
<tr>
<td align="right" width="15%"><font color="red">★</font>${addressVt.property.zipcode}：</td>
<td width="30%" >
<input type="text" class="inputText" id="Address.zipcode" name="Address.zipcode" value="${personnel.address.zipcode}" <fisc:authentication sourceName="Address.zipcode" taskId=""/>>
</td>
<td align="right" width="15%">${addressVt.property.qq_msn}：</td>
<td width="40%" >
<input type="text" class="inputText" id="Address.qq_msn" name="Address.qq_msn" value="${personnel.address.qq_msn}" <fisc:authentication sourceName="Address.qq_msn" taskId=""/>>
</td>
</tr>
<tr>
<td align="right" width="15%">${addressVt.property.email}：</td>
<td width="30%" >
<input type="text" class="inputText" id="Address.email" name="Address.email" value="${personnel.address.email}" <fisc:authentication sourceName="Address.email" taskId=""/>>
</td>
<td align="right" width="15%">${addressVt.property.homephone}：</td>
<td width="40%" >
<input type="text" class="inputText" id="Address.homephone" name="Address.homephone" value="${personnel.address.homephone}" <fisc:authentication sourceName="Address.homephone" taskId=""/>>
</td>
</tr>
<tr>
<td align="right" width="15%"><font color="red">★</font>${addressVt.property.phone}：</td>
<td width="30%" >
<input type="text" class="inputText" id="Address.phone" name="Address.phone" value="${personnel.address.phone}" <fisc:authentication sourceName="Address.phone" taskId=""/>>
</td>
<td align="right" width="15%">${addressVt.property.addresstype}：</td>
<td width="40%" >
<div id="div_Addressaddresstype_dict"></div>
<fisc:dictionary boName="Address" boProperty="addresstype" dictionaryName="YDADDRESSTYPE" divId="div_Addressaddresstype_dict" isNeedAuth="false" value="${personnel.address.addresstype}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td align="right" width="15%">${addressVt.property.extnumber}：</td>
<td width="30%" >
<input type="text" class="inputText" id="Address.extnumber" name="Address.extnumber" value="${personnel.address.extnumber}" <fisc:authentication sourceName="Address.extnumber" taskId=""/>>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%"valign="top"><font color="red">★</font>${addressVt.property.address}：</td>
<td width="30%" colspan="3" >
<textarea rows="4" cols="54" id="Address.address" name="Address.address" <fisc:authentication sourceName="Address.address" taskId=""/> >${personnel.address.address}</textarea>
</td>
</tr>
<input type="hidden" id="Address.addressid" name="Address.addressid" value="${personnel.address.addressid}">
</table>
</form>
</div>
<div id="div_orgPersonnel" ></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '';

//页面文本
var Txt={
	//人员
	personnel:'${vt.boText}',
	          
	//地址信息
	address:'${addressVt.boText}',
	//boText创建
	address_Create:'${addressVt.boTextCreate}',
	//boText复制创建
	address_CopyCreate:'${addressVt.boTextCopyCreate}',
	// 进行【地址信息复制创建】操作时，只允许选择一条记录！
	address_CopyCreate_AllowOnlyOneItemForOperation:'${addressVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【地址信息复制创建】操作的记录！
	address_CopyCreate_SelectToOperation:'${addressVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【地址信息批量删除】操作的记录！
	address_Deletes_SelectToOperation:'${addressVt.deletes_SelectToOperate}',
	// 您选择了【地址信息批量删除】操作，是否确定继续该操作？
	address_Deletes_ConfirmOperation:'${addressVt.deletes_ConfirmOperation}',
          
	//组织员工
	orgPersonnel:'${orgPersonnelVt.boText}',
	// 请选择需要进行【组织员工批量删除】操作的记录！
	orgPersonnel_Deletes_SelectToOperation:'${orgPersonnelVt.deletes_SelectToOperate}',
	// 您选择了【组织员工批量删除】操作，是否确定继续该操作？
	orgPersonnel_Deletes_ConfirmOperation:'${orgPersonnelVt.deletes_ConfirmOperation}',
	//boText复制创建
	personnel_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	personnel_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	personnel_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:0,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						           items:[
			              
						                {
						            		title:'${addressVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'addressTab',
						            		contentEl:'div_address'
						            	},
          
						                {
						            		title:'${orgPersonnelVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'orgPersonnelTab',
						            		contentEl:'div_orgPersonnel'
						            	}
						    ]}
						   ]}
]
				}
                 ]
	});
	
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_saveOrUpdatePersonnel',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdatePersonnel,iconCls:'icon-table-save'},'-',
          
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
	});
	
	

Ext.onReady(function(){
    var tabsSize = 2;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 2 ; i++){
		   tabs.setActiveTab(2-1-i);
		}
	}
 });
//});
</script>
