package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.Portal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortalRepository extends JpaRepository<Portal, Long> {
    @Query("select p from Portal p join fetch p.portalProfessors pp join fetch pp.professor pr where p.department = :major and p.lectureName = :lectureName")
    Optional<List<Portal>> findAllByDepartmentAndLectureName(String major, String lectureName);

    @Query("select p from Portal p join fetch p.portalProfessors pp join fetch pp.professor pr where p.department = :major and pr.name = :professorName")
    Optional<List<Portal>> findAllByDepartmentAndProfessorName(String major, String professorName);
}
