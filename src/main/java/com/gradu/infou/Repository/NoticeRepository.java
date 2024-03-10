package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.NoticeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NoticeRepository extends ElasticsearchRepository<NoticeDocument, Long> {

}
