package com.cwks.bizcore.yyfb.mapping.mapper;


import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001TYYfwFxyyFjPojo;

import java.util.List;

public interface Yyfb001TYYfwFxyyFjMapper {

    public List select(Yyfb001TYYfwFxyyFjPojo pojo);

    public Yyfb001TYYfwFxyyFjPojo selectByPKey(Yyfb001TYYfwFxyyFjPojo pojo);

    public void updateByPKey(Yyfb001TYYfwFxyyFjPojo pojo);

    public void updateByPKeySelective(Yyfb001TYYfwFxyyFjPojo pojo);

    public void insert(Yyfb001TYYfwFxyyFjPojo pojo);

    public void insertSelective(Yyfb001TYYfwFxyyFjPojo pojo);

    public void deleteByPKey(Yyfb001TYYfwFxyyFjPojo pojo);

}
