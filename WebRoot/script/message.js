//加载黄页分类列表
	function loadMessageDg(){
		$('#message_dg').datagrid({    
		    url:'message/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center'},    
		        {field:'userName',title:'用户名',width:100,align:'center'},    
		        {field:'email',title:'邮箱',width:100,align:'center'},    
		        {field:'theme',title:'主题',width:100,align:'center'},    
		        {field:'content',title:'内容',width:100,align:'center'}
		    ]],
		    toolbar: [{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#message_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('message/delete','id=' + row.id,function(data){
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#message_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}],
			loadMsg:'正在加载数据...'
		}); 
	}