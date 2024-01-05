package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.PortalProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortalProfessorRepository extends JpaRepository<PortalProfessor, Long> {

    @Query("select pp from PortalProfessor pp join fetch pp.portal p join fetch pp.professor pr where p.department = :major and p.lectureName = :lectureName")
    Optional<List<PortalProfessor>> findByLectureName(String major, String lectureName);

    @Query("select pp from PortalProfessor pp join fetch pp.portal p join fetch pp.professor pr where p.department = :major and pr.name = :professorName")
    Optional<List<PortalProfessor>> findByProfessorName(String major, String professorName);

}
