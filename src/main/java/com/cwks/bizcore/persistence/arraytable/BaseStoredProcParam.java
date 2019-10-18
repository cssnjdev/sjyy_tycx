package com.cwks.bizcore.persistence.arraytable;

/**
 * 用来封装存储过程的基础参数:
 *         index: 参数的顺序。
 *         value:参数值，如果参数是IN或者INOUT参数类型时，要输入这个参数的值。
 *         paramType:参数类型。这些类型只可能为IN, OUT, INOUT三种。
 *         dataType:数据类型，当参数为OUT或者INOUT参数类型时，一定要定义它的返
 *                  回值的数据类型，这种数据类型是java.sql.Types中的一种。
 * <p>Title: StoredProcParamObj</p>
 * <p>Company: 中软技术股份有限公司</p>
 * @author Administrator
 * @version 1.0
 * @since 2012-05-14
 */

public class BaseStoredProcParam {
    
	private String userDefineType;
    
    public String getUserDefineType() {
		return userDefineType;
	}

	public void setUserDefineType(String userDefineType) {
		this.userDefineType = userDefineType;
	}
	
	
	//数组的维度，1为1维，2为二维
	private int arrayType = 1;
	

	public int getArrayType() {
		return arrayType;
	}

	public void setArrayType(int arrayType) {
		this.arrayType = arrayType;
	}
	
	/**
     * 参数的顺序。
     */
    private int index;  //
    /**
     * 参数值，如果参数是IN或者INOUT参数类型时，要输入这个参数的值。
     */
    private Object value;  // //
    /**
     * 参数类型。这些类型只可能为IN, OUT, INOUT三种。
     */
    private String paramType;  //
    /*
     *数据类型，当参数为OUT或者INOUT参数类型时，一定要定义它的返回值的数
     *据类型，这种数据类型是java.sql.Types中的一种。
     */
    private int dataType;

    public static final String IN = "In" ;
    public static final String OUT = "Out" ;
    public static final String INOUT = "InAndOut" ;

    public BaseStoredProcParam() {
    }

    public BaseStoredProcParam(int index,Object value,
                              String paramType,int dataType){
        this.index = index ;
        this.value = value ;
        this.paramType = paramType ;
        this.dataType = dataType ;
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
    public String getParamType() {
        return paramType;
    }
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
    public int getDataType() {
        return dataType;
    }
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

}
