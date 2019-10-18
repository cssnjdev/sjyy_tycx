package com.cwks.bizcore.tycx.core.dao;


import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004CxZtcxxmPojo;
import com.cwks.common.dao.JdbcDao;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class TycxInfoDao {
    Logger logger;
	@Autowired
	JdbcDao jdbcDao;
	  private ArrayList<Object> params =new ArrayList<Object>();
			  
	  public List getColumns(Tycx004CxZtcxxmPojo pojo){
	        return null;
	  }

	  public  Map getWdxx(String sqlxh) {
//		  String sql="select * from cx_wdxx where sqlxh = ? and xybj='Y' ";
		  String showtype = getShowType(sqlxh);
		  String dlzd = getDlzd(sqlxh);
		  String sql="select HZB wdzd,'dlzd' dlzd,txlx linetype,'showtype' showtype,'top' layout,'Y' yxbj,hzbdw dldw,zzbmc dlindex from CX_TXPZXX where sqlxh = ?";
		  params.clear();
		  params.add(sqlxh);
		  try{
			  List<Map> list = jdbcDao.queryforlist(sql,params);
			  if(list.size()>0){

				  list.get(0).replace("SHOWTYPE",showtype);
				  list.get(0).replace("DLZD",dlzd);
				  return list.get(0);
			  }else{
				  return null;
			  }
		  }catch(Exception e){
			  return null;
		  }
	  }
	public String getDlzd(String sqlxh){
		String sql = "select to_char(wmsys.wm_concat(xxx.zzb)) showType  from (select t.zzb  from CX_TXPZXX t where t.sqlxh=?) xxx";
		params.clear();
		params.add(sqlxh);
		Map list = (Map)jdbcDao.queryforlist(sql,params).get(0);
		if(list.get("showType")==null||list.get("showType").equals(null)){
			return null;
		}
		return list.get("showType").toString();
	}
	  public String getShowType(String sqlxh){
	  	String sql = "select to_char(wmsys.wm_concat(xxx.txlx)) showType  from (select t.txlx  from CX_TXPZXX t where t.sqlxh=?) xxx ";
		  params.clear();
		  params.add(sqlxh);
		  Map list = (Map)jdbcDao.queryforlist(sql,params).get(0);
		  if(list.get("showType")==null||list.get("showType").equals(null)){
			  return null;
		  }
		  return list.get("showType").toString();
	  }
	  public  List getTjtj(String sqlxh) {
		  
		  String sql=" select * from cx_cxjgdy where sqlxh=? and tjlx = '1'";
		  params.clear();
		  params.add(sqlxh);
		  
		  try{
			  return jdbcDao.queryforlist(sql,params);
		  }catch(Exception e){
			  return null;
		  }
		  
	  }
	  
	  
	  public  List getColdata(String sqlxh) {
		  
		  String sql=" select * from cx_cxjgdy where sqlxh=? order by ycbj desc,xsxh asc ";
		  params.clear();
		  params.add(sqlxh);
		  
		  try{
			  return jdbcDao.queryforlist(sql,params);
		  }catch(Exception e){
			  return null;
		  }
		  
	  }
	  
	  public Map searchSington(String sqlxh){
		  
		  String sql="select * from cx_cxdy_sington where sqlxh = ? and xybj='Y' ";
		  params.clear();
		  params.add(sqlxh);
		   
		  try{
			  List<Map> list = jdbcDao.queryforlist(sql,params);
			  if(list.size()>0){
				  return list.get(0);
			  }else{
				  return null;
			  }
		  }catch(Exception e){
			  return null;
		  }
		  
	  }
	  
	  public Map queryformap(String sql){
		  return this.queryformap(sql);
	  }
	
	  public Map getOneJgl(String sqlxh,String lmc){
		  
		  String sql = " select * from cx_cxjgdy where sqlxh = ? and lmc = ? ";
		  
		  params.clear();
		  params.add(sqlxh);
		  params.add(lmc);
		  
		  return jdbcDao.queryformap(sql, params) ;
 		  
	  }
	  
	  public List queryforlist(String sql){
		  return jdbcDao.queryforlist(sql);
	  }
	  
}
