package com.cwks.bizcore.persistence.arraytable;


/**
 * 数据库对象类型
 * @author Administrator
 *
 */
public class ArrayTableParam implements IArray{
	//描述信息
	private String typeName;
	//二维数组数据
	private Object[][] data;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	public ArrayTableParam(String typeName, Object[][] data) {
		this.typeName = typeName;
		this.data = data;
	}
		
}
