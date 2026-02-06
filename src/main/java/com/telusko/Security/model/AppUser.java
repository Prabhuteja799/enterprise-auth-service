package com.telusko.Security.model;


import com.telusko.Security.responseDTO.AppUserDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.stream.Collectors;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;

    String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Roles",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns={@JoinColumn(name = "role_id")}
    )
    Set<Roles> roles;


    public AppUserDTO toAppUserDTO() {
        return AppUserDTO.builder()
                .id(this.id)
                .username(this.username)
                .roles(this.roles.stream().
                        map(Roles::getRole).collect(Collectors.toSet()))
                .build();
    }


}
