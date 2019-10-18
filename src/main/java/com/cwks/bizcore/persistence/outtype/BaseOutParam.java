package com.cwks.bizcore.persistence.outtype;
//基本返回结果类型，只是为了挖个坑
public class BaseOutParam implements IOuterParam{
     private int index;
     private Object value;
   //定义的java.sql.Types.***类型，为空的话，默认返回字符串

     private int type; 
     
     private String userDefineType;
    
     public String getUserDefineType() {
		return userDefineType;
	}

	public void setUserDefineType(String userDefineType) {
		if (userDefineType!=null) {
			this.userDefineType = userDefineType.toUpperCase();
		}
	}

	//无参数构造函数 默认为字符串
     public BaseOutParam() {
    	 this.type = java.sql.Types.CHAR; 
     }

	/**
      * 构造不同的返回类型，java.sql.Types定义了格式
      * 
      * @param type java.sql.Types 里头定义的类型格式
      */
     public BaseOutParam(int type) {
    	 this.type = type;
     }
     
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
     
}
