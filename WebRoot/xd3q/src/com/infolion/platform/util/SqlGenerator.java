/*
 * @(#)SqlGenerator.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：整理
 */
package com.infolion.platform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * <pre>
 * SQL构造器，根据hashMap的键值对规则构造SQL.
 * 根据本类中规定的命名规则、表达式规则构造出INSERT、UPDATE、WHERE条件语句
 * </pre>
 <B><A name="sqlgenerator">SQL构造器命名规则</A></B>
 <table border=1 style="border-collapse:collapse;font-size:12px" bordercolor="#333333">
  <tr align="center" valign="middle" bgcolor="#ccccff" height="22">
    <td rowspan="2"><strong>命名规则</strong></td>
    <td rowspan="2"><strong>规则说明</strong></td>
    <td colspan="4"><strong>构造的SQL语句</strong></td>
  </tr>
  <tr height="22">
    <td align="center" bgcolor="#ccccff"><strong>INSERT</strong></td>
    <td align="center" bgcolor="#ccccff"><strong>UPDATE</strong></td>
    <td align="center" bgcolor="#ccccff"><strong>DELETE</strong></td>
    <td align="center" bgcolor="#ccccff"><strong>WHERE</strong></td>
  </tr>
  <tr valign="middle">
    <td height="22">SYSARG_TYPE</td>
    <td height="22">SQL语句操作类型</td>
    <td height="22" align="center">insert 可选</td>
    <td height="22" align="center">update 可选</td>
    <td height="22" align="center">delete 可选</td>
    <td height="22" align="center">无</td>
  </tr>
  <tr valign="middle">
    <td height="22">SYSARG_WHERE</td>
    <td height="22">  SQL语句的过滤条件</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22">SYSARG_GROUPBY</td>
    <td height="22">  SQL查询语句的分组语句</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22">SYSARG_ORDERBY</td>
    <td height="22">  SQL查询语句的排序语句</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22"><b>Name</b></td>
    <td height="22">数据库字段名</td>
    <td height="22" align="center">必要</td>
    <td height="22" align="center">必要</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">无</td>
  </tr>
  <tr valign="middle">
    <td height="22">BUTTON_<b>Name</b></td>
    <td height="22" colspan="5">不参与SQL语句构造的参数</td>
  </tr>
  <tr valign="middle">
    <td height="22">TEMP_<b>Name</b></td>
    <td height="22" colspan="5">不参与SQL语句构造的参数</td>
  </tr>
  <tr valign="middle">
    <td height="22">REPLACE_<strong>Name</strong>_val</td>
    <td height="22" colspan="5">参与构造 <a href="#replace">参见</a></td>
  </tr>
  <tr valign="middle">
    <td height="22"> SYSIGNORE_<b>Name</b></td>
    <td height="22">  在SQL插入语句中实现调用数据库内部函数功能
    </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">无 </td>
    <td height="22" align="center">无 </td>
  </tr>
  <tr valign="middle">
    <td height="22">NNN_<b>Name</b></td>
    <td height="22">标明此参数为数字类型</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22"><b>Name</b>_WHERE_BETWEEN1<br>
      <font color="#0000FF">可加前缀SYSIGNORE_或NNN_<b></b></td>
    <td height="22">在SQL查询语句的过滤条件中实现大等于功能</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22"><b>Name</b>_WHERE_BETWEEN2<br>
      <font color="#0000FF">可加前缀SYSIGNORE_或NNN_<b></b></td>
    <td height="22">在SQL查询语句的过滤条件中实现小等于功能</td>
    <td height="22" align="center">无</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
    <td height="22" align="center">可选</td>
  </tr>
  <tr valign="middle">
    <td height="22"><b>Name</b>_WHERE_LIKE</td>
    <td height="22">在SQL查询语句的过滤条件中实现like
      ‘%val%’功能 </td>
    <td height="22" align="center">无 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
  </tr>
  <tr valign="middle">
    <td height="22"> <b>Name</b>_WHERE_IS</td>
    <td height="22">  在SQL查询语句的过滤条件中实现IS NULL或IS NOT NULL功能 </td>
    <td height="22" align="center">无 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
  </tr>
  <tr valign="middle">
    <td height="22"> <b>Name</b>_WHERE<br>
      <font color="#0000FF">可加前缀SYSIGNORE_或NNN_<b></b></td>
    <td height="22">  在SQL查询语句的过滤条件中实现等于功能
    </td>
    <td height="22" align="center">无 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
  </tr>
  <tr valign="middle">
 <td height="22"><b>Name</b>_WHERE_EXP_<strong>DataType</strong><br>      </td>
    <td height="22">参与构造 <a href="#datatype">参见</a></td>
    <td height="22" align="center">无 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
    <td height="22" align="center">可选 </td>
  </tr>
 </table>
 <DIV STYLE="FONT-SIZE:12PX">
 读表说明:
 <li>加粗<B>Name</B>为数据库字段名，可根据实际需要确定；
 <li>REPLACE_Name_val中val为替换对象，可根据实际需要确定；
 <li>Name_WHERE_EXP_DataType为表达式条件语句构造规则，其中DataType规则<A href="#datatype">参见专门的说明</A>；
 <li>其余名称为SQL构造器保留字，不能修改，但大小写不敏感。
 <BR>
 <pre>
  例子：
    ·构造一条插入表systuser中记录的SQL语句
      HashMap map = new HashMap();
      map.put("userid","zhangsan");
      map.put("username","张三");//用户名为张三
      map.put("Password","123");//密码123
      map.put("sysignore_salary","3100");//工资为3100

      //map.put("Sysarg_type","update");//告诉构造器是进行插入操作

      SqlGenerator bsp = new SqlGenerator();

      //String sql = bsp.getSQL(map,"systuser");
      String sql = bsp.getInsertSQL(map,"systuser");

      System.out.println(sql);

      构造出的SQL语句为：
        INSERT INTO systuser(Password,userid,username,salary)
                      VALUES('123','zhangsan','张三',3100)



    ·构造一条修改表systuser中记录的SQL语句
      HashMap map = new HashMap();

      //要修改的字段
      map.put("salary","3100");//修改工资为3100
      map.put("Password","");//密码修改为空

      //以下为条件
      map.put("deptid_where_is","null");//部门为空
      map.put("Userid_where_is","not null");//用户ID非空

      map.put("USERNAME_WHERE_EXP_CHAR","!李四");//用户姓名不为李四

      //map.put("replace_sysignore_czrq00_aa","20041005");
      map.put("replace_csrq00_aa","20041005");//出生日期为20041005
      map.put("sysignore_czrq00_where_between1","to_char('aa','yyyy-mm-dd')");

      map.put("Sysarg_type","update");//告诉构造器是进行修改操作
      SqlGenerator bsp = new SqlGenerator();

      String sql = bsp.getSQL(map,"systuser");

      System.out.println(sql);

      构造出的SQL语句为：
        UPDATE systuser SET Password = NULL,salary = '3100'
             WHERE Userid IS not null AND
             (NOT USERNAME = '李四' ) AND
             czrq00 >= to_char('aa','yyyy-mm-dd') AND
             deptid IS null
 </pre>
 </DIV>
 <BR>
 <BR>
 <font color="#000033"><a name="replace"></a>REPLACE_<strong>Name</strong>_val用法:</font><BR>
  <pre>
  处理以replace_开头的参数
 * 当前找到replace_(字段名 可不含修饰符,如sysignore_rq0000,则为rq0000)_(AA)时,
 * 此参数值用于替换rq0000的参数值中的AA
 * 例子:

   ·在HashMap中：
     HashMap中键值对：    replace_rq0000_AA = 20041005 及sysignore_rq0000 = to_date('AA','yyyy-mm-dd')
     调用构造器处理结果： rq0000=to_date('20041005','yyyy-mm-dd')
     说明：构造器读取replace_rq0000_AA的值后，
 在HashMap中查找类似于 rq0000、sysignore_rq0000、nnn_rq0000、rq0000_where的键（大小写不敏感），
           如果找到则在找到的键的值中查找AA，然后用replace_rq000_AA的值替换。（其中AA大小写敏感）

   ·在页面的表单中：
 一文本表单域（用户可输入值）:&lt;type="text" name="replace_rq0000_AA"  value="20041005"&gt;
     一隐藏表单域:                &lt;type="hidden" name="sysignore_rq0000" value="to_date('AA','yyyy-mm-dd')"&gt;
     提交到构造器处理结果是:      rq0000=to_date('20041005','yyyy-mm-dd')
     说明：构造器读取replace_rq0000_AA的值后，
 在表单中查找类似于 rq0000、sysignore_rq0000、nnn_rq0000、rq0000_where的表单域（大小写不敏感），
           如果找到则在找到的表单域的值中查找AA，然后用replace_rq000_AA的值替换。（其中AA大小写敏感）
  </pre>
 <BR>
 <BR>
 <font color="#000033"><a name="datatype"></a><strong>Name</strong>_WHERE_EXP_DataType用法:</font><BR>
 <B>DataType类型说明</B>
 <table border="1" style="border-collapse:collapse;font-size:12px" bordercolor="#333333">
  <tr align="center" valign="middle" bgcolor="#ccccff" height="30">
    <td width="48"><strong>类型</strong></td>
    <td><strong>类型说明</strong></td>
 <td><strong>例子</strong></td>
  </tr>
  <tr height="22" valign="middle">
    <td>AGE</td>
 <td>
  年龄，表达式的数字为年龄，
  <BR>它需要转化为出生日期进行构造。
  <BR>表达表中的规则<A href="#express">参见下表</A>。
 </td>
 <td>
  CSRQ00_WHERE_EXP_AGE，
  <BR>设值为 14
  <BR>则结果为 (CSRQ00 LIKE '1990%')
  <BR>设值为 14~24
  <BR>则结果为 (CSRQ00 >= '19800000' AND CSRQ00 <= '19901231')
  <BR>设值为 14,24
  <BR>则结果为 (CSRQ00 like '1990%' OR CSRQ00 like '1980%' )
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>CHAR</td>
 <td>
  字符，表达式将按字符方式构造。
 </td>
 <td>
  USERNAME_WHERE_EXP_CHAR，
  <BR>设值为 张三
  <BR>则结果为 (USERNAME = '张三')
  <BR>设值为 李~张
  <BR>则结果为 (USERNAME >= '李' AND USERNAME <= '张')
  <BR>设值为 李四,张三
  <BR>则结果为 (USERNAME = '李四' OR USERNAME = '张三' )
  <BR>设值为 张?
  <BR>则结果为 (USERNAME like '张_' )
  <BR>设值为 张*
  <BR>则结果为 (USERNAME like '张%' )
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>BM</td>
 <td>
  表码，同CHAR。
 </td>
 <td>
  CSRQ00_WHERE_EXP_BM
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>NUMBER</td>
 <td>
  数字，表达式将按数字方式构造。
 </td>
 <td>
  SALARY_WHERE_EXP_NUMBER，
  <BR>设值为 1400
  <BR>则结果为 (SALARY = 1400 )
  <BR>设值为 1400~3000
  <BR>则结果为 (SALARY >= 1400 AND SALARY <= 3000)
  <BR>设值为 1400,3000
  <BR>则结果为 (SALARY = 1400 OR SALARY = 3000 )
  <BR>设值为 14??,
  <BR>则结果为 (SALARY like 14__ )
  <BR>设值为 14*,
  <BR>则结果为 (SALARY like 14% )
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>D8</td>
 <td>
  日期，8位日期yyyyMMdd格式。
 </td>
 <td>
  RQ0000_WHERE_EXP_D8，
  <BR>设值为 2004
  <BR>则结果为 (RQ0000 LIKE '2004%' )
  <BR>设值为 200410
  <BR>则结果为 (RQ0000 LIKE '200410%' )
  <BR>设值为 20041005
  <BR>则结果为 (RQ0000 = '20041005' )
  <BR>设值为 20041005~20041031
  <BR>则结果为 (RQ0000 >= '20041005' AND RQ0000 <= '20041031')
  <BR>设值为 20041005,20041031
  <BR>则结果为 (RQ0000 = '20041005' OR RQ0000 = '20041031' )
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>D14</td>
 <td>
  日期，14位日期yyyyMMddHHmmss格式。
 </td>
 <td>
  RQ0000_WHERE_EXP_D14，
  <BR>设值为 2004
  <BR>则结果为 (RQ0000 LIKE '2004%' )
  <BR>设值为 2004100501
  <BR>则结果为 (RQ0000 LIKE '2004100501%' )
  <BR>设值为 20041005010101
  <BR>则结果为 (RQ0000 = '20041005010101' )
  <BR>设值为 20041005~20041031
  <BR>则结果为 (RQ0000 >= '20041005000000' AND RQ0000 <= '20041031235959')
  <BR>设值为 20041005,20041031
  <BR>则结果为 (RQ0000 like '20041005%' OR RQ0000 like '20041031%' )
 </td>
  </tr>
 </table>
 <A name="express"><B>表达表规则说明</B></A>
 <table border="1" style="border-collapse:collapse;font-size:12px" bordercolor="#333333">
  <tr align="center" valign="middle" bgcolor="#ccccff" height="30">
    <td><strong>符号</strong></td>
    <td><strong>符号说明</strong></td>
 <td><strong>例子</strong></td>
  </tr>
  <tr height="22" valign="middle">
    <td>*、×、％、%</td>
 <td>
  转换成%,
  <BR>即oracle查询中的全匹配字符
  <BR>（不区分全角和半角）
 </td>
 <td>
  USERNAME_WHERE_EXP_CHAR，
  <BR>设值为 李*生
  <BR>则结果为 (USERNAME like '李%生' )
 </td>
  </tr>
  <tr height="22" valign="middle">
    <td>？、?、_</td>
 <td>
  转换成_,
  <BR>即oracle查询中的单字符匹配字符
  <BR>（不区分全角和半角）
 </td>
 <td>
  USERNAME_WHERE_EXP_CHAR，
  <BR>设值为 李?生
  <BR>则结果为 (USERNAME like '李_生' )
 </td>
  </tr>
    <tr height="22" valign="middle">
    <td>~</td>
 <td>
  转换成区间查询语句
  <BR>（不区分全角和半角）
 </td>
 <td>
  <BR>设值为 20041005~20041031
  <BR>则结果为 (RQ0000 >= '20041005000000' AND RQ0000 <= '20041031235959')
 </td>
  </tr>
  </tr>
    <tr height="22" valign="middle">
    <td>!、！</td>
 <td>
  转换成SQL非语句 NOT
  <BR>（不区分全角和半角）
 </td>
 <td>
  USERNAME_WHERE_EXP_CHAR，
  <BR>设值为 !李四
  <BR>则结果为 (NOT USERNAME = '李四' )
  <BR>设值为 李*生,!李四生
  <BR>则结果为 (USERNAME like '李%生' ) AND (NOT USERNAME = '李四生' )
 </td>
  </tr>
    </tr>
    <tr height="22" valign="middle">
    <td>,、，</td>
 <td>
  分隔的多个值转换成OR的关系
  <BR>（不区分全角和半角）
 </td>
 <td>
  RQ0000_WHERE_EXP_D14，
  <BR>设值为 20041005,20041031
  <BR>则结果为 (RQ0000 like '20041005%' OR RQ0000 like '20041031%' )
 </td>
  </tr>
 </table>
 *
 * <BR><DL><DT><B>JDK版本:</B></DT><BR><DD>1.4</DD></DL>
 * @author  
 * @version  1.0
 * @since    1.0
 */
