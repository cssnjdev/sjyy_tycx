package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.QxJggwSwrySfPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface QxJggwSwrySfMapper extends MyMapper<QxJggwSwrySfPojo> {

public List select(QxJggwSwrySfPojo vo);

public QxJggwSwrySfPojo selectByPKey(QxJggwSwrySfPojo pojo);

public void updateByPKey(QxJggwSwrySfPojo pojo);

public void updateByPKeySelective(QxJggwSwrySfPojo pojo);

public int insert(QxJggwSwrySfPojo pojo);

public int insertSelective(QxJggwSwrySfPojo pojo);

public void deleteByPKey(QxJggwSwrySfPojo pojo);

public void deleteBySwrysfdm(QxJggwSwrySfPojo pojo);

public void deleteByGWXH(QxJggwSwrySfPojo pojo);
}
