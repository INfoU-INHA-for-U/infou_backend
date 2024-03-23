package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.NoticeBookmarkDocument;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import com.gradu.infou.Domain.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NoticeBookmarkRepository extends ElasticsearchRepository<NoticeBookmarkDocument, String>, CrudRepository<NoticeBookmarkDocument, String> {
    Page<NoticeBookmarkDocument> findAllByUserId(String userId, Pageable pageable);
    Page<NoticeBookmarkDocument> findAllByUserIdAndAndNoticeTagsContaining(String userId, String tags, Pageable pageable);

    Optional<NoticeBookmarkDocument> findByUserIdAndNoticeDocumentId(String userId, String noticeDocumentId);

}
