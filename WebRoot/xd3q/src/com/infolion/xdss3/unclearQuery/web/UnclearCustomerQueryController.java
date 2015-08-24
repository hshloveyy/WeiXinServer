package com.infolion.xdss3.unclearQuery.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;

import com.infolion.xdss3.unclearQuery.domain.UnclearCustomer;
import com.infolion.xdss3.unclearQuery.service.UnclearCustomService;

//@BDPController(parent = "baseMultiActionController")
public class UnclearCustomerQueryController extends BaseMultiActionController {
	@Autowired
	private GridService gridService;

	public void setGridService(GridService gridService) {
		this.gridService = gridService;
	}
	@Autowired
	private UnclearCustomService unclearCustomerService;
	
    public void setUnclearCustomerService(
			UnclearCustomService unclearCustomerService) {
		this.unclearCustomerService = unclearCustomerService;
	}

	public ModelAndView unclearCustomerQuery(HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("xdss3/unclearquery/unclearCustomerQuery");
    }
    
	public void queryGrid(HttpServletRequest request,
			HttpServletResponse response, GridQueryCondition gridQueryCondition)
			throws Exception {
		String bukrs = (String) request.getParameter("bukrs.fieldValue"); // 公司代码
		String gsber = (String) request.getParameter("GSBER.fieldValue"); // 业务范围
		String kunnr = (String) request.getParameter("KUNNR.fieldValue"); // 客户
		String waers = (String) request.getParameter("waers.fieldValue"); // 币别
		String saknr = (String) request.getParameter("SAKNR.fieldValue"); // 清账科目
		String augdt_to = (String) request.getParameter("augdt_to"); // 过账日期
		String augdt_from = (String) request.getParameter("augdt_from"); // 过账日期
		String signRegExp = "[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\"
			+ "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:"
			+ "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\"
			+ "‘\\’\\。\r+\n+\t+\\s\\ ]";
		augdt_to = augdt_to.replaceAll(signRegExp, "");
		augdt_from = augdt_from.replaceAll(signRegExp, "");
		
	
//		gridQueryCondition.setBoName("");
//		gridQueryCondition.setTableSql("");
//		gridQueryCondition.setDefaultCondition("");
//		gridQueryCondition.setWhereSql("");
//		gridQueryCondition.setOrderSql("belnr DESC");
//		gridQueryCondition.setGroupBySql("");
//		gridQueryCondition.setTableName("(" + sql.toString() + ") t");
//		gridQueryCondition
//				.setHandlerClass("com.infolion.xdss3.unclearQuery.domain.UnclearCustomerQueryGrid");
//		String editable = "false";
//		String needAuthentication = "true";
//		GridData gridJsonData = this.gridService.getGridData(
//				gridQueryCondition, editable, needAuthentication);
		List<UnclearCustomer> list =unclearCustomerService.getUnclearCustomer(bukrs, gsber, kunnr, waers, saknr, augdt_to, augdt_from);
		
		MultyGridData gridJsonData2 = new MultyGridData();
		gridJsonData2.setData(list.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData2);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
}
