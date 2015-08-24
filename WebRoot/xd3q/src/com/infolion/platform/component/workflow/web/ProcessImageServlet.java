/*
 * @(#)ProcessImageServlet.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-18
 *  描　述：创建
 */
package com.infolion.platform.component.workflow.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

import com.infolion.platform.component.workflow.JBPMStoreFileService;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 改造后的流程图显示器
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
public class ProcessImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final long processDefinitionId = Long.parseLong(request
				.getParameter("definitionId"));
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils
				.getBeanByName("jbpmTemplate");
		byte[] bytes = (byte[]) jbpmTemplate.execute(new JbpmCallback() {

			public Object doInJbpm(JbpmContext jbpmContext)
					throws JbpmException {
				byte[] bytes = null;
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().loadProcessDefinition(
								processDefinitionId);
				JBPMStoreFileService storeFileService = new JBPMStoreFileService();
				String processName = processDefinition.getName();
				try {
					bytes = storeFileService.getBytes(processName,
							"processimage.jpg");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return bytes;
			}
		});
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();

		// leave this in. it is in case we want to set the mime type later.
		// get the mime type
		// String contentType =
		// URLConnection.getFileNameMap().getContentTypeFor( fileName );
		// set the content type (=mime type)
		// response.setContentType( contentType );
	}
}
