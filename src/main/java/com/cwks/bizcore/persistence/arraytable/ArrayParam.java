package com.cwks.bizcore.persistence.arraytable;


/**
 * 一维数组对象 需要在数据库中定义该类型
 * @author Administrator
 *
 */
public class ArrayParam implements IArray{
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
	public ArrayParam(String typeName, Object[] data) {
		this.typeName = typeName;
		this.data = data;
	}
		
}
