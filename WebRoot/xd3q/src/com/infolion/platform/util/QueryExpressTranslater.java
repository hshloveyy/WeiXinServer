/*
 * @(#)QueryExpressTranslater.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：整理
 */
package com.infolion.platform.util;

/**
 * 
 * <pre>
 * 组装SQL中的条件部分所要用到的公用函数.
 * &lt;blockquote&gt;
 * 功能 1： 将传入的列名和表达式译成相应的条件表达式
 * &lt;li&gt;将×（不区分全角和半角）转换成%,即oracle查询中的全匹配字符
 * &lt;li&gt;将％（全角）转换成%,即oracle查询中的全匹配字符
 * &lt;li&gt;将？（不区分全角和半角）转换成_,即oracle查询中的单字符匹配字符
 * &lt;li&gt;将～（不区分全角和半角）转换成between and 语句
 * &lt;li&gt;将!（不区分全角和半角）转换成SQL非语句 NOT
 * &lt;li&gt;将以，（不区分全角和半角）分隔的多个值转换成OR的关系
 * &lt;li&gt;如果是年龄类型，自动完成岁数到日期的转换
 * &lt;/blockquote&gt;
 * </pre>
 * 
 * <br>
 * JDK版本:1.4
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class QueryExpressTranslater {

	/**
	 * 年龄（要求在构造时转化为yyyyMMdd格式日期的比较方式）
	 */
	public static final char DATA_TYPE_AGE = 'A'; // 年龄

	/**
	 * 表码（与DATA_TYPE_CHAR类似）
	 */
	public static final char DATA_TYPE_BM = 'B'; // 表码

	/**
	 * 字符或字符串（与DATA_TYPE_BM类似）
	 */
	public static final char DATA_TYPE_CHAR = 'C'; // 字符

	/**
	 * 数字
	 */
	public static final char DATA_TYPE_NUMBER = 'N'; // 数字

	/**
	 * 以8位表示的日期型yyyymmdd
	 */
	public static final char DATA_TYPE_STR_DATE_8 = 'D'; // 以8位表示的日期型yyyymmdd

	/**
	 * 以14位表示的日期型yyyymmddhh24miss
	 */
	public static final char DATA_TYPE_STR_DATE_14 = 'E'; // 以14位表示的日期型yyyymmddhh24miss

	/**
	 * 保留<还存在问题的类型/>
	 */
	public static final char DATA_TYPE_DATE = 'Y'; // 真实数据类型

	/**
	 * 保留<目前只供程序内部使用>
	 */
	public static final String DATA_ALL_DATE_TYPE = "ADEY";

	/**
	 * 保留<目前只供程序内部使用>
	 */
	public static final String DATA_ALL_TYPE = "ABCDENDY"; // 包含所有类型的串

	/**
	 * 根据用户传入的类型值自动改变条件值（要求一律大写）.
	 * 
	 * @param paStr_Express
	 *            String 条件表达式
	 * @param DATA_TYPE
	 *            char 类型，参考本类fields
	 * @param paBoo_isRange
	 *            boolean false 不是转到区间，true 是转到区间
	 * @return String 转换后的表达式
	 */
	private static String changeColumnValue(String paStr_Express,
			char DATA_TYPE, boolean paBoo_isRange) {
		String str_Ret = null;

		// 将含区间的表达式分成两段
		String str_SubStr1 = null;
		String str_SubStr2 = null;
		// 是否为非表达式
		boolean Boo_IsNot = false;
		// 是否为区间条件
		boolean Boo_IsBetween = false;
		// 是否为年龄
		boolean Boo_IsAge = false;

		if (paBoo_isRange) {
			if (paStr_Express.indexOf("~") > -1) { // 本来就是区间
				String loStr_1 = null, loStr_2 = null;
				int loInt_1 = paStr_Express.indexOf("~");
				loStr_1 = paStr_Express.substring(0, loInt_1).trim();
				loStr_2 = paStr_Express.substring(loInt_1 + 1).trim();
				if (DATA_TYPE == DATA_TYPE_AGE) {
					if (loStr_1.length() == 0) {
						loStr_1 = "0";
					}
					if (loStr_2.length() == 0) {
						loStr_2 = "200";
					}
					paStr_Express = toFullDateString(loStr_2, DATA_TYPE, true)
							+ "~" + toFullDateString(loStr_1, DATA_TYPE, false);
				} else {
					paStr_Express = toFullDateString(loStr_1, DATA_TYPE, true)
							+ "~" + toFullDateString(loStr_2, DATA_TYPE, false);
				}
			} else {
				paStr_Express = StringUtils.replaceString(paStr_Express, "*",
						"");
				paStr_Express = StringUtils.replaceString(paStr_Express, "?",
						"");
				paStr_Express = toFullDateString(paStr_Express, DATA_TYPE, true)
						+ "~"
						+ toFullDateString(paStr_Express, DATA_TYPE, false);
			}
		}
		try {
			// 该类型的值是否存在,其中E:14位日期；Y:Date
			if (DATA_ALL_TYPE.indexOf(DATA_TYPE) == -1) {
				throw new Exception("在TJUtils中，系统所传入的类型：'" + DATA_TYPE
						+ "' 不合法");
			}

			if (paStr_Express.charAt(0) == '!') {
				paStr_Express = paStr_Express.substring(1);
				Boo_IsNot = true;
			}

			int Int_Pos;

			if ((Int_Pos = paStr_Express.indexOf('~')) > -1) {
				// 将一个区间符号分成两个子表达式
				str_SubStr1 = paStr_Express.substring(0, Int_Pos).trim();
				str_SubStr2 = paStr_Express.substring(Int_Pos + 1).trim();
				// 是区间条件
				Boo_IsBetween = true;
			} else {
				str_SubStr1 = paStr_Express;
				str_SubStr2 = "";
			}

			switch (DATA_TYPE) {
			// 字符
			case DATA_TYPE_CHAR:
				if (!str_SubStr1.equals("")) {
					str_SubStr1 = "\'" + str_SubStr1 + "\'";
				}
				if (!str_SubStr2.equals("")) {
					str_SubStr2 = "\'" + str_SubStr2 + "\'";
				}
				break;
			// 年龄,如果是年龄则自动进行日期的处理,所以不能在本段后加上 break (切记)
			case DATA_TYPE_AGE:

				// 如果是年龄则转成相应的年份,如果是区间则加上 ％，否则用特定日期
				if (str_SubStr1.length() != 0 && str_SubStr1.length() <= 3
						|| str_SubStr2.length() != 0
						&& str_SubStr2.length() <= 3) {
					// 是年龄标志
					Boo_IsAge = true;
					// 如果是年龄且是区间。则对区间的值进行交换
					if (Boo_IsBetween) {
						String str_Temp = str_SubStr2;
						str_SubStr2 = str_SubStr1;
						str_SubStr1 = str_Temp;
					}

					// 在年龄后增加一段日期
					String str_Age = null;
					// 倒计年份
					int Int_OldYear = 0;
					// 求今年的年份
					int Int_year = StringUtils.getInt(DateUtils
							.getDateFormat("yyyy"));

					if (!str_SubStr1.equals("")) {
						Int_OldYear = Int_year - Integer.parseInt(str_SubStr1);
						str_SubStr1 = "\'" + Int_OldYear
								+ (Boo_IsBetween ? "0000" : "%") + "\'";
					}
					if (!str_SubStr2.equals("")) {
						if (str_SubStr1.equals("")) {
							str_Age = Boo_IsBetween ? "0101" : "%";
						} else {
							str_Age = Boo_IsBetween ? "1231" : "%";
						}

						Int_OldYear = Int_year - Integer.parseInt(str_SubStr2);
						str_SubStr2 = "\'" + Int_OldYear + str_Age + "\'";
					}
				}
				// 日期
			case DATA_TYPE_STR_DATE_8:
				if ((str_SubStr1.length() > 3 || str_SubStr2.length() > 3)
						&& !Boo_IsAge) {
					if (str_SubStr1.length() != 0) {
						if (str_SubStr1.length() != 8) {
							str_SubStr1 = "\'"
									+ str_SubStr1
									+ (Boo_IsBetween ? "00000101"
											.substring(str_SubStr1.length())
											: "%") + "\'";
						} else {
							str_SubStr1 = "\'" + str_SubStr1 + "\'";
						}
					}

					if (str_SubStr2.length() != 0) {
						if (str_SubStr2.length() != 8) {
							str_SubStr2 = "\'"
									+ str_SubStr2
									+ (Boo_IsBetween ? "99991231"
											.substring(str_SubStr2.length())
											: "%") + "\'";
						} else {
							str_SubStr2 = "\'" + str_SubStr2 + "\'";
						}
					}
				}
				break;
			// 14位日期
			case DATA_TYPE_STR_DATE_14:
				if ((str_SubStr1.length() > 3 || str_SubStr2.length() > 3)
						&& !Boo_IsAge) {
					if (str_SubStr1.length() != 0) {
						if (str_SubStr1.length() != 14) {
							str_SubStr1 = "\'"
									+ str_SubStr1
									+ (Boo_IsBetween ? "00000101000000"
											.substring(str_SubStr1.length())
											: "%") + "\'";
						} else {
							str_SubStr1 = "\'" + str_SubStr1 + "\'";
						}
					}

					if (str_SubStr2.length() != 0) {
						if (str_SubStr2.length() != 14) {
							str_SubStr2 = "\'"
									+ str_SubStr2
									+ (Boo_IsBetween ? "99991231235959"
											.substring(str_SubStr2.length())
											: "%") + "\'";
						} else {
							str_SubStr2 = "\'" + str_SubStr2 + "\'";
						}
					}
				}
				break;
			case DATA_TYPE_DATE: // 真实日期，与日期同

				// 加上年龄的转换
				int Int_year = StringUtils.getInt(DateUtils
						.getDateFormat("yyyy"));
				if (str_SubStr1.length() != 0 && str_SubStr1.length() <= 3) {
					str_SubStr1 = new Integer(Int_year
							- Integer.parseInt(str_SubStr1)).toString();
				}
				if (str_SubStr2.length() != 0 && str_SubStr2.length() <= 3) {
					str_SubStr2 = new Integer(Int_year
							- Integer.parseInt(str_SubStr2)).toString();
				}

				//
				if ((str_SubStr1.length() > 3 || str_SubStr2.length() > 3)
						&& !Boo_IsAge) {
					if (str_SubStr1.length() != 0) {
						if (str_SubStr1.length() != 8) {
							int ii = str_SubStr1.length();
							str_SubStr1 = "\'"
									+ str_SubStr1
									+ (Boo_IsBetween ? "00000101000000"
											.substring(str_SubStr1.length())
											: "%") + "\'";
						} else {
							str_SubStr1 = "\'" + str_SubStr1 + "\'";
						}
					}

					if (str_SubStr2.length() != 0) {
						if (str_SubStr2.length() != 8) {
							str_SubStr2 = "\'"
									+ str_SubStr2
									+ (Boo_IsBetween ? "99991231235959"
											.substring(str_SubStr2.length())
											: "%") + "\'";
						} else {
							str_SubStr2 = "\'" + str_SubStr2 + "\'";
						}
					}
				}
				break;
			// 表码
			case DATA_TYPE_BM:
				if (!str_SubStr1.equals("")) {
					str_SubStr1 = "\'" + str_SubStr1 + "\'";
				}
				if (!str_SubStr2.equals("")) {
					str_SubStr2 = "\'" + str_SubStr2 + "\'";
				}
				break;
			}
		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		str_Ret = "";
		if (!str_SubStr1.equals("")) {
			str_Ret += str_SubStr1;
		}
		if (Boo_IsBetween) {
			str_Ret += "~";
		}
		if (!str_SubStr2.equals("")) {
			str_Ret += str_SubStr2;
		}
		if (Boo_IsNot) {
			str_Ret = "!" + str_Ret;
		}
		return str_Ret;

	}

	/**
	 * 将字符串翻译成条件表达式，字符不支持全角和半角(对单一没有逗号分开的条件进行翻译)
	 * 
	 * @param paStr_ColumnName
	 *            String 列名称
	 * @param paStr_Express
	 *            String 列表达式
	 * @param DATA_TYPE
	 *            char 列的类型
	 * @return String 拼装完毕的列表达式
	 */
	private static String columnExpressUnit(String paStr_ColumnName,
			String paStr_Express, char DATA_TYPE) {
		String str_RetExpress = null;
		boolean boo_NotExpress = false;

		// 错误处理
		if (paStr_ColumnName == null || paStr_Express == null) {
			return null;
		}
		if (paStr_ColumnName.equals("") || paStr_Express.equals("")
				|| DATA_TYPE == ' ') {
			return null;
		}

		// 根据类型改变条件的值
		paStr_Express = changeColumnValue(paStr_Express, DATA_TYPE, false);

		// 如果是个含有非条件的表达式
		try {
			if (paStr_Express.charAt(0) == '!') {
				boo_NotExpress = true;
				paStr_Express = paStr_Express.substring(1);
			}

			int Int_Pos = 0;
			if (paStr_Express.indexOf('%') > -1
					|| paStr_Express.indexOf('_') > -1) {
				if (DATA_TYPE != DATA_TYPE_DATE) {
					str_RetExpress = paStr_ColumnName + " like "
							+ paStr_Express;
				} else {
					str_RetExpress = "to_char(" + paStr_ColumnName
							+ ",'YYYYMMDDHH24MISS') like " + paStr_Express;

				}
			} else if ((Int_Pos = paStr_Express.indexOf('~')) > -1) {
				// 将一个区间符号分成两个子表达式
				String str_SubStr1 = paStr_Express.substring(0, Int_Pos).trim();
				String str_SubStr2 = paStr_Express.substring(Int_Pos + 1)
						.trim();

				str_RetExpress = QJCondition(paStr_ColumnName, str_SubStr1,
						str_SubStr2, DATA_TYPE);
			} else {

				if (DATA_TYPE != 'Y') {
					str_RetExpress = paStr_ColumnName + " = " + paStr_Express;
				} else {
					str_RetExpress = "to_char(" + paStr_ColumnName
							+ ",'YYYYMMDD') = " + paStr_Express;

				}
			}

			if (boo_NotExpress) {
				str_RetExpress = " NOT " + str_RetExpress;
			}
		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		return str_RetExpress;
	}

	/**
	 * 将字符串翻译成条件表达式，字符不支持全角和半角(对单一没有逗号分开的条件进行翻译)
	 * 
	 * @param paStr_ColumnName1
	 *            String 列1名称
	 * @param paStr_ColumnName2
	 *            String 列2名称
	 * @param paStr_Express
	 *            String 列表达式
	 * @param DATA_TYPE
	 *            char 列的类型
	 * @return String 拼装完毕的列表达式
	 */
	private static String columnExpressUnit(String paStr_ColumnName1,
			String paStr_ColumnName2, String paStr_Express, char DATA_TYPE) {
		String str_RetExpress = null;
		boolean boo_NotExpress = false;

		if (StringUtils.isNullBlank(paStr_ColumnName1)
				|| StringUtils.isNullBlank(paStr_ColumnName2)
				|| StringUtils.isNullBlank(paStr_Express)) {
			throw new IllegalArgumentException("列名1，列名2，条件表达式都不能为空。");
		}
		if (DATA_ALL_TYPE.indexOf(DATA_TYPE) == -1) {
			throw new IllegalArgumentException("数据类型不正确.");
		}

		if (paStr_Express.charAt(0) == '!') {
			boo_NotExpress = true;
			paStr_Express = paStr_Express.substring(1);
		}
		// 根据类型改变条件的值
		paStr_Express = changeColumnValue(paStr_Express, DATA_TYPE, true);
		// 如果是个含有非条件的表达式
		try {
			int Int_Pos = 0;
			if ((Int_Pos = paStr_Express.indexOf('~')) > -1) { // 区间
				// 将一个区间符号分成两个子表达式
				String str_SubStr1 = paStr_Express.substring(0, Int_Pos).trim();
				String str_SubStr2 = paStr_Express.substring(Int_Pos + 1)
						.trim();
				str_RetExpress = QJCondition(paStr_ColumnName1,
						paStr_ColumnName2, str_SubStr1, str_SubStr2, DATA_TYPE);
			} else { // 单值
				if (DATA_TYPE != DATA_TYPE_DATE) {
					str_RetExpress = "(" + paStr_Express + " BETWEEN "
							+ paStr_ColumnName1 + " AND " + paStr_ColumnName2
							+ ")";
				} else {
					str_RetExpress = "( to_date(" + paStr_Express
							+ ",'YYYYMMDDHH24MISS') BETWEEN "
							+ paStr_ColumnName1 + " AND " + paStr_ColumnName2
							+ ")";

				}
			}
			if (boo_NotExpress) {
				str_RetExpress = " NOT " + str_RetExpress;
			}
		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		return str_RetExpress;
	}

	/**
	 * 返回一个列的条件表达式
	 * 
	 * @param paStr_ColumnName
	 *            String 列名称（含两部分内容，即列名称和列类型。 <BR>
	 *            最后的一个字符为该列的类型，大写表示，中间用_间隔）
	 * @param paStr_Express
	 *            String 列的条件表达式
	 * @param DATA_TYPE
	 *            char 数据类型
	 * @return String 拼装完毕的列的表达式,错误返回 null
	 */
	public static String columnExpess(String paStr_ColumnName,
			String paStr_Express, char DATA_TYPE) {
		// 表达式(分别用于不含非条件和含非条件)
		String str_RetExpress = "", str_RetExpress1 = "", str_RetExpress2 = "";

		// 表达式计数用(分别用于不含非条件和含非条件)
		int Int_Count1 = 0, Int_Count2 = 0;
		// 错误处理
		if (paStr_ColumnName == null || paStr_Express == null) {
			return "";
		}
		if (paStr_ColumnName.equals("") || paStr_Express.equals("")) {
			return "";
		}
		String str_ColumnName = paStr_ColumnName;
		char Cha_ColumnType = Character.toUpperCase(DATA_TYPE);
		try {
			// 将外部的条件表达式串转换为内部的形式
			paStr_Express = toInnerString(paStr_Express);
			// 转成数组
			String str_ColArray[] = StringUtils.splitString(paStr_Express, ",");
			// 不含非表达式
			for (int int_i = 0; int_i < str_ColArray.length; int_i++) {

				if (str_ColArray[int_i].charAt(0) != '!') {
					str_RetExpress1 += columnExpressUnit(str_ColumnName,
							str_ColArray[int_i], Cha_ColumnType)
							+ " OR ";

					Int_Count1++;
				}

			}
			// 删除条件串后端的 “OR”
			if (Int_Count1 > 0) {
				str_RetExpress1 = str_RetExpress1.trim();
				str_RetExpress1 = str_RetExpress1.substring(0, str_RetExpress1
						.length() - 2);
			}

			// 含非表达式
			for (int int_i = 0; int_i < str_ColArray.length; int_i++) {
				if (str_ColArray[int_i].charAt(0) == '!') {

					str_RetExpress2 += columnExpressUnit(str_ColumnName,
							str_ColArray[int_i], Cha_ColumnType)
							+ " AND ";

					Int_Count2++;
				}

			}
			// 删除条件后端的“AND”
			if (Int_Count2 > 0) {
				str_RetExpress2 = str_RetExpress2.trim();
				str_RetExpress2 = str_RetExpress2.substring(0, str_RetExpress2
						.length() - 3);
			}

			// 将表达式合并

			// 不含非表达式
			if (Int_Count2 == 0) {
				str_RetExpress = str_RetExpress1;
			}
			// 只含非表达式
			else if (Int_Count1 == 0) {
				str_RetExpress = str_RetExpress2;
			}
			// Int_Count1!=0 and Int_Count2!=0
			else {

				str_RetExpress = "(" + str_RetExpress1 + ") AND ("
						+ str_RetExpress2 + ")";

			}

		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		return "(" + str_RetExpress + ")";

	}

	/**
	 * 返回一个列的条件表达式
	 * 
	 * @param paStr_ColumnName1
	 *            String 列名称
	 * @param paStr_ColumnName2
	 *            String 列名称
	 * @param paStr_Express
	 *            String 列的表达式
	 * @param DATA_TYPE
	 *            char 数据类型
	 * @return String 拼装完毕的列的表达式,错误返回 null,不支持XML
	 */
	public static String columnExpess(String paStr_ColumnName1,
			String paStr_ColumnName2, String paStr_Express, char DATA_TYPE) {
		String str_RetExpress = "", str_RetExpress1 = "", str_RetExpress2 = "";

		// 表达式计数用(分别用于不含非条件和含非条件)
		int Int_Count1 = 0, Int_Count2 = 0;
		// 错误处理
		if (StringUtils.isNullBlank(paStr_ColumnName1)
				|| StringUtils.isNullBlank(paStr_ColumnName2)
				|| StringUtils.isNullBlank(paStr_Express)) {
			throw new IllegalArgumentException("列名1，列名2，条件表达式都不能为空。");
		}
		if (DATA_ALL_TYPE.indexOf(Character.toUpperCase(DATA_TYPE)) == -1) {
			throw new IllegalArgumentException("数据类型必须为（年龄、8、14位日期，日期，数字、字符）.");
		}
		String str_ColumnName1 = paStr_ColumnName1, str_ColumnName2 = paStr_ColumnName2;
		char Cha_ColumnType = Character.toUpperCase(DATA_TYPE);
		try {
			// 将外部的条件表达式串转换为内部的形式
			paStr_Express = toInnerString(paStr_Express);
			// 转成数组
			String str_ColArray[] = StringUtils.splitString(paStr_Express, ",");
			// 不含非表达式
			for (int int_i = 0; int_i < str_ColArray.length; int_i++) {
				if (str_ColArray[int_i].charAt(0) != '!') {
					str_RetExpress1 += columnExpressUnit(str_ColumnName1,
							str_ColumnName2, str_ColArray[int_i],
							Cha_ColumnType)
							+ " OR ";
					Int_Count1++;
				}
			}
			// 删除条件串后端的 “OR”
			if (Int_Count1 > 0) {
				str_RetExpress1 = str_RetExpress1.trim();
				str_RetExpress1 = str_RetExpress1.substring(0, str_RetExpress1
						.length() - 2);
			}

			// 含非表达式
			for (int int_i = 0; int_i < str_ColArray.length; int_i++) {
				if (str_ColArray[int_i].charAt(0) == '!') {

					str_RetExpress2 += columnExpressUnit(str_ColumnName1,
							str_ColumnName2, str_ColArray[int_i],
							Cha_ColumnType)
							+ " AND ";
					Int_Count2++;
				}

			}
			// 删除条件后端的“AND”
			if (Int_Count2 > 0) {
				str_RetExpress2 = str_RetExpress2.trim();
				str_RetExpress2 = str_RetExpress2.substring(0, str_RetExpress2
						.length() - 3);
			}

			// 将表达式合并

			// 不含非表达式
			if (Int_Count2 == 0) {
				str_RetExpress = str_RetExpress1;
			}
			// 只含非表达式
			else if (Int_Count1 == 0) {
				str_RetExpress = str_RetExpress2;
			}
			// Int_Count1!=0 and Int_Count2!=0
			else {
				str_RetExpress = "(" + str_RetExpress1 + ") AND ("
						+ str_RetExpress2 + ")";

			}

		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}
		return "(" + str_RetExpress + ")";

	}

	/**
	 * 翻译区间条件的表达式
	 * 
	 * @param paStr_ColumnName
	 *            String 字段名称
	 * @param paStr_Express1
	 *            String 字段条件表达式第一区间
	 * @param paStr_Express2
	 *            String 字段条件表达式第二区间
	 * @param DATA_TYPE
	 *            char 字段类型
	 * @return String 条件表达式
	 */
	private static String QJCondition(String paStr_ColumnName,
			String paStr_Express1, String paStr_Express2, char DATA_TYPE) {
		String str_RetExpress = "";
		// 条件计数
		int Int_Count = 0;
		boolean piBoo_IsXML = false;
		// 将一个区间符号分成两个子表达式
		try {
			if (!paStr_Express1.equals("") && !paStr_Express1.equals("''")) {
				if (!piBoo_IsXML) {
					if (DATA_TYPE != 'Y') {
						str_RetExpress = paStr_ColumnName + " >= "
								+ paStr_Express1;
					} else {

						// str_RetExpress = "to_char("+paStr_ColumnName +
						// ",'YYYYMMDD') >= " + paStr_Express1;
						str_RetExpress = "paStr_ColumnName>= to_date("
								+ paStr_Express1 + ",'YYYYMMDDHH24MISS')";

					}
				} else {
					str_RetExpress += "<Condition Operator=\"GE\" Value=\""
							+ paStr_Express1 + "\">\n<Element>"
							+ paStr_ColumnName + "</Element>\n</Condition>";
				}

				Int_Count++;
			}
			if (!paStr_Express2.equals("") && !paStr_Express2.equals("''")) {
				if (piBoo_IsXML) {
					str_RetExpress += "<Condition Operator=\"LE\" Value=\""
							+ paStr_Express2 + "\">\n<Element>"
							+ paStr_ColumnName + "</Element>\n</Condition>";
				}

				Int_Count++;
				if (Int_Count == 2) {
					if (!piBoo_IsXML) {
						if (DATA_TYPE != 'Y') {
							str_RetExpress += " AND " + paStr_ColumnName
									+ " <= " + paStr_Express2;
						} else {
							str_RetExpress += " AND "
									+ "paStr_ColumnName<= to_date("
									+ paStr_Express2 + ",'YYYYMMDDHH24MISS')";
						}
					} else {
						str_RetExpress = "<Conditions type = \"AND\">"
								+ str_RetExpress + "</Conditions>";
					}
				} else if (!piBoo_IsXML) {
					if (DATA_TYPE != 'Y') {
						str_RetExpress += paStr_ColumnName + " <= "
								+ paStr_Express2;
					} else {
						str_RetExpress += "to_char(" + paStr_ColumnName
								+ ",'YYYYMMDD') <= " + paStr_Express2;
					}
				}
			}
		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		if (!piBoo_IsXML && Int_Count == 2) {
			return "(" + str_RetExpress + ")";
		} else {
			return str_RetExpress;
		}
	}

	/**
	 * 翻译区间条件的表达式
	 * 
	 * @param paStr_ColumnName1
	 *            String 字段名称
	 * @param paStr_ColumnName2
	 *            String 字段名称 paStr_ColumnName2>=paStr_ColumnName1
	 * @param paStr_Express1
	 *            String 字段条件表达式第一区间
	 * @param paStr_Express2
	 *            String 字段条件表达式第二区间 paStr_Express2>=paStr_Express1
	 * @param DATA_TYPE
	 *            char 字段类型
	 * @return String 条件表达式
	 */
	private static String QJCondition(String paStr_ColumnName1,
			String paStr_ColumnName2, String paStr_Express1,
			String paStr_Express2, char DATA_TYPE) {
		String str_RetExpress = "";
		boolean piBoo_IsXML = false;
		// 条件计数
		int Int_Count = 0;
		// 将一个区间符号分成两个子表达式
		if ((paStr_Express1.equals("") || paStr_Express1.equals("''"))
				&& (paStr_Express2.equals("") || paStr_Express2.equals("''"))) {
			throw new IllegalArgumentException("区间头尾两个值都为空，这个情况不允许。");
		}

		try {
			if (paStr_Express1.equals("") && paStr_Express1.equals("''")) { // ~value2　上区间的情况
				if (DATA_TYPE != DATA_TYPE_DATE) {
					str_RetExpress = "(" + paStr_ColumnName1 + " <= "
							+ paStr_Express2 + " ) ";
				} else {
					str_RetExpress = "(" + paStr_ColumnName1 + " <= to_date("
							+ paStr_Express2 + ",'YYYYMMDDHH24MISS') ) ";
				}
			} else if (paStr_Express2.equals("") && paStr_Express2.equals("''")) { // value1~下区间的情况
				if (DATA_TYPE != DATA_TYPE_DATE) {
					str_RetExpress = "(" + paStr_ColumnName2 + " >= "
							+ paStr_Express1 + " ) ";
				} else {
					str_RetExpress = "(" + paStr_ColumnName2 + " >= to_date("
							+ paStr_Express1 + ",'YYYYMMDDHH24MISS') ) ";
				}
			} else {
				if (DATA_TYPE != DATA_TYPE_DATE) {
					str_RetExpress = "(NOT ((" + paStr_ColumnName2 + " < "
							+ paStr_Express1 + " ) OR (" + paStr_ColumnName1
							+ ">" + paStr_Express2 + ")))";
				} else {
					str_RetExpress = "(NOT ((" + paStr_ColumnName2
							+ " < to_date(" + paStr_Express1
							+ ",'YYYYMMDDHH24MISS') ) OR (" + paStr_ColumnName1
							+ "> to_date(" + paStr_Express2
							+ ",'YYYYMMDDHH24MISS'))))";
				}
			}
		} catch (Exception PaEx_Ex) {
			throw new RuntimeException(PaEx_Ex.getMessage());
		}

		if (!piBoo_IsXML && Int_Count == 2) {
			return "(" + str_RetExpress + ")";
		} else {
			return str_RetExpress;
		}
	}

	/**
	 * 将外部传入的条件表达式转换为内部的表示形式，主要完成以下4部分工作. <br>
	 * 1 用户自定义特殊字符转换为内部的字符 <br>
	 * 2 进行全角半角字符的替换 <br>
	 * 3 删除表达式中空格 <br>
	 * 3 删除多余的逗号
	 * 
	 * @param paStr_Express
	 *            String 需要处理的条件表达式串
	 * @return String 处理后的表达式串
	 */
	private static String toInnerString(String paStr_Express) {
		// 进行用户自定义符号的替换
		// 进行符号的全角和半角替换
		paStr_Express = paStr_Express.replace('＊', '%');
		paStr_Express = paStr_Express.replace('*', '%');
		paStr_Express = paStr_Express.replace('×', '%');
		paStr_Express = paStr_Express.replace('％', '%');
		paStr_Express = paStr_Express.replace('？', '_');
		paStr_Express = paStr_Express.replace('?', '_');
		paStr_Express = paStr_Express.replace('，', ',');
		paStr_Express = paStr_Express.replace('！', '!');
		paStr_Express = paStr_Express.replace('～', '~');
		// 将表达式中的空格删除
		paStr_Express = StringUtils.replaceString(paStr_Express, " ", "");
		// 删除头尾逗号和中间的无用逗号
		int loInt_begin = 0, loInt_end = 0;
		for (int i = 0; i < paStr_Express.length(); i++) {
			if (paStr_Express.charAt(i) == ',') {
				loInt_begin++;
				continue;
			}
			break;
		}
		for (int i = paStr_Express.length() - 1; i >= 0; i--) {
			if (paStr_Express.charAt(i) == ',') {
				loInt_end++;
				continue;
			}
			break;
		}
		// 截除头尾多余的逗号
		paStr_Express = paStr_Express.substring(loInt_begin, paStr_Express
				.length()
				- loInt_end);
		// 去除中间多余的逗号
		while (paStr_Express.indexOf(",,") > -1) {
			paStr_Express = StringUtils.replaceString(paStr_Express, ",,", ",");
		}

		// ///////////////////////////////////////////////////////////////////////

		return paStr_Express;

	}

	/**
	 * 查询符号替换
	 * 
	 * @param paStr_ExpressValue
	 *            String 用户条件表达式
	 * @param DATA_TYPE
	 *            char 列类型
	 * @param paBoo_toMin
	 *            boolean 转为最小，false 转为最大
	 * @return String 替换后的表达式
	 */
	private static String toFullDateString(String paStr_ExpressValue,
			char DATA_TYPE, boolean paBoo_toMin) {
		switch (DATA_TYPE) {
		case DATA_TYPE_AGE:
			int Int_year = StringUtils.getInt(DateUtils.getDateFormat("yyyy"));
			if (paStr_ExpressValue.length() != 0
					&& paStr_ExpressValue.length() <= 3) {
				paStr_ExpressValue = new Integer(Int_year
						- Integer.parseInt(paStr_ExpressValue)).toString();
			}
		case DATA_TYPE_STR_DATE_8:
			if (paStr_ExpressValue.length() != 8) {
				if (paBoo_toMin) { // to floor
					paStr_ExpressValue = paStr_ExpressValue
							+ "00000101".substring(paStr_ExpressValue.length());
				} else { // to ceil
					paStr_ExpressValue = paStr_ExpressValue
							+ "99991231".substring(paStr_ExpressValue.length());
				}
			} else {
				paStr_ExpressValue = paStr_ExpressValue;
			}
			return paStr_ExpressValue;
		case DATA_TYPE_STR_DATE_14:
		case DATA_TYPE_DATE:
			if (paStr_ExpressValue.length() != 14) {
				if (paBoo_toMin) { // to floor
					paStr_ExpressValue = paStr_ExpressValue
							+ "00000101000000".substring(paStr_ExpressValue
									.length());
				} else { // to ceil
					paStr_ExpressValue = paStr_ExpressValue
							+ "99991231235959".substring(paStr_ExpressValue
									.length());
				}
			} else {
				paStr_ExpressValue = paStr_ExpressValue;
			}
			return paStr_ExpressValue;
		}

		return "";
	}

	public static void main(String[] args) {
		System.out.println("12~34(年龄)－－>"
				+ columnExpess("aa", "12~34", DATA_TYPE_AGE));
		System.out.println("！陈~汤，何(字符)－－>"
				+ columnExpess("aa", "！陈~汤，何", DATA_TYPE_BM));

		System.out.println("！陈~汤，何(字符)－－>"
				+ columnExpess("aa", "！陈~汤，何", DATA_TYPE_CHAR));

		System.out.println("陈~汤，何(字符)－－>"
				+ columnExpess("aa", "陈~汤，何", DATA_TYPE_CHAR));

		System.out.println("2002～2004，！2003－－>"
				+ columnExpess("aa", "2002～2004，！2003", DATA_TYPE_STR_DATE_14));
		System.out.println("2002～2004，！2003－－>"
				+ columnExpess("aa", "2002～2004,!2003", DATA_TYPE_NUMBER));
		System.out.println("2002～2004，！2003－－>"
				+ columnExpess("aa", "2002～2004,!2003", DATA_TYPE_DATE));

		System.out.println("200202020209-->"
				+ columnExpess("aa", "bb", "2002～2004，！2003",
						DATA_TYPE_STR_DATE_14));
	}
}
