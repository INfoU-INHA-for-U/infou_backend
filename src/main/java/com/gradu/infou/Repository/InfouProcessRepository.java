package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.InfouProcessDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InfouProcessRepository extends ElasticsearchRepository<InfouProcessDocument, String>, CrudRepository<InfouProcessDocument, String> {
    InfouProcessDocument findByAcademicNumberAndProfessorName(String academic, String professor);

    Page<InfouProcessDocument> findAllByDepartment(String depart, Pageable pageable);
    Page<InfouProcessDocument> findAllByLectureName(String lecture, Pageable pageable);
    Page<InfouProcessDocument> findAllByProfessorName(String professor, Pageable pageable);
}
