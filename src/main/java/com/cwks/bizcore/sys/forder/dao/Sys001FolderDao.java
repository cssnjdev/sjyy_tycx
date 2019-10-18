package com.cwks.bizcore.sys.forder.dao;

//
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class Sys001FolderDao {
   @Autowired
	JdbcDao jdbcDao;
	public List searchFolderByType(String folderlx_dm){
		
		String sql = " select folder_id id,pfolder_id pid,mc name,pxxh,("
		  		   + " select sum(count1) from\n"
		  		   + " (select t.folder_id, t.mc, t.pfolder_id, nvl(t2.count, 0) count1\n"
		  		   + "   from sys_folder t,\n"
		  		   + "       (select count(1) count, folder_id\n"
		  		   + "          from etl_def \n"
		  		   + "         where xybj = '1'\n"
		  		   + "         group by folder_id) t2\n"
		  		   + " where t.folderlx_dm = ? \n"
		  		   + "   and t.folder_id = t2.folder_id(+))  " 
		  		   + "		connect by prior folder_id = pfolder_id start with folder_id = x.folder_id) count "
		  		   + " from sys_folder x where folderlx_dm = ? and upper(xybj) = upper('Y') order by pxxh ";
		  		
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(folderlx_dm);
		param.add(folderlx_dm);

		return jdbcDao.queryforlist(sql,param);
		
		
	}
	
	
	public void createFolder(String mldm,String mlmc,String sjml,String swry_dm,String pxxh,String folderlx_dm){
		
 		
		String sql = " INSERT INTO SYS_FOLDER                                                   \n" +
 			   "  (MC, MC_J, PFOLDER_ID, FOLDERLX_DM, SSSWJG_DM, XYBJ, PXXH,FOLDER_ID)   \n" + 
 			   " VALUES                                                                  \n" + 
 			   "  (?, ?, ?, ?, ?, 'Y',?,?)                                              " ;

		ArrayList<String> params = new ArrayList<String>();
		
		params.add(mlmc);
    	params.add(mlmc);
    	params.add(sjml);
    	params.add(folderlx_dm);

    	
    	params.add(swry_dm);
    	params.add(pxxh);
    	params.add(mldm);
		
    	jdbcDao.update(sql,params);
     	
	}
	
	
	public void updateFolder(String mldm,String mlmc,String sjml,String swry_dm,String pxxh,String folderlx_dm){
		
		
		String sql =  " UPDATE SYS_FOLDER                                                         \n" +
		 			   "  SET MC =?, MC_J=?, PFOLDER_ID=?, FOLDERLX_DM=?, SSSWJG_DM=?,  PXXH=?  \n" + 
		 			   " WHERE FOLDER_ID=?     ";

			ArrayList<String> params = new ArrayList<String>();
			
			params.add(mlmc);
	    	params.add(mlmc);
	    	params.add(sjml);
	    	params.add(folderlx_dm);

	    	params.add(swry_dm);
	    	params.add(pxxh);
	    	params.add(mldm);
			
	    	jdbcDao.update(sql,params);
		
	}
	
	
}
