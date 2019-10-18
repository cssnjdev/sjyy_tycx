package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002CxCxzxxxPojo;

import java.util.List;

public interface Tycx002CxCxzxxxMapper {

    public List select(Tycx002CxCxzxxxPojo pojo);

    public Tycx002CxCxzxxxPojo selectByPKey(Tycx002CxCxzxxxPojo pojo);

    public void updateByPKey(Tycx002CxCxzxxxPojo pojo);

    public void updateByPKeySelective(Tycx002CxCxzxxxPojo pojo);

    public void insert(Tycx002CxCxzxxxPojo pojo);

    public void insertSelective(Tycx002CxCxzxxxPojo pojo);

    public void deleteByPKey(Tycx002CxCxzxxxPojo pojo);

}
