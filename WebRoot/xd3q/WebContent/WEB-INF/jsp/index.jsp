<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>ExtTLD Examples</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
<script type="text/javascript" defer="defer" src="js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" defer="defer" src="js/ext/ext-all.js"></script>

<div class="x-hide-display" id="ext_panel_tag5fadceEl"></div>
<div class="x-hide-display" id="ext_panel_tagb28f30El">
<iframe id="codePreview" frameborder="0"  scrolling="no" src="" height="90%" width="100%"></iframe></div>
<div class="x-hide-display" id="ext_panel_tag17d80e9El">
<div id="compPreview"><img
	src="img/examples/components/window.png" /></div>
</div>
<script> 
		Ext.onReady(function(){
			var viewport = new Ext.Viewport({
							   layout:'border',
							   items:[
							   new Ext.Panel({
			  					   id:'ext_panel_tag5fadce',
			  			           region:'west',
			  			           width:200,
			  			           split:true,
				                   items:[
							           new Ext.tree.TreePanel({
		    	                           loader:new Ext.tree.TreeLoader(),
		    	                           rootVisible:false,
		  			                       id:'ext_tree_treePanel_tag18bd37a',
		  			                       title:'Examples',
		  			                       border:false,
				                           root:new Ext.tree.AsyncTreeNode({
			                                        text: 'Top Site',
			                                        draggable:false,
			                                        id:'source',
				                                    children:${jsondata}
				                                }),
		  		                          listeners:{
		  		                          }
		                               })
				                  ],
			  		              listeners:{
			  		              },
					              contentEl:'ext_panel_tag5fadceEl'
			                  }),
			                  new Ext.TabPanel({
			   					  border:false,
			  					  region:'center',
			  					  defaults:{autoScroll:true},
			  			  		  activeItem:'0',
			  			 		  border:true,
				        		  items:[
					    	 			new Ext.Panel({
			  					       		id:'ext_panel_tag17d80e9',
			  					 			title:'show',
			  					 			listeners:{
			  						    	},
							      		contentEl:'ext_panel_tagb28f30El'//ext_panel_tagb28f30El,ext_panel_tag17d80e9El
			    		      			})
				        		  ],
								  listeners:{
			  					  }
			   		         })
						],
		  				listeners:{
		  				}
		   			 })
				})
		Ext.onReady(function(){
			try {		
				ext_panel_tag5fadce = Ext.getCmp('ext_panel_tag5fadce');
				ext_tree_treePanel_tag18bd37a = Ext.getCmp('ext_tree_treePanel_tag18bd37a');
				ext_panel_tagb28f30 = Ext.getCmp('ext_panel_tagb28f30');
				ext_panel_tag17d80e9 = Ext.getCmp('ext_panel_tag17d80e9');
			} catch(e){};
		});
		function loadExample(exId){
			Ext.getDom("codePreview").src=exId;
		}
	</script>
</body>
</html>