package com.telusko.security.repository;

import com.telusko.security.model.RoleName;
import com.telusko.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface RolesRepo extends JpaRepository<Role,String> {


    List<Role> findAllByRoleIn(Collection<RoleName> roles);

    Optional<Role> findByRole(RoleName roleName);


}
