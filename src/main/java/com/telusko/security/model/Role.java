package com.telusko.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_roles_role" , columnNames = "role")} )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RoleName role;

    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL ,orphanRemoval = true)
    Set<UserRole> userRoles;


}
