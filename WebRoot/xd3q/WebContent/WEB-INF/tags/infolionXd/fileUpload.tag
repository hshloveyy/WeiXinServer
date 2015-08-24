<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.sapss.Constants"%>
<%@ tag import="com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService"%>
<%@ tag import="com.infolion.platform.util.EasyApplicationContextUtils"%>
<%@ attribute name="resourceId" required="true" description="资源编号"%>
<%@ attribute name="resourceName" required="true" description="资源名称"%>
<%@ attribute name="recordId" required="false" description="记录Id"%>
<%@ attribute name="increasable" required="false" description="是否可修改,决定上传按键是否可用"%> 
<%@ attribute name="erasable" required="false" description="是否可修改,决定删除按键是否可用"%> 
<%@ attribute name="recordIdHiddenInputName" required="true" description="当projectId不是在页面打开时就存在时,等生成后,向此隐藏域填值;此时用于传递recordId 的隐藏输入域"%>

<%@ attribute name="divId" required="true" description="div ID"%>
<%
	String url = "&resourceName="+resourceName+"&resourceId="+resourceId;
	jspContext.setAttribute("fileprojectId",url);
	String creasable ="true";
	if(null!=increasable && !"".equals(increasable)){
		creasable = increasable.toLowerCase();
	}
	jspContext.setAttribute("increasable",creasable=="true"?"false":"true");
	
	String erase ="true";
	if(null!=erasable && !"".equals(erasable)){
		erase = erasable.toLowerCase();
	}
	jspContext.setAttribute("erasable",erase=="true"?"false":"true");
	
%>
	
	<%/*******20140825modify by cat 新增对附件的处理***********/
	AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
	int i = abs.fileUploadModifyCheck((String)jspContext.getAttribute("recordId"));
	%>
<script language="javascript" src="<%= request.getContextPath() %>/js/ext/CrossDomainAjax.js"></script>
<script type="text/javascript">
   	function fileUploadCheck(){   	     
	  	Ext.Ajax.request({
			url: encodeURI('/extComponentServlet?type=fileUploadCheck&userId=<%=Constants.ins().getCurrentUserId()%>&filepath='+Ext.getCmp('fileSelected').getValue()),
			params:'',
			success: function(response, options){
				var resp = Ext.util.JSON.decode(response.responseText);
				top.ExtModalWindowUtil.alert('提示',resp.msg);
			}	
	    });
   	}
   	
   function valiDateTimes(str){                 
     //var reg =  /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
       var reg =  /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;        
     var r = str.match(reg); 
     if(r==null) return false; 
     r[2]=r[2]-1; 
     var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]); 
          
     if(d.getFullYear()!=r[1]) return false; 
     if(d.getMonth()!=r[2]) return false; 
     if(d.getDate()!=r[3]) return false; 
     if(d.getHours()!=r[4]) return false; 
     if(d.getMinutes()!=r[5]) return false; 
     if(d.getSeconds()!=r[6]) return false; 
     return true;
    }
