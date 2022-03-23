package com.techgeeknext.repository;
import com.techgeeknext.model.Admin;
import com.techgeeknext.model.RefreshToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Admin findByEmail(String email);
    @Query("select s from Admin s where s.email=?1")
    Admin getAdminUsername(String email);

    Optional<Admin> findById(Long id);
}