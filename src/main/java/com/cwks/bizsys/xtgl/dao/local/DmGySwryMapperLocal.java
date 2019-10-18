package com.cwks.bizsys.xtgl.dao.local;

import com.cwks.bizsys.xtgl.domain.DmGySwryPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface DmGySwryMapperLocal  extends MyMapper<DmGySwryPojo> {

public List select(DmGySwryPojo vo);

public DmGySwryPojo selectByPKey(DmGySwryPojo pojo);

public void updateByPKey(DmGySwryPojo pojo);

public void updateByPKeySelective(DmGySwryPojo pojo);

public int insert(DmGySwryPojo pojo);

public int insertSelective(DmGySwryPojo pojo);

public void deleteByPKey(DmGySwryPojo pojo);

}