public class SqlGenerator
{

    //为结合QueryExpressTranslater使用
    /**
     * 年龄（要求在构造时转化为yyyyMMdd格式日期的比较方式）
     */
    protected static final String DATA_TYPE_AGE = "AGE"; //年龄

    /**
     * 表码（与DATA_TYPE_CHAR类似）
     */
    protected static final String DATA_TYPE_BM = "BM"; //表码

    /**
     * 字符或字符串（与DATA_TYPE_BM类似）
     */
    protected static final String DATA_TYPE_CHAR = "CHAR"; //字符

    /**
     * 数字
     */
    protected static final String DATA_TYPE_NUMBER = "NUMBER"; //数字

    /**
     * 以8位表示的日期型yyyymmdd
     */
    protected static final String DATA_TYPE_STR_DATE_8 = "D8"; //以8位表示的日期型yyyymmdd

    /**
     * 以14位表示的日期型yyyymmddhh24miss
     */
    protected static final String DATA_TYPE_STR_DATE_14 = "D14"; //以14位表示的日期型yyyymmddhh24miss

    //保存sql
    private StringBuffer v_sqlBuf1; //insert时字段部分，update时的全部除where语句
    private StringBuffer v_sqlBuf2; //insert时的values部分，update时没用上
    private StringBuffer v_sqlBuf3; //where
    private StringBuffer v_sqlBuf4; //group by
    private StringBuffer v_sqlBuf5; //order by

