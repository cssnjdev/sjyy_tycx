package com.cwks.bizcore.persistence.outtype;

import sun.jdbc.rowset.CachedRowSet;

import java.util.List;


//import sun.jdbc.rowset.CachedRowSet;

/**
 * 为Oracle存储过程返回的游标定义一种类型，当持久层遇到这种类型时，将参数构造成OUT参数
 * @author Administrator
 *
 */
public class OracleCursor implements IOuterParam{
	
	private int index;
	private List value;
	private CachedRowSet rowset;
     
	public CachedRowSet getRowset() {
		return rowset;
	}
	
	public void setRowset(CachedRowSet rowset) {
		this.rowset = rowset;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public List getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = (List)value;
	}
	
}
