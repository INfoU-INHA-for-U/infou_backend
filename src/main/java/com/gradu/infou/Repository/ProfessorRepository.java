package com.gradu.infou.Repository;

import com.gradu.infou.Domain.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}

