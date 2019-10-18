package com.cwks.bizcore.yyfb.dao;


import com.cwks.bizcore.yyfb.mapping.mapper.Yyfb001TYYfwFxyyFjMapper;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001TYYfwFxyyFjPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Yyfb001TYYfwFxyyFjDao {

    @Autowired
    private Yyfb001TYYfwFxyyFjMapper yyfb001TYYfwFxyyFjMapper;

    public List select(Yyfb001TYYfwFxyyFjPojo pojo){
        return yyfb001TYYfwFxyyFjMapper.select(pojo);
    };

    public Yyfb001TYYfwFxyyFjPojo selectByPKey(Yyfb001TYYfwFxyyFjPojo pojo){
        return yyfb001TYYfwFxyyFjMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Yyfb001TYYfwFxyyFjPojo pojo){
        yyfb001TYYfwFxyyFjMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Yyfb001TYYfwFxyyFjPojo pojo){
        yyfb001TYYfwFxyyFjMapper.updateByPKeySelective(pojo);
    };

    public void insert(Yyfb001TYYfwFxyyFjPojo pojo){
        yyfb001TYYfwFxyyFjMapper.insert(pojo);
    };

    public void insertSelective(Yyfb001TYYfwFxyyFjPojo pojo){
        yyfb001TYYfwFxyyFjMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Yyfb001TYYfwFxyyFjPojo pojo){
        yyfb001TYYfwFxyyFjMapper.deleteByPKey(pojo);
    };

}
