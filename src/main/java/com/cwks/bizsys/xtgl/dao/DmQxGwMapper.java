package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.DmQxGwPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface DmQxGwMapper extends MyMapper<DmQxGwPojo> {

public List select(DmQxGwPojo vo);

public DmQxGwPojo selectByPKey(DmQxGwPojo pojo);

public void updateByPKey(DmQxGwPojo pojo);

public void updateByPKeySelective(DmQxGwPojo pojo);

public int insert(DmQxGwPojo pojo);

public int insertSelective(DmQxGwPojo pojo);

public void deleteByPKey(DmQxGwPojo pojo);

}
