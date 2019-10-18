package com.cwks.bizcore.tycx.core.dao;


import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx003CxTxpzxxMapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx003CxTxpzxxPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Tycx003CxTxpzxxDao {

    @Autowired
    private Tycx003CxTxpzxxMapper tycx003CxTxpzxxMapper;

    public List select(Tycx003CxTxpzxxPojo pojo){
        return tycx003CxTxpzxxMapper.select(pojo);
    };
    public List selectAll(Tycx003CxTxpzxxPojo pojo){
        return tycx003CxTxpzxxMapper.selectAll(pojo);
    };
    public Tycx003CxTxpzxxPojo selectByPKey(Tycx003CxTxpzxxPojo pojo){
        return tycx003CxTxpzxxMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.insert(pojo);
    };

    public void insertSelective(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.deleteByPKey(pojo);
    };
    public void deleteByPKeySelective(Tycx003CxTxpzxxPojo pojo){
        tycx003CxTxpzxxMapper.deleteByPKeySelective(pojo);
    };

}
