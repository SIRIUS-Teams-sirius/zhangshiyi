/*
package com.example.federatedlearning.controller;

import com.example.federatedlearning.model.Data;
import com.example.federatedlearning.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping
    public List<Data> getAllData() {
        return dataService.getAllData();
    }

    @GetMapping("/connection-status/statistics")
    public Map<String, Integer> getConnectionStatusStatistics() {
        return dataService.getConnectionStatusStatistics();
    }

    @GetMapping("/service-type/statistics")
    public Map<String, Integer> getServiceTypeStatistics() {
        return dataService.getServiceTypeStatistics();
    }

    @GetMapping("/protocol-type/statistics")
    public Map<String, Integer> getProtocolTypeStatistics() {
        return dataService.getProtocolTypeStatistics();
    }
}*/
package com.example.federatedlearning.controller;

import com.example.federatedlearning.model.DataSource;
import com.example.federatedlearning.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data-sources")
public class DataSourceController {

    @Autowired
    private DataService dataService;

    @GetMapping
    public List<DataSource> getAllDataSources() {
        return dataService.getAllDataSources();
    }

    @GetMapping("/{id}")
    public DataSource getDataSourceById(@PathVariable Long id) {
        return dataService.getDataSourceById(id);
    }

    @GetMapping("/{id}/connection-status")
    public Map<String, Integer> getConnectionStatusStatistics(@PathVariable Long id) {
        return dataService.getConnectionStatusStatistics(id);
    }

    @GetMapping("/{id}/service-type")
    public Map<String, Integer> getServiceTypeStatistics(@PathVariable Long id) {
        return dataService.getServiceTypeStatistics(id);
    }

    @GetMapping("/{id}/protocol-type")
    public Map<String, Integer> getProtocolTypeStatistics(@PathVariable Long id) {
        return dataService.getProtocolTypeStatistics(id);
    }
    @GetMapping("/{id}/attack-type")
    public Map<String, Integer> getAttackTypeStatistics(@PathVariable Long id) {
        return dataService.getAttackTypeStatistics(id);
    }
}