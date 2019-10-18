package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004RYhsJcxxPojo;

import java.util.List;

public interface Tycx004RYhsJcxxMapper {

    public List select(Tycx004RYhsJcxxPojo pojo);

    public Tycx004RYhsJcxxPojo selectByPKey(Tycx004RYhsJcxxPojo pojo);

    public void updateByPKey(Tycx004RYhsJcxxPojo pojo);

    public void updateByPKeySelective(Tycx004RYhsJcxxPojo pojo);

    public void insert(Tycx004RYhsJcxxPojo pojo);

    public void insertSelective(Tycx004RYhsJcxxPojo pojo);

    public void deleteByPKey(Tycx004RYhsJcxxPojo pojo);

}
