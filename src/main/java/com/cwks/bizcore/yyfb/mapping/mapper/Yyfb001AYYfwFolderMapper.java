package com.cwks.bizcore.yyfb.mapping.mapper;

import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwFolderPojo;

import java.util.List;

public interface Yyfb001AYYfwFolderMapper {

    public List select(Yyfb001AYYfwFolderPojo pojo);

    public Yyfb001AYYfwFolderPojo selectByPKey(Yyfb001AYYfwFolderPojo pojo);

    public void updateByPKey(Yyfb001AYYfwFolderPojo pojo);

    public void updateByPKeySelective(Yyfb001AYYfwFolderPojo pojo);

    public void insert(Yyfb001AYYfwFolderPojo pojo);

    public void insertSelective(Yyfb001AYYfwFolderPojo pojo);

    public void deleteByPKey(Yyfb001AYYfwFolderPojo pojo);

}
