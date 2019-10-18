package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.DmQxGwflPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface DmQxGwflMapper extends MyMapper<DmQxGwflPojo> {

public List select(DmQxGwflPojo vo);

public DmQxGwflPojo selectByPKey(DmQxGwflPojo pojo);

public void updateByPKey(DmQxGwflPojo pojo);

public void updateByPKeySelective(DmQxGwflPojo pojo);

public int insert(DmQxGwflPojo pojo);

public int insertSelective(DmQxGwflPojo pojo);

public void deleteByPKey(DmQxGwflPojo pojo);

}
