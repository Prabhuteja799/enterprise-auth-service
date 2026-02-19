package com.telusko.security.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = {@Index(name = "idx_token" ,columnList ="tokenHash" ,unique = true) })
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Version
    Long version; //for Optimistic Locking when two threads tries to update the same row

    String username;

    @Column(nullable = false)
    String tokenHash;

    @Column(nullable = false,unique = true)
    String jti;

    Boolean revoked;

    Instant expiryDate;

    String deviceInfo;
    String ipAddress;


}
