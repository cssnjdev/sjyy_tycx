package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizcore.comm.vo.ComboTreePojo;
import com.cwks.bizsys.xtgl.domain.DmGySwjgPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface DmGySwjgMapper extends MyMapper<DmGySwjgPojo> {

public List select(DmGySwjgPojo vo);

public DmGySwjgPojo selectByPKey(DmGySwjgPojo pojo);

public void updateByPKey(DmGySwjgPojo pojo);

public void updateByPKeySelective(DmGySwjgPojo pojo);

public int insert(DmGySwjgPojo pojo);

public int insertSelective(DmGySwjgPojo pojo);

public void deleteByPKey(DmGySwjgPojo pojo);

public List selectComboSjSwjgDm(ComboTreePojo pojo);

}
