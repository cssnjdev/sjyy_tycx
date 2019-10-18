package com.cwks.bizcore.persistence;

import com.cwks.bizcore.persistence.arraytable.BaseStoredProcParam;


//存储过程参数类
public class SpParam extends BaseStoredProcParam {

	public SpParam() {
		super();
	}


	public SpParam(int index, Object value, String paramType, int dataType) {
		super(index, value, paramType, dataType);
	}
	
	private Object[] colNames;
	
	
	private String[] colTypes;


	private Object[] getColNames() {
		return colNames;
	}

	private void setColNames(Object[] colNames) {
		this.colNames = colNames;
	}

	private String[] getColTypes() {
		return colTypes;
	}

	private void setColTypes(String[] colTypes) {
		this.colTypes = colTypes;
	}

	@Override
	public String toString() {
		
		StringBuffer sb=new StringBuffer("{index=");
		sb.append(this.getIndex()).append(",paramType=").append(this.getParamType()).append(",dataType=").append(this.getDataType())
		.append(",value=").append(this.getValue());
		return sb.toString();
	}
	
	
	

}
