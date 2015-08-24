package com.infolion.sapss.bapi;

import java.util.Iterator;

import com.sap.mw.jco.JCO;

public class SAPTableList implements Iterable<JCO.FieldIterator> {

	private JCO.Table table;
	
	public SAPTableList(JCO.Table table){
		this.table=table;
	}
	public Iterator<JCO.FieldIterator> iterator() {
		return new Iterator<JCO.FieldIterator>(){
			
			public boolean hasNext() {
				return table.nextRow();
			}

			public JCO.FieldIterator next() {
				return  table.fields();
			}

			public void remove() {
				table.clear();
			}
			
		};
	}

}
