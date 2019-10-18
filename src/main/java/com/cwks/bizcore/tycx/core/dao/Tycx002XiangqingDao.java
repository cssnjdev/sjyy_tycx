package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx002XiangqingMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002XiangqingPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx002XiangqingDao {
	@Autowired
	JdbcDao jdbcDao;
	@Autowired
	Tycx002XiangqingMapper xiangqingMapper;
	
	public List<Tycx002XiangqingPojo> getXiangqing(String fxyyId){
		
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
		return (List<Tycx002XiangqingPojo>) jdbcDao.queryforlist(sql,params,Tycx002XiangqingPojo.class) 	;
 	}
	
}
