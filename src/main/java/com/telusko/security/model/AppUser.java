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
    Set<UserRole> userRoles = new HashSet<>();

    public void addRole(Role role) {

        //relying on DB unique constraint(uk_user_role) is even better
//        if (userRoles.stream()
//                .anyMatch(ur -> ur.getRole().equals(role))) {
//            return;
//        }

        UserRole link = new UserRole(this,role);
        this.userRoles.add(link);
        role.getUserRoles().add(link);



    }

    public void removeRole(Role role){

        /**
        userRoles.removeIf(ur-> {
                    boolean match = (ur.getRole().getRole() == role.getRole());
                    if(match){
                        role.getUserRoles().remove(ur);
                        ur.setUser(null);
                        ur.setRole(null);
                    }
                    return match;
                });
         **/
        if(role.getRole() == RoleName.ROLE_ADMIN){
            throw new IllegalStateException("Admin role cannot be removed");
        }

        UserRole link = userRoles.stream().
                filter(ur-> ur.getRole().getRole() == role.getRole())
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Role not assigned"));

        this.userRoles.remove(link);
        role.getUserRoles().remove(link);

        link.setRole(null);
        link.setUser(null);//Even though orphanRemoval will delete the row…
        // Null-ing prevents: stale references,accidental reuse,weird persistence states.It is defensive coding.


    }



    public void clearRoles() {

        for(UserRole ur :  new HashSet<>(this.userRoles)){  //if you are modifying (add,remove or clear)
            // the SAME collection you are iterating.Java detects this and throws:ConcurrentModificationException
            //that's why copying fixes it.
            //we can send this to above removeRole function, but it goes through filters,unnecessary for this.
            this.userRoles.remove(ur);
            ur.getRole().getUserRoles().remove(ur);
            ur.setRole(null);
            ur.setUser(null);
        }

//        userRoles.forEach(ur-> ur.getRole().getUserRoles().remove(ur));
//        userRoles.clear();


    }
}
