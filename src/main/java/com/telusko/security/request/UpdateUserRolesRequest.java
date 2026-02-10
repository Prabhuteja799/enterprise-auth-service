package com.telusko.security.request;

import com.telusko.security.model.RoleName;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

public record UpdateUserRolesRequest(
        @NotNull
        Set<RoleName> roles
    ) { }
