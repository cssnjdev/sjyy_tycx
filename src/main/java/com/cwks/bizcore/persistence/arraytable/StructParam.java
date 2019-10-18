package com.cwks.bizcore.persistence.arraytable;


//把结构体也视为数组类型
public class StructParam implements IArray{
	//描述信息
	private String typeName;
	//一维数组数据
	private Object[] data;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	
	
}