    private String v_replaceStr = "'||chr(39)||'"; //缺省用于替换单引号的字符
    private String v_ignorereplist = ""; //不被替换单引号的字段

    private String v_SpecialDBColumnList; //只可以包含的列
    private String v_SpecialIgnoreParamList; //应忽略掉的列

    /**
     * 用于在构造SQL时限定哪些参数可以参与构造SQL，用|间隔。
     */
    public final static String INCLUDECOLS = "BUTTON_SpecialDBColumnName_lIST";

    /**
     * 用于在构造SQL时限定哪些参数被忽略参与构造SQL，用|间隔。
     */
    public final static String IGNORECOLS = "BUTTON_SpecialIgnoreParam_lIST";

    /**
     * 构造函数
     */
    public SqlGenerator()
    {
    }

    /**
     * 设置构造sql过程中单引号是否进行替换成str.
     * <BR>如果不调用此方法，执行设置操作则默认设置为Oracle支持的方式：<code> '||chr(39)||' </code>
     * <BR>在SQLServer中时调用此方法，入参使用两个单引号： <code>''</code>
     * @param str
     * 单引号被替换的字符,如果str为null则不进行替换
     */
    public void setReplace(String str)
    {
        this.v_replaceStr = str;
    }

    /**
     * 设置构造sql过程中单引号不进行进行替换的域.
     * <br>在不调用此方法时，默认设置为进行全部替换（除SYSIGNORE_开头及NNN_开头的域）
     * @param ignorereplist
     * 不被替换的字段列表,多个用默认分隔符 | 间隔
     */
    public void setIgnoreReplace(String ignorereplist)
    {
        this.v_ignorereplist = (ignorereplist == null) ? "" :
            "|" + ignorereplist + "|";
    }

