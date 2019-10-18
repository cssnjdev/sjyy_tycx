package com.cwks.bizcore.tycx.core.dao;


import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx004RYhsJcxxMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004RYhsJcxxPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx004RYhsJcxxDao {

    @Autowired
    private Tycx004RYhsJcxxMapper tycx004RYhsJcxxMapper;

    public List select(Tycx004RYhsJcxxPojo pojo){
        return tycx004RYhsJcxxMapper.select(pojo);
    };

    public Tycx004RYhsJcxxPojo selectByPKey(Tycx004RYhsJcxxPojo pojo){
        return tycx004RYhsJcxxMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx004RYhsJcxxPojo pojo){
        tycx004RYhsJcxxMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx004RYhsJcxxPojo pojo){
        tycx004RYhsJcxxMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx004RYhsJcxxPojo pojo){
        tycx004RYhsJcxxMapper.insert(pojo);
    };

    public void insertSelective(Tycx004RYhsJcxxPojo pojo){
        tycx004RYhsJcxxMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx004RYhsJcxxPojo pojo){
        tycx004RYhsJcxxMapper.deleteByPKey(pojo);
    };

}
