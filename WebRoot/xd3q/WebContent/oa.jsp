<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<HTML>
<HEAD>
    <TITLE></TITLE>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/buffalo.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/desktopTitle.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/desktopMove.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/desktop.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/desktopData.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/desktopFunc.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/pageCommon.js"></script>
	<script language="javascript" src="<%= request.getContextPath() %>/js/OA/desktop/pictureNews.js"></script>
	
	<LINK href="<%= request.getContextPath() %>/css/desktop.css" type="text/css" media="screen" rel="stylesheet">
	<LINK href="<%= request.getContextPath() %>/css/mode.css" type="text/css" media="screen" rel="stylesheet">

    <base target="_self">
</HEAD>
<body marginwidth="0" topmargin="0" leftmargin="0" onload="onLoad()" marginheight="0">
<script language=javascript>
	var waitingImg='<%= request.getContextPath() %>/images/oa/loading.gif';
	var minImg='<%= request.getContextPath() %>/images/oa/min.gif';
	var revertImg='<%= request.getContextPath() %>/images/oa/revert.gif';
	var loadingImg='<%= request.getContextPath() %>/images/oa/loading.gif';
	var listImg='<%= request.getContextPath() %>/images/oa/list.gif';
	var moreImg='<%= request.getContextPath() %>/images/oa/more.gif';
	var newImg='<%= request.getContextPath() %>/images/oa/new.gif';
	
	var endPointTop='/OAapp/bfapp';
	var xmlPath='/oa/js/CN/city/';
	var weatherImgPath='/images/oa/weather/';
</script>

<form method="post" name="actForm" action="">
<a id="msgQuery" href=""></a>
<a id="fileDownload" href=""></a>
<a id="addressQuery" href=""></a>
<a id="submitVotting" href=""></a>
<a id="viewVotting" href=""></a>
		
