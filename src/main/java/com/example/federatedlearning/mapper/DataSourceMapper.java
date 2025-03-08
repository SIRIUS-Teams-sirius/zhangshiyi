package com.example.federatedlearning.mapper;

import com.example.federatedlearning.model.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    @Select("SELECT * FROM data_sources")
    List<DataSource> getAllDataSources();

    @Select("SELECT * FROM data_sources WHERE id = #{id}")
    DataSource getDataSourceById(Long id); // 添加该方法

    @Select("SELECT url FROM data_sources WHERE id = #{id}")
    String getUrlById(Long id);
}