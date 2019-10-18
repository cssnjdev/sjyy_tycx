package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004CxZtcxxmPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class TycxEditDao  {
	@Autowired
	JdbcDao jdbcDao;
	 private ArrayList<Object> params =new ArrayList<Object>();
	 
	 public List getColumns(Tycx004CxZtcxxmPojo pojo){
	        return null;
	 }

	 
	 public Map getInfoBySqlxh(String sqlxh){
		 
		  String sql="select * from cx_yybwh where sqlxh = ? ";
		  params.clear();
		  params.add(sqlxh);
		  return jdbcDao.queryformap(sql,params);
		 
	 }
	 
	 public void insertOneRow(String sqlxh,Map map){
		 
 		 
		 Map infoMap = this.getInfoBySqlxh(sqlxh);
		 
		 String insertSql = (String) infoMap.get("INSERT_SQL");
		 
		 String usercol = (String) infoMap.get("INSERT_USE_COL");
		 
		 String[] colarr = usercol.split(",");
		 
		 params.clear();
		 
		 for(int i=0;i<colarr.length;i++){
			 params.add(map.get(colarr[i]));
		 }
		 
		 jdbcDao.update(insertSql,params);
		 
	 }
	 
	 public void updateOneRow(String sqlxh,Map map){
		 
		 Map infoMap = this.getInfoBySqlxh(sqlxh);
		  
		 String sql = (String) infoMap.get("UPDATE_SQL");
		 
		 String usercol  = (String) infoMap.get("UPDATE_USE_COL");
		 
		 String[] colarr = usercol.split(",");
		 
		 params.clear();
		 
		 for(int i=0;i<colarr.length;i++){
			 params.add(map.get(colarr[i]));
		 }
		 jdbcDao.update(sql,params);
	 }
	 
	 public  void update(String sqlxh,Map map,List updateList) {
		  
		 String sql = "select * from cx_wdxx where sqlxh = ? and xybj='Y' ";
		   
	 }
	 
	 public void deleteRowList(String sqlxh,List list){
		 
		 Map infoMap = this.getInfoBySqlxh(sqlxh);
		  
		 String sql = (String) infoMap.get("DELETE_SQL");
		 
		 String usercol  = (String) infoMap.get("DELETE_USE_COL");
		 
		 String[] colarr = usercol.split(",");
		 
		 
		 for(int k=0;k<list.size();k++){
			 
			 Map map = (Map) list.get(k);
			 
			 params.clear();
			 
			 for(int i=0;i<colarr.length;i++){
				 params.add(map.get(colarr[i]));
			 }
			 
			 jdbcDao.update(sql,params);

		 }
		 
		 
	 }
	 
	  
}
