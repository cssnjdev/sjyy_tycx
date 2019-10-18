package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxtjdyPojo;

import java.util.List;

public interface Tycx001CxCxtjdyMapper {

    public List select(Tycx001CxCxtjdyPojo pojo);

    public Tycx001CxCxtjdyPojo selectByPKey(Tycx001CxCxtjdyPojo pojo);

    public void updateByPKey(Tycx001CxCxtjdyPojo pojo);

    public void updateByPKeySelective(Tycx001CxCxtjdyPojo pojo);

    public void insert(Tycx001CxCxtjdyPojo pojo);

    public void insertSelective(Tycx001CxCxtjdyPojo pojo);

    public void deleteByPKey(Tycx001CxCxtjdyPojo pojo);

}
