/*
 * @(#)JBPMStoreFileService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-18
 *  描　述：创建
 */

package com.infolion.platform.component.workflow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jbpm.JbpmConfiguration;
import org.jbpm.util.IoUtil;

/**
 * 
 * <pre>
 * jBPM流程定义文件服务，为流程图获取提供服务
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
public class JBPMStoreFileService {
	public byte[] getBytes(String processName, String fileName)
			throws IOException {
		byte[] bytes = null;
		InputStream in = getInputStreamFromFileSystem(processName, fileName);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IoUtil.transfer(in, out);
		bytes = out.toByteArray();
		return bytes;
	}

	private InputStream getInputStreamFromFileSystem(String processName,
			String name) throws FileNotFoundException {
		InputStream inputStream = null;
		String fileName = getFilePath(processName, name);
		System.out.println("loading file '" + name + "' from file system '"
				+ fileName + "'");
		inputStream = new FileInputStream(fileName);
		return inputStream;
	}

	String getFilePath(String processName, String name) {
		String filePath = getRootDir() + "/" + processName + "/" + name;
		new File(filePath).getParentFile().mkdirs();
		return filePath;
	}

	String getRootDir() {
		String rootDir = null;
		if (JbpmConfiguration.Configs.hasObject("jbpm.files.dir")) {
			rootDir = getClass().getResource("/").getPath() + "../../"
					+ JbpmConfiguration.Configs.getString("jbpm.files.dir");
		}
		return rootDir;
	}
}
