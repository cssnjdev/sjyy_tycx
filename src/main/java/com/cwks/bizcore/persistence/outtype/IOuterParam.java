package com.cwks.bizcore.persistence.outtype;
//定义返回结果的参数类型，用于定义存储过程执行的结果
public interface IOuterParam {
	public void setIndex(int index);
	
	public void setValue(Object value);
}
