/*
 * @(#)TestFDConverter.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-3-1
 *  描　述：创建
 */

package com.infolion.XDSS.budget.sheet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.spreadsheet.SpreadsheetException;
import com.infolion.platform.spreadsheet.Utils;
import com.infolion.platform.spreadsheet.dataConverters.AbstractFDConverter;
import com.infolion.platform.spreadsheet.dataConverters.item.FlexItemDataConverter;
import com.infolion.platform.spreadsheet.flex.FlexData;
import com.infolion.platform.spreadsheet.flex.Header;
import com.infolion.platform.spreadsheet.flex.Item;
import com.infolion.platform.spreadsheet.flex.Sheet;
import com.infolion.platform.spreadsheet.flex.SheetData;

// 数据转换器实现
public class BudgetSheetFDConverter extends AbstractFDConverter
{
	protected BudgetClass budgetClass;

	public void setBudgetClass(BudgetClass budgetClass)
	{
		this.budgetClass = budgetClass;
	}

	public BudgetClass getBudgetClass()
	{
		return budgetClass;
	}

	private List toDataList(Object boInst, String proName, BusinessObject businessObject, BudgetTemplate budgetTemplate)
	{
		// 取得子对象集合
		Collection<?> subObjSet;
		Object subObj = null;
		subObj = boInst == null ? null : Utils.getProperty(boInst, proName);
		if (subObj != null && !(subObj instanceof Collection))
		{
			throw new SpreadsheetException("Flex数据转换失败：主业务对象[" + businessObject.getBoName() + "]的属性[" + proName + "]必须是集合类型");
		}
		else
		{
			subObjSet = subObj == null ? null : (Collection) subObj;
		}
		// 子对象关联模板预算项的属性名
		String relatePro = BudgetSheetService.BUD_TEM_ITEM_ID;

		// 根据模板预算项的顺序排列子对象集合
		List<Object> dataList = new ArrayList<Object>();
		for (BudgetTemplateItem bti : budgetTemplate.getSortedItems())
		{
			Object data = null;
			if (subObjSet != null)
				for (Object obj : subObjSet)
				{
					if (bti.getBudtemitemid().equals(Utils.getProperty(obj, relatePro)))
					{
						data = obj;
						break;
					}
				}
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	protected List getDataList(Object boInst, Sheet sheet)
	{
		BusinessObject businessObject = BusinessObjectService.getBusinessObjectByBoId(this.budgetClass.getBoid());

		// 找到sheet对应BudgetTemplate
		BudgetTemplate budgetTemplate = budgetClass.getBudgetTemps().get(sheet.getBoName());
		if (budgetTemplate == null)
		{
			throw new SpreadsheetException("找不到与表格[" + sheet.getSheetName() + "]相对应的预算模板");
		}
		List dataList = toDataList(boInst, sheet.getProName(), businessObject, budgetTemplate);
		return dataList;
	}

}