var ${divId}_ns_ds;
Ext.namespace('${divId}_ns');
 ${divId}_ns.fuc=function(){
 	return {
 	fileUpload:function(){
	var formUpload=new Ext.form.FormPanel({
		applyTo:'${divId}',
		frame:true,
		baseCls:'x-plain',
		autoHeight:true,
		labelWidth:60,
		width:520,
		fileUpload:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		defaults:{
			anchor:'90%',
			msgTarget:'side'
		},
		items:[{
			id:'fileSelected',
			xtype:'textfield',
			fieldLabel:'文件',
			name:'upload',
			inputType:'file',
			allowBlank:false,
			blankText:'请选择文件',
			width:400,
			anchor:'90%'
		},
		{
			id:'description',
			width:400,
			height:40,
			allowBlank:true,
			xtype:'textarea',
			fieldLabel:'备注1'
		},
    <%if(i==1){%>
    	 {id:'modifyAddTime',
			width:200,
			allowBlank:true,
			xtype:'textfield',
			inputType:'text',
			emptyText:'yyyy-MM-dd HH:mm:ss',
			fieldLabel:'上传时间'
		},
    <%}	%>
		{
			contentEl:'fileGrid',
			title:'文件列表'
		}]
	});
	var rdId = '${recordId}';
	var dsUrl;
	if(rdId=='')
		dsUrl = '${fileprojectId}'+'&recordId='+Ext.getDom('${recordIdHiddenInputName}').value;
	else
		dsUrl = '${fileprojectId}'+'&recordId=${recordId}';
	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'<%=request.getContextPath()%>/extComponentServlet?type=fileGrid'+dsUrl}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'OLD_NAME'},
					{name:'READ_NAME'},
					{name:'ATTACHEMENT_CMD'},
					{name:'UPLOAD_TIME'},
					{name:'ATTACHEMENT_ID'}
          		])
    });
    ${divId}_ns_ds = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           header: '上传时间',
           width: 100,
           sortable: true,
           dataIndex: 'UPLOAD_TIME'
          },{
           header: '原文件名',
           width: 250,
           sortable: false,
           dataIndex: 'OLD_NAME'
          },{
           header: '描述',
           width: 50,
           sortable: false,
           renderer:renderTopic,
           dataIndex: 'ATTACHEMENT_CMD'
          },{
           header: '下载',
           width: 100,
           sortable: false,
           dataIndex: 'READ_NAME',
           renderer:renderToHref
          },{
           header: 'id',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'ATTACHEMENT_ID'
          }
    ]);
    
    function renderTopic(value, p, record){
        if(value==null) return '';
        return '<span onclick=Ext.Msg.alert("Msg","'+value+'");>'+value+'<span>';
    }
   function renderToHref(value, p, record){
        var url = encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=fileDownload&fileName='+value);
        return '<b><a href="'+url+'" target="_self">下载</a></b>';
    }
    cm.defaultSortable = true;
    
    var paging = new Ext.PagingToolbar({
        pageSize: 5,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
   	var del = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	    disabled:${erasable},
		handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
					}else{
							Ext.Msg.show({
   								msg: '是否确定删除',
   								buttons: {yes:'确定', no:'取消'},
   								fn: function(buttonId,text){
 									if(buttonId=='yes'){
 									    SetCrossDomain();
										Ext.Ajax.request({
											url: '<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=fileDelete&userId=<%=Constants.ins().getCurrentUserId()%>&fileName='+records[0].data.READ_NAME+'&attachementId='+records[0].data.ATTACHEMENT_ID,
											params:'',
											scriptTag: true,
											success: function(response){
												top.ExtModalWindowUtil.alert('提示',response.responseText);
												ds.reload();
											}	
										});
									}
   								},
   								icon: Ext.MessageBox.WARNING
							});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
		}
   	});

   	var add = new Ext.Toolbar.Button({
			id:'btn',
			text:'上传',
			iconCls:'add',
			disabled:${increasable},
			handler:function(){
						var recordId = Ext.getDom('${recordIdHiddenInputName}').value;
						if('${recordId}'!='')
							recordId = '${recordId}';
						var	result = '&recordId='+recordId+'${fileprojectId}';
						if(formUpload.form.isValid()){
							formUpload.getForm().submit({
								url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=fileUpload&userId=<%=Constants.ins().getCurrentUserId()%>&description='+Ext.getCmp('description').getValue()+'&filepath='+Ext.getCmp('fileSelected').getValue())+result,
								success:function(form,action){
									setTimeout("fileUploadCheck()",1000);
						            ds.reload();
						            formUpload.getForm().reset();
								},
								waitMsg: '正在上传文件...',
								failure:function(form,action){
									Ext.Msg.alert('Error','文件上传失败');
								}
							})
						}
						
						
					}
	});
	/*******20140825modify by cat 新增对附件的处理***********/
	
   	var modifyAdd = new Ext.Toolbar.Button({
		id:'modifyAdd',
		text:'补录上传',
		iconCls:'add',
		handler:function(){
					var recordId = Ext.getDom('${recordIdHiddenInputName}').value;
					if('${recordId}'!='')
						recordId = '${recordId}';
					var	result = '&recordId='+recordId+'${fileprojectId}';
					if(formUpload.form.isValid()){
					    var modifyAddTime = Ext.getCmp('modifyAddTime').getValue();
					    
					    if(!valiDateTimes(modifyAddTime)) {
					       Ext.Msg.alert('Error','上传时间格式错误');
					       return;
					    }
						formUpload.getForm().submit({
							url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=modifyAdd&userId=<%=Constants.ins().getCurrentUserId()%>&description='+Ext.getCmp('description').getValue()+'&filepath='+Ext.getCmp('fileSelected').getValue()+result+'&modifyAddTime='+modifyAddTime),
							success:function(form,action){
								setTimeout("fileUploadCheck()",1000);
					            ds.reload();
					            formUpload.getForm().reset();
							},
							waitMsg: '正在上传文件...',
							failure:function(form,action){
								Ext.Msg.alert('Error','文件上传失败');
							}
						})
					}
					
					
				}
	});

    var modifyDel = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行删除！');
					}else{
							Ext.Msg.show({
   								msg: '是否确定删除',
   								buttons: {yes:'确定', no:'取消'},
   								fn: function(buttonId,text){
 									if(buttonId=='yes'){
 									    SetCrossDomain();
										Ext.Ajax.request({
											url: encodeURI('<%=Constants.FILE_LOCATION_URL%>extComponentServlet?type=modifyDel&userId=<%=Constants.ins().getCurrentUserId()%>&fileName='+records[0].data.READ_NAME+'&attachementId='+records[0].data.ATTACHEMENT_ID),
											params:'',
											scriptTag: true,
											success: function(response){
												top.ExtModalWindowUtil.alert('提示',response.responseText);
												ds.reload();
											}	
										});
									}
   								},
   								icon: Ext.MessageBox.WARNING
							});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
		}
   	});
    var modifyReplace = new Ext.Toolbar.Button({
   		text:'替换',
	    iconCls:'add',
		handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
					}else{
							Ext.Msg.show({
   								msg: '是否确定修改',
   								buttons: {yes:'确定', no:'取消'},
   								fn: function(buttonId,text){
 									if(buttonId=='yes'){
										if('${recordId}'!='')
											recordId = '${recordId}';
										var	result = '&recordId='+recordId+'${fileprojectId}'+'&attachementId='+records[0].data.ATTACHEMENT_ID;
										if(formUpload.form.isValid()){
											formUpload.getForm().submit({
												url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=modifyReplace&userId=<%=Constants.ins().getCurrentUserId()%>&description='+Ext.getCmp('description').getValue()+'&filepath='+Ext.getCmp('fileSelected').getValue())+result,
												success:function(form,action){
													setTimeout("fileUploadCheck()",1000);
										            ds.reload();
										            formUpload.getForm().reset();
												},
												waitMsg: '正在上传文件...',
												failure:function(form,action){
													Ext.Msg.alert('Error','文件上传失败');
												}
											})
										}
									}
   								},
   								icon: Ext.MessageBox.WARNING
							});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
		}
   	});
	/******************/
    var gridTbar= new Ext.Toolbar({    
    <%if(i==1){%>
    	items:[add,'-',del,'-',modifyAdd,'-',modifyDel,'-',modifyReplace,'-']
    <%} else {%>
        items:[add,'-',del]
    <%}%>
    });
    /************/

    /************/
 //	ds.load({params:{start:0, limit:10}});
 	var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        height:135,
        width:550,
        bbar: paging,
        tbar:gridTbar,
        border:false,
        loadMask:true,
        applyTo:'fileGrid',
        layout:'fit'
    }); 	
}}}();
 Ext.onReady(${divId}_ns.fuc.fileUpload,${divId}_ns.fuc);
</script>
<div id="fileGrid"></div>
<input type="hidden" id="${recordIdHiddenInputName}" name="${recordIdHiddenInputName}"/>

