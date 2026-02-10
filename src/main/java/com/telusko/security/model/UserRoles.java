package com.telusko.security.model;


import com.telusko.security.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_user_role" ,
        columnNames = {"user_id" , "roles_id"})},
        indexes = {@Index(name = "idx_user_roles_user" ,columnList = "user_id"),
            @Index(name = "idx_user_roles_role_id" ,columnList = "role_id")
            }
            )




public class UserRoles {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private AppUser user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "role_id" , nullable = false)
    private Roles role;

    @CreationTimestamp
    private LocalDateTime assignedAt;


    public UserRoles(AppUser user, Roles role) {
        this.user=user;
        this.role=role;
    }
}
