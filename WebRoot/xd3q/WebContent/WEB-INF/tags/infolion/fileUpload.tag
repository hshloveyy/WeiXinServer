<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="resourceId" required="true" description="资源编号"%>
<%@ attribute name="resourceName" required="true" description="资源名称"%>
<%@ attribute name="recordId" required="false" description="记录Id"%>
<%@ attribute name="increasable" required="false" description="是否可修改,决定上传按键是否可用"%> 
<%@ attribute name="erasable" required="false" description="是否可修改,决定删除按键是否可用"%> 
<%@ attribute name="recordIdHiddenInputName" required="true" description="当projectId不是在页面打开时就存在时,等生成后,向此隐藏域填值;此时用于传递recordId 的隐藏输入域"%>
<script src="<%= request.getContextPath() %>/js/ext/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/js/ext/ext-all.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/js/ext/xmdp.js" type="text/javascript"></script>

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
<script type="text/javascript" defer="defer">
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
		labelWidth:40,
		width:570,
		fileUpload:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		defaults:{
			anchor:'95%',
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
			width:500,
			anchor:'95%'
		},{
			id:'description',
			width:500,
			allowBlank:true,
			xtype:'textarea',
			fieldLabel:'备注'
		},{
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
           width: 100,
           sortable: false,
           dataIndex: 'OLD_NAME'
          },{
           header: '描述',
           width: 200,
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
        var url = encodeURI('/extComponentServlet?type=fileDownload&fileName='+value);
        return '<b><a href="'+url+'" target="_self">下-载</a></b>';
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
										Ext.Ajax.request({
											url: '<%=request.getContextPath()%>/extComponentServlet?type=fileDelete&fileName='+records[0].data.READ_NAME+'&attachementId='+records[0].data.ATTACHEMENT_ID,
											params:'',
											success: function(response, options){
												var resp = Ext.util.JSON.decode(response.responseText);
												top.ExtModalWindowUtil.alert('提示',resp.msg);
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
								url:encodeURI('<%=request.getContextPath()%>/extComponentServlet?type=fileUpload&description='+Ext.getCmp('description').getValue()+'&filepath='+Ext.getCmp('fileSelected').getValue())+result,
								success:function(form,action){
									Ext.Msg.alert('Success','文件上传成功');
									var url = '<%=request.getContextPath()%>/extComponentServlet?type=fileGrid'+result;
									ds.proxy= new Ext.data.HttpProxy({url:url});
									ds.load({params:{start:0, limit:5}});
									//ds.reload();

								},
								waitMsg: '正在上传文件...',
								failure:function(form,action){
									Ext.Msg.alert('Error','文件上传失败');
								}
							}
						)}
					}
	});
    var gridTbar= new Ext.Toolbar({
    	items:[add,'-',del]
    });
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
<input type="hidden" name="${recordIdHiddenInputName}"/>

