package com.infolion.platform.component.processor;

import java.io.File;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileRenamePolicyImpl implements FileRenamePolicy{

	private String dir;
	private String fileTime;
	private String userId;
	private String prefix;
	
	public FileRenamePolicyImpl(String dir,String userId,String fileTime,String prefix){
		this.dir = dir;
		this.fileTime = fileTime;
		this.userId = userId;
		this.prefix = prefix;
	}
	public File rename(File f) {
		return new File(dir+"/"+userId+"_"+fileTime+prefix);
	}
}
