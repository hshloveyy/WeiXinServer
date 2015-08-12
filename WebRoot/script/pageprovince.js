//加载省份列表
	function loadPageProvinceDg(){
		$('#pageprovince_dg').datagrid({    
		    url:'pageprovince/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center'},    
		        {field:'name',title:'名称',width:100,align:'center'},    
		        {field:'enName',title:'英文名',width:100,align:'center'},    
		        {field:'sort',title:'排序号',width:100,align:'center'}
		    ]],
		    toolbar: [{
		    	text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					$('#dd_addPageProvince').dialog({    
					    title: '添加省份',    
					    width: 400,    
					    height: 200,    
					    closed: false,    
					    cache: false,    
					    href: 'pageprovince/toAdd',    
					    modal: true,
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_pageProvinceAdd').form('submit',{    
								    url:'pageprovince/save',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	$('#dd_addPageProvince').dialog('close');
								    	data = eval('(' + data + ')');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'添加成功':'添加失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#pageprovince_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addPageProvince').dialog('close');
							}
						}]   
					});    
				}
			},'-',{
		    	text:'编辑',
				iconCls: 'icon-edit',
				handler: function(){
					var row = $('#pageprovince_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$('#dd_addPageProvince').dialog({    
					    title: '编辑省份',    
					    width: 400,    
					    height: 200,    
					    closed: false,    
					    cache: false,    
					    href: 'pageprovince/toForm',    
					    modal: true,
					    onLoad:function(){
					    	$('#ff_pageProvinceEdit').form('load','pageprovince/toEdit?id=' + row.id);
					    },
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_pageProvinceEdit').form('submit',{    
								    url:'pageprovince/update',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	$('#dd_addPageProvince').dialog('close');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'修改成功':'修改失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#pageprovince_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addPageProvince').dialog('close');
							}
						}]   
					}); 
				}
			},'-',{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#pageprovince_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('pageprovince/delete','id=' + row.id,function(data){
						    	data = eval('(' + data + ')');
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#pageprovince_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}],
			loadMsg:'正在加载数据...'
		}); 
	}