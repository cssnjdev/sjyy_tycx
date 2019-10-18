package com.cwks.bizcore.yyfb.mapping.mapper;


import com.cwks.bizcore.yyfb.mapping.pojo.FxyyPojo;

import java.util.List;

public interface FxyyMapper {

    public List select(FxyyPojo pojo);

    public FxyyPojo selectByPKey(FxyyPojo pojo);

    public void updateByPKey(FxyyPojo pojo);

    public void updateByPKeySelective(FxyyPojo pojo);

    public void insert(FxyyPojo pojo);

    public void insertSelective(FxyyPojo pojo);

    public void deleteByPKey(FxyyPojo pojo);

}
