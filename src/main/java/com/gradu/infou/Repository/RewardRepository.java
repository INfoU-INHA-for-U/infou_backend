package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.RewardDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface RewardRepository extends ElasticsearchRepository<RewardDocument, String>, CrudRepository<RewardDocument, String> {
    Page<RewardDocument> findAllByUserId(String userId, Pageable pageable);
}
