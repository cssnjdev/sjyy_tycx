package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx002CxCxzxxxMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002CxCxzxxxPojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx002CxCxzxxxDao {
    @Autowired
    private Tycx002CxCxzxxxMapper tycx002CxCxzxxxMapper;

    public List select(Tycx002CxCxzxxxPojo pojo){
        return tycx002CxCxzxxxMapper.select(pojo);
    };

    public Tycx002CxCxzxxxPojo selectByPKey(Tycx002CxCxzxxxPojo pojo){
        return tycx002CxCxzxxxMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx002CxCxzxxxPojo pojo){    	
        tycx002CxCxzxxxMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx002CxCxzxxxPojo pojo){
        tycx002CxCxzxxxMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx002CxCxzxxxPojo pojo){
        tycx002CxCxzxxxMapper.insert(pojo);
    };

    public void insertSelective(Tycx002CxCxzxxxPojo pojo){
        tycx002CxCxzxxxMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx002CxCxzxxxPojo pojo){
        tycx002CxCxzxxxMapper.deleteByPKey(pojo);
    };

}
