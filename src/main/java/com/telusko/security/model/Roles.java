package com.telusko.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity

public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    RoleName role;

    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL ,orphanRemoval = true)
    List<UserRoles> userRoles;


}
