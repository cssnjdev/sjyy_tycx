package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;

import java.util.List;

public interface Tycx001CxCxjgdyMapper {

    public List select(Tycx001CxCxjgdyPojo pojo);

    public Tycx001CxCxjgdyPojo selectByPKey(Tycx001CxCxjgdyPojo pojo);

    public void updateByPKey(Tycx001CxCxjgdyPojo pojo);

    public void updateByPKeySelective(Tycx001CxCxjgdyPojo pojo);

    public void insert(Tycx001CxCxjgdyPojo pojo);

    public void insertSelective(Tycx001CxCxjgdyPojo pojo);

    public void deleteByPKey(Tycx001CxCxjgdyPojo pojo);

	public void saveAddCxjgl(Tycx001CxCxjgdyPojo pojo);

}
