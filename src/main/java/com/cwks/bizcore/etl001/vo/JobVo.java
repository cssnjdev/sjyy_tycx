package com.cwks.bizcore.etl001.vo;

import java.util.ArrayList;

public class JobVo {
	
	private String etlid;
	
	private ArrayList<DataUnitVo> dataunitlist = new ArrayList<DataUnitVo>();
	 
	private JobVo(String etlid){
		this.etlid = etlid;
	}
	
	public String getEtlid(){
		return this.etlid;
	}
	
	public boolean equals(JobVo arg0) {
 		return etlid.equals(arg0.getEtlid());
 	}
	
}
