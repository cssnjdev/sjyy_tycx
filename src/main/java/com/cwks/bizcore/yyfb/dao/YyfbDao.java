package com.cwks.bizcore.yyfb.dao;


import com.cwks.bizcore.yyfb.mapping.mapper.FxyyMapper;
import com.cwks.bizcore.yyfb.mapping.pojo.FxyyPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class YyfbDao {
	@Autowired
    private FxyyMapper fxyyMapper;
    @Autowired
    JdbcDao jdbcDao;
    public List select(FxyyPojo pojo){
        return fxyyMapper.select(pojo);
    };
    public void insert(FxyyPojo pojo){
    	fxyyMapper.insert(pojo);
    };
    public void insertSelective(FxyyPojo pojo){
    	fxyyMapper.insertSelective(pojo);
    };
    public void updateByPKey(FxyyPojo pojo){
    	fxyyMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(FxyyPojo pojo){
    	fxyyMapper.updateByPKeySelective(pojo);
    };
    public void deleteByPKey(FxyyPojo pojo){
    	fxyyMapper.deleteByPKey(pojo);
    };

	public List executeSql(String sql,int page,int limit)throws Exception {
     List list= jdbcDao.queryPage(sql, page, limit);
     return list;
   }
	public Map executeSql2(String sql,ArrayList param)throws Exception {
	     Map map= jdbcDao.queryformap(sql, param);
	     return map;
	   }
}