<DIV ID=Content>
	<DIV STYLE="width:218px; float:left;" ID=sub>
		<div urlvalue="Oasp5203" functionid="FUNC00025" urlname="快捷菜单" recordcount="5" minflg="0" id="FUNC00025">
			<DIV ID=Block>
			 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
             		<TR VALIGN=center>
             			<td ><img src="<%= request.getContextPath() %>/images/eis/gexcd.jpg"></td>
             		</tr>
             	</TABLE>
                <div class="BlockMemo" id="FUNC00025MAX">
					<table border="0" width="100%"   bgcolor="C5E1FD" cellSpacing="0" cellPadding="0"   >          
						<tr height="48px">
							<td class="Changymk" width="60%" align="center"> 
					     		<a href="#" onclick="actionMenu('1')"><img src="<%= request.getContextPath() %>/images/oa/tu1.gif" width="41" height="36"   border="0"><br><font color="#000000">考勤管理</font></a>
					        </td>
					        <td class="Changymk" >
					            <a href="#" onclick="actionMenu('2')"><img src="<%= request.getContextPath() %>/images/oa/tu2.gif" width="41" height="36"   border="0"><br><font color="#000000">标准文件</font></a>
					        </td>
					    </tr>
					    <tr height="48px"> 
					     	<td width="50%" class="Changymk" align="center">
					     		<a href="#" onclick="actionMenu('3')"><img src="<%= request.getContextPath() %>/images/oa/tu3.gif" width="41" height="36"   border="0"><br><font color="#000000">收文管理</font></a>
					        </td>
					        <td class="Changymk">
					        	<a href="#" onclick="actionMenu('4')"><img src="<%= request.getContextPath() %>/images/oa/tu4.gif" width="41" height="36"   border="0"><br><font color="#000000">发文管理</font></a>
					        </td>
					    </tr>
					    <tr height="48px">
					    	<td class="Changymk" align="center" > 
					     		<a href="#" onclick="actionMenu('5')"><img src="<%= request.getContextPath() %>/images/oa/tu5.gif" width="41" height="36"   border="0"><br><font color="#000000">办公用品</font></a>
					        </td>
					        <td class="Changymk">
					        	<a href="#" onclick="actionMenu('6')"><img src="<%= request.getContextPath() %>/images/oa/tu6.gif" width="41" height="36"   border="0"><br><font color="#000000">通 讯 录</font></a>
					        </td>
					    </tr>
				  		<tr height="58px">
				  			<td class="Changymk" align="center">  
				  				<a href="#" onclick="actionMenu('7')"><img src="<%= request.getContextPath() %>/images/oa/tu3.gif" width="41" height="36"   border="0"><br><font color="#000000">订会议室</font></a>
				  			</td>
			          		<td class="Changymk">
			            		<a href="#" onclick="actionMenu('8')"><img src="<%= request.getContextPath() %>/images/oa/tu4.gif" width="41" height="36"   border="0"><br><font color="#000000">车辆管理</font></a>
			          		</td>
		          		</tr>
		          	</table>
                </div>
			</DIV>
		</div>
              
		<div urlvalue="" functionid="FUNC00007" urlname="链接地址" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00007">
			<DIV ID=Block>
			 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
             		<TR VALIGN=center>
             			<td ><img src="<%= request.getContextPath() %>/images/eis/gongsjj.jpg"></td>
             		</tr>
             	</TABLE>
                <div class="BlockMemo" STYLE="height: 100px;" id="FUNC00007MAX">
                </div>
			</DIV>
		</div>
            
		<div urlvalue="" functionid="FUNC00008" urlname="股东信息" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00008">
			<DIV ID=Block>
			 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
             		<TR VALIGN=center>
             			<td ><img src="<%= request.getContextPath() %>/images/eis/gudxx.jpg"></td>
             		</tr>
             	</TABLE>
                <div class="BlockMemo" STYLE="height: 100px;" id="FUNC00008MAX">
                </div>
			</DIV>
		</div>
	</DIV>
	<DIV STYLE="width:590px; float:left;" ID=sub>
                  <div urlvalue="Oasp3103" functionid="FUNC00001" urlname="个人待办" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00001">
                  <DIV ID=Block>
				 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
	             		<TR VALIGN=center>
	             			<td ><img src="<%= request.getContextPath() %>/images/eis/gerdb.jpg"></td>
	             		</tr>
	             	</TABLE>
	             	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
	             		<tr>
	             		<td width="70%">
                      <div class="BlockMemo" id="FUNC00001MAX">
                      </td>
                      <td width="30%">
                      	<img src="<%= request.getContextPath() %>/images/eis/db.jpg">
                      	</td>
                      </tr>
                      </div>
                    </TABLE>  
                  </DIV>
                  </div>
			<div urlvalue="Oasp3103" functionid="FUNC00002" urlname="公告通知" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00002">
				<DIV ID=Block>
				 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
	             		<TR VALIGN=center>
	             			<td ><img src="<%= request.getContextPath() %>/images/eis/gonggl.jpg"></td>
	             		</tr>
	             	</table>
	                <div class="BlockMemo" id="FUNC00002MAX"><img border="0" src="<%= request.getContextPath() %>/images/oa/loading.gif" /></div>
	            </DIV>
			</div>
			<div urlvalue="Oasp0933" functionid="FUNC00003" urlname="新闻简报" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00003">
			<DIV ID=Block>
		 		<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
            		<TR VALIGN=center>
            			<td ><img src="<%= request.getContextPath() %>/images/eis/jujff.jpg"></td>
            		</tr>
            	</table>
                <div class="BlockMemo" id="FUNC00003MAX">
                </div>
            </DIV>
			</div>
			<div urlvalue="Oasp1219" functionid="FUNC00004" urlname="公司文件" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00004">
			<DIV ID=Block>
		 		<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
            		<TR VALIGN=center>
            			<td ><img src="<%= request.getContextPath() %>/images/eis/guizzd.jpg"></td>
            		</tr>
            	</table>
                <div class="BlockMemo" id="FUNC00004MAX">
                </div>
            </DIV>
			</div>
				
	</DIV>
	<DIV STYLE="width:218px; float:center;" ID=right>
				<div functionid="FUNC00005" urlname="滚动信息" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00005">
				<DIV ID=Block>
				 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
	             		<TR VALIGN=center>
	             			<td ><img src="<%= request.getContextPath() %>/images/eis/zuixgz.jpg"></td>
	             		</tr>
	             	</TABLE>
							<table border="0" width="100%" cellSpacing="0" cellPadding="0" >
								<tr height="147"  width="100%">
									<marquee behavior="scroll" direction="up" width="100%" height="140" scrollamount="1" scrolldelay="60" onmouseover="this.stop()" onmouseout="this.start()">
								        <font color="#4E4E4E">
								        <div class="BlockMemo" STYLE="height: 150px;" id="FUNC00005MAX">
								          </div>
										</font>
									</marquee>
								</tr>
							</table>
                         <!--   <img border="0" src="<%= request.getContextPath() %>/images/oa/loading.gif" />-->
                      </DIV>
				</div>
				<div functionid="FUNC00006" urlname="图片新闻" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00006">
				<DIV ID=Block>
                          <div class="BlockMemo" STYLE="height: 250px;" id="FUNC00006MAX"></div>
                      </DIV>
				</div>  
				
				<div functionid="FUNC00009" urlname="专题栏目" recordcount="5" minflg="0" class="DesktopBlock" id="FUNC00009">
				<DIV ID=Block>
				 	<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT=100% WIDTH=100%>
	             		<TR VALIGN=center>
	             			<td ><img src="<%= request.getContextPath() %>/images/eis/zhuantlm.jpg"></td>
	             		</tr>
	             	</TABLE>
                          <div class="BlockMemo" STYLE="height: 250px;" id="FUNC00009MAX">
							<table border="0" width="206" height="250" valign="top" cellSpacing="0" cellPadding="0"  background="<%= request.getContextPath() %>/images/eis/bg.jpg">
							<tr height="15"><td align="center"></td></tr>
							<tr><td align="center">
							<a href="#" onclick="actionLink('BBS论坛','http://bem.ffcs.cn:81/Bbs/index.asp')">
							<img src="<%= request.getContextPath() %>/images/eis/bbslt.jpg" border="0">
							</a></td></tr>	
							</table>
                          </div>
                      </DIV>
				</div> 

	</DIV>
          <p></p>
          <p></p>
          <p></p>
</DIV>
    <input type="hidden" name="wosid" value="UCy0IbdqEWGwM5isMN7okM" />
</form>
    
<form method="post" name="homeForm" action="">
	<input type="hidden" name="alertMsg" /> 
	<input type="hidden" name="newUrl" /> 
	<input type="hidden" name="newWindow" />
	<input type="hidden" name="confirmMsg" /> 
	<input type="hidden" value="" name="topTitle" />
	<input type="hidden" value="desktop" name="pgID" />
	<input type="hidden" name="returnPage" />
	<input type="hidden" name="wosid" value="UCy0IbdqEWGwM5isMN7okM" />
</form>
<form method="post" id="menuForm" action="">
	<input type="hidden" name="url" />
	<input type="hidden" name="urlName" />
	<input type="hidden" name="wosid" value="UCy0IbdqEWGwM5isMN7okM" />
</form>

<script type="text/javascript" language="JavaScript1.2">
//writeMenu(false);
</script>

</body>
</HTML>