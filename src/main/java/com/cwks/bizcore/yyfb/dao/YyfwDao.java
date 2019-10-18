package com.cwks.bizcore.yyfb.dao;

import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx002DzcxMapper;
import com.cwks.bizcore.yyfb.mapping.pojo.YyfwPjPojo;
import com.cwks.bizcore.yyfb.mapping.pojo.YyfwXiangqingPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@SuppressWarnings("all")
public class YyfwDao  {
	@Autowired
	JdbcDao jdbcDao;
	 @Autowired
	 private Tycx002DzcxMapper tycx002DzcxMapper;
	
	public int searchPjCount(String pjdj,String fxyy_id){
    	 String sql ="  select count(1) c from t_yyfw_PJ where pjdj_dm=? and fxyy_id=?";
    	 ArrayList<Object> params = new ArrayList<Object>();
    	 params.add(pjdj); params.add(fxyy_id);
    	 Map map= jdbcDao.queryformap(sql,params);
    	 return Integer.valueOf(map.get("C").toString()) ;
    	//return tycx002DzcxMapper.searchPj(pjdj);
     };
     
     public int countPj(String fxyy_id){
     	return tycx002DzcxMapper.countPj(fxyy_id);
     };
     
	 
	 public String fxyymc(String fxyyid){
    	return tycx002DzcxMapper.fxyymc(fxyyid);
     };
     
     public String fxyylxdm(String fxyyid){
     	return tycx002DzcxMapper.fxyylxdm(fxyyid);
     };
     

     public String fxyylxmc(String fxyylxdm){
     	return tycx002DzcxMapper.fxyylxmc(fxyylxdm);
     };
     
     public void insertPj(YyfwPjPojo pojo){
         tycx002DzcxMapper.insertPj(pojo);
     };
     public void deletePj(String fxyyid,String swry_dm){
     	  String delSql = "delete t_yyfw_PJ where fxyy_id = ? and pjr_dm=?";
 		  ArrayList<Object> params = new ArrayList<Object>();
 		  params.add(fxyyid);params.add(swry_dm);
 		  jdbcDao.update(delSql,params);
     };

     public YyfwPjPojo searchPj(String fxyyid,String swry_dm){
     	 String sql = "select fxyy_id,pjdj_dm,pjnr from t_yyfw_PJ  where fxyy_id=? and pjr_dm=?";
       	 ArrayList<Object> params = new ArrayList<Object>();
 		 params.add(fxyyid);params.add(swry_dm);
 		 return  (YyfwPjPojo) jdbcDao.queryForObject(sql,params,YyfwPjPojo.class);
     }
     
     public List<YyfwPjPojo> selectPj(String pjdj,String fxyyid){
    	 String sql ="  select  pjsj,pjnr, (select swryxm from dm_gy_swry where swry_dm=pjr_dm) pjrxm  from t_yyfw_PJ where ( pjdj_dm=? or ? is null)and fxyy_id=? ";
    	 ArrayList<Object> param = new ArrayList<Object>();
    	 param.add(pjdj);param.add(pjdj); param.add(fxyyid);
    	 return jdbcDao.queryforlist(sql, param, YyfwPjPojo.class);
    };
    
	public List<YyfwXiangqingPojo> getXiangqing(String fxyyId){
		
		String sql =" SELECT  \n"+		 
			"     FXYY_MC,\n" + 
			"     FXYYLX_DM,\n" + 
			"     YWKJ,\n" + 
			"     JSKJ,\n" + 
			"     YXBJ,\n" + 
			"     LR_SJ,\n" + 
			"     XG_SJ,\n" + 
			"     LRRY_DM,\n" + 
			"     XGRY_DM,\n" + 
			"     LRJG_DM,\n" + 
			"     XGJG_DM,\n" + 
			"     YYURL,\n" + 
			"     FBRDM,\n" + 
			"     FB_SJ,\n" + 
			"     BANBEN,\n" + 
			"     ZT_BJ,\n" + 
			"     GNMS,\n" + 
			"     GNLJ,\n" + 
			"     XQDW_DM,\n" + 
			"     XQRY_DM,\n" + 
			"     KFDW_DM,\n" + 
			"     KFRY_DM,\n" + 
			"     KFRLXDH,\n" + 
			"     KFRLXFS,\n" + 
			"     GJZ,\n" + 
			"     PROCEDURE_NAME,\n" + 
			"     XTGNDM,\n" + 
			"     XTSJCD\n" + 
			" FROM t_yyfw_fxyy t  where t.FXYY_ID =? "	; 
		
		ArrayList<Object> params = new ArrayList<Object>()				;
		params.add(fxyyId)						;
		return (List<YyfwXiangqingPojo>) jdbcDao.queryforlist(sql,params,YyfwXiangqingPojo.class) 	;
 	}
	
	public void insertFX(Map map,String tsr,String bt) {
		
		String sql = 	
				" insert into a_xt_xxzx									\n" +
				"  (uuid, tsr, btsr, tsbt, tsnr, tsurl, lrsj)			\n" + 
				" values												\n" + 
				"  (PKG_PUB_FUN.FUN_GET_GUID, ?, ?, ?, ?, ?, sysdate)	  ";

		
		String fxurl = (String) map.get("fxurl");
		String jsr_dm = (String) map.get("jsr_dm");
		String fxly = "分享理由："+(String) map.get("fxly");

		ArrayList<Object> params = new ArrayList<Object>()				;

		String[] btsrArr = jsr_dm.split(",");
		
		for(int i=0;i<btsrArr.length;i++){
			
			params.clear();
			
			params.add(tsr);
			params.add(btsrArr[i]);
			params.add(bt);
			params.add(fxly);
			params.add(fxurl);
			
			jdbcDao.update(sql,params);
			
		}
		
 	}
	
	
	
}
