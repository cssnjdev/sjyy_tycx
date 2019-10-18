package com.cwks.bizcore.yyfb.dao;


import com.cwks.bizcore.yyfb.mapping.mapper.Yyfb001AYYfwFolderMapper;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwFolderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("all")
public class Yyfb001AYYfwFolderDao {

    @Autowired
    private Yyfb001AYYfwFolderMapper yyfb001AYYfwFolderMapper;

    public List select(Yyfb001AYYfwFolderPojo pojo){
        return yyfb001AYYfwFolderMapper.select(pojo);
    };

    public Yyfb001AYYfwFolderPojo selectByPKey(Yyfb001AYYfwFolderPojo pojo){
        return yyfb001AYYfwFolderMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Yyfb001AYYfwFolderPojo pojo){
        yyfb001AYYfwFolderMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Yyfb001AYYfwFolderPojo pojo){
        yyfb001AYYfwFolderMapper.updateByPKeySelective(pojo);
    };

    public void insert(Yyfb001AYYfwFolderPojo pojo){
        yyfb001AYYfwFolderMapper.insert(pojo);
    };

    public void insertSelective(Yyfb001AYYfwFolderPojo pojo){
        yyfb001AYYfwFolderMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Yyfb001AYYfwFolderPojo pojo){
        yyfb001AYYfwFolderMapper.deleteByPKey(pojo);
    };

}
