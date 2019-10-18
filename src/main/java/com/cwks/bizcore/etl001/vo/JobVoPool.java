package com.cwks.bizcore.etl001.vo;

import java.util.ArrayList;
import java.util.Iterator;

public class JobVoPool {

	private ArrayList<JobVo> joblist = new ArrayList<JobVo>();
 
	
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
	
	//通过etlid 获取作业
	public JobVo getJob(String etlid){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.getEtlid()==etlid){
				 return ite_job;
			}
		}
		return null;
	}
	
	//移除作业
	public void deleteJob(String etlid){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.getEtlid()==etlid){
				ite.remove();
			}
		}
	}
	
	//移除作业
	public void deleteJob(JobVo job){
		Iterator<JobVo> ite = joblist.iterator();
		if(ite.hasNext()){
			JobVo ite_job = ite.next();
			if(ite_job.equals(job)){
				ite.remove();
			}
		}
	}
	
	//更新的作业 
	public void updateJob(JobVo job){
		this.deleteJob(job);
		joblist.add(job);
	}
	
	
}