    /**
     * 构造Insert语句
     * @param hashmap
     * 含有键值的集合(符全构造SQL的参数规则)
     * @param tableName
     * 表名
     * @return
     * 返回sql语句
     */
    public String getInsertSQL(HashMap hashmap,String tableName)
    {
        if(hashmap == null || tableName == null || tableName.trim().equals("")){
            return "";
        }
        getSQLPart(hashmap,1);
        v_sqlBuf1 = removeExcrescentMark(v_sqlBuf1,",");
        v_sqlBuf2 = removeExcrescentMark(v_sqlBuf2,",");
        StringBuffer sqlbuf = new StringBuffer("INSERT INTO ");
        sqlbuf.append(tableName).append("(").append(v_sqlBuf1).append(")");
        sqlbuf.append(" VALUES(").append(v_sqlBuf2).append(")");
        return sqlbuf.toString();
    }

    /**
     * 构造update语句
     * @param hashmap
     * 含有键值的集合(符全构造SQL的参数规则)
     * @param tableName
     * 表名
     * @return
     * 返回sql语句
     */
    public String getUpdateSQL(HashMap hashmap,String tableName)
    {
        if(hashmap == null || tableName == null || tableName.trim().equals("")){
            return "";
        }
        getSQLPart(hashmap,2);
        v_sqlBuf1 = removeExcrescentMark(v_sqlBuf1,",");
        StringBuffer sqlbuf = new StringBuffer("UPDATE ");
        sqlbuf.append(tableName).append(" SET ").append(v_sqlBuf1);

        if(!(v_sqlBuf3.toString()).trim().equals("")){
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3,"AND ");
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3," AND");
            sqlbuf.append(" WHERE ").append(v_sqlBuf3);
        }
        return sqlbuf.toString();
    }

    /**
     * 构造delete语句
     * @param hashmap
     * 含有键值的集合(符全构造SQL的参数规则)
     * @param tableName
     * 表名
     * @return
     * 返回sql语句
     */
    public String getDeleteSQL(HashMap hashmap,String tableName)
    {
        if(hashmap == null || tableName == null || tableName.trim().equals("")){
            return "";
        }
        getSQLPart(hashmap,3);
        StringBuffer sqlbuf = new StringBuffer("DELETE FROM ");
        sqlbuf.append(tableName);
        if(!(v_sqlBuf3.toString()).trim().equals("")){
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3,"AND ");
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3," AND");
            sqlbuf.append(" WHERE ").append(v_sqlBuf3);
        }
        return sqlbuf.toString();
    }

    /**
     * 构造SQLWhere语句,不含where关键字
     * @param hashmap
     * 含有键值的集合(符全构造SQL的参数规则)
     * @return
     * 返回SQLWhere语句,不含where关键字
     */
    public String getSQLWhere(HashMap hashmap)
    {
        if(hashmap == null){
            return "";
        }
        getSQLPart(hashmap,0);
        StringBuffer sqlbuf = new StringBuffer("");
        if(!(v_sqlBuf3.toString()).trim().equals("")){
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3,"AND ");
            v_sqlBuf3 = removeExcrescentMark(v_sqlBuf3," AND");
            sqlbuf.append(v_sqlBuf3);
        }
        return sqlbuf.toString();
    }

    /**
     * 只实现构造insert,update,delete语句,不实现select语句.
     * 构造select请使用getSQLWhere(request)实现构造where条件不含where关键字
     * 使用此方法要请设置sysarg_type参数
     *
     * @param hashmap 含有键值的集合(符全构造SQL的参数规则)
     * @param tableName 表名
     * @return 构造出的SQL语句
     * @throws RuntimeException
     */
    public String getSQL(HashMap hashmap,String tableName)
        throws RuntimeException
    {
        if(hashmap == null || tableName == null || tableName.trim().equals("")){
            return "";
        }
        String Retu_sql = "";
        String sysarg_type = getStringFromHash(hashmap,"SYSARG_TYPE");
        if(sysarg_type == null){
            throw new RuntimeException("没有设置SYSARG_TYPE关键字");
        }
        if(sysarg_type.trim().equalsIgnoreCase("INSERT")){
            Retu_sql = this.getInsertSQL(hashmap,tableName);
        }
        else if(sysarg_type.trim().equalsIgnoreCase("UPDATE")){
            Retu_sql = this.getUpdateSQL(hashmap,tableName);
        }
        else if(sysarg_type.trim().equalsIgnoreCase("DELETE")){
            Retu_sql = this.getDeleteSQL(hashmap,tableName);
        }
        else{
            throw new RuntimeException("设置的SYSARG_TYPE关键字非法:" + sysarg_type);
        }
        return Retu_sql;
    }

    /**
     * 从HashMap中得到构造SQL的各个组成部分
     * @param hashmap
     * 包含有键(全大写字母),值的HashMap,用于按指定规则构造SQL
     * @param operationType
     * 操作类型1表insert 2表update 3表delete 0或其它表select
     */
    private void getSQLPart(HashMap hashmap,int operationType)
    {
        v_sqlBuf1 = new StringBuffer("");
        v_sqlBuf2 = new StringBuffer("");
        v_sqlBuf3 = new StringBuffer("");
        v_sqlBuf4 = new StringBuffer("");
        v_sqlBuf5 = new StringBuffer("");
        if(hashmap == null || hashmap.isEmpty()){
            hashmap = new HashMap(1);
            return;
        }

        //取得设置值（用于处理字段是否要参与构造）
        this.v_SpecialDBColumnList = getStringFromHash(hashmap,
            SqlGenerator.INCLUDECOLS);
        if(this.v_SpecialDBColumnList != null &&
           !this.v_SpecialDBColumnList.trim().equals("")){
            this.v_SpecialDBColumnList = "|" +
                this.v_SpecialDBColumnList.toUpperCase() + "|";
        }
        this.v_SpecialIgnoreParamList = getStringFromHash(hashmap,
            SqlGenerator.IGNORECOLS);
        if(this.v_SpecialIgnoreParamList != null &&
           !this.v_SpecialIgnoreParamList.trim().equals("")){
            this.v_SpecialIgnoreParamList = "|" +
                this.v_SpecialIgnoreParamList.toUpperCase() + "|";
        }

        String t_colname = null; //在hashMap中的域名字（未经过大写转化）
        String t_up_colname = null; //从hashMap中取出的域名字并经过大写转化
        String colname = null; //从hashMap中取出的域名字并经过大写转化后去其命名规则的前缀及后缀
        String t_colvalue = null;
        String colvalue = null;
        java.util.Set set = hashmap.keySet();
        java.util.Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            t_colname = (String)iterator.next();
            if(!isField(t_colname)){
                continue; //非字段域
            }
            t_colvalue = (String)hashmap.get(t_colname);

            //把t_colname转化为大写
            t_up_colname = t_colname.toUpperCase();
            if(t_up_colname.indexOf("_WHERE") >= 0){ //构造where条件
                makeSQLWhere(hashmap,v_sqlBuf3,t_colname,t_colvalue);
            }
            else{ //构造insert及update全部及前面部分
                if(t_up_colname.startsWith("SYSIGNORE_")){ //不加单引号（表函数等）
                    colname = t_colname.substring(10);
                    colvalue = getReplaceValue(hashmap,t_up_colname,
                                               colname.toUpperCase(),
                                               t_colvalue);
                    if(operationType == 1){ //insert
                        if(colvalue == null || colvalue.equals("")){
                            continue;
                        }
                        v_sqlBuf1.append(colname).append(",");
                        v_sqlBuf2.append(colvalue).append(",");
                    }
                    else if(operationType == 2){ //update
                        if(colvalue == null || colvalue.equals("")){
                            colvalue = "NULL";
                        }
                        v_sqlBuf1.append(colname).append(" = ").append(colvalue).
                            append(",");
                    }
                }
                else if(t_up_colname.startsWith("NNN_")){ //不加单引号（表数字，与SYSIGNORE_类似）
                    colname = t_colname.substring(4);
                    colvalue = getReplaceValue(hashmap,t_up_colname,
                                               colname.toUpperCase(),
                                               t_colvalue);
                    if(operationType == 1){ //insert
                        if(colvalue == null || colvalue.equals("")){
                            continue;
                        }
                        v_sqlBuf1.append(colname).append(",");
                        v_sqlBuf2.append(colvalue).append(",");
                    }
                    else if(operationType == 2){ //update
                        if(colvalue == null || colvalue.equals("")){
                            colvalue = "NULL";
                        }
                        v_sqlBuf1.append(colname).append(" = ").append(colvalue).
                            append(",");
                    }
                }
                else{ //处理没有修饰符的参数
                    colname = t_colname;
                    colvalue = getReplaceValue(hashmap,t_up_colname,
                                               colname.toUpperCase(),
                                               t_colvalue);
                    if(operationType == 1){ //insert
                        if(colvalue == null || colvalue.equals("")){
                            continue;
                        }
                        else{
                            colvalue = "'" + colvalue + "'";
                        }
                        v_sqlBuf1.append(colname).append(",");
                        v_sqlBuf2.append(colvalue).append(",");
                    }
                    else if(operationType == 2){ //update
                        if(colvalue == null || colvalue.equals("")){
                            colvalue = "NULL";
                        }
                        else{
                            colvalue = "'" + colvalue + "'";
                        }
                        v_sqlBuf1.append(colname).append(" = ").append(colvalue).
                            append(",");
                    }
                } // end of 处理没有修饰符的参数
            } // end of 构造
        } // end of while

        //取得其它组成部分
        String sysarg_where = getStringFromHash(hashmap,"SYSARG_WHERE");
        String sysarg_groupby = getStringFromHash(hashmap,"SYSARG_GROUPBY");
        String sysarg_orderby = getStringFromHash(hashmap,"SYSARG_ORDERBY");
        v_sqlBuf3.append(sysarg_where);
        v_sqlBuf4.append(sysarg_groupby);
        v_sqlBuf5.append(sysarg_orderby);
    }

    /**
     * 根据参数名判断是否为字段域
     * @param param
     * request中的参数名
     * @return
     * 是返回true,否则返回false
     */
    private boolean isField(String param)
    {
        if(param == null || param.equals("")){
            return false;
        }

        param = param.toUpperCase();
        // SYSARG_ BUTTON_ TEMP_ REPLACE_
        if(param.startsWith("SYSARG_")){
            return false;
        }
        if(param.startsWith("BUTTON_")){
            return false;
        }
        if(param.startsWith("TEMP_")){
            return false;
        }
        if(param.startsWith("REPLACE_")){
            return false;
        }

        //判断是否允许参与构造
        if(!isSpecialDataBaseColumnName(param)){ //不可以参与构造
            return false;
        }

        //判断是否要求忽略参与构造
        if(isSpecialIgnoreParam(param)){ //将被忽略
            return false;
        }

        return true;
    }

    /**
     * 判断入参是否为指定的数据库字段（即要求参与到SQL构造中）。
     * 参照信息为构造信息中的HashMap对象中的BUTTON_SpecialDBColumnName_lIST，
     * 使用|分隔，
     * 如果对象中不包含此数据，则均返回true
     * @param paramName String
     * 参数名（要求大写）
     * @return boolean
     * 是返回true，不是返回false
     */
    private boolean isSpecialDataBaseColumnName(String paramName)
    {
        //没有设置此参数时，所有参数均可参与构造
        if(null == this.v_SpecialDBColumnList ||
           "".equals(this.v_SpecialDBColumnList.trim())){
            return true;
        }

        paramName = clearParameterFix(paramName);

        if(this.v_SpecialDBColumnList.indexOf("|" + paramName + "|") >= 0){
            return true;
        }

        return false;
    }

    /**
     * 判断指定的参数是否为不参与到SQL的参数。
     * 参照信息为构造信息对象HashMap对象中的BUTTON_SpecialIgnoreParam_lIST,
     * 使用|分隔，
     * 如果对象中不包含此数据，则均返回true
     * @param paramName String
     * 参数名（要求大写）
     * @return boolean
     * 是返回true，不是返回false
     */
    private boolean isSpecialIgnoreParam(String paramName)
    {
        //没有设置时，所有的参数均不被忽略
        if(null == this.v_SpecialIgnoreParamList ||
           "".equals(this.v_SpecialIgnoreParamList.trim())){
            return false;
        }

        paramName = clearParameterFix(paramName);

        if(this.v_SpecialIgnoreParamList.indexOf("|" + paramName + "|") >= 0){
            return true;
        }

        return false;
    }

    /**
     * 清除参数的前缀，后缀（构造器规则）
     * @param paramName String
     * 参数名（要求大写）
     * @return String
     * 处理后的参数
     */
    private String clearParameterFix(String paramName)
    {
        String retu = paramName;
        if(retu.startsWith("SYSIGNORE_")){
            retu = retu.substring(10);
        }
        if(retu.startsWith("NNN_")){
            retu = retu.substring(4);
        }

        int pos = retu.indexOf("_WHERE");
        if(pos > 0){
            retu = retu.substring(0,pos);
        }
        return retu;
    }

    /**
     * 处理以Replace_开头的参数.
     * 当前找到Replace_(字段名<可不含修饰符,如sysignore_czrq00,则为czrq00>)_(AA)时,
     * 此参数值用于替换czrq00的参数值中的AA.
     * <BR>对SYSIGNORE_及NNN_开头及在忽略列表中的域不进行单引号替换
     * <pre>
     * 例子:
     * 有一参数Replace_czrq00_AA = "20030705"(此参数值一般由用户录入)
     * 另一参数sysignore_czrq00 = "to_date('AA','yyyymmdd')";(此参数一般为隐藏域)
     * (AA请使用大小方式)
     * 则处理后的结果为 czrq00 = to_date('20030705','yyyymmdd')
     * </pre>
     * @param hashmap
     * 包含有键(全大写字母),值的HashMap,用于按指定规则构造SQL
     * @param t_colname
     * 值中某一段字符将被替换的含修饰符的参数名
     * @param colname
     * 值中某一段字符将被替换的不含前缀的字段名
     * @param t_colvalue
     * 此值中某一段字符将被替换
     * @return
     * 被替换后的值
     */
    private String getReplaceValue(HashMap hashmap,
                                   String t_colname, //要求为大写
                                   String colname, //要求为大写
                                   String t_colvalue)
    {
        boolean isReplace = false;
        String Retu = t_colvalue;
        if(hashmap == null){
            hashmap = new HashMap(1); //为了处理空指针
        }
        java.util.Set set = hashmap.keySet();
        java.util.Iterator iterator = set.iterator();
        String key,val;
        while(iterator.hasNext()){
            key = (String)iterator.next();
            if(key == null || key.trim().equals("")){
                continue;
            }
            //找到进行替换的值
            if(key.toUpperCase().startsWith("REPLACE_" + t_colname + "_") ||
               key.toUpperCase().startsWith("REPLACE_" + colname + "_")){
                int repLen = (key.toUpperCase().startsWith("REPLACE_" +
                    t_colname + "_")) ?
                    ("REPLACE_" + t_colname + "_").length() :
                    ("REPLACE_" + colname + "_").length();
                String repStr = key.substring(repLen);
                val = (String)hashmap.get(key);
                //单引号替换处理，对SYSIGNORE_及NNN_开头及在忽略列表中的域不进行单引号替换
                if(this.v_replaceStr != null &&
                   v_ignorereplist.toUpperCase().indexOf("|" + colname + "|") <
                   0 &&
                   t_colname.indexOf("SYSIGNORE_") < 0 &&
                   t_colname.indexOf("NNN_") < 0
                   ){
                    val = StringUtils.replaceString(val,"'",this.v_replaceStr,true);
                }

                Retu = StringUtils.replaceString(Retu,repStr,val,true);
                repStr = null;
                isReplace = true; //找到并进行了替换
                break;
            }
            else{
                continue;
            }
        }
        iterator = null;
        set = null;
        key = null;
        val = null;

        //单引号替换处理，对SYSIGNORE_及NNN_开头及在忽略列表中的域不进行单引号替换
        if(this.v_replaceStr != null &&
           !isReplace &&
           v_ignorereplist.toUpperCase().indexOf("|" + colname + "|") < 0 &&
           t_colname.indexOf("SYSIGNORE_") < 0 &&
           t_colname.indexOf("NNN_") < 0
           ){ //没有替换过
            Retu = StringUtils.replaceString(Retu,"'",this.v_replaceStr,true);
        }

        return Retu;
    }

    /**
     * 对入口参数进行构造where语句
     * @param hashmap
     * 所有参数的值
     * @param strbuf
     * 保存构造好的where语句
     * @param t_colname
     * 域名，未经大小写处理
     * @param t_colvalue
     * 入口参数值
     */
    private void makeSQLWhere(HashMap hashmap,
                              StringBuffer strbuf,
                              String t_colname,
                              String t_colvalue)
    {
        if(t_colvalue == null || t_colvalue.equals("")){
            return;
        }
        if(null == t_colname || "".equals(t_colname.trim())){
            return;
        }

        //把t_colname转化为大写
        String t_up_colname = t_colname.toUpperCase();

        int eLen = t_up_colname.indexOf("_WHERE");
        String colname;
        String up_colname; //把colname转化为大写
        String colvalue;

        boolean isQuotes = true; //是否要求含有单引号

        colname = t_colname.substring(0,eLen);
        up_colname = colname.toUpperCase();
        if(up_colname.startsWith("SYSIGNORE_")){
            colname = colname.substring(10);
            isQuotes = false;
        }
        else if(up_colname.startsWith("NNN_")){
            colname = colname.substring(4);
            isQuotes = false;
        }

        //调用replace_
        colvalue = getReplaceValue(hashmap,t_up_colname,up_colname,t_colvalue);

        if(t_up_colname.indexOf("_WHERE_BETWEEN1") >= 0){
            if(isQuotes){
                colvalue = "'" + colvalue + "'";
            }
            strbuf.append(colname).append(" >= ").append(colvalue).append(
                " AND ");
        }
        else if(t_up_colname.indexOf("_WHERE_BETWEEN2") >= 0){
            if(isQuotes){
                colvalue = "'" + colvalue + "'";
            }
            strbuf.append(colname).append(" <= ").append(colvalue).append(
                " AND ");
        }
        else if(t_up_colname.indexOf("_WHERE_LIKE") >= 0){
            strbuf.append(colname).append(" like '%").append(colvalue).append(
                "%' AND ");
        }
        else if(t_up_colname.indexOf("_WHERE_IS") >= 0){ //主要用于 is null or is not null
            strbuf.append(colname).append(" IS ").append(colvalue).append(
                " AND ");
        }
        else if(t_up_colname.indexOf("_WHERE_EXP") >= 0){ //将调用QueryExpressTranslater中的方法处理
            //取出数据类型
            String DATA_TYPE = t_up_colname.substring(t_up_colname.indexOf(
                "_WHERE_EXP_") + 11);
            String express = getExpressTranslater(colname,colvalue,DATA_TYPE);
            strbuf.append(express).append(" AND ");
        }
        else if(t_up_colname.indexOf("_WHERE") >= 0){
            if(isQuotes){
                colvalue = "'" + colvalue + "'";
            }
            strbuf.append(colname).append(" = ").append(colvalue).append(
                " AND ");
        }
        colname = null;
        colvalue = null;
    }

    /**
     * 方法根据列名colname、列数据（一般为表达式）colvalue、数据类型DATA_TYPE构造SQLWHWERE.
     * 当数据类型为无效类型或null或空字符串时,使用DATA_TYPE_CHAR类型构造SQLWHERE，
     * 些时两个前缀修饰符将无效。
     * @param colname String
     * 列名称，删除了前缀、后缀修饰符后
     * @param colvalue String
     * 列表达式
     * @param DATA_TYPE String
     * 数据类型，当数据类型为无效类型或null或空字符串时,使用DATA_TYPE_CHAR类型，参见说明
     * @return String
     * 构造好的SQLWHERE
     */
    private String getExpressTranslater(String colname,
                                        String colvalue,
                                        String DATA_TYPE //必须转化为大写
                                        )
    {
        if(null == colname || "".equals(colname.trim())){
            return null;
        }
        if(null == colvalue){
            return null;
        }

        if(this.DATA_TYPE_AGE.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_AGE);
        }
        else if(this.DATA_TYPE_BM.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_BM);
        }
        else if(this.DATA_TYPE_CHAR.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_CHAR);
        }
        else if(this.DATA_TYPE_NUMBER.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_NUMBER);
        }
        else if(this.DATA_TYPE_STR_DATE_8.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_STR_DATE_8);
        }
        else if(this.DATA_TYPE_STR_DATE_14.equalsIgnoreCase(DATA_TYPE)){
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_STR_DATE_14);
        }
        else{ //没有符合的类型时，使用类型 DATA_TYPE_CHAR
            return QueryExpressTranslater.columnExpess(colname,
                colvalue,
                QueryExpressTranslater.
                DATA_TYPE_CHAR);
        }
    }

    /**
     * 删除多余的最前面及结尾的标记符号
     * @param strbuf
     * 待处理的字符串buffer
     * @param mark
     * 删除的标记
     * @return
     * 删除多余前缀及后缀后的字符串buffer
     */
    private StringBuffer removeExcrescentMark(StringBuffer strbuf,
                                              String mark)
    {
        if(mark == null || mark.trim().equals("")){
            return strbuf;
        }
        String str = strbuf.toString();
        if(str.startsWith(mark)){
            str = str.substring(mark.length());
        }
        if(str.endsWith(mark)){
            str = str.substring(0,str.length() - mark.length());
        }
        return new StringBuffer(str);
    }

    /**
     * 从HashMap中根据名字取出值，忽略大小写
     * @param hashmap HashMap
     * 存放着数据的对象
     * @param name String
     * 忽略大小写的名称
     * @return String
     * 没有时返回空字符串
     */
    private String getStringFromHash(HashMap hashmap,String name)
    {
        if(hashmap == null || hashmap.isEmpty() || name == null ||
           name.trim().equals("")){
            return "";
        }
        String retu;
        if(hashmap.containsKey(name)){ //名字配置（包括大小写）时直接取值
            retu = (String)hashmap.get(name);
            return(null == retu) ? "" : retu;
        }

        //忽略大小写试取值
        Set set = hashmap.keySet();
        if(null == set){
            return "";
        }
        Iterator it = set.iterator();
        if(null == it){
            return "";
        }
        String key = null;
        retu = null;
        while(it.hasNext()){
            key = (String)it.next();
            if(null == key || "".equals(key.trim())){
                continue;
            } //end of if
            if(key.equalsIgnoreCase(name)){
                retu = (String)hashmap.get(key);
                break;
            }
        } //end of while

        key = null;
        it = null;
        set = null;

        return(null == retu) ? "" : retu;
    }

    public static void main(String[] args)
        throws Exception
    {
        /*
             HashMap map = new HashMap();
             map.put("Userid_where_is","null");
             map.put("USERNAME_WHERE_EXP_CHAR","!李四");
             //map.put("sysignore_czrq00_where_between1","to_char('aa','yyyy-mm-dd')");
             //map.put("replace_sysignore_czrq00_aa","20041005");
             //map.put("replace_czrq00_aa","20041005");
             map.put("Username","谢计生");
             map.put("Password","");
             map.put("Sysarg_type","update");
             SqlGenerator bsp = new SqlGenerator();
             String sql = bsp.getSQL(map,"systuser");
             System.out.println(sql);
         */
        /*
             HashMap map = new HashMap();

             //要修改的字段
             map.put("salary","3100");//修改工资为3100
             map.put("Password","");//密码修改为空

             //以下为条件
             map.put("deptid_where_is","null");//部门为空
          map.put("Userid_where_is","not null");//用户ID非空

             map.put("USERNAME_WHERE_EXP_CHAR","!李四");//用户姓名不为李四

             //map.put("replace_sysignore_czrq00_aa","20041005");
             map.put("replace_csrq00_aa","20041005");//出生日期为20041005
         map.put("sysignore_czrq00_where_between1","to_char('aa','yyyy-mm-dd')");

             map.put("Sysarg_type","update");//告诉构造器是进行修改操作
             SqlGenerator bsp = new SqlGenerator();

             String sql = bsp.getSQL(map,"systuser");

             System.out.println(sql);
         */

        HashMap map = new HashMap();
        map.put("userid","zhangsan");
        map.put("username","张三"); //用户名为张三
        map.put("Password","123"); //密码123
        map.put("sysignore_salary","3100"); //工资为3100
        map.put("userid_where","zhangsan");
        map.put(SqlGenerator.INCLUDECOLS,"password|username|userid");
        SqlGenerator bsp = new SqlGenerator();

        String sql = bsp.getUpdateSQL(map,"systuser");
        System.out.println(sql);
    }
}
