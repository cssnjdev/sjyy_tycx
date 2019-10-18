package com.cwks.bizcore.yyfb.dao;


import com.cwks.bizcore.yyfb.mapping.mapper.Yyfb001AYYfwSjlyMapper;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwSjlyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Yyfb003AYYfwSjlyDao {

    @Autowired
    private Yyfb001AYYfwSjlyMapper yyfb003AYYfwSjlyMapper;

    public List select(Yyfb001AYYfwSjlyPojo pojo){
        return yyfb003AYYfwSjlyMapper.select(pojo);
    };

    public Yyfb001AYYfwSjlyPojo selectByPKey(Yyfb001AYYfwSjlyPojo pojo){
        return yyfb003AYYfwSjlyMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Yyfb001AYYfwSjlyPojo pojo){
        yyfb003AYYfwSjlyMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Yyfb001AYYfwSjlyPojo pojo){
        yyfb003AYYfwSjlyMapper.updateByPKeySelective(pojo);
    };

    public void insert(Yyfb001AYYfwSjlyPojo pojo){
        yyfb003AYYfwSjlyMapper.insert(pojo);
    };

    public void insertSelective(Yyfb001AYYfwSjlyPojo pojo){
        yyfb003AYYfwSjlyMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Yyfb001AYYfwSjlyPojo pojo){
        yyfb003AYYfwSjlyMapper.deleteByPKey(pojo);
    };

}
