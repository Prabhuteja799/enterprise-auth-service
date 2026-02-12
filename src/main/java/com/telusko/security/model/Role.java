package com.telusko.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "userRoles")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_roles_role" , columnNames = "role")} )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
            @EqualsAndHashCode.Include
    String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RoleName role;

    @Builder.Default
    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL ,orphanRemoval = true)
    Set<UserRole> userRoles = new HashSet<>();


}
