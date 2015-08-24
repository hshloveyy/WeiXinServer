package com.infolion.xdss3.vatdetail.domain;

import java.util.ArrayList;
import java.util.List;


import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

/**
 * 增值税商品明细汇总表
 */

public class VatGoodDetail extends BaseObject {
    String bukrs;
    String gsber;
    String matnr;
    String matnr_name;
    String matgroup;
    String matgroup_name;
    String taxp;
    String saptype;
    String qcyjcwdp;
    String qcydpwjc;
    String qcydswjc;
    String qcysckts;
    String bqrkje;
    String bqjyje;
    String inputtax;
    String inputurn;
    String taxturn;
    String outboundm;
    String busin;
    String salesin;
    String outputtax;
    String buscost;
    String salescost;
    String exporttax;
    String accexport;
    String qmyjcwdp;
    String qmydpwjc;
    String qmydswjc;
    String qmysckts;
    public String getBukrs() {
        return bukrs;
    }
    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }
    public String getGsber() {
        return gsber;
    }
    public void setGsber(String gsber) {
        this.gsber = gsber;
    }
    public String getMatnr() {
        return matnr;
    }
    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }
    public String getMatnr_name() {
        return matnr_name;
    }
    public void setMatnr_name(String matnr_name) {
        this.matnr_name = matnr_name;
    }
    public String getMatgroup() {
        return matgroup;
    }
    public void setMatgroup(String matgroup) {
        this.matgroup = matgroup;
    }
    public String getMatgroup_name() {
        return matgroup_name;
    }
    public void setMatgroup_name(String matgroup_name) {
        this.matgroup_name = matgroup_name;
    }
    public String getTaxp() {
        return taxp;
    }
    public void setTaxp(String taxp) {
        this.taxp = taxp;
    }
    public String getSaptype() {
        return saptype;
    }
    public void setSaptype(String saptype) {
        this.saptype = saptype;
    }
    public String getQcyjcwdp() {
        return qcyjcwdp;
    }
    public void setQcyjcwdp(String qcyjcwdp) {
        this.qcyjcwdp = qcyjcwdp;
    }
    public String getQcydpwjc() {
        return qcydpwjc;
    }
    public void setQcydpwjc(String qcydpwjc) {
        this.qcydpwjc = qcydpwjc;
    }
    public String getQcydswjc() {
        return qcydswjc;
    }
    public void setQcydswjc(String qcydswjc) {
        this.qcydswjc = qcydswjc;
    }
    public String getQcysckts() {
        return qcysckts;
    }
    public void setQcysckts(String qcysckts) {
        this.qcysckts = qcysckts;
    }
    public String getBqrkje() {
        return bqrkje;
    }
    public void setBqrkje(String bqrkje) {
        this.bqrkje = bqrkje;
    }
    public String getBqjyje() {
        return bqjyje;
    }
    public void setBqjyje(String bqjyje) {
        this.bqjyje = bqjyje;
    }
    public String getInputtax() {
        return inputtax;
    }
    public void setInputtax(String inputtax) {
        this.inputtax = inputtax;
    }
    public String getInputurn() {
        return inputurn;
    }
    public void setInputurn(String inputurn) {
        this.inputurn = inputurn;
    }
    public String getTaxturn() {
        return taxturn;
    }
    public void setTaxturn(String taxturn) {
        this.taxturn = taxturn;
    }
    public String getOutboundm() {
        return outboundm;
    }
    public void setOutboundm(String outboundm) {
        this.outboundm = outboundm;
    }
    public String getBusin() {
        return busin;
    }
    public void setBusin(String busin) {
        this.busin = busin;
    }
    public String getSalesin() {
        return salesin;
    }
    public void setSalesin(String salesin) {
        this.salesin = salesin;
    }
    public String getOutputtax() {
        return outputtax;
    }
    public void setOutputtax(String outputtax) {
        this.outputtax = outputtax;
    }
    public String getBuscost() {
        return buscost;
    }
    public void setBuscost(String buscost) {
        this.buscost = buscost;
    }
    public String getSalescost() {
        return salescost;
    }
    public void setSalescost(String salescost) {
        this.salescost = salescost;
    }
    public String getExporttax() {
        return exporttax;
    }
    public void setExporttax(String exporttax) {
        this.exporttax = exporttax;
    }
    public String getAccexport() {
        return accexport;
    }
    public void setAccexport(String accexport) {
        this.accexport = accexport;
    }
    public String getQmyjcwdp() {
        return qmyjcwdp;
    }
    public void setQmyjcwdp(String qmyjcwdp) {
        this.qmyjcwdp = qmyjcwdp;
    }
    public String getQmydpwjc() {
        return qmydpwjc;
    }
    public void setQmydpwjc(String qmydpwjc) {
        this.qmydpwjc = qmydpwjc;
    }
    public String getQmydswjc() {
        return qmydswjc;
    }
    public void setQmydswjc(String qmydswjc) {
        this.qmydswjc = qmydswjc;
    }
    public String getQmysckts() {
        return qmysckts;
    }
    public void setQmysckts(String qmysckts) {
        this.qmysckts = qmysckts;
    }
    
    
}
