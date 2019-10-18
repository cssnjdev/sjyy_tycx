package com.cwks.bizcore.tycx.core.vo;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;

import java.util.ArrayList;
import java.util.List;

public class TreeVO {
	/**
	 * 子节点
	 */
	public List<TreeVO> childrenList=new ArrayList<TreeVO>();
	public Tycx001CxCxjgdyPojo pojo=new Tycx001CxCxjgdyPojo();
	public Tycx001CxCxjgdyPojo getPojo() {
		return pojo;
	}
	public void setPojo(Tycx001CxCxjgdyPojo pojo) {
		this.pojo = pojo;
	}
	/**
	 * 构造方法
	 */
	public TreeVO(){
		
	}	
	public List<TreeVO> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<TreeVO> childrenList) {
		this.childrenList = childrenList;
	}
	

}
