package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004CxZtcxxmPojo;

import java.util.List;

public interface Tycx004CxZtcxxmMapper {

    public List select(Tycx004CxZtcxxmPojo pojo);
    
    public List selectCxxmBySjdm(Tycx004CxZtcxxmPojo pojo);

    public Tycx004CxZtcxxmPojo selectByPKey(Tycx004CxZtcxxmPojo pojo);

    public void updateByPKey(Tycx004CxZtcxxmPojo pojo);

    public void updateByPKeySelective(Tycx004CxZtcxxmPojo pojo);

    public void insert(Tycx004CxZtcxxmPojo pojo);

    public void insertSelective(Tycx004CxZtcxxmPojo pojo);

    public void deleteByPKey(Tycx004CxZtcxxmPojo pojo);

}
