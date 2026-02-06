package com.telusko.Security.model;

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
public class Roles {

    @Id
            @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    RoleName role;


    @ManyToMany(mappedBy = "roles")
    Set<AppUser> userId;



}
