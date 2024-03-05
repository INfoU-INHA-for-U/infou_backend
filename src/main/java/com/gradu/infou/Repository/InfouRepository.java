package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.InfouDocument;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfouRepository extends ElasticsearchRepository<InfouDocument, Long>, CrudRepository<InfouDocument, Long> {

    Page<InfouDocument> findByLectureName(String lectureName, Pageable pageable);
//    Page<InfouDocument> findByLectureTypeGroup()
    Page<InfouDocument> findByAcademicNumberAndProfessorName(String academicNumber, String professorName, Pageable pageable);
    Page<InfouDocument> findByLectureTypeIn(List<String> lectureType, Pageable pageable);


}
