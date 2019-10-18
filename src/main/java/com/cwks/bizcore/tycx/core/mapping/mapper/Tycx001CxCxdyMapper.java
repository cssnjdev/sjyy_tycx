package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxdyPojo;

import java.util.List;

public interface Tycx001CxCxdyMapper {

    public List selectCxdy(Tycx001CxCxdyPojo pojo);

    public Tycx001CxCxdyPojo selectByPKey(Tycx001CxCxdyPojo pojo);

    public void updateByPKey(Tycx001CxCxdyPojo pojo);

    public void updateByPKeySelective(Tycx001CxCxdyPojo pojo);

    public void insert(Tycx001CxCxdyPojo pojo);

    public void insertSelective(Tycx001CxCxdyPojo pojo);

    public void deleteByPKey(Tycx001CxCxdyPojo pojo);
    
    public String getSjymc(String sjylx);
}
