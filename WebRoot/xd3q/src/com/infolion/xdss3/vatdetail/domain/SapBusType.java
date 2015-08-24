package com.infolion.xdss3.vatdetail.domain;

import java.util.ArrayList;
import java.util.List;

public class SapBusType {
    private static List<String> busType = new ArrayList();
    
   public static List<String> getbusTypeList() {
       return busType;
   }
    static {
        busType.add("代理出口");
        busType.add("代理进口");
        busType.add("自营出口*");
        busType.add("自营进口*");
        busType.add("进口其他");
        busType.add("进口通用");
        busType.add("内贸");
        busType.add("委托加工");
        busType.add("虚拟订单");
        busType.add("转口");
        busType.add("自营出口");
        busType.add("自营进口");
        busType.add("自营其他");
        busType.add("自营通用");
    }
}
