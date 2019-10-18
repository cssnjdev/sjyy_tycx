package com.cwks.bizsys.xtgl.dao;

import com.cwks.bizsys.xtgl.domain.TXtGnsPojo;
import com.cwks.common.config.MyMapper;

import java.util.List;


public interface TXtGnsMapper extends MyMapper<TXtGnsPojo> {

public List select(TXtGnsPojo vo);

public TXtGnsPojo selectByPKey(TXtGnsPojo pojo);

public void updateByPKey(TXtGnsPojo pojo);

public void updateByPKeySelective(TXtGnsPojo pojo);

public int insert(TXtGnsPojo pojo);

public int insertSelective(TXtGnsPojo pojo);

public void deleteByPKey(TXtGnsPojo pojo);

}
