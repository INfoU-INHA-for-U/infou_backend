package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.InfouDocument;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends ElasticsearchRepository<NoticeDocument, Long>, CrudRepository<NoticeDocument, Long> {
    Page<NoticeDocument> findAllByType(String type, Pageable pageable);
    Page<NoticeDocument> findAllByTypeAndTagsContaining(String type, String tags, Pageable pageable);
    Optional<NoticeDocument> findById(String id);
    Page<NoticeDocument> findAllByTitleContainingAndType(String title, String type, Pageable pageable);
    List<NoticeDocument> findAllByTagsContaining(String tag);
}
