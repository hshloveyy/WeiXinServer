//加载类别列表
	function loadAdvertDg(){
		$('#advert_dg').datagrid({    
		    url:'advert/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center'},    
		        {field:'city',title:'城市',width:100,align:'center'},
		        {field:'page',title:'商铺',width:100,align:'center'},
		        {field:'type',title:'尺寸',width:100,align:'center',formatter:function(value,row,index){
		        	if(value == 1){
		        		return "大尺寸";
		        	}else{
		        		return "小尺寸";
		        	}
		        }},
		        {field:'sort',title:'排序号',width:100,align:'center'},
		        {field:'ul',title:'操作',width:100,align:'center',formatter:function(value, row,index){
		        	return '<a href="javascript:uploadMoreAdvert(' + row.id + ')">上传多张</a>';
		        }}
		    ]],
		    toolbar: [{
		    	text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					$('#dd_addAdvert').dialog({    
					    title: '添加广告',    
					    width: 500,    
					    height: 400,    
					    closed: false,    
					    cache: false,    
					    href: 'advert/toAdd',    
					    modal: true,
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_advertAdd').form('submit',{    
								    url:'advert/save',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	data = eval('(' + data + ')');
								    	$('#dd_addAdvert').dialog('close');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'添加成功':'添加失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#advert_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addAdvert').dialog('close');
							}
						}]   
					});    
				}
			},'-',{
		    	text:'编辑',
				iconCls: 'icon-edit',
				handler: function(){
					var row = $('#advert_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$('#dd_addAdvert').dialog({    
					    title: '编辑省份',    
					    width: 400,    
					    height: 200,    
					    closed: false,    
					    cache: false,    
					    href: 'advert/toForm',    
					    modal: true,
					    onLoad:function(){
					    	$('#ff_advertEdit').form('load','advert/toEdit?id=' + row.id);
					    },
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_advertEdit').form('submit',{    
								    url:'advert/update',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	$('#dd_addAdvert').dialog('close');
								    	data = eval('(' + data + ')');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'修改成功':'修改失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#advert_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addAdvert').dialog('close');
							}
						}]   
					}); 
				}
			},'-',{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#advert_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('advert/delete','id=' + row.id,function(data){
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#advert_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}],
			loadMsg:'正在加载数据...'
		});
	}
		
	function uploadMoreAdvert(id){
		$('#dd_uploadMoreAdvert').dialog({
			title : '批量上传图片',
			width : 800,
			height : 600,
			closed : false,
			cache : false,
			href : 'advert/uploadmoreadvert',
			modal : true,
			buttons : [{
				text : '保存',
				handler : function() {
					var images = '';
					$('input[name="imageId"]').each(function(index,item){
						var imgid = $(item).val();
						if(imgid){
							images += (index == 0?'':',') + imgid;
						}
					});
					
					$.post('advert/uploadImages',{
						id:id,
						images:images
					},function(data){
						data = eval('(' + data + ')');
						if(data.code == '1'){
							$.messager.show({
								title : '我的消息',
								msg : '上传成功',
								timeout : 5000,
								showType : 'slide'
							});
							$('#dd_uploadMoreAdvert').dialog('close');
						}else{
							$.messager.show({
								title : '我的消息',
								msg : '上传失败',
								timeout : 5000,
								showType : 'slide'
							});
							$('#dd_uploadMoreAdvert').dialog('close');
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
					$('#dd_uploadMore').dialog('close');
				}
			}]
		});
	}