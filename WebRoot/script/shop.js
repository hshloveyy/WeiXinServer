//加载商铺列表
	function loadShopDg(){
		$('#shop_dg').datagrid({    
		    url:'shop/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center',formatter:function(value, row, index){
		        	return value + '<img id="img_' + value + '" width="100" height="120" src="fileService/Download?fileId=' + row.uploadPics + '"/>';
		        }},    
		        {field:'name',title:'名称',width:100,align:'center'},   
		        {field:'provinceName',title:'省份',width:40,align:'center'},  
		        {field:'cityName',title:'城市',width:40,align:'center'},  
		        {field:'category',title:'分类',width:40,align:'center'},  
		        {field:'subCategory',title:'子类',width:40,align:'center'},  
		        {field:'sort',title:'排序号',width:20,align:'center'},
		        {field:'op',title:'图片',width:100,align:'center',formatter:function(value,row,index){
						return '<form id="shop_' + row.id + '" method="post" enctype="multipart/form-data" action="fileService/Upload">'
									+ '<input type="file" name="file" value="上传图片" onchange="showSubmitButton(this, ' + row.id + ')"/></form><a href="javascript:uploadMore(' + row.id + ')">上传多张</a>';
					}
				} ] ],
							toolbar : [
									{
										text : '添加',
										iconCls : 'icon-add',
										handler : function() {
											$('#dd_addShop')
													.dialog(
															{
																title : '添加省份',
																width : 650,
																height : 400,
																closed : false,
																cache : false,
																href : 'shop/toAdd',
																modal : true,
																buttons : [
																		{
																			text : '保存',
																			handler : function() {
																				$(
																						'#ff_shopAdd')
																						.form(
																								'submit',
																								{
																									url : 'shop/save',
																									onSubmit : function() {
																										// do some check    
																										// return false to prevent submit;    
																									},
																									success : function(
																											data) {
																										data = eval('('
																												+ data
																												+ ')');
																										$(
																												'#dd_addShop')
																												.dialog(
																														'close');
																										$.messager
																												.show({
																													title : '我的消息',
																													msg : data.result ? '添加成功'
																															: '添加失败',
																													timeout : 5000,
																													showType : 'slide'
																												});
																										$(
																												'#shop_dg')
																												.datagrid(
																														'reload');
																									}
																								});

																			}
																		},
																		{
																			text : '关闭',
																			handler : function() {
																				$(
																						'#dd_addShop')
																						.dialog(
																								'close');
																			}
																		} ]
															});
										}
									},
									'-',
									{
										text : '编辑',
										iconCls : 'icon-edit',
										handler : function() {
											var row = $('#shop_dg').datagrid(
													'getSelected');
											if (row == null) {
												$.messager.alert('提示',
														'请选择一条记录进行操作', 'info');
												return;
											}
											$('#dd_addShop')
													.dialog(
															{
																title : '编辑省份',
																width : 650,
																height : 400,
																closed : false,
																cache : false,
																href : 'shop/toForm',
																modal : true,
																onLoad : function() {
																	$('#ff_shopEdit').form({
																		onLoadSuccess :function(data){
																			$('#shopEdit_city').val(data.city);
																			loadCity();
																			
																			$('#shopEdit_subclass').val(data.subclass);
																			loadSubCategory();
																		}
																	});
																	$('#ff_shopEdit').form(
																					'load',
																					'shop/toEdit?id='
																							+ row.id);
																	
																},
																buttons : [
																		{
																			text : '保存',
																			handler : function() {
																				$(
																						'#ff_shopEdit')
																						.form(
																								'submit',
																								{
																									url : 'shop/update',
																									onSubmit : function() {
																										// do some check    
																										// return false to prevent submit;    
																									},
																									success : function(
																											data) {
																										$(
																												'#dd_addShop')
																												.dialog(
																														'close');
																										data = eval('('
																												+ data
																												+ ')');
																										$.messager
																												.show({
																													title : '我的消息',
																													msg : data.result ? '修改成功'
																															: '修改失败',
																													timeout : 5000,
																													showType : 'slide'
																												});
																										$(
																												'#shop_dg')
																												.datagrid(
																														'reload');
																									}
																								});

																			}
																		},
																		{
																			text : '关闭',
																			handler : function() {
																				$(
																						'#dd_addShop')
																						.dialog(
																								'close');
																			}
																		} ]
															});
										}
									},
									'-',
									{
										text : '删除',
										iconCls : 'icon-remove',
										handler : function() {
											var row = $('#shop_dg').datagrid(
													'getSelected');
											if (row == null) {
												$.messager.alert('提示',
														'请选择一条记录进行操作', 'info');
												return;
											}
											$.messager
													.confirm(
															'确认对话框',
															'确认删除？',
															function(r) {
																if (r) {
																	$.get('shop/delete', 'id=' + row.id, function(data) {
																		$.messager.show({
																			title : '我的消息',
																			msg : data.result ? '删除成功' : '删除失败',
																			timeout : 5000,
																			showType : 'slide'
																		});
																		$('#shop_dg').datagrid('reload');
																	},'json');
																}
															});
										}
									},'-',{
										text : '营业时间设置',
										iconCls : 'icon-add',
										handler : function() {
											var row = $('#shop_dg').datagrid('getSelected');
											if (row == null) {
												$.messager.alert('提示', '请选择一条记录进行操作', 'info');
												return;
											}
											$('#dd_worktime').dialog({
													title : '设置营业时间',
													width : 450,
													height : 300,
													closed : false,
													cache : false,
													href : 'shop/toWorktime?shopId=' + row.id,
													modal : true,
													buttons : [{
														text : '保存',
														handler : function() {
															var str = '';
															$('#ff_shopWorktime table tr').each(function(index, tr){
																var first = $(tr).find('td input:eq(0)').val();
																var second = $(tr).find('td input:eq(1)').val();
																if(first && second){
																	str += index + '-' + first + '-' + second + ',';
																}
															});
															
															if(str != null && str != ''){
																$.post('shop/savetime',{times:str, shopId:row.id},function(data){
																	data = eval('(' + data + ')');
																	$.messager.show({
																		title : '我的消息',
																		msg : data.result ? '保存成功' : '保存失败',
																		timeout : 5000,
																		showType : 'slide'
																	});
																	$('#dd_worktime').dialog('close');
																});
															}
														}
													},
													{
														text : '关闭',
														handler : function() {
															$('#dd_worktime').dialog('close');
														}
													} ]
												});
										}
									}],
							loadMsg : '正在加载数据...'
						});
	}
	
	function showSubmitButton(obj, id){
		$(obj).after('<a href="javascript:void(0)" onclick="submitImage(' + id + ')" class="easyui-linkbutton" data-options="iconCls:"icon-search">提交</a>');
	}
	
	function submitImage(id){
		console.log($('#shop_' + id).length);
		$('#shop_' + id).form('submit',{
			url:'fileService/Upload',    
		    onSubmit: function(){    
		        // do some check    
		        // return false to prevent submit;    
		    },    
		    success:function(data){
		    	data = eval('(' + data + ')');
		        if(data.code == 1){
		        	$.post('shop/update',{
		        		id:id,
		        		uploadPics:data.data
		        	},function(data2){
		        		if(data2.result){
				        	$('#shop_' + id + ' a').remove();
				        	$('#img_' + id).attr('src','fileService/Download?fileId=' + data.data + '');
		        		}
		        	},'json');
		        }
		    }    
		});
		
	}
	
	function uploadMore(id){
		$('#dd_uploadMore').dialog({
				title : '批量上传图片',
				width : 800,
				height : 600,
				closed : false,
				cache : false,
				href : 'shop/uploadmore',
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
						
						$.post('shop/uploadImages',{
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
								$('#dd_uploadMore').dialog('close');
							}else{
								$.messager.show({
									title : '我的消息',
									msg : '上传失败',
									timeout : 5000,
									showType : 'slide'
								});
								$('#dd_uploadMore').dialog('close');
							}
						});
//						$('#ff_shopMoreImage').form('submit',{
//								url : 'shop/uploadImages',
//								onSubmit : function(param) {
//									param['id'] = id;
//									// do some check    
//									// return false to prevent submit;    
//								},
//								success : function(data) {
//									data = eval('(' + data + ')');
//									$.messager .show({
//										title : '我的消息',
//										msg : data.result ? '上传成功' : '上传失败',
//										timeout : 5000,
//										showType : 'slide'
//									});
//									
//									if(data.result){
//										$('#dd_uploadMore').dialog('close');
//									}
//							}
//						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#dd_uploadMore').dialog('close');
					}
				}]
		});
	}
	
	function loadCity(){
		var provinceId = $('#province').val();
		$('#shop_add_city option').remove();
		$.get('province/provinceChild', {id:provinceId}, function(data){
			$.each(data.obj.citys, function(index, item){
				$('#shop_add_city').append('<option value="' + item.id + '">' + item.cityName + '</option>');
			});
			
			var shopEditCity = $('#shopEdit_city').val();
			if(shopEditCity){
				$('#shop_add_city option[value="' + shopEditCity + '"]').attr('selected','selected');
			}
		}, 'json');
	}