package com.infolion.sapss.common;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;

public class WorkflowUtils {
	/**
	 * 根据基础流程名和其它规则确定流程版本
	 * @param baseWorkflowName
	 * @return
	 */
	public static String chooseWorkflowName(String baseWorkflowName){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String code = txt.getSysDept().getDeptcode();//部门编码
		String def = baseWorkflowName;
		if(baseWorkflowName.indexOf("_v")!=-1){
			int pos = baseWorkflowName.lastIndexOf("_v");
			baseWorkflowName = baseWorkflowName.substring(0, pos);
		}
		if(code!=null && !"".equals(code)){
			if(code.startsWith("22")){
				return def;
				//return baseWorkflowName+"_v2";//上海信达诺国际贸易有限公司
			}else if("1002".equals(code)){
				return baseWorkflowName+"_v5";//上海信达诺
			}else if(code.startsWith("24")){
				return baseWorkflowName+"_v3";//厦门信达安贸易有限公司
			}else if("2501".equals(code)){
				return baseWorkflowName+"_v4";//广西金达有色金属有限公司
			}else if(code.startsWith("23")){
				return baseWorkflowName+"_v6";//香港信达诺国际有限公司
			}
			else if(code.startsWith("27")){
				return def;
				//return baseWorkflowName+"_v7";//石油公司
			}
			else if(code.startsWith("28")){
				return def;
				//return baseWorkflowName+"_v8";//芜湖 公司
			}
			else if(code.startsWith("26")){
				return baseWorkflowName+"_v9";//香港信达诺
			}
			else if(code.startsWith("29")){
				return def;
				//return baseWorkflowName+"_v10";//成都信达诺
			}
			else if(code.startsWith("30")){
				return baseWorkflowName+"_v11";//新加坡信达安
			}
			else if(code.startsWith("31")){
				return def;//新加坡资源
			}
		}
		return def;
	}
	
	public static String chooseWorkflowName(String baseWorkflowName,String code){
		String def = baseWorkflowName;
		if(baseWorkflowName.indexOf("_v")!=-1){
			int pos = baseWorkflowName.lastIndexOf("_v");
			baseWorkflowName = baseWorkflowName.substring(0, pos);
		}
		if(code!=null && !"".equals(code)){
			if(code.startsWith("22")){
				return def;
				//return baseWorkflowName+"_v2";//上海信达诺国际贸易有限公司
			}else if("1002".equals(code)){
				return baseWorkflowName+"_v5";//上海信达诺
			}else if(code.startsWith("24")){
				return baseWorkflowName+"_v3";//厦门信达安贸易有限公司
			}else if("2501".equals(code)){
				return baseWorkflowName+"_v4";//广西金达有色金属有限公司
			}else if(code.startsWith("23")){
				return baseWorkflowName+"_v6";//香港信达诺国际有限公司
			}
			else if(code.startsWith("27")){
				return def;
				//return baseWorkflowName+"_v7";//石油公司
			}
			else if(code.startsWith("28")){
				return def;
				//return baseWorkflowName+"_v8";//芜湖 公司
			}
			else if(code.startsWith("26")){
				return baseWorkflowName+"_v9";//香港信达诺
			}else if(code.startsWith("29")){
				return def;
				//return baseWorkflowName+"_v10";//成都信达诺
			}else if(code.startsWith("30")){
				return baseWorkflowName+"_v11";//新加坡信达安
			}else if(code.startsWith("31")){
				return def;
				//return baseWorkflowName+"_v12";//信达资源
			}
		}
		return def;
	}
	/**
	 * 是否关联立项
	 * @return
	 */
	public static boolean isRelatedProject(){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String code = txt.getSysDept().getDeptcode();//部门编码
		
		if("1".equals(txt.getSysDept().getIsFuncDept()))
			return false;//职能部门不需要关联立项
		if("2201".equals(code)){
			return true;//上海信达诺国际贸易有限公司
		}else if("1002".equals(code)){
			return false;//上海信达诺
		}else if("2401".equals(code)){
			return true;//厦门信达安贸易有限公司
		}else if("2501".equals(code)){
			return true;//广西金达有色金属有限公司
		}else if("2301".equals(code)||"2601".equals(code)||"2701".equals(code)||"2801".equals(code)||"2901".equals(code)||"3001".equals(code)){
			return true;
		}
		return true;
	}
	/**
	 * 是否关联合同
	 * @return
	
	public static boolean isRelatedContract(){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String code = txt.getSysDept().getDeptcode();//部门编码
		if("2201".equals(code)){
			return true;//上海信达诺国际贸易有限公司
		}else if("1002".equals(code)){
			return false;//上海信达诺
		}else if("2401".equals(code)){
			return true;//厦门信达安贸易有限公司
		}else if("2501".equals(code)){
			return false;//广西金达有色金属有限公司
		}
		return true;
	}
	 */
	/**
	 * 是否是外贸公司的部门
	 * 比较:上海信达诺国际贸易有限公司(2200),上海信达诺(1002),厦门信达安贸易有限公司(2400),广西金达有色金属有限公司(2500)
	 * @param deptcode
	 * @return
	 */
	public static boolean isForeignTradeDept(String deptcode) {
		String depts[] = notForeignTradeDeptCodes();
		for(int i=0;i<depts.length;i++){
			if(depts[i].equals(deptcode))
				return false;
		}
		return true;
	}
	/**
	 * 非外贸部门代码
	 * @return
	 */
	private static String[] notForeignTradeDeptCodes(){
		String[] depts=new String[]{"2201","1002","2401","2501","2601","2701","2801","2901","3001","3098"};
		return depts;
	}
	
	public static String getCompanyName(String companyCode){
		if("2100".equals(companyCode)) return "信达股份有限公司";
		else if("2200".equals(companyCode)) return "上海信达诺国际贸易有限公司";
		else if("2400".equals(companyCode)) return "厦门信达安贸易有限公司";
		else if("2600".equals(companyCode)) return "香港信达诺有限公司";
		else if("2700".equals(companyCode)) return "信达(厦门)石油有限公司";		
		else if("2800".equals(companyCode)) return "芜湖信达贸易有限公司";
		else if("2900".equals(companyCode)) return "成都信达诺投资有限公司";
		else if("3000".equals(companyCode)) return "新加坡信达安资源有限公司";
		else if("3100".equals(companyCode)) return "信达资源（新加坡）有限公司";
		return "";
	}
}
