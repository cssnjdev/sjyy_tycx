package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.QxSwjgGwPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;

public interface QxSwjgGwMapper extends MyMapper<QxSwjgGwPojo> {

public List select(QxSwjgGwPojo vo);

public QxSwjgGwPojo selectByPKey(QxSwjgGwPojo pojo);

public void updateByPKey(QxSwjgGwPojo pojo);

public void updateByPKeySelective(QxSwjgGwPojo pojo);

public int insert(QxSwjgGwPojo pojo);

public int insertSelective(QxSwjgGwPojo pojo);

public void deleteByPKey(QxSwjgGwPojo pojo);

public void deleteBySWJGDM(QxSwjgGwPojo pojo);

public void deleteByGWXH(QxSwjgGwPojo pojo);

}
