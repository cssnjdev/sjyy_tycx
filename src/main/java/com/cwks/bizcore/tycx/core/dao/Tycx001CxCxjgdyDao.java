package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx001CxCxjgdyMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx001CxCxjgdyDao {
    @Autowired
    JdbcDao jdbcDao;
    @Autowired
    private Tycx001CxCxjgdyMapper tycx001CxCxjgdyMapper;

    public List select(Tycx001CxCxjgdyPojo pojo){
        return tycx001CxCxjgdyMapper.select(pojo);
    };

    public Tycx001CxCxjgdyPojo selectByPKey(Tycx001CxCxjgdyPojo pojo){
        return tycx001CxCxjgdyMapper.selectByPKey(pojo);
    };
    
    private ArrayList<Object> param = new ArrayList<Object>();
    public List selectBySqlxh(String sqlxh){
    	
    	String sql =
    			"SELECT UUID,SQLXH,XH,LMC,YCBJ,GLBJ,URL,TJLX,XSXH,XZCS,SJLMC,SJGSDQ,\n" +
				"              LBM,JCBZDLX,DMSQL,ZSFS,KZLX,DYGHBBJ,DQFS,XSGS,LKD,LMS,LLX,MBBZ,\n" + 
				"              LRR_DM,LRRQ,XGR_DM,XGRQ,NVL(SDBJ,0) SDBJ,TZFS,JSKJ,YWKJ,\n" + 
				"              (SELECT B.LMS FROM CX_CXJGDY B WHERE A.SJLMC=B.UUID) SJLMS,YJFW,urlmc,color,zdybj\n" + 
				"         FROM CX_CXJGDY A\n" + 
				"        WHERE 1 = 1 and  sqlxh =  ? \n"+
				"       order by ycbj desc,xsxh asc ";

    	param.clear();
    	param.add(sqlxh);
    	return jdbcDao.queryforlist(sql, param, Tycx001CxCxjgdyPojo.class);
    	
        //return tycx001CxCxjgdyMapper.selectByPKey(pojo);
    			
    };
    
    //获取列的层级关系
    public List getCjgx(String sqlxh){
    	
    	String sql = 
    			"select sjlmc, wm_concat(lmc) lmcs \n" +
					"         from cx_cxjgdy\n" + 
					"        where sjlmc is not null\n" + 
					"          and sqlxh = ?\n" + 
					"        group by sjlmc";
    	param.clear();
    	param.add(sqlxh);
    	return jdbcDao.queryforlist(sql, param);
    	
    }

    public void updateByPKey(Tycx001CxCxjgdyPojo pojo){
        tycx001CxCxjgdyMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx001CxCxjgdyPojo pojo){
        tycx001CxCxjgdyMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx001CxCxjgdyPojo pojo){
        tycx001CxCxjgdyMapper.insert(pojo);
    };

    public void insertSelective(Tycx001CxCxjgdyPojo pojo){
        tycx001CxCxjgdyMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx001CxCxjgdyPojo pojo){
        tycx001CxCxjgdyMapper.deleteByPKey(pojo);
    }

	public void saveAddCxjgl(Tycx001CxCxjgdyPojo pojo) {
		tycx001CxCxjgdyMapper.saveAddCxjgl(pojo);
	}


}
