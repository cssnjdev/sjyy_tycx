package com.cwks.bizcore.etl001.vo;

import java.io.Serializable;

public class TaskGzVo implements Serializable {
 
	private JobVo job;
	
	private JobVo preJob ;
	 
	public JobVo getJob(){
		return this.job;
	}
	
	public JobVo getPreJob(){
		return this.preJob;
	}
	
	public TaskGzVo(JobVo job,JobVo preJob){
		
		
	}
	
	
 	public boolean equals(TaskGzVo arg0) {
 		
		 if(job.equals(arg0.getJob())&&preJob.equals(arg0.getPreJob())){
			 return true;
		 }else{
			 return false;
		 }
		 
 	}
 	
	
}
