package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.InfouDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InfouRepository extends ElasticsearchRepository<InfouDocument, Long>, CrudRepository<InfouDocument, Long> {
    Slice<InfouDocument> findSliceByLectureName(String lectureName, Pageable pageable);
}
