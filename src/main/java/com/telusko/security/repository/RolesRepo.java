package com.telusko.security.repository;

import com.telusko.security.model.RoleName;
import com.telusko.security.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface RolesRepo extends JpaRepository<Roles,String> {


    List<Roles> findAllByRolesIn(Collection<RoleName> roles);
}
