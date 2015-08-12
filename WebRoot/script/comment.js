//加载足迹列表
	function loadCommentDg(){
		$('#comment_dg').datagrid({    
		    url:'comment/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[   
		        {field:'id',title:'ID',width:15,align:'center'},    
		        {field:'userName',title:'评论用户',width:20,align:'center'},    
		        {field:'shopName',title:'商铺',width:35,align:'center'},      
		        {field:'commentContent',title:'内容',width:100,align:'center'},    
		        {field:'consume',title:'消费',width:10,align:'center',formatter:function(value,rowIndex, rowData){
		        	if(value){
		        		return '$' + value;
		        	}else{
		        		return value;
		        	}
		        }},  
		        {field:'grade',title:'评分',width:10,align:'center'}
		    ]],
		    toolbar: [{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#comment_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('comment/delete','id=' + row.id,function(data){
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#comment_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}],
			loadMsg:'正在加载数据...'
		});
	}