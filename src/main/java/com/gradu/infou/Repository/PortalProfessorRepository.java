package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.Portal;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortalProfessorRepository extends JpaRepository<PortalProfessor, Long> {

    @Query("select pp from PortalProfessor pp join fetch pp.portal p join fetch pp.professor pr where p.department = :major and p.lectureName = :lectureName")
    Optional<List<PortalProfessor>> findByLectureName(String major, String lectureName);

    @Query("select pp from PortalProfessor pp join fetch pp.portal p join fetch pp.professor pr where p.department = :major and pr.name = :professorName")
    Optional<List<PortalProfessor>> findByProfessorName(String major, String professorName);


    @Query(value = "select pp from PortalProfessor pp join fetch pp.portal p join fetch pp.professor pr where p.department = :department order by p.option_5 desc, p.option_4 desc, p.option_3 desc, p.option_2 desc, p.option_1 desc"
            , countQuery = "select count(pp) from PortalProfessor pp join pp.portal p where p.department = :department"
    )
    Slice<PortalProfessor> findSliceByDepartment(String department,Pageable pageable);

    Optional<List<PortalProfessor>> findByPortal(Portal p);

}
