package com.cwks.bizcore.yyfb.mapping.mapper;

import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwSjlyPojo;

import java.util.List;

public interface Yyfb001AYYfwSjlyMapper {

    public List select(Yyfb001AYYfwSjlyPojo pojo);

    public Yyfb001AYYfwSjlyPojo selectByPKey(Yyfb001AYYfwSjlyPojo pojo);

    public void updateByPKey(Yyfb001AYYfwSjlyPojo pojo);

    public void updateByPKeySelective(Yyfb001AYYfwSjlyPojo pojo);

    public void insert(Yyfb001AYYfwSjlyPojo pojo);

    public void insertSelective(Yyfb001AYYfwSjlyPojo pojo);

    public void deleteByPKey(Yyfb001AYYfwSjlyPojo pojo);

}
