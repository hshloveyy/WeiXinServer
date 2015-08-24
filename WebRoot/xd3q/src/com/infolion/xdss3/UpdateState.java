package com.infolion.xdss3;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UpdateState {
	private static  UpdateState singleton = null;
	private static Map<String,String> map= null;
	private UpdateState() {
	}

	public static UpdateState getInstance(){
		if (singleton== null){	
			synchronized (UpdateState.class){	
				if (singleton== null){	
					singleton= new UpdateState();
					map=new HashMap<String,String>();
				}
			}
		}
		return singleton;
	}
	public static Map<String,String> getMap(){
		return UpdateState.getInstance().map;
	}
	public static void main(String[] args){
		UpdateState.getMap().put("cu", "dd");
		 System.out.println(UpdateState.getMap().get("cu"));
		 UpdateState.getMap().remove("cu");
		 UpdateState.getMap().remove("cu");
		 System.out.println(UpdateState.getMap().get("cu"));
	}
}
