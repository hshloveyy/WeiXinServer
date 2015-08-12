//加载商铺列表
	function loadYellowPageDg(){
		$('#yellowpage_dg').datagrid({    
		    url:'page/list',   
		    pagination:true,
		    fitColumns:true,
		    singleSelect:true,
		    striped:true,
		    columns:[[    
		        {field:'id',title:'ID',width:100,align:'center'},    
		        {field:'name',title:'名称',width:100,align:'center'},   
		        {field:'province',title:'省份',width:40,align:'center'},  
		        {field:'city',title:'城市',width:40,align:'center'},  
		        {field:'category',title:'分类',width:40,align:'center'},  
		        {field:'subCategory',title:'子类',width:40,align:'center'},  
		        {field:'sort',title:'排序号',width:20,align:'center'}
		        ] ],
				toolbar : [
						{
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								$('#dd_addPage')
										.dialog(
												{
													title : '添加黄页信息',
													width : 650,
													height : 380,
													closed : false,
													cache : false,
													href : 'page/toAdd',
													modal : true,
													buttons : [
															{
																text : '保存',
																handler : function() {
																	$(
																			'#ff_pageAdd')
																			.form(
																					'submit',
																					{
																						url : 'page/save',
																						onSubmit : function() {
																							// do some check    
																							// return false to prevent submit;    
																						},
																						success : function(
																								data) {
																							data = eval('('
																									+ data
																									+ ')');
																							$('#dd_addPage')
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
																									'#yellowpage_dg')
																									.datagrid(
																											'reload');
																						}
																					});

																}
															},
															{
																text : '关闭',
																handler : function() {
																	$('#dd_addPage')
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
								var row = $('#yellowpage_dg').datagrid(
										'getSelected');
								if (row == null) {
									$.messager.alert('提示',
											'请选择一条记录进行操作', 'info');
									return;
								}
								$('#dd_addPage')
										.dialog(
												{
													title : '编辑黄页信息',
													width : 650,
													height : 380,
													closed : false,
													cache : false,
													href : 'page/toForm',
													modal : true,
													onLoad : function() {
														$('#ff_pageEdit').form({
															onLoadSuccess :function(data){
																$('#pageEdit_city').val(data.city);
																loadPageCity();
																
																$('#pageEdit_subkind').val(data.subKindId);
																loadSubKind();
															}
														});
														$(
																'#ff_pageEdit')
																.form(
																		'load',
																		'page/toEdit?id='
																				+ row.id);
													},
													buttons : [
															{
																text : '保存',
																handler : function() {
																	$(
																			'#ff_pageEdit')
																			.form(
																					'submit',
																					{
																						url : 'page/update',
																						onSubmit : function() {
																							// do some check    
																							// return false to prevent submit;    
																						},
																						success : function(
																								data) {
																							$('#dd_addPage')
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
																									'#yellowpage_dg')
																									.datagrid(
																											'reload');
																						}
																					});

																}
															},
															{
																text : '关闭',
																handler : function() {
																	$('#dd_addPage')
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
								var row = $('#yellowpage_dg').datagrid(
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
														$
																.get(
																		'page/delete',
																		'id='
																				+ row.id,
																		function(
																				data) {
																			$.messager
																					.show({
																						title : '我的消息',
																						msg : data.result ? '删除成功'
																								: '删除失败',
																						timeout : 5000,
																						showType : 'slide'
																					});
																			$(
																					'#yellowpage_dg')
																					.datagrid(
																							'reload');
																		},
																		'json');
													}
												});
							}
						} ],
				loadMsg : '正在加载数据...'
			});
	}
	
	function showSubmitPageButton(obj, id){
		$(obj).after('<a href="javascript:void(0)" onclick="submitImage(' + id + ')" class="easyui-linkbutton" data-options="iconCls:"icon-search">提交</a>');
	}
	
	function submitPageImage(id){
		$('#shop_' + id).form('submit',{
			url:'fileService/Upload',    
		    onSubmit: function(){    
		        // do some check    
		        // return false to prevent submit;    
		    },    
		    success:function(data){
		    	data = eval('(' + data + ')');
		        if(data.code == 1){
		        	$.post('page/update',{
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
	
	function loadPageCity(){
		var provinceId = $('#pageprovince').val();
		$('#page_add_city option').remove();
		$.get('pageprovince/provinceChild', {id:provinceId}, function(data){
			$.each(data.obj.citys, function(index, item){
				$('#page_add_city').append('<option value="' + item.id + '">' + item.cityName + '</option>');
			});
			
			var shopEditCity = $('#pageEdit_city').val();
			if(shopEditCity){
				$('#page_add_city option[value="' + shopEditCity + '"]').attr('selected','selected');
			}
		}, 'json');
	}