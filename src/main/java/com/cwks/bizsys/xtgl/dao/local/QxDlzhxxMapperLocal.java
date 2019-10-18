package com.cwks.bizsys.xtgl.dao.local;

import com.cwks.bizsys.xtgl.domain.QxDlzhxxPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface QxDlzhxxMapperLocal extends MyMapper<QxDlzhxxPojo> {

public List select(QxDlzhxxPojo pojo);

public QxDlzhxxPojo selectByPKey(QxDlzhxxPojo pojo);

public void updateByPKey(QxDlzhxxPojo pojo);

public void updateByPKeySelective(QxDlzhxxPojo pojo);

public void updateBySwrydm(QxDlzhxxPojo pojo);

public int insert(QxDlzhxxPojo pojo);

public int insertSelective(QxDlzhxxPojo pojo);

public void deleteByPKey(QxDlzhxxPojo pojo);

}
