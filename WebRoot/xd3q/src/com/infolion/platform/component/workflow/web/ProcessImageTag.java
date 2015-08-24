/*
 * @(#)ProcessImageTag.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-18
 *  描　述：创建
 */
package com.infolion.platform.component.workflow.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

import com.infolion.platform.component.workflow.JBPMStoreFileService;
import com.infolion.platform.util.EasyApplicationContextUtils;
/**
 * 
 * <pre>流程图显示标签，实现当前节点定位与突出显示功能</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ProcessImageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private long taskInstanceId = -1;
	private long tokenInstanceId = -1;

	private byte[] gpdBytes = null;
	private byte[] imageBytes = null;
	private Token currentToken = null;
	// 当前节点名称
	private String currentNodeName;
	// 当前web上下文路径
	private String contextPath;
	private ProcessDefinition processDefinition = null;

	static String currentTokenColor = "red";
	static String childTokenColor = "blue";
	static String tokenNameColor = "blue";

	public void release() {
		taskInstanceId = -1;
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
	}

	public int doEndTag() throws JspException {
		try {
			initialize(this.taskInstanceId, this.tokenInstanceId);
			// retrieveByteArrays();
			if (gpdBytes != null && imageBytes != null) {
				writeTable();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		}
		release();
		return EVAL_PAGE;
	}

	private void retrieveByteArrays() {
		try {
			FileDefinition fileDefinition = processDefinition
					.getFileDefinition();
			gpdBytes = fileDefinition.getBytes("gpd.xml");
			imageBytes = fileDefinition.getBytes("processimage.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTable() throws IOException, DocumentException {

		int borderWidth = 4;
		Element rootDiagramElement = DocumentHelper.parseText(
				new String(gpdBytes)).getRootElement();
		int[] boxConstraint;
		int[] imageDimension = extractImageDimension(rootDiagramElement);
		String imageLink = this.contextPath + "processimage?definitionId="
				+ processDefinition.getId();
		JspWriter jspOut = pageContext.getOut();

		if (tokenInstanceId > 0) {

			List allTokens = new ArrayList();
			walkTokens(currentToken, allTokens);

			jspOut
					.println("<div style='position:relative; background-image:url("
							+ imageLink
							+ "); width: "
							+ imageDimension[0]
							+ "px; height: " + imageDimension[1] + "px;'>");

			for (int i = 0; i < allTokens.size(); i++) {
				Token token = (Token) allTokens.get(i);

				// check how many tokens are on teh same level (= having the
				// same parent)
				int offset = i;
				if (i > 0) {
					while (offset > 0
							&& ((Token) allTokens.get(offset - 1)).getParent()
									.equals(token.getParent())) {
						offset--;
					}
				}
				boxConstraint = extractBoxConstraint(rootDiagramElement, token);

				// Adjust for borders
				// boxConstraint[2]-=borderWidth*2;
				// boxConstraint[3]-=borderWidth*2;

				jspOut.println("<div style='position:absolute; left: "
						+ boxConstraint[0] + "px; top: " + boxConstraint[1]
						+ "px; ");

				if (i == (allTokens.size() - 1)) {
					jspOut.println("border: " + currentTokenColor);
				} else {
					jspOut.println("border: " + childTokenColor);
				}

				jspOut.println(" " + borderWidth + "px groove; " + "width: "
						+ boxConstraint[2] + "px; height: " + boxConstraint[3]
						+ "px;'>");

				if (token.getName() != null) {
					jspOut.println("<span style='color:" + tokenNameColor
							+ ";font-style:italic;position:absolute;left:"
							+ (boxConstraint[2] + 10) + "px;top:"
							+ ((i - offset) * 20) + ";'>&nbsp;"
							+ token.getName() + "</span>");
				}

				jspOut.println("</div>");
			}
			jspOut.println("</div>");
		} else {
			boxConstraint = extractBoxConstraint(rootDiagramElement);

			jspOut.println("<table border=0 cellspacing=0 cellpadding=0 width="
					+ imageDimension[0] + " height=" + imageDimension[1] + ">");
			jspOut.println("  <tr>");
			jspOut.println("    <td width=" + imageDimension[0] + " height="
					+ imageDimension[1] + " style=\"background-image:url("
					+ imageLink + ")\" valign=top>");
			jspOut
					.println("      <table border=0 cellspacing=0 cellpadding=0>");
			jspOut.println("        <tr>");
			jspOut.println("          <td width="
					+ (boxConstraint[0] - borderWidth) + " height="
					+ (boxConstraint[1] - borderWidth)
					+ " style=\"background-color:transparent;\"></td>");
			jspOut.println("        </tr>");
			jspOut.println("        <tr>");
			jspOut
					.println("          <td style=\"background-color:transparent;\"></td>");
			jspOut
			.println("          <td id='currentToken' class='cToken' width="
					+ boxConstraint[2] + " height="
					+ (boxConstraint[3])
					+ ">&nbsp;</td>");
//			jspOut
//					.println("          <td style=\"border-color:"
//							+ currentTokenColor
//							+ "; border-width:"
//							+ borderWidth
//							+ "px; border-style:groove; background-color:transparent;\" width="
//							+ boxConstraint[2] + " height="
//							+ (boxConstraint[3] + (2 * borderWidth))
//							+ ">&nbsp;</td>");
			jspOut.println("        </tr>");
			jspOut.println("      </table>");
			jspOut.println("    </td>");
			jspOut.println("  </tr>");
			jspOut.println("</table>");
		}
	}

	private int[] extractBoxConstraint(Element root) {
		int[] result = new int[4];
		// String nodeName = currentToken.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + currentNodeName
				+ "']");
		Element node = (Element) xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();
		return result;
	}

	private int[] extractBoxConstraint(Element root, Token token) {
		int[] result = new int[4];
		String nodeName = token.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();
		return result;
	}

	private int[] extractImageDimension(Element root) {
		int[] result = new int[2];
		result[0] = Integer.valueOf(root.attribute("width").getValue())
				.intValue();
		result[1] = Integer.valueOf(root.attribute("height").getValue())
				.intValue();
		return result;
	}

	private void initialize(final long taskId, final long tokenId) {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		contextPath = request.getContextPath();
		contextPath = "".equals(contextPath) ? "/" : contextPath + "/";
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils
				.getBeanByName("jbpmTemplate");
		jbpmTemplate.execute(new JbpmCallback() {
			public Object doInJbpm(JbpmContext jbpmContext)
					throws JbpmException {
				if (taskId > 0) {
					TaskInstance taskInstance = jbpmContext
							.getTaskMgmtSession().loadTaskInstance(
									taskInstanceId);
					currentToken = taskInstance.getToken();
				} else {
					if (tokenId > 0)
						currentToken = jbpmContext.getGraphSession().loadToken(
								tokenId);
				}
				currentNodeName = currentToken.getNode().getName();
				processDefinition = currentToken.getProcessInstance()
						.getProcessDefinition();
				String processName = processDefinition.getName();
				try {
					// FileDefinition fileDefinition = currentToken
					// .getProcessInstance().getProcessDefinition()
					// .getFileDefinition();
					// FileDefinition fileDefinition = new FileDefinition();
					// fileDefinition.setProcessDefinition(processDefinition);
					JBPMStoreFileService storeFileService = new JBPMStoreFileService();
					gpdBytes = storeFileService
							.getBytes(processName, "gpd.xml");
					imageBytes = storeFileService.getBytes(processName,
							"processimage.jpg");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		});
		// JbpmContext jbpmContext =
		// jbpmTemplate.getJbpmConfiguration().getCurrentJbpmContext();//JbpmContext.getCurrentJbpmContext();
		// if (this.taskInstanceId > 0) {
		// TaskInstance taskInstance = jbpmContext.getTaskMgmtSession()
		// .loadTaskInstance(taskInstanceId);
		// currentToken = taskInstance.getToken();
		// } else {
		// if (this.tokenInstanceId > 0)
		// currentToken = jbpmContext.getGraphSession().loadToken(
		// this.tokenInstanceId);
		// }
		// processDefinition = currentToken.getProcessInstance()
		// .getProcessDefinition();
	}

	private void walkTokens(Token parent, List allTokens) {
		Map children = parent.getChildren();
		if (children != null && children.size() > 0) {
			Collection childTokens = children.values();
			for (Iterator iterator = childTokens.iterator(); iterator.hasNext();) {
				Token child = (Token) iterator.next();
				walkTokens(child, allTokens);
			}
		}

		allTokens.add(parent);
	}

	public void setTask(long id) {
		this.taskInstanceId = id;
	}

	public void setToken(long id) {
		this.tokenInstanceId = id;
	}

}
