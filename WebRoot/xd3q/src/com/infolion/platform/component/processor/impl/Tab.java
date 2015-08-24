package com.infolion.platform.component.processor.impl;

import java.util.ArrayList;
import java.util.Iterator;

import com.infolion.platform.console.menu.domain.SysResource;

public class Tab {
	public Boolean[] processor(String[] model,ArrayList<SysResource> list,String[] pageResource){
		Boolean[] b_arr = new Boolean[pageResource.length];
		//不需要授权
		if(model==null || model.length==0){
			for (int i = 0; i < b_arr.length; i++) {
				b_arr[i]=Boolean.FALSE;
			}
			return b_arr;
		}
		for (int i = 0; i < pageResource.length; i++) {
			b_arr[i] = Boolean.TRUE;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SysResource item = (SysResource) iterator.next();
				if (model[i].equals(item.getParentid())&& pageResource[i].equals(item.getResourcename())) {
					b_arr[i] = Boolean.FALSE;
					break;
				}
			}
			
		}
		return b_arr;
		
	}
}
