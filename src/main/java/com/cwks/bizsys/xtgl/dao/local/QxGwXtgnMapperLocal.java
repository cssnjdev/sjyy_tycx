package com.cwks.bizsys.xtgl.dao.local;

import com.cwks.bizsys.xtgl.domain.QxGwXtgnPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface QxGwXtgnMapperLocal extends MyMapper<QxGwXtgnPojo> {

public List select(QxGwXtgnPojo vo);

public QxGwXtgnPojo selectByPKey(QxGwXtgnPojo pojo);

public void updateByPKey(QxGwXtgnPojo pojo);

public void updateByPKeySelective(QxGwXtgnPojo pojo);

public int insert(QxGwXtgnPojo pojo);

public int insertSelective(QxGwXtgnPojo pojo);

public void deleteByPKey(QxGwXtgnPojo pojo);

}
