package com.cwks.bizcore.tycx.core.mapping.mapper;

import com.cwks.bizcore.tycx.core.mapping.pojo.Etl001DatasourcePojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface Etl001DatasourceMapper {

    public List select(Etl001DatasourcePojo pojo);

    public Etl001DatasourcePojo selectByPKey(Etl001DatasourcePojo pojo);

    public void updateByPKey(Etl001DatasourcePojo pojo);

    public void updateByPKeySelective(Etl001DatasourcePojo pojo);

    public void insert(Etl001DatasourcePojo pojo);

    public void insertSelective(Etl001DatasourcePojo pojo);

    public void deleteByPKey(Etl001DatasourcePojo pojo);

}
