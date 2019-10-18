package com.cwks.bizcore.tycx.core.dao;


import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx004YhsDao {
	@Autowired
	JdbcDao jdbcDao;
	  public List executeSql(String sql,ArrayList param,String sjymc)
		throws Exception {
		  List resultList;
		  if(!TycxUtils.isEmpty(sjymc)){
			  DataSourceUtil dataSourceUtils = new DataSourceUtil(sjymc);
//			  jdbcDao.setJdbcTemplateByJndiName(sjymc);
			  resultList =dataSourceUtils.queryforlist(sql, param);
		  }else {
			  resultList =jdbcDao.queryforlist(sql, param);
		  }

		  return resultList;
	  }
	  public List executeProcedure(String sjymc,String djxh) throws Exception {
		  List rList = null;
		  DataSourceUtil dataSourceUtils = new DataSourceUtil(sjymc);
//		  jdbcDao.setJdbcTemplateByJndiName(sjymc);
//			// 选择存储过程开始		
			String ccgcmc="{P_YHS_JCXX(?,?,?)}";
//	    	rList=this.callStoreProcess(ccgcmc,proParam,false);
			 final String AS_SUCCESS = "";
			 final int AS_MESSAGE=0;	
			 ArrayList proParam = new ArrayList();
			 proParam.add(new StoredProcParamObj(1,djxh, StoredProcParamObj.IN,
						java.sql.Types.VARCHAR));
			 proParam.add(new StoredProcParamObj(2, AS_SUCCESS, StoredProcParamObj.OUT,
						java.sql.Types.VARCHAR));
			 proParam.add(new StoredProcParamObj(3, AS_MESSAGE, StoredProcParamObj.OUT,
						java.sql.Types.VARCHAR));
			// 选择存储过程开始
	    	rList=dataSourceUtils.callStoreProcess("{call P_YHS_JCXX(?,?,?)}",proParam,false);
		
	    	return rList;
	  }
}
