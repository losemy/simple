package com.github.losemy.simple.integration.es.repository;

import com.github.losemy.simple.integration.es.model.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author lose
 * @date 2019-09-09
 **/
public interface UserRepository extends ElasticsearchRepository<UserES, Long> {

    /**
     * 通过名称搜索
     * @param name
     * @return
     */
    List<UserES> findByName(String name);
}