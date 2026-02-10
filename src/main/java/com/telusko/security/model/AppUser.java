package com.telusko.security.model;


import com.telusko.security.request.UserRequest;
import com.telusko.security.responseDTO.AppUserDTO;
import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_app_user_username", columnNames = "username")})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<UserRoles> userRoles = new HashSet<>();

    public void addRole(Roles role) {
        UserRoles userRole = new UserRoles(this, role);
        this.userRoles.add(userRole);
        role.getUserRoles().add(userRole);

    }

    public AppUserDTO toAppUserDTO( ){

        Set<RoleName> roles = this.getUserRoles().stream()
                .map(ur-> ur.getRole().getRole() )
                .collect(Collectors.toSet());

        return  new AppUserDTO(this.id,this.username,roles);
    }

}
