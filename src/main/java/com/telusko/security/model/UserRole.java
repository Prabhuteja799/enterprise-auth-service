package com.telusko.security.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_user_role" ,
        columnNames = {"user_id" , "role_id"})},
        indexes = {@Index(name = "idx_user_role_user" ,columnList = "user_id"),
            @Index(name = "idx_user_role_role_id" ,columnList = "role_id")
            }
            )




public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private AppUser user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "role_id" , nullable = false)
    private Role role;

    @CreationTimestamp
    private LocalDateTime assignedAt;


    public UserRole(AppUser user, Role role) {
        this.user=user;
        this.role=role;
    }
}
