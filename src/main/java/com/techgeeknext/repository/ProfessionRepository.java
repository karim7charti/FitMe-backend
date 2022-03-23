package com.techgeeknext.repository;

import com.techgeeknext.model.Admin;
import com.techgeeknext.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {

}
