/*
 * @(#)ImpDeclarationController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年06月23日 17点30分26秒
 *  描　述：创建
 */
package com.infolion.xdss3.declaration.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.xdss3.declaration.dao.ImpDeclarationJdbcDao;
import com.infolion.xdss3.declaration.domain.ImpDeclaration;
import com.infolion.xdss3.declarationGen.web.ImpDeclarationControllerGen;
import com.oreilly.servlet.MultipartRequest;

/**
 * <pre>
 * 进口报关单(ImpDeclaration)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@BDPController(parent = "baseMultiActionController")
public class ImpDeclarationController extends ImpDeclarationControllerGen {
    
    
    @Autowired
    protected ImpDeclarationJdbcDao impDeclarationJdbcDao;
    
    public ImpDeclarationJdbcDao getImpDeclarationJdbcDao() {
        return impDeclarationJdbcDao;
    }

    public void setImpDeclarationJdbcDao(ImpDeclarationJdbcDao impDeclarationJdbcDao) {
        this.impDeclarationJdbcDao = impDeclarationJdbcDao;
    }

    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("xdss3/declaration/impDeclarationExcel");
    }

    public ModelAndView saveExcel(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<String> existsList = new ArrayList<String>();
        // 文件上传部分
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        int iCount = 0;
        if (isMultipart) {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 取得上传的文件流对象
            MultipartFile multipartFile = multipartRequest.getFile("excelfile");
            InputStream fin = null;
            try {
                fin = multipartFile.getInputStream();

                HSSFWorkbook wb = new HSSFWorkbook(fin);
                HSSFSheet sheet = wb.getSheetAt(0); // 第一个工作表
                int rownum = sheet.getLastRowNum();
                for (int i = 4; i <= rownum; i++) {
                    HSSFRow row = sheet.getRow(i);
                    ImpDeclaration impDeclaration = new ImpDeclaration();
                    if (row != null) {
                        int colnum = row.getLastCellNum();
                        for (int j = 0; j <= colnum; j++) {
                            HSSFCell cell = row.getCell((short) j); // 读取单元格
                            // 报关单编号 贸易方式 成交方式 进口口岸 报关币种
                            // 合同号 报关金额 报关金额折美元 进口日期 总量核查状态
                            // 逐笔核查状态
                            if (cell != null) {
                                switch (j) {
                                case 0:
                                    impDeclaration.setDeclarationsno(cell.getStringCellValue());
                                    break;
                                case 1:
                                    impDeclaration.setTrade_type(cell.getStringCellValue());
                                    break;
                                case 2:
                                    impDeclaration.setTransactionway(cell.getStringCellValue());
                                    break;
                                case 3:
                                    try {
                                        impDeclaration.setImportport(cell.getStringCellValue());
                                    } catch (Exception e) {
                                    	DecimalFormat df2 = new DecimalFormat("0");
                                    	String am2 = df2.format(cell.getNumericCellValue());
                                        impDeclaration.setImportport(am2);
                                    }
                                    break;
                                case 4:
                                    impDeclaration.setWaers(cell.getStringCellValue());
                                    break;
                                case 5:
                                    impDeclaration.setContractno(cell.getStringCellValue());
                                    break;
                                case 6:
                                	DecimalFormat df = new DecimalFormat("0.00");
                                	String am = df.format(cell.getNumericCellValue());
                                    impDeclaration.setBgje(am);
                                    break;
                                case 7:
                                	DecimalFormat df2 = new DecimalFormat("0.00");
                                	String am2 = df2.format(cell.getNumericCellValue());
                                    impDeclaration.setBgjezmy(am2);
                                    break;
                                case 8:
                                    impDeclaration.setImportdate(cell.getStringCellValue());
                                    break;
                                case 9:
                                    impDeclaration.setTotalcheckflag(cell.getStringCellValue());
                                    break;
                                case 10:
                                    impDeclaration.setEarchcheckflag(cell.getStringCellValue());
                                    break;
                                }
                            }
                        }
                    }
                    impDeclaration.setDeclarationsid(UUID.randomUUID().toString());
                    if (! this.impDeclarationJdbcDao.hasExists(impDeclaration)) { // 不存在记录则保存
                        impDeclaration.setClient("800");
                        this.impDeclarationService._save(impDeclaration);
                        iCount ++ ;
                    } else {
                        existsList.add(impDeclaration.getDeclarationsno());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.operateFailly(response);
            } finally {
                if (fin!=null)
                    try {
                        fin.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            this.operateFailly(response);
            request.setAttribute("message", "导入异常");
        }
        request.setAttribute("message", "完成，正常导入"+ iCount + " 笔数据");
        return new ModelAndView("xdss3/declaration/impDeclarationExcel");
    }
}