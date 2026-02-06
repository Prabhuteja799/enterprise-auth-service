package com.telusko.Security.repository;

import com.telusko.Security.model.RoleName;
import com.telusko.Security.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RolesRepo extends JpaRepository<Roles,String> {
    List<Roles> findAllByRoles(Set<RoleName> roles);
}
