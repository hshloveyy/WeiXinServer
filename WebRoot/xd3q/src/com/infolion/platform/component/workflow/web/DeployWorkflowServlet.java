/*
 * @(#)DeployWorkflowServlet.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 流程定义文件发布
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class DeployWorkflowServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 文件上传部分
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart == true) {
			try {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				// 得到所有的表单域，它们目前都被当作FileItem
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iter = fileItems.iterator();

				// 依次处理每个表单域
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					if (!item.isFormField()) {
						// 如果item是文件上传表单域

						// 获得文件名及路径
						final String fileName = item.getName();
						if (fileName != null) {
							File fullFile = new File(item.getName());

							// 如果文件存在则上传
							if (fullFile.exists()) {
								final InputStream fin = item.getInputStream();
								JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils
										.getBeanByName("jbpmTemplate");
								jbpmTemplate.execute(new JbpmCallback() {

									public Object doInJbpm(JbpmContext context)
											throws JbpmException {
										ProcessDefinition pd = ProcessDefinition
												.parseXmlInputStream(fin);
										context.deployProcessDefinition(pd);// 发布上传过来的流程
										System.out.println("文件" + fileName
												+ "上传成功");
										return null;
									}
								});
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("the enctype must be multipart/form-data");
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/component/workflow/index.jsp");
		dispatcher.forward(request, response);
		return;
	}
}
