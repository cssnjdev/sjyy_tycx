package com.cwks.bizcore.etl001.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class TaskVo implements Serializable{
  
	private String taskid ;
	
	private ArrayList<JobVo> joblist = new ArrayList<JobVo>();
  
	private ArrayList<TaskGzVo> taskGzList = new ArrayList<TaskGzVo>();
  
	public TaskVo(String taskid){
		this.taskid = taskid;
	}
	
	//添加和更新作业
	public void updateJob(JobVo job){
		removeJob(job.getEtlid());
		joblist.add(job);
	}
	
	//判断是否存在作业
	public boolean hasJob(JobVo job){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.equals(job)){
				return true;
			}
		}
		return false;
	}
	
	//移除作业
	public void removeJob(String etlid){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.getEtlid()==etlid){
				ite.remove();
			}
		}
	}
	
	//移除作业
	public void removeJob(JobVo job){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.equals(job)){
				ite.remove();
			}
		}
	} 
	 
	
	//添加和更新作业
	public void updateGz(TaskGzVo vo){
		if(hasGz(vo)){
			removeGz(vo);
		}
		taskGzList.add(vo);
	}
	
	//判断是否存在作业
	public boolean hasGz(TaskGzVo vo){
		Iterator<TaskGzVo> ite = taskGzList.iterator();
		if(ite.hasNext()){
			TaskGzVo ite_job = ite.next();
			if(ite_job.equals(vo)){
				return true;
			}
		}
		return false;
	}
	
	//移除作业
	public void removeGz(TaskGzVo vo){
		Iterator<TaskGzVo> ite = taskGzList.iterator();
		if(ite.hasNext()){
			TaskGzVo ite_job = ite.next();
			if(ite_job.equals(vo)){
				ite.remove();
			}
		}
	} 
		
	
	
 	public boolean equals(TaskVo arg0) {
 		return taskid.equals(arg0.taskid) ;
	}
	
}
