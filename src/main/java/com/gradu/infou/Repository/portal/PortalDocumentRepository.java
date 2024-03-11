package com.gradu.infou.Repository.portal;

import com.gradu.infou.Domain.Entity.PortalDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PortalDocumentRepository extends ElasticsearchRepository<PortalDocument, Long> {
   List<PortalDocument> findAllByAcademicNumber(String academicNumber);
   Optional<PortalDocument> findByAcademicNumberAndProfessorNameAndSemester(String academic, String professor, String semester);
   List<PortalDocument> findAllByAcademicNumberAndProfessorName(String academic, String professor);

}
