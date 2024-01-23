package com.gradu.infou.logstashTest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface ElasticSearchItemsRepository extends ElasticsearchRepository<ElasticSearchItems, Long>, CrudRepository<ElasticSearchItems, Long> {

}
