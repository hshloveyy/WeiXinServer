//加载城市列表
	function loadNoticeDg(){
		$('#notice_dg').datagrid({    
		    url:'common/noticelist',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:20,align:'center'},    
		        {field:'noticeTheme',title:'主题',width:60,align:'center'},    
		        {field:'noticeContent',title:'内容',width:100,align:'center'},    
		        {field:'createTime',title:'发表日期',width:50,align:'center'}
		    ]],
		    toolbar: [{
		    	text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					$('#dd_addNotice').dialog({    
					    title: '发表通知',    
					    width: 600,    
					    height: 400,    
					    closed: false,    
					    cache: false,    
					    href: 'common/toAddNotice',    
					    modal: true,
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_NoticeAdd').form('submit',{    
								    url:'common/addnotice',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	data = eval('(' + data + ')');
								    	$('#dd_addNotice').dialog('close');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'添加成功':'添加失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#notice_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addNotice').dialog('close');
							}
						}]   
					});    
				}
			},'-',{
		    	text:'编辑',
				iconCls: 'icon-edit',
				handler: function(){
					var row = $('#notice_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$('#dd_addNotice').dialog({    
					    title: '编辑省份',    
					    width: 600,    
					    height: 400,    
					    closed: false,    
					    cache: false,    
					    href: 'common/toNoticeForm',    
					    modal: true,
					    onLoad:function(){
					    	$('#ff_NoticeEdit').form('load','common/toNoticeEdit?id=' + row.id);
					    },
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_NoticeEdit').form('submit',{    
								    url:'common/updateNotice',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	$('#dd_addNotice').dialog('close');
								    	data = eval('(' + data + ')');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'修改成功':'修改失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#notice_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addNotice').dialog('close');
							}
						}]   
					}); 
				}
			},/*'-',{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#notice_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('Notice/delete','id=' + row.id,function(data){
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#notice_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}*/],
			loadMsg:'正在加载数据...'
		});
	}