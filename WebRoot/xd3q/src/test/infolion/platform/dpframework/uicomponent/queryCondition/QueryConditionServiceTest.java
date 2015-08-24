/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.queryCondition;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.queryCondition.QueryConditionService;

/**
 * 
 * <pre>
 * 查询条件服务测试类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class QueryConditionServiceTest extends JdbcServiceTest
{
	public void testGetQueryExpression() throws SQLException
	{
		Map<String, String[]> parameters = new HashMap();

		// //////////////////////////////////////////////////
		// creditName.fieldName
		String[] fieldNameAr = new String[1];
		String fieldName;
		fieldName = "creditName.fieldName";
		fieldNameAr[0] = "creditName";
		parameters.put(fieldName, fieldNameAr);

		// creditName.option
		String[] optionAr = new String[1];
		String option;
		option = "creditName.option";
		optionAr[0] = "equal";
		parameters.put(option, optionAr);

		// creditName.option
		String[] dataTypeAr = new String[1];
		String dataType;
		dataType = "creditName.dataType";
		dataTypeAr[0] = "S";
		parameters.put(dataType, dataTypeAr);

		// creditName.fieldValue
		String[] fieldValueAr = new String[1];
		String fieldValue;
		fieldValue = "creditName.fieldValue";
		fieldValueAr[0] = "FUCKYOU";
		parameters.put(fieldValue, fieldValueAr);

		// creditName.isRangeValue
		String[] isRangeValueAr = new String[1];
		String isRangeValue;
		isRangeValue = "creditName.isRangeValue";
		isRangeValueAr[0] = "false";
		parameters.put(isRangeValue, isRangeValueAr);

		// creditName.maxValue
		String[] maxValueAr = new String[1];
		String maxValue;
		maxValue = "creditName.maxValue";
		maxValueAr[0] = "";
		parameters.put(maxValue, maxValueAr);

		// creditName.minValue
		String[] minValueAr = new String[1];
		String minValue;
		minValue = "creditName.minValue";
		minValueAr[0] = "";
		parameters.put(minValue, minValueAr);

		// //////////////////////////////////////////////////
		// creditNo.fieldName
		String[] fieldNameAr1 = new String[1];
		String fieldName1;
		fieldName1 = "creditNo.fieldName";
		fieldNameAr1[0] = "creditNo";
		parameters.put(fieldName1, fieldNameAr1);

		// creditNo.option
		String[] optionAr1 = new String[1];
		String option1;
		option1 = "creditNo.option";
		optionAr1[0] = "lessequal";
		parameters.put(option1, optionAr1);

		// creditNo.option
		String[] dataTypeAr1 = new String[1];
		String dataType1;
		dataType1 = "creditNo.dataType";
		dataTypeAr1[0] = "N";
		parameters.put(dataType1, dataTypeAr1);

		// creditNo.fieldValue
		String[] fieldValueAr1 = new String[1];
		String fieldValue1;
		fieldValue1 = "creditNo.fieldValue";
		fieldValueAr1[0] = "89";
		parameters.put(fieldValue1, fieldValueAr1);

		// creditNo.isRangeValue
		String[] isRangeValueAr1 = new String[1];
		String isRangeValue1;
		isRangeValue1 = "creditNo.isRangeValue";
		isRangeValueAr1[0] = "false";
		parameters.put(isRangeValue1, isRangeValueAr1);

		// creditNo.maxValue
		String[] maxValueAr1 = new String[1];
		String maxValue1;
		maxValue1 = "creditNo.maxValue";
		maxValueAr1[0] = "";
		parameters.put(maxValue1, maxValueAr1);

		// creditNo.minValue
		String[] minValueAr1 = new String[1];
		String minValue1;
		minValue1 = "creditNo.minValue";
		minValueAr1[0] = "";
		parameters.put(minValue1, minValueAr1);

		// //////////////////////////////////////////////////
		// sDate.fieldName
		String[] fieldNameAr2 = new String[1];
		String fieldName2;
		fieldName2 = "sDate.fieldName";
		fieldNameAr2[0] = "sDate";
		parameters.put(fieldName2, fieldNameAr2);

		// sDate.option
		String[] optionAr2 = new String[1];
		String option2;
		option2 = "sDate.option";
		optionAr2[0] = "lessequal";
		parameters.put(option2, optionAr2);

		// sDate.option
		String[] dataTypeAr2 = new String[1];
		String dataType2;
		dataType2 = "sDate.dataType";
		dataTypeAr2[0] = "N";
		parameters.put(dataType2, dataTypeAr2);

		// sDate.fieldValue
		String[] fieldValueAr2 = new String[1];
		String fieldValue2;
		fieldValue2 = "sDate.fieldValue";
		fieldValueAr2[0] = "";
		parameters.put(fieldValue2, fieldValueAr2);

		// sDate.isRangeValue
		String[] isRangeValueAr2 = new String[1];
		String isRangeValue2;
		isRangeValue2 = "sDate.isRangeValue";
		isRangeValueAr2[0] = "true";
		parameters.put(isRangeValue2, isRangeValueAr2);

		// sDate.minValue
		String[] minValueAr2 = new String[1];
		String minValue2;
		minValue2 = "sDate.minValue";
		minValueAr2[0] = "11";
		parameters.put(minValue2, minValueAr2);

		Iterator it = parameters.entrySet().iterator();
		// 使用entrySet方法将hashMap转化为Set视图,返回的Set中的每个元素都是一个Map.Entry
		while (it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			// Map.Entry可以看成是一种特殊的Map,与Map不同的是Map.Entry只能含有一对键-值
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			System.out.println("key:" + key + "   value:" + value[0]);
		}

		// QueryConditionService.bind(parameters);
		String strSql = QueryConditionService.getQueryExpression(parameters);
		System.out.println("strSql:" + strSql);
		
		String strSql1 = QueryConditionService.getQueryExpressionAppendWhere(parameters);
		System.out.println("strSql1:" + strSql1);

	}

}
