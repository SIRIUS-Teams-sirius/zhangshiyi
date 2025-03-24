package com.example.federatedlearning.mapper;

import com.example.federatedlearning.dto.NetworkData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataStatisticsMapper {


        @Select("SELECT label, land as connectionStatus, service as serviceType, protocol_type as protocolType FROM network_data_kdd99cup")
        List<NetworkData> findAll();

}
