package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.DmQxSwrySfPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface DmQxSwrySfMapper extends MyMapper<DmQxSwrySfPojo> {

public List select(DmQxSwrySfPojo vo);

public DmQxSwrySfPojo selectByPKey(DmQxSwrySfPojo pojo);

public void updateByPKey(DmQxSwrySfPojo pojo);

public void updateByPKeySelective(DmQxSwrySfPojo pojo);

public void updateBySwrydm(DmQxSwrySfPojo pojo);

public int insert(DmQxSwrySfPojo pojo);

public int insertSelective(DmQxSwrySfPojo pojo);

public void deleteByPKey(DmQxSwrySfPojo pojo);

}
