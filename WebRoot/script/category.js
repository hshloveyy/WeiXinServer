//加载类别列表
	function loadCategoryDg(){
		$('#category_dg').datagrid({    
		    url:'category/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center'},    
		        {field:'categoryName',title:'名称',width:100,align:'center'},    
		        {field:'sort',title:'排序号',width:100,align:'center'}
		    ]],
		    toolbar: [{
		    	text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					$('#dd_addCategory').dialog({    
					    title: '添加省份',    
					    width: 400,    
					    height: 200,    
					    closed: false,    
					    cache: false,    
					    href: 'category/toAdd',    
					    modal: true,
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_categoryAdd').form('submit',{    
								    url:'category/save',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	data = eval('(' + data + ')');
								    	$('#dd_addCategory').dialog('close');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'添加成功':'添加失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#category_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addCategory').dialog('close');
							}
						}]   
					});    
				}
			},'-',{
		    	text:'编辑',
				iconCls: 'icon-edit',
				handler: function(){
					var row = $('#category_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$('#dd_addCategory').dialog({    
					    title: '编辑省份',    
					    width: 400,    
					    height: 200,    
					    closed: false,    
					    cache: false,    
					    href: 'category/toForm',    
					    modal: true,
					    onLoad:function(){
					    	$('#ff_categoryEdit').form('load','category/toEdit?id=' + row.id);
					    },
					    buttons:[{
							text:'保存',
							handler:function(){
								$('#ff_categoryEdit').form('submit',{    
								    url:'category/update',    
								    onSubmit: function(){    
								        // do some check    
								        // return false to prevent submit;    
								    },    
								    success:function(data){
								    	$('#dd_addCategory').dialog('close');
								    	data = eval('(' + data + ')');
								    	$.messager.show({
								    		title:'我的消息',
								    		msg:data.result?'修改成功':'修改失败',
								    		timeout:5000,
								    		showType:'slide'
								    	});
								    	$('#category_dg').datagrid('reload');
								    }    
								});    

							}
						},{
							text:'关闭',
							handler:function(){
								$('#dd_addCategory').dialog('close');
							}
						}]   
					}); 
				}
			},'-',{
		    	text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#category_dg').datagrid('getSelected');
					if(row == null){
						$.messager.alert('提示','请选择一条记录进行操作','info');
						return;
					}
					$.messager.confirm('确认对话框', '确认删除？', function(r){
						if (r){
						    $.get('category/delete','id=' + row.id,function(data){
						    	$.messager.show({
						    		title:'我的消息',
						    		msg:data.result?'删除成功':'删除失败',
						    		timeout:5000,
						    		showType:'slide'
						    	});
						    	$('#category_dg').datagrid('reload');
						    },'json');
						}
					});
				}
			}],
			loadMsg:'正在加载数据...'
		});
	}
	
	function loadSubCategory(){
		var categoryId = $('#category').val();
		$('#shop_add_subcategory option').remove();
		$.get('category/categoryChild', {id:categoryId}, function(data){
			$.each(data.obj.subCategorys, function(index, item){
				$('#shop_add_subcategory').append('<option value="' + item.id + '">' + item.subCategoryName + '</option>');
			});
			
			var shopEditSubclass = $('#shopEdit_subclass').val();
			if(shopEditSubclass){
				$('#shop_add_subcategory option[value="' + shopEditSubclass + '"]').attr('selected','selected');
			}
		}, 'json');
	}