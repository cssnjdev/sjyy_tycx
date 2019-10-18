package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx003CxTxpzxxPojo;

import java.util.List;

public interface Tycx003CxTxpzxxMapper {

    public List select(Tycx003CxTxpzxxPojo pojo);
    public List selectAll(Tycx003CxTxpzxxPojo pojo);
    public Tycx003CxTxpzxxPojo selectByPKey(Tycx003CxTxpzxxPojo pojo);

    public void updateByPKey(Tycx003CxTxpzxxPojo pojo);

    public void updateByPKeySelective(Tycx003CxTxpzxxPojo pojo);

    public void insert(Tycx003CxTxpzxxPojo pojo);

    public void insertSelective(Tycx003CxTxpzxxPojo pojo);

    public void deleteByPKey(Tycx003CxTxpzxxPojo pojo);
    public void deleteByPKeySelective(Tycx003CxTxpzxxPojo pojo);

}
